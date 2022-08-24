package com.idriss.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idriss.demo.entities.GAB;

@Repository
public interface GABRepository extends JpaRepository<GAB, String> {

}
