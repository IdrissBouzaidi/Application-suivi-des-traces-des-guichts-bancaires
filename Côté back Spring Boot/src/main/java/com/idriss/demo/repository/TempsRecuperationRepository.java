package com.idriss.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idriss.demo.entities.TempsRecuperation;

@Repository
public interface TempsRecuperationRepository extends JpaRepository<TempsRecuperation, Integer> {

}
