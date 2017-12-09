package Go_Game;

public class Joueur {
	private int score;
	private Couleur couleur;
	
	Joueur(Couleur couleur) {
		score = 0;
		this.couleur = couleur;
	}
	
	void calculerScore(Goban goban) {
		//Calculer score depuis le terrain
		score = 0;
		return;
	}
}
