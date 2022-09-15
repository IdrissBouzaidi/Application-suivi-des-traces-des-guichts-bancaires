package com.idriss.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.idriss.demo.entities.Email;
import com.idriss.demo.mail.MailConfig;
import com.idriss.demo.methods.Methods;
import com.idriss.demo.repository.EmailRepository;

import Exceptions.FormatMailIncorrecteException;

@Service
public class EnvoieMailImpl implements EnvoieMail {
	
	public static String[][] resultatGABs;
	
    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    public EmailRepository emailRepository;
    

    @Override
	public void envoyerMail() throws MessagingException {
    	try {
    		if(MailConfig.statutEnvoyeurEmail == true) {
        		MimeMessage message = emailSender.createMimeMessage();
        		boolean multipart = true;
        		MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
        		String htmlMsg = genererFichierHTML();
        		message.setContent(htmlMsg, "text/html");
        	
            	String[] recepteursEmails = getRecepteursEmails();
            	helper.setTo(recepteursEmails);
            	helper.setSubject("Résultat de récupération des traces le jour " + Methods.getDateActuelle());
            	System.out.println("L'envoie du mail a commencé");
            	this.emailSender.send(message);
            	System.out.println("L'envoie du mail a été terminé");
    		}
    	}
    	catch(FormatMailIncorrecteException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    public String[] getRecepteursEmails() throws FormatMailIncorrecteException{
    	List<Email> emails = emailRepository.findAll();
    	List<String> listeRecepteursEmails = new ArrayList<>();
    	for(Email email : emails) {
    		String adresse = email.getAdresseMail();
    		if(Methods.validerFormatAdresseMail(adresse)) {
        		if(email.getStatut() == 0)
        			listeRecepteursEmails.add(adresse);
    		}
    		else
    			throw new FormatMailIncorrecteException("On n'a pas pu envoyer le mail à l'adresse " + adresse + " car elle ne présente pas une adresse mail");
    	}
    	int nbreEmailsRecepteurs = listeRecepteursEmails.size();
    	String[] tableauRecepteursEmails = new String[nbreEmailsRecepteurs];
    	for(int i = 0; i<nbreEmailsRecepteurs; i++)
    		tableauRecepteursEmails[i] = listeRecepteursEmails.get(i);
    	return tableauRecepteursEmails;
    }
    
    public String genererFichierHTML() {
    	String htmlMsg = "<h2>Voici le résultat de récupération des traces pour aujourd'hui</h2>";
    	List<List<String>> arrayResultatsGABs = convertirArrayResultatsGABs();
    	if(arrayResultatsGABs.get(0).size() == 0)
    		htmlMsg += "<p>Aucun trace n'a été récupéré</p>";
    	else if(arrayResultatsGABs.get(1).size() == 0 && arrayResultatsGABs.get(2).size() == 0 && arrayResultatsGABs.get(3).size() == 0) {
    		htmlMsg += "<h3>On a à récupérer les traces de tous les GABs, qui sont :</h3>";
    		for(String trace : arrayResultatsGABs.get(0))
    			
    			
    			
    			
    			htmlMsg += "<table><tbody><tr><td>" + trace + "</tr></tbody></table>";
    	}
    	else if(arrayResultatsGABs.get(0).size() != 0) {
    		htmlMsg += "<h3>Les GABs dont les traces ont été récupérés : </h3><table><tbody>";
    		for(String trace : arrayResultatsGABs.get(0))
    			htmlMsg += "<tr><td>" + trace + "</td></tr>";
    		htmlMsg += "</tbody></table><h3>Maintenant les traces qui n'ont pas été récupérés : </h3>";
    	}
    	
    	if(arrayResultatsGABs.get(1).size() != 0) {
    		htmlMsg += "<h4>Les GABs dont le nom de traces existe déjà dans le serveur central : </h4><table><tbody>";
    		for(String trace : arrayResultatsGABs.get(1))
    			htmlMsg += "<tr><td>" + trace + "</td></tr>";
    		htmlMsg += "</tbody></table>";
    	}
    	if(arrayResultatsGABs.get(2).size() != 0) {
    		htmlMsg += "<h4>Des GABs dont les traces n'ont pas été trouvés : </h4><table><tbody>";
    		for(String trace : arrayResultatsGABs.get(2))
    			htmlMsg += "<tr><td>" + trace + "</td></tr>";
    		htmlMsg += "</tbody></table>";
    	}
    	if(arrayResultatsGABs.get(3).size() != 0) {
    		htmlMsg += "<h4>Des GABs auxquels on n'a pas pu accéder : </h4><table><tbody>";
    		for(String trace : arrayResultatsGABs.get(3))
    			htmlMsg += "<tr><td>" + trace + "</td></tr>";
    		htmlMsg += "</tbody></table>";
    	}
    	return htmlMsg;
    }

    public List<List<String>> convertirArrayResultatsGABs(){
    	List<List<String>> listeResultatGABs = new ArrayList<>();
    	for(int i = 0; i<4; i++)
    		listeResultatGABs.add(new ArrayList<>());
    	for(int i = 0; i<resultatGABs.length; i++) {
    		switch(Integer.parseInt(resultatGABs[i][0])) {
    			case 1:
    				listeResultatGABs.get(0).add(resultatGABs[i][1]);
    				break;
    			case 0:
    				listeResultatGABs.get(1).add(resultatGABs[i][1]);
    				break;
    			case 2:
    				listeResultatGABs.get(2).add(resultatGABs[i][1]);
    				break;
    			case 16:
    				listeResultatGABs.get(3).add(resultatGABs[i][1]);
    				break;
    		}
    	}
    	return listeResultatGABs;
    }
    
}
