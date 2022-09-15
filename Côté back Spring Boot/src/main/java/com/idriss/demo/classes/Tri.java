package com.idriss.demo.classes;

public final class Tri {

	public static void triFusion(String tableau[][]){
		int longueur=tableau.length;
		if (longueur>0)
			triFusion(tableau,0,longueur-1);
	}
	private static void triFusion(String tableau[][],int deb,int fin) {
		if (deb!=fin) {
			int milieu=(fin+deb)/2;
			triFusion(tableau,deb,milieu);
			triFusion(tableau,milieu+1,fin);
			fusion(tableau,deb,milieu,fin);
		}
	}

	private static void fusion(String tableau[][],int deb1,int fin1,int fin2) {
		int deb2=fin1+1;

	    //on recopie les éléments du début du tableau
		String table1[][]=new String[fin1-deb1+1][2];
		for(int i=deb1;i<=fin1;i++)
			table1[i-deb1]=tableau[i];
		int compt1 = deb1;
		int compt2 = deb2;
		
		for(int i=deb1;i<=fin2;i++) {
			if (compt1==deb2)//c'est que tous les éléments du premier tableau ont été utilisés
				break; //tous les éléments ont donc été classés
			else if (compt2==(fin2+1)) {//c'est que tous les éléments du second tableau ont été utilisés
				tableau[i]=table1[compt1-deb1]; //on ajoute les éléments restants du premier tableau
				compt1++;
			}
			else if (!comparerDeuxStrings(table1[compt1-deb1][1], tableau[compt2][1])) {
				tableau[i]=table1[compt1-deb1]; //on ajoute un élément du premier tableau
				compt1++;
			}
			else {
				tableau[i]=tableau[compt2]; //on ajoute un élément du second tableau
				compt2++;
			}
		}
	}
	
	public static String remplirParEspace(String ch, int nbreEspaces) {
		for(int i = 0; i<nbreEspaces; i++)
			ch += ' ';
		return ch;
	}
	
	public static boolean comparerDeuxStrings(String ch1, String ch2) {
		if(ch1.length() > ch2.length())
			ch2 = remplirParEspace(ch2, ch1.length()-ch2.length());
		else
			ch1 = remplirParEspace(ch1, ch2.length()-ch1.length());
		int length = ch1.length();
		String nombre1 = "";
		String nombre2 = "";
		for(int i = 0; i<length; i++) {
			if(ch1.charAt(i) != ch2.charAt(i)) {
				if((('0'<=ch1.charAt(i) && ch1.charAt(i)<='9') && ('0'<=ch2.charAt(i) && ch2.charAt(i)<='9')) || (!nombre1.equals("") && !nombre2.equals(""))) {
					if('0'<=ch1.charAt(i) && ch1.charAt(i)<='9')
						nombre1 += ch1.charAt(i);
					if('0'<=ch2.charAt(i) && ch2.charAt(i)<='9')
						nombre2 += ch2.charAt(i);
				}
				else if(!nombre1.equals("") && !nombre2.equals("")) {
					int entier1 = Integer.parseInt(nombre1);
					int entier2 = Integer.parseInt(nombre2);
					return entier1 >= entier2;
				}
				else if(ch1.charAt(i) > ch2.charAt(i))
					return true;
				else
					return false;
			}
		}
		if(!nombre1.equals(""))
			return Integer.parseInt(nombre1) >= Integer.parseInt(nombre2);
		return true;
	}
	
}
