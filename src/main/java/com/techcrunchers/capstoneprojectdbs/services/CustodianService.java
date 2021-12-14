package com.techcrunchers.capstoneprojectdbs.services;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techcrunchers.capstoneprojectdbs.beans.Custodian;
import com.techcrunchers.capstoneprojectdbs.repositories.CustodianRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CustodianService {
	@Autowired
    CustodianRepository custodianRepository;

    public List<Custodian> findAll(){
        return custodianRepository.findAll();
    }
}
