package com.techcrunchers.capstoneprojectdbs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techcrunchers.capstoneprojectdbs.beans.Client;
import com.techcrunchers.capstoneprojectdbs.beans.Custodian;

import java.util.List;
@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    List<Client> findAll();

    List<Client> findAllByCustodian(Custodian custodian);

}
