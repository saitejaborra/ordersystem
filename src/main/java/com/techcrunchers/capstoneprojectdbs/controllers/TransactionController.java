package com.techcrunchers.capstoneprojectdbs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techcrunchers.capstoneprojectdbs.beans.OrderBook;
import com.techcrunchers.capstoneprojectdbs.models.ClientWiseStats;
import com.techcrunchers.capstoneprojectdbs.models.CustodianWiseStats;
import com.techcrunchers.capstoneprojectdbs.models.OrderBookRequest;
import com.techcrunchers.capstoneprojectdbs.repositories.OrderBookRepository;
import com.techcrunchers.capstoneprojectdbs.services.DashboardService;
import com.techcrunchers.capstoneprojectdbs.services.TransactionService;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000/")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @Autowired
    DashboardService dashboardService;

    @Autowired
    OrderBookRepository orderBookRepository;

    @GetMapping("orderBook")
    public List<OrderBook> getAllOrders() {
        return orderBookRepository.findAll();
    }

    @PostMapping("transaction")
    public ResponseEntity<OrderBook> transaction(@RequestBody OrderBookRequest orderBookRequest) {
        OrderBook orderBook = transactionService.processTransaction(orderBookRequest);
        return new ResponseEntity<>(orderBook, HttpStatus.OK);
    }

    @GetMapping("custodianWiseStats")
    public ResponseEntity<List<CustodianWiseStats>> getCustodianWiseStats() {
        List<CustodianWiseStats> stats = dashboardService.getCustodianWiseStats();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    @GetMapping("clientWiseStats")
    public ResponseEntity<List<ClientWiseStats>> getClientWiseStats() {
        List<ClientWiseStats> stats = dashboardService.getClientWiseStats();
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

}
