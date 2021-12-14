package com.techcrunchers.capstoneprojectdbs.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techcrunchers.capstoneprojectdbs.beans.Custodian;
import com.techcrunchers.capstoneprojectdbs.beans.Instrument;
import com.techcrunchers.capstoneprojectdbs.services.CustodianService;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins="http://localhost:3000/")
public class CustodianController {

    @Autowired
    CustodianService custodianService;

    @GetMapping(value="/custodians")
    public ResponseEntity<List<Custodian>> getAllCustodians(){
        return new ResponseEntity<>(custodianService.findAll(), HttpStatus.OK);
    }
}
