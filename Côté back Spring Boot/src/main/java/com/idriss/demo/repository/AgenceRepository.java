package com.idriss.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idriss.demo.entities.Agence;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, String> {

}
