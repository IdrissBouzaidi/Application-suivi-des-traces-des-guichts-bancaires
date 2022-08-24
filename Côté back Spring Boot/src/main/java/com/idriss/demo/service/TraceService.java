package com.idriss.demo.service;

import java.util.List;

import com.idriss.demo.classes.AjoutTrace;
import com.idriss.demo.classes.Chemin;
import com.idriss.demo.entities.Agence;
import com.idriss.demo.entities.GAB;
import com.idriss.demo.entities.Region;
import com.idriss.demo.entities.Trace;

public interface TraceService {
	Chemin genererCheminTrace(int idTrace);
	Trace ajouterTrace(AjoutTrace ajout);
}
