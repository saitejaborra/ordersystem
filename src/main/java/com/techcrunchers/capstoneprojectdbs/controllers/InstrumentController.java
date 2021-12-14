package com.techcrunchers.capstoneprojectdbs.controllers;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.techcrunchers.capstoneprojectdbs.beans.Client;
import com.techcrunchers.capstoneprojectdbs.beans.Instrument;
import com.techcrunchers.capstoneprojectdbs.repositories.InstrumentRepository;
import com.techcrunchers.capstoneprojectdbs.services.InstrumentService;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins="http://localhost:3000/")
@RequestMapping(value="/instruments")
public class InstrumentController {
	@Autowired
    InstrumentService instrumentService;
    
    @GetMapping(value="/getinstrument/{id}")
    public ResponseEntity<Instrument> getClient(@PathVariable(name="id") String id) {
        Instrument instrument = instrumentService.findByInstrumentId(id);
        return new ResponseEntity<>(instrument, HttpStatus.OK);
    }

    @GetMapping(value="/getallinstruments")
    public ResponseEntity<List<Instrument>> getAllInstruments(){
        return new ResponseEntity<>(instrumentService.findAll(),HttpStatus.OK);
    }

}
