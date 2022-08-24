package com.idriss.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.idriss.demo.entities.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

}
