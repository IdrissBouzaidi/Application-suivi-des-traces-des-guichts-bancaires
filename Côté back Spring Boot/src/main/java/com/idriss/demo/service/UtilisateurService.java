package com.idriss.demo.service;

import com.idriss.demo.classes.Utilisateurs_nbreLignes;
import com.idriss.demo.entities.Utilisateur;


public interface UtilisateurService {
	public static final int EMAIL_INVALIDE = 0;
	public static final int MOT_DE_PASSE_INVALIDES = 1;
	public static final int EMAIL_DEJA_UTILISE = 1;
	public static final int NOUVEAU_EMAIL = 0;
	Utilisateur verifierUtilisateur(String data) ;
	Utilisateurs_nbreLignes findUtilisateurs(String data);
	int ajouterUtilisateur(String data);
}
