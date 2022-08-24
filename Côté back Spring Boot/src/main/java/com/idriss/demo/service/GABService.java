package com.idriss.demo.service;

import java.util.List;

import com.idriss.demo.entities.Agence;
import com.idriss.demo.entities.GAB;
import com.idriss.demo.entities.Region;
import com.idriss.demo.entities.Trace;

public interface GABService {
	List<Trace> findGabByCode(String codeGAB);
}
