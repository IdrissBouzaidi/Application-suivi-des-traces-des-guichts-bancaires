package com.idriss.demo.service;

import java.util.List;

import com.idriss.demo.classes.Regions_nbreLignes;
import com.idriss.demo.entities.Region;

public interface RegionService {
	List<Region> findAll();

	Regions_nbreLignes findRegions(String data);
	int ajouterRegion(String data);
}
