package Go_Game;

import java.io.Serializable;

public class Pierre implements Serializable {

	private static final long serialVersionUID = 4438330629692151834L;
	private Couleur couleur;
	private Position pos;
	
	Pierre(Position pos) {
		couleur = Couleur.Vide;
		this.pos = pos;
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
	
	public Position getPosition() {
		return pos;
	}
	
	public void supprimer() {
		couleur = Couleur.Vide;
		return;
	}
	
	public void poser(Couleur couleur) {
		this.couleur = couleur;
		return;
	}
	
	public String toString() {
		return pos + " " + couleur;
	}
}
