package com.idriss.demo.mail;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.idriss.demo.entities.Email;
import com.idriss.demo.methods.Methods;
import com.idriss.demo.repository.EmailRepository;

import Exceptions.FormatMailIncorrecteException;
import Exceptions.PlusieursEnvoyeursException;

@Configuration
public class MailConfig {

	public static boolean statutEnvoyeurEmail = true;

	@Autowired
	public EmailRepository emailRepository;
	
    @Bean
    public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		try {
			String[] mailEnvoyeur = getAdresseMailEnvoyeur();
			mailSender.setHost("smtp.gmail.com");
			mailSender.setPort(587);
			mailSender.setUsername(mailEnvoyeur[0]);
			mailSender.setPassword(mailEnvoyeur[1]);
			Properties props = mailSender.getJavaMailProperties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.debug", "true");
		}
		catch(Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
        return mailSender;
    }

	String[] getAdresseMailEnvoyeur() throws FormatMailIncorrecteException, PlusieursEnvoyeursException {
		List<Email> emails = emailRepository.findAll();
		int nbreEmailsEnvoyeurs = 0;
		String adresseEnvoyeur = null;
		String motDePasseEnvoyeur = null;
		for(Email email : emails) {
			String adresse = email.getAdresseMail();
			if(Methods.validerFormatAdresseMail(adresse)) {
				if(email.getStatut() == 1) {
					adresseEnvoyeur = adresse;
					motDePasseEnvoyeur = email.getPassword();
					nbreEmailsEnvoyeurs++;
				}
			}
			else {
				statutEnvoyeurEmail = false;
				throw new FormatMailIncorrecteException("On n'a pas pu envoyer le mail car l'adresse de l'émetteur qui vaut " + adresse + " ne semble pas à une adresse mail");
			}
		}
		if(nbreEmailsEnvoyeurs != 1) {
			statutEnvoyeurEmail = false;
			throw new PlusieursEnvoyeursException("On n'a pas pu envoyer le mail car le nombre de mails qui jouent le rôle de l'envoyeur vaut " + nbreEmailsEnvoyeurs);
		}
		return new String[] {adresseEnvoyeur, motDePasseEnvoyeur};
	}

}