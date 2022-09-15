package com.idriss.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idriss.demo.classes.Tri;
import com.idriss.demo.classes.Utilisateurs_nbreLignes;
import com.idriss.demo.entities.Utilisateur;
import com.idriss.demo.repository.UtilisateurRepository;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	@Autowired
	UtilisateurRepository utilisateurRepository;

	@Override
	public Utilisateur verifierUtilisateur(String data) {
		String email = null;
		String motDePasse = null;
		for(int i = 0; i<data.length(); i++) {
			if(data.charAt(i) == '&') {
				email = data.substring(0, i);
				motDePasse = data.substring(i+1);
			}
		}
		List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
		for(Utilisateur user : utilisateurs) {
			if(user.getEmail().equals(email)) {
				if(user.getMotDePasse().equals(motDePasse))
					return user;
				else
					return new Utilisateur(MOT_DE_PASSE_INVALIDES);
			}
		}
		return new Utilisateur(EMAIL_INVALIDE);
	}

	@Override
	public Utilisateurs_nbreLignes findUtilisateurs(String data) {
		StringBuffer _data = new StringBuffer(data);
		String profil = null;
		int lignes = -1;
		int lignesTotales = 0;
		String recherche = "";
		String attributATrier = null;
		int l = 0;
		for(int i = 0; i<_data.length(); i++) {
			if(_data.charAt(i) == '&') {
				if(l == 0) {
					profil = _data.substring(0, i);
					l = i+1;
				}
				else if(lignes == -1) {
					lignes = Integer.parseInt(_data.substring(l, i));
					l = i+1;
				}
				else {
					recherche = _data.substring(l, i);
					attributATrier = _data.substring(i+1);
				}
			}
		}
		List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
		List<Utilisateur> toutUtilisateur = new ArrayList<>();
		if(profil.equals("")) {
			toutUtilisateur = utilisateurs;
		}
		else {
			for(Utilisateur utilisateur: utilisateurs) {
				int p = Integer.parseInt(profil);
				if(utilisateur.getProfil() == p)
					toutUtilisateur.add(utilisateur);
			}
		}
		toutUtilisateur = recupererElementsChercheables(toutUtilisateur, recherche);
		lignesTotales = toutUtilisateur.size();
		if(!attributATrier.equals("")) {
			boolean inverser = false;
			if(attributATrier.substring(0, 4).equals("DESC")) {
				inverser = true;
				attributATrier = attributATrier.substring(4);
			}
			String[][] tableauTrie = elementsTries(toutUtilisateur, attributATrier);
			Tri.triFusion(tableauTrie);
			toutUtilisateur = trierElements(toutUtilisateur, tableauTrie);
			if(inverser == true) {
				toutUtilisateur = inverser(toutUtilisateur);
			}
		}
		toutUtilisateur = toutUtilisateur.subList(Math.min(lignes, toutUtilisateur.size()), Math.min(lignes + 100, toutUtilisateur.size()));
		return new Utilisateurs_nbreLignes(toutUtilisateur, lignesTotales);
	}

	public static List<Utilisateur> inverser(List<Utilisateur> listeElements){
		int nbreLignes = listeElements.size();
		List<Utilisateur> elementsInverses = new ArrayList<>();
		for(int i = 0; i<nbreLignes; i++)
			elementsInverses.add(listeElements.get(nbreLignes - i - 1));
		return elementsInverses;
	}
	

	public static String[][] elementsTries(List<Utilisateur> utilisateurs, String attribut){
		int nbreLignes = utilisateurs.size();
		String[][] tableauTrie = new String[nbreLignes][2];
		for(int i = 0; i<nbreLignes; i++) {
			Integer _i = i;
			tableauTrie[i][0] = _i.toString();
			if(attribut.equals("codeU")) {
				Integer codeU = utilisateurs.get(i).getCodeU();
				tableauTrie[i][1] = codeU.toString();
			}
			if(attribut.equals("nom")) 
				tableauTrie[i][1] = utilisateurs.get(i).getNom();
			if(attribut.equals("prenom")) 
				tableauTrie[i][1] = utilisateurs.get(i).getPrenom();
			if(attribut.equals("email")) 
				tableauTrie[i][1] = utilisateurs.get(i).getEmail();
			if(attribut.equals("motDePasse")) 
				tableauTrie[i][1] = utilisateurs.get(i).getMotDePasse();
			if(attribut.equals("profil")) {
				Integer profil = utilisateurs.get(i).getProfil();
				tableauTrie[i][1] = profil.toString();
			}
		}
		return tableauTrie;
	}
	
	public static List<Utilisateur> trierElements(List<Utilisateur> utilisateurs, String[][] tableauTrie){
		int nbreLignes = utilisateurs.size();
		ArrayList<Utilisateur> utilisateursTries = new ArrayList<>();
		for(int i = 0; i<nbreLignes; i++) {//Pour remplir la liste par 'nbreLignes' valeur qu'on va changer par la suite.
			utilisateursTries.add(null);
		}
		for(int i = 0; i<nbreLignes; i++) {
			int index = Integer.parseInt(tableauTrie[i][0]);
			utilisateursTries.set(i, utilisateurs.get(index));
		}
		return utilisateursTries;
	}
	
	
	static List<Utilisateur> recupererElementsChercheables(List<Utilisateur> utilisateurs, String nomRecherche) {
		List<Utilisateur> utilisateursRecherches = new ArrayList<>();
		for(Utilisateur utilisateur: utilisateurs) {
			Integer codeU = utilisateur.getCodeU();
			Integer profil = utilisateur.getProfil();
			if(codeU.toString().contains(nomRecherche))
				utilisateursRecherches.add(utilisateur);
			else if(utilisateur.getNom().contains(nomRecherche))
				utilisateursRecherches.add(utilisateur);
			else if(utilisateur.getPrenom().contains(nomRecherche))
				utilisateursRecherches.add(utilisateur);
			else if(utilisateur.getEmail().contains(nomRecherche))
				utilisateursRecherches.add(utilisateur);
			else if(utilisateur.getMotDePasse().contains(nomRecherche))
				utilisateursRecherches.add(utilisateur);
			else if(profil.toString().contains(nomRecherche))
				utilisateursRecherches.add(utilisateur);
		}
		return utilisateursRecherches;
	}

	@Override
	public int ajouterUtilisateur(String data) {
		String codeU = null;
		String nom = null;
		String prenom = null;
		String email = null;
		String motDePasse = null;
		int profil = 0;
		String etatModification = null;
		
		StringBuffer dataS = new StringBuffer(data);
		int position = 0;
		for(int i = 0; i<data.length(); i++) {
			if(dataS.charAt(i) == '&') {
				if(codeU == null) {
					codeU = dataS.substring(position, i);
					position = i+1;
				}
				else if(nom == null) {
					nom = dataS.substring(position, i);
					position = i+1;
				}
				else if(prenom == null) {
					prenom = dataS.substring(position, i);
					position = i+1;
				}
				else if(email == null) {
					email = dataS.substring(position, i);
					position = i+1;
				}
				else if(motDePasse == null) {
					motDePasse = dataS.substring(position, i);
					position = i+1;
				}
				else if(etatModification == null) {
					profil = Integer.parseInt(dataS.substring(position, i));
					etatModification = dataS.substring(i+1);
				}
			}
		}
		if(etatModification.equals("false")) {
			List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
			for(int i = 0; i<utilisateurs.size(); i++) {
				if(utilisateurs.get(i).getEmail().equals(email)) {
					return EMAIL_DEJA_UTILISE;
				}
			}
		}
		Utilisateur utilisateur;
		if(!codeU.equals("")) {
			utilisateur = utilisateurRepository.findById(Integer.parseInt(codeU)).get();
			utilisateur.modifier(nom, prenom, email, motDePasse, profil);
		}
		else {
			utilisateur = new Utilisateur(nom, prenom, email, motDePasse, profil);
		}
		utilisateurRepository.save(utilisateur);
		return NOUVEAU_EMAIL;
	}
}
