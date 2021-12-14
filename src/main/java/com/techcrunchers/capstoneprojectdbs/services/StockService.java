package com.techcrunchers.capstoneprojectdbs.services;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techcrunchers.capstoneprojectdbs.beans.Client;
import com.techcrunchers.capstoneprojectdbs.beans.Instrument;
import com.techcrunchers.capstoneprojectdbs.beans.Stocks;
import com.techcrunchers.capstoneprojectdbs.exceptions.ValidationException;
import com.techcrunchers.capstoneprojectdbs.repositories.StocksRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StockService {
	@Autowired
    StocksRepository stocksRepository;

    /**
     * save stock
     *
     * @param client     client
     * @param instrument instrument
     * @param quantity   quantity (Can be Positive or Negative)
     * @return {@link Stocks} saves a particular stocks for user, if already exists adds quantity ( Quantity can be Negative i.e. reduce Stocks for that client )
     * @see Stocks
     */
    @Transactional
    public Stocks saveStock(Client client, Instrument instrument, Integer quantity) {
        Stocks getOrCreate = stocksRepository.findByClientAndInstrument(client, instrument).orElseGet(() -> {
            Stocks newStock = new Stocks();
            newStock.setStockId(UUID.randomUUID().toString());
            newStock.setClient(client);
            newStock.setInstrument(instrument);
            newStock.setQuantity(Math.abs(quantity));
            return newStock;
        });
        int finalQuantity = getOrCreate.getQuantity()+quantity; //0    0... -100
        System.out.println(finalQuantity); //0 -100
        if(finalQuantity<0){  
            throw new ValidationException("the seller doesn't have the required number of stocks to complete transaction");
        }
        getOrCreate.setQuantity(getOrCreate.getQuantity() + quantity); //0 -100
        return getOrCreate;
    }


    /**
     * get stock quantity
     *
     * @param client     client
     * @param instrument instrument
     * @return {@link Stocks} Returns a Stock Object if found else Create an empty stock with 0 quantity and returns
     * @see Stocks
     */
    Stocks getStockQuantity(Client client, Instrument instrument) {
        return stocksRepository.findByClientAndInstrument(client, instrument).orElseGet(() -> {
            Stocks newStock = new Stocks();
            newStock.setStockId(UUID.randomUUID().toString());
            newStock.setClient(client);
            newStock.setInstrument(instrument);
            newStock.setQuantity(0);
            return newStock;
        });
    }

    public List<Stocks> getAllStocks(){
        return stocksRepository.findAll();
    }

}

