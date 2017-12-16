package Go_Game;

import static org.junit.Assert.*;

import org.junit.Test;

public class GobanTest {

	@Test
	public void testGetPierre() {
		Goban goban = new Goban();
		Joueur J = new Joueur("Quentin", Couleur.Blanc);
		Position posH = new Position(5,0);
		Position posD = new Position(18,5);
		Position posG = new Position(0,5);
		Position posB = new Position(5,18);
		
		Position coinHG = new Position(0,0);
		Position coinHD = new Position(18,0);
		Position coinBG = new Position(0,18);
		Position coinBD = new Position(18,18);
		
		Position pos = new Position(7,7);
		
		goban.poserPierre(J, posH);
		goban.poserPierre(J, posD);
		goban.poserPierre(J, posG);
		goban.poserPierre(J, posB);
		
		goban.poserPierre(J, coinHG);
		goban.poserPierre(J, coinHD);
		goban.poserPierre(J, coinBG);
		goban.poserPierre(J, coinBD);
		
		goban.poserPierre(J, pos);
		
		/*
		 * Pour tester, il faut rendre publique la methode getPierre(pos, Cote)
		
		assertEquals(null ,goban.getPierre(posH, Cote.HAUT));
		assertEquals(null ,goban.getPierre(posD, Cote.DROITE));
		assertEquals(null ,goban.getPierre(posG, Cote.GAUCHE));
		assertEquals(null ,goban.getPierre(posB, Cote.BAS));
		
		
		assertEquals(null ,goban.getPierre(coinHG, Cote.HAUT));
		assertEquals(null ,goban.getPierre(coinHG, Cote.GAUCHE));
		
		assertEquals(null ,goban.getPierre(coinHD, Cote.HAUT));
		assertEquals(null ,goban.getPierre(coinHD, Cote.DROITE));
		
		assertEquals(null ,goban.getPierre(coinBG, Cote.BAS));
		assertEquals(null ,goban.getPierre(coinBG, Cote.GAUCHE));
		
		assertEquals(null ,goban.getPierre(coinBD, Cote.BAS));
		assertEquals(null ,goban.getPierre(coinBD, Cote.DROITE));
		*/
	}

}
