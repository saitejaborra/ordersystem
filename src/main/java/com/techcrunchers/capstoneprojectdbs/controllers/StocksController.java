package com.techcrunchers.capstoneprojectdbs.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techcrunchers.capstoneprojectdbs.beans.Instrument;
import com.techcrunchers.capstoneprojectdbs.beans.Stocks;
import com.techcrunchers.capstoneprojectdbs.services.StockService;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins="http://localhost:3000/")
public class StocksController {

    @Autowired
    StockService stockService;

    @GetMapping(value="stocks")
    public ResponseEntity<List<Stocks>> getAllInstruments(){
        return new ResponseEntity<>(stockService.getAllStocks(), HttpStatus.OK);
    }

}
