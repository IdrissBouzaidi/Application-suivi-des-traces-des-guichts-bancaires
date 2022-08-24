package com.idriss.demo.service;

import java.util.List;

import com.idriss.demo.entities.Agence;
import com.idriss.demo.entities.GAB;
import com.idriss.demo.entities.Region;

public interface AgenceService {
	List<GAB> findAgenceByCode(String codeBurpo);
}
