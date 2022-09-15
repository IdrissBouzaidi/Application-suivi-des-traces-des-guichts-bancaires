package com.idriss.demo.service;

import java.io.IOException;
import java.text.ParseException;

import com.idriss.demo.classes.Traces_nbreLignes;

public interface TraceService {
	public static final int RECUOERATION_TRACE_AUTOMATIQUE = 0;
	public static final int RECUOERATION_TRACE_VIA_CLIENT = 1;
	
	String[][] recupererTraces(String ajout, int statut) throws ParseException, IOException, InterruptedException;
	Traces_nbreLignes findTraces(String data);
	
}
