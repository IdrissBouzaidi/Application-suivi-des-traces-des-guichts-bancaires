package com.idriss.demo.methods;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class Methods {
    public static boolean validerFormatAdresseMail(String adresseMail) {
    	int nbreArobase = 0;
    	for(int i = 0; i<adresseMail.length(); i++) {
    		if(adresseMail.charAt(i) == '@')
    			nbreArobase++;
    	}
    	if(nbreArobase == 1)
    		return true;
    	else
    		return false;
    }
    
	public static int executerCommande(String command) throws IOException, InterruptedException {
		String[] commande = {"cmd.exe","/c", command};
		System.out.print("Exécution de la commande : " + command);
		ProcessBuilder pb = new ProcessBuilder(commande);
		pb.redirectError(Redirect.INHERIT);
		pb.redirectOutput(Redirect.INHERIT);
		Process p = pb.start();
		int statutCommande = p.waitFor();
		System.out.println("Commande exécutée\n\n");
		return statutCommande;
	}

	public static String changerCaracteres(String ch, char[] chars1, char[] chars2){
		StringBuffer chB = new StringBuffer(ch);
		for(int i = 0; i<ch.length(); i++) {
			for(int j = 0; j<chars1.length; j++) {
				if(ch.charAt(i) == chars1[j])
					chB.setCharAt(i, chars2[j]);
			}
		}
		return chB.toString();
	}

	public static String parseDateString(Date date) {
		@SuppressWarnings("deprecation")
		LocalDate localDate = LocalDate.of(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
		return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
	}
	
	public static java.sql.Date parseStringToDate(String dateS) throws ParseException {
	    java.sql.Date date = java.sql.Date.valueOf(dateS);
	    return date;
	}
	
	public static String getDateActuelle() {
		Date dateActuelle = new Date();
		return parseDateString(dateActuelle);
	}
}
