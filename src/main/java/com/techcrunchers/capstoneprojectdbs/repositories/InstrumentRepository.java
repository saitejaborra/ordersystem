package com.techcrunchers.capstoneprojectdbs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techcrunchers.capstoneprojectdbs.beans.Instrument;

import java.util.List;
@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, String> {
    List<Instrument> findAll();
}
