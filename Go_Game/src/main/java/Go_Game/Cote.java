package Go_Game;

import java.io.Serializable;

public enum Cote implements Serializable {
HAUT,
DROITE,
BAS,
GAUCHE;
	
	public int toInt() {
		switch(this) {
		case HAUT:
			return 0;
		case DROITE:
			return 1;
		case BAS:
			return 2;
		case GAUCHE:
			return 3;
		default:
			return -1;
		}
	}
}
