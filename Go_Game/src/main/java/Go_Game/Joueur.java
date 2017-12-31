package Go_Game;

import java.io.Serializable;

public class Joueur  implements Serializable {
	private static final long serialVersionUID = -7016853658676287606L;
	private int score;
	private Couleur couleur;
	private String nom;
	
	
	Joueur(String nom, Couleur couleur) {
		score = 0;
		this.couleur = couleur;
		this.nom = new String(nom);
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
	
	public int calculerScore(Goban goban) {
		//Calculer score depuis le terrain
		score = goban.nbCaptured(couleur);
		return score;
	}
	
	public String toString() {
		return nom;
	}
}
