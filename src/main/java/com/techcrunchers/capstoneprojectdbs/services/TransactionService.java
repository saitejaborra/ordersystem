package com.techcrunchers.capstoneprojectdbs.services;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.techcrunchers.capstoneprojectdbs.beans.Client;
import com.techcrunchers.capstoneprojectdbs.beans.Instrument;
import com.techcrunchers.capstoneprojectdbs.beans.OrderBook;
import com.techcrunchers.capstoneprojectdbs.beans.Stocks;
import com.techcrunchers.capstoneprojectdbs.exceptions.ResourceNotFoundException;
import com.techcrunchers.capstoneprojectdbs.exceptions.ValidationException;
import com.techcrunchers.capstoneprojectdbs.models.CustomResponse;
import com.techcrunchers.capstoneprojectdbs.models.OrderBookRequest;
import com.techcrunchers.capstoneprojectdbs.models.OrderDirection;
import com.techcrunchers.capstoneprojectdbs.models.OrderStatus;
import com.techcrunchers.capstoneprojectdbs.repositories.*;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService {
	@Autowired
    ClientRepository clientRepository;
	@Autowired
    CustodianRepository custodianRepository;
	@Autowired
    InstrumentRepository instrumentRepository;
	@Autowired
    StocksRepository stocksRepository;
	@Autowired
    OrderBookRepository orderBookRepository;
	@Autowired
    StockService stockService;
	@Autowired
    ClientService clientService;

    public OrderBook processTransaction(OrderBookRequest orderBookRequest) {
        if (orderBookRequest.getOrderDirection().equals(OrderDirection.BUY)) {
            return this.buyOrder(orderBookRequest);
        }
        if (orderBookRequest.getOrderDirection().equals(OrderDirection.SELL)) {
            return this.sellOrder(orderBookRequest);
        }
        throw new ValidationException("Invalid request");
    }

    private OrderBook buyOrder(OrderBookRequest orderReqOfBuyer) {

        Instrument buyerInstrument = instrumentRepository.findById(orderReqOfBuyer.getInstrumentId())
                .orElseThrow(() -> new ResourceNotFoundException("Instrument Not Found"));
        Client buyer = clientRepository.findById(orderReqOfBuyer.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not Found"));

        //first create the OrderBook instance of the seller request(to be saved to the database later)
        OrderBook buyerOrderBookInstance = new OrderBook(
                UUID.randomUUID().toString(),
                buyer,
                buyerInstrument,
                orderReqOfBuyer.getPrice(),
                orderReqOfBuyer.getQuantity(),
                orderReqOfBuyer.getQuantity(),
                OrderStatus.PROCESSING,
                OrderDirection.BUY,
                false,
                new Date()
        );

        System.out.println("uuid:"+buyerOrderBookInstance.getOrderId());

        List<OrderBook> sellOrdersWithSameInstrument = orderBookRepository
                .findAllByOrderDirectionAndOrderStatus(OrderDirection.SELL, OrderStatus.PROCESSING).stream()
                .filter(record -> {
                    return record.getInstrument().getInstrumentId().equals(buyerInstrument.getInstrumentId());
                })
                .collect(Collectors.toList());

       
        if (sellOrdersWithSameInstrument.isEmpty()) {
            return orderBookRepository.save(buyerOrderBookInstance);
        }

        double buyerStocksWorth = orderReqOfBuyer.getQuantity() * orderReqOfBuyer.getPrice();


        List<OrderBook> perfectSellers = sellOrdersWithSameInstrument
                .stream()
                .filter(orders -> orders.getClient().getTransactionLimit() >= buyerStocksWorth)
                .filter(item -> item.getPrice().equals(orderReqOfBuyer.getPrice())).collect(Collectors.toList());

        if (perfectSellers.isEmpty()) {
            return orderBookRepository.save(buyerOrderBookInstance);
        }


        Optional<OrderBook> minSellerOrderReqOpt = perfectSellers.stream()
                .filter(order -> order.getQuantity() >= orderReqOfBuyer.getQuantity())
                .min(Comparator.comparing(OrderBook::getTimeStamp));

        if (minSellerOrderReqOpt.isPresent()) {
            OrderBook minSellerOrderBookInstance = minSellerOrderReqOpt.get();
            // Perfect Buy Sell Trade
            Client seller = minSellerOrderBookInstance.getClient();

            exchangeStocks(buyer, seller, buyerOrderBookInstance,minSellerOrderBookInstance,true);
        } else {
            
            List<OrderBook> sellers = perfectSellers.stream()
                    .sorted(Comparator.comparing(OrderBook::getTimeStamp))
                    .filter(order -> order.getQuantity() < orderReqOfBuyer.getQuantity())
                    .collect(Collectors.toList());

                        int buyerQuantity = orderReqOfBuyer.getQuantity();

            for (OrderBook sellerOrderBookInstance : sellers) {
                Client seller = sellerOrderBookInstance.getClient();

                if (buyerQuantity <= sellerOrderBookInstance.getQuantity()) {
                    exchangeStocks(buyer, seller, buyerOrderBookInstance,sellerOrderBookInstance,true);
                    break;
                } else {
                    buyerQuantity = buyerQuantity - sellerOrderBookInstance.getQuantity();
                    exchangeStocks(buyer, seller, buyerOrderBookInstance,sellerOrderBookInstance,false);
                }
            }
        }

        return orderBookRepository.save(buyerOrderBookInstance);

    }

    private OrderBook sellOrder(OrderBookRequest orderReqOfSeller) {

        Instrument sellerInstrument = instrumentRepository.findById(orderReqOfSeller.getInstrumentId())
                .orElseThrow(() -> new ResourceNotFoundException("Instrument Not Found"));
        Client seller = clientRepository.findById(orderReqOfSeller.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not Found"));


        //check whether the seller has the specified stock and if he/she has the atleast the specified number of stocks
//        Stocks stockAboutToSell = stocksRepository.findByClientAndInstrument(seller,sellerInstrument).orElseThrow(() ->
//                new ValidationException("seems like the seller doesn't have the specified stocks"));
//        if(stockAboutToSell.getQuantity() < orderReqOfSeller.getQuantity()){
//            throw new ValidationException("the seller doesn't have the specified number of stocks");
//        }

 
        OrderBook sellerOrderBookInstance = new OrderBook(UUID.randomUUID().toString(),
                seller,
                sellerInstrument,
                orderReqOfSeller.getPrice(),
                orderReqOfSeller.getQuantity(),
                orderReqOfSeller.getQuantity(),
                OrderStatus.PROCESSING,
                OrderDirection.SELL,
                false,
                new Date()
        );

        List<OrderBook> buyOrdersWithSameInstrument = orderBookRepository
                .findAllByOrderDirectionAndOrderStatus(OrderDirection.BUY, OrderStatus.PROCESSING).stream()
                .filter(record -> {
                    return record.getInstrument().getInstrumentId().equals(sellerInstrument.getInstrumentId());
                })
                .collect(Collectors.toList());

        if (buyOrdersWithSameInstrument.isEmpty()) {
            return orderBookRepository.save(sellerOrderBookInstance);
        }

        double sellerStocksWorth = orderReqOfSeller.getQuantity() * orderReqOfSeller.getPrice();

       
        List<OrderBook> perfectBuyers = buyOrdersWithSameInstrument
                .stream()
                .filter(orders -> orders.getClient().getTransactionLimit() >= sellerStocksWorth)
                .filter(item -> item.getPrice().equals(orderReqOfSeller.getPrice())).collect(Collectors.toList());

        if (perfectBuyers.isEmpty()) {
            return orderBookRepository.save(sellerOrderBookInstance);
        }

                Optional<OrderBook> minBuyerOrderReqOpt = perfectBuyers.stream()
                .filter(order -> order.getQuantity() >= orderReqOfSeller.getQuantity())
                .min(Comparator.comparing(OrderBook::getTimeStamp));

        if (minBuyerOrderReqOpt.isPresent()) {
            OrderBook minBuyerOrderBookInstance = minBuyerOrderReqOpt.get();
            // Perfect Buy Sell Trade
            Client buyer = minBuyerOrderBookInstance.getClient();

            exchangeStocks(buyer, seller, minBuyerOrderBookInstance,sellerOrderBookInstance,false);
        } else {
            
                        List<OrderBook> buyers = perfectBuyers.stream()
                    .sorted(Comparator.comparing(OrderBook::getTimeStamp))
                    .filter(order -> order.getQuantity() < orderReqOfSeller.getQuantity())
                    .collect(Collectors.toList());

                        int sellerQuantity = orderReqOfSeller.getQuantity();

            for (OrderBook buyerOrderBookInstance : buyers) {
                Client buyer = buyerOrderBookInstance.getClient();

                if (sellerQuantity <= buyerOrderBookInstance.getQuantity()) {
                    exchangeStocks(buyer, seller, buyerOrderBookInstance,sellerOrderBookInstance,false);
                    break;
                } else {
                    sellerQuantity = sellerQuantity - buyerOrderBookInstance.getQuantity();
                    exchangeStocks(buyer, seller, buyerOrderBookInstance,sellerOrderBookInstance,true);
                }
            }
        }

                return orderBookRepository.save(sellerOrderBookInstance);
    }

    @Transactional
    void exchangeStocks(Client buyer, Client seller, OrderBook buyerOrderBookInstance,OrderBook sellerOrderBookInstance,boolean considerBuyersOrderReq) {
        OrderBook orderReq;
        if(considerBuyersOrderReq){ 
            orderReq = buyerOrderBookInstance;
        }
        else{
            orderReq = sellerOrderBookInstance;
        }

        stockService.saveStock(buyer, orderReq.getInstrument(), orderReq.getQuantity()); 
        stockService.saveStock(seller, orderReq.getInstrument(), -1 * orderReq.getQuantity());

     
        buyer.setTransactionLimit(buyer.getTransactionLimit() - orderReq.getQuantity() * orderReq.getPrice());
        seller.setTransactionLimit(seller.getTransactionLimit() - orderReq.getQuantity() * orderReq.getPrice());
        clientRepository.save(buyer);
        clientRepository.save(seller);

                int quantityToBeSubtracted = orderReq.getQuantity(); 
        buyerOrderBookInstance.setQuantity(buyerOrderBookInstance.getQuantity() - quantityToBeSubtracted); 
        sellerOrderBookInstance.setQuantity(sellerOrderBookInstance.getQuantity() - quantityToBeSubtracted); 
        if(buyerOrderBookInstance.getQuantity().equals(0)){
            buyerOrderBookInstance.setOrderStatus(OrderStatus.COMPLETED);
        }
        if(sellerOrderBookInstance.getQuantity().equals(0)){
            sellerOrderBookInstance.setOrderStatus(OrderStatus.COMPLETED);
        }
        orderBookRepository.save(buyerOrderBookInstance);
        orderBookRepository.save(sellerOrderBookInstance);

    }


}
