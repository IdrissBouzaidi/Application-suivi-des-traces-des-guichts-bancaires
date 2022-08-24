package com.idriss.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idriss.demo.entities.Agence;
import com.idriss.demo.entities.GAB;
import com.idriss.demo.entities.Region;
import com.idriss.demo.entities.Trace;
import com.idriss.demo.repository.AgenceRepository;
import com.idriss.demo.repository.GABRepository;
import com.idriss.demo.repository.RegionRepository;

@Service
public class GABServiceImpl implements GABService {

	@Autowired
	private GABRepository gabRepository;

	@Override
	public List<Trace> findGabByCode(String codeGAB) {
		GAB gab = gabRepository.findById(codeGAB).get();
		return gab.getTraces();
	}
	
}
