package com.idriss.demo.service;

import java.util.List;

import com.idriss.demo.entities.Agence;
import com.idriss.demo.entities.Region;

public interface RegionService {
	List<Region> findAll();
	List<Agence> findRegionByLib(String libRegion);
}
