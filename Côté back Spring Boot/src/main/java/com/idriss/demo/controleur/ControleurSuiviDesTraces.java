package com.idriss.demo.controleur;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idriss.demo.classes.AjoutTrace;
import com.idriss.demo.classes.Chemin;
import com.idriss.demo.entities.Agence;
import com.idriss.demo.entities.GAB;
import com.idriss.demo.entities.Region;
import com.idriss.demo.entities.Trace;
import com.idriss.demo.service.AgenceService;
import com.idriss.demo.service.GABService;
import com.idriss.demo.service.RegionService;
import com.idriss.demo.service.TraceService;
import com.idriss.demo.service.TraceServiceImpl.CopieException;

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
	
	@GetMapping("/regions")
	public ResponseEntity<List<Region>> get(){
		List<Region> regions = regionService.findAll();
		return new ResponseEntity<List<Region>>(regions, HttpStatus.OK);
	}
	

	@GetMapping("/regions/{id}")
	public ResponseEntity<List<Agence>> get(@PathVariable("id") String libRegion){
		List<Agence> agences = regionService.findRegionByLib(libRegion);
		return new ResponseEntity<List<Agence>>(agences, HttpStatus.OK);
	}

	@GetMapping("/agences/{id}")
	public ResponseEntity<List<GAB>> getGABs(@PathVariable("id") String codeBurpo){
		List<GAB> gabs = agenceService.findAgenceByCode(codeBurpo);
		return new ResponseEntity<List<GAB>>(gabs, HttpStatus.OK);
	}
	
	@GetMapping("/GABs/{id}")
	public ResponseEntity<List<Trace>> getTraces(@PathVariable("id") String codeGAB){
		List<Trace> traces = gabService.findGabByCode(codeGAB);
		return new ResponseEntity<List<Trace>>(traces, HttpStatus.OK);
	}
	

	@GetMapping("/traces/{id}")
	public ResponseEntity<Chemin> getCheminTrace(@PathVariable("id") int idTrace){
		Chemin chemin = traceService.genererCheminTrace(idTrace);
		return new ResponseEntity<Chemin>(chemin, HttpStatus.OK);
	}
	
	@PostMapping("/ajouterTrace")
	public ResponseEntity<Trace> ajouterTrace(@RequestBody AjoutTrace ajout) throws CopieException{
		Trace trace = traceService.ajouterTrace(ajout);
		return new ResponseEntity<Trace>(trace, HttpStatus.OK);
	}

}