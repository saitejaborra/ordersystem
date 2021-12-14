package com.techcrunchers.capstoneprojectdbs.controllers;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.techcrunchers.capstoneprojectdbs.beans.Client;
import com.techcrunchers.capstoneprojectdbs.exceptions.ResourceNotFoundException;
import com.techcrunchers.capstoneprojectdbs.services.ClientService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/client")
@CrossOrigin(origins="http://localhost:3000/")
public class ClientsController {
	@Autowired
    ClientService clientService;
    @GetMapping(value = "/getclient/{id}")
    public ResponseEntity<Client> getClient(@PathVariable(name="id") String id) {
    	System.out.println(id);
//      Client client = clientService.findByClientId(id);
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("message", "success");
    	return new ResponseEntity<>(clientService.findByClientId(id), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/allclients")
    public ResponseEntity<List<Client>> getAllClients(){
        return new ResponseEntity<>(clientService.findAll(),HttpStatus.OK);
    }

    @GetMapping(value = "clientsByCustodian/{custodianId}")
    public ResponseEntity<List<Client>> getAllClientsByCustodian(@PathVariable("custodianId") String custodianId){
        List<Client> clients =  clientService.findAllByCustodian(custodianId);
        return new ResponseEntity<>(clients,HttpStatus.OK);
    }

    @GetMapping(value = "/test")
    public ResponseEntity<Object> testMethod() {
        throw new ResourceNotFoundException("something went wrong");
    }
}
