package com.idriss.demo.controleur;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idriss.demo.classes.Agences_nbreLignes;
import com.idriss.demo.classes.GABs_nbreLignes;
import com.idriss.demo.classes.Message;
import com.idriss.demo.classes.Regions_nbreLignes;
import com.idriss.demo.classes.Traces_nbreLignes;
import com.idriss.demo.classes.Utilisateurs_nbreLignes;
import com.idriss.demo.entities.Utilisateur;
import com.idriss.demo.service.AgenceService;
import com.idriss.demo.service.GABService;
import com.idriss.demo.service.RegionService;
import com.idriss.demo.service.TraceService;
import com.idriss.demo.service.UtilisateurService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class ControleurSuiviDesTraces {

	@Autowired
	RegionService regionService;

	@Autowired
	AgenceService agenceService;

	@Autowired
	GABService gabService;
	
	@Autowired
	TraceService traceService;
	
	@Autowired
	UtilisateurService utilisateurService;
	
	

	@GetMapping("/authentification/{id}")
	public ResponseEntity<Utilisateur> verifierUtilisateur(@PathVariable("id") String data){
		Utilisateur user = utilisateurService.verifierUtilisateur(data);
		return new ResponseEntity<Utilisateur>(user, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/regions/{id}")
	public ResponseEntity<Regions_nbreLignes> getRegions(@PathVariable("id") String data){
		Regions_nbreLignes regions = regionService.findRegions(data);
		return new ResponseEntity<Regions_nbreLignes>(regions, HttpStatus.OK);
	}
	@GetMapping("/ajouterRegion/{id}")
	public ResponseEntity<Integer> ajouterRegion(@PathVariable("id") String data){
		Integer reponse = regionService.ajouterRegion(data);
		return new ResponseEntity<Integer>(reponse, HttpStatus.OK);
	}
	
	

	@GetMapping("/agences/{id}")
	public ResponseEntity<Agences_nbreLignes> getAgences(@PathVariable("id") String data){
		Agences_nbreLignes agences = agenceService.findAgences(data);
		return new ResponseEntity<Agences_nbreLignes>(agences, HttpStatus.OK);
	}
	@GetMapping("/ajouterAgence/{id}")
	public ResponseEntity<Integer> ajouterAgence(@PathVariable("id") String data){
		Integer reponse = agenceService.ajouterAgence(data);
		return new ResponseEntity<Integer>(reponse, HttpStatus.OK);
	}
	
	

	@GetMapping("/GABs/{id}")
	public ResponseEntity<GABs_nbreLignes> getGABs(@PathVariable("id") String data){
		GABs_nbreLignes GABs = gabService.findGABs(data);
		return new ResponseEntity<GABs_nbreLignes>(GABs, HttpStatus.OK);
	}
	@GetMapping("/ajouterGAB/{id}")
	public ResponseEntity<Integer> ajouterGAB(@PathVariable("id") String data){
		Integer reponse = gabService.ajouterGAB(data);
		return new ResponseEntity<Integer>(reponse, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/traces/{id}")
	public ResponseEntity<Traces_nbreLignes> getTraces(@PathVariable("id") String data){
		Traces_nbreLignes traces = traceService.findTraces(data);
		return new ResponseEntity<Traces_nbreLignes>(traces, HttpStatus.OK);
	}
	@GetMapping("/recupererTraces/{id}")
	public ResponseEntity<Message> recupererTrace(@PathVariable("id") String data) throws ParseException, IOException, InterruptedException{
		Message message = new Message(traceService.recupererTraces(data, 1));
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
	
	

	@GetMapping("/profils/{id}")
	public ResponseEntity<Utilisateurs_nbreLignes> getUtilisateurs(@PathVariable("id") String data){
		Utilisateurs_nbreLignes utilisateurs = utilisateurService.findUtilisateurs(data);
		return new ResponseEntity<Utilisateurs_nbreLignes>(utilisateurs, HttpStatus.OK);
	}
	@GetMapping("/ajouterProfil/{id}")
	public ResponseEntity<Integer> ajouterUtilisateur(@PathVariable("id") String data){
		int reponse = utilisateurService.ajouterUtilisateur(data);
		return new ResponseEntity<Integer>(reponse, HttpStatus.OK);
	}
	


}