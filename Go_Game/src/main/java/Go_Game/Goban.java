package Go_Game;

import java.util.ArrayList;

public class Goban {
	final private int HAUTEUR = 19;
	final private int LARGEUR = 19;
	
	private ArrayList<Pierre> pierres = new ArrayList<Pierre>();
	
	Goban() {
		for(int i = 0; i < HAUTEUR; i++) {
			for(int j = 0; j < LARGEUR; j++) {
				pierres.add(new Pierre(new Position(i, j)));
			}
		}
	}

	
	public void afficher() {
		for(int i = 0; i < HAUTEUR; i++) {
			for(int j = 0; j < LARGEUR; j++) {
				switch(pierres.get(i).getCouleur()) {
					case Vide:
						System.out.print("+");
						break;
					case Blanc:
						System.out.print("B");
						break;
					case Noir:
						System.out.print("N");
						break;
					default:
						break;
				}
			}
			System.out.println("");
		}
	}
}
