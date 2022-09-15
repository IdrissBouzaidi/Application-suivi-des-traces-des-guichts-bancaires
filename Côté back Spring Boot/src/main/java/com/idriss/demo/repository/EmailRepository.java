package com.idriss.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idriss.demo.entities.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, String> {

}
