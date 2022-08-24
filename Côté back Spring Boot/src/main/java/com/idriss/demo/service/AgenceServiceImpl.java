package com.idriss.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idriss.demo.entities.Agence;
import com.idriss.demo.entities.GAB;
import com.idriss.demo.entities.Region;
import com.idriss.demo.repository.AgenceRepository;
import com.idriss.demo.repository.RegionRepository;

@Service
public class AgenceServiceImpl implements AgenceService {

	@Autowired
	private AgenceRepository agenceRepository;
	
	@Override
	public List<GAB> findAgenceByCode(String codeBurpo) {
		Agence agence = agenceRepository.findById(codeBurpo).get();
		return agence.getGABs();
	}

	

}
