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


//	croix simple 0
//	croix point	1
//	bord haut 2
//	bord bas	3
//	bord gauche	4
//	bord droit	5
//	coin superieur gauche 6
//	coin superieur droit 7
//	coin inferieur gauche 8
//	coin inferieur droit 9
	 
	
	public int grille[][]=
		{
				{6,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,7},
				{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5},
				{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5},
				{4,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,5},
				{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5},
				{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5},
				{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5},
				{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5},
				{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5},
				{4,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,5},
				{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5},
				{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5},
				{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5},
				{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5},
				{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5},
				{4,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,5},
				{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5},
				{4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5},
				{8,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,9}			
		};

}