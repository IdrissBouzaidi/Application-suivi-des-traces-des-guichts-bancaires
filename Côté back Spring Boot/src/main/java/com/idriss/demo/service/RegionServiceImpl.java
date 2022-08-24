package com.idriss.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idriss.demo.entities.Agence;
import com.idriss.demo.entities.Region;
import com.idriss.demo.repository.RegionRepository;

@Service
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionRepository regionRepository;
	
	@Override
	public List<Region> findAll() {
		return regionRepository.findAll();
	}

	@Override
	public List<Agence> findRegionByLib(String  libRegion) {
		List<Region> regions = regionRepository.findAll();
		for(Region region : regions) {
			if(region.getLibReg().equals(libRegion))
				return region.getAgences();
		}
		return null;
	}

}
