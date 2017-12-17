package Go_Game;

public class Joueur {
	private int score;
	private int nbPiecesCapturees;
	private Couleur couleur;
	private String nom;
	
	
	Joueur(String nom, Couleur couleur) {
		nbPiecesCapturees = 0;
		score = 0;
		this.couleur = couleur;
		this.nom = new String(nom);
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
	
	public void augmenterNbCaptures(int nombre) {
		if(nombre > 0)
			nbPiecesCapturees += nombre;
		return;
	}
	
	public void calculerScore(Goban goban) {
		//Calculer score depuis le terrain
		score = goban.nbCaptured(couleur);
		
		//score = nbPiecesCapturees;
		return;
	}
	
	public int getScore() {
		return score;
	}
	
	public String toString() {
		return nom;
	}
}
