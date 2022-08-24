package com.idriss.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idriss.demo.entities.Trace;

@Repository
public interface TraceRepository extends JpaRepository<Trace, Long> {

}
