package com.techcrunchers.capstoneprojectdbs.services;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techcrunchers.capstoneprojectdbs.beans.Client;
import com.techcrunchers.capstoneprojectdbs.beans.Custodian;
import com.techcrunchers.capstoneprojectdbs.repositories.ClientRepository;
import com.techcrunchers.capstoneprojectdbs.repositories.CustodianRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {
	@Autowired
    ClientRepository clientRepository;
	@Autowired
    CustodianRepository custodianRepository;

    public Client findByClientId(String clientId) {
    	Optional<Client> clientOptional = clientRepository.findById(clientId);
		 if(clientOptional.isPresent())
			return clientOptional.get();
		 return null;
    }

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public List<Client> findAllByCustodian(String custodianId){
        Custodian custodian = custodianRepository.findById(custodianId).get();
        return clientRepository.findAllByCustodian(custodian);
    }
}
