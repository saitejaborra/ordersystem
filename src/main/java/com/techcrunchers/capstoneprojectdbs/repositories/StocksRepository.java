package com.techcrunchers.capstoneprojectdbs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techcrunchers.capstoneprojectdbs.beans.Client;
import com.techcrunchers.capstoneprojectdbs.beans.Instrument;
import com.techcrunchers.capstoneprojectdbs.beans.Stocks;

import java.util.List;
import java.util.Optional;
@Repository
public interface StocksRepository extends JpaRepository<Stocks, String> {
    List<Stocks> findAllByClient(Client id);
    Optional<Stocks> findByClientAndInstrument(Client client, Instrument instrument);
    List<Stocks> findAll();
}
