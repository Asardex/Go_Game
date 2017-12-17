package Go_Game;

import java.util.ArrayList;
import java.util.List;

public class Goban {
	final private int HAUTEUR = 19;
	final private int LARGEUR = 19;
	private int pointB =0;
	private int pointN =0;
	
	private List<Pierre> pierres = new ArrayList<Pierre>();
	private List<Pierre> capturedB = new ArrayList<Pierre>();
	private List<Pierre> capturedN = new ArrayList<Pierre>();
	private List<ArrayList<Pierre>> groupes = new ArrayList<ArrayList<Pierre>>();
	private List<ArrayList<Pierre>> territoires = new ArrayList<ArrayList<Pierre>>();
	
	Goban() {
		int y = -1;
		int x = 0;
		for(int index = 0; index < HAUTEUR*LARGEUR; index++) {
			if(index % LARGEUR == 0) {
				y++;
			}
			x = index % LARGEUR;
			pierres.add(new Pierre(new Position(x, y)));
		}
		for(Cote c : Cote.values()) { //Pour les 4 cotés, on créé un tableau de pierres (vide)
			groupes.add(new ArrayList<Pierre>());
		}
	}

	
	public void afficher() {
		for(int index = 0; index < HAUTEUR*LARGEUR; index++) {
			if(index % LARGEUR == 0)
				System.out.println("");
			switch(pierres.get(index).getCouleur()) {
				case Vide:
					System.out.print("+ ");
					break;
				case Blanc:
					System.out.print("B ");
					break;
				case Noir:
					System.out.print("N ");
					break;
				default:
					break;
			}
		}
		System.out.println("");
	}

	public boolean poserPierre(Joueur joueur, Position pos) {
		int index = pos.getY() * LARGEUR + pos.getX();
		if(pierres.get(index).getCouleur() == Couleur.Vide) {
			pierres.get(index).poser(joueur.getCouleur());
			return true;
		} else {
			System.out.println("Coup invalide : place déjà prise");
			return false;
		}
	}
	
	public void capturerPierre(Position pos) {
		Pierre p = getPierre(pos);
		Couleur couleurACapturer = p.getCouleur().equals(Couleur.Blanc) ? Couleur.Noir : Couleur.Blanc; //couleur ennemie de la pierre à la pos envoyée
		System.err.println("capturerPierre : couleurACapturer = " + couleurACapturer);
		
		for(Cote c : Cote.values()) { //Pour les 4 cotés
			Pierre pierreAdjacente = getPierre(pos, c); //On récupère la pierre d'à coté
			System.err.println("capturerPierre : Cote = " + c + " pierreAdjacente = " + pierreAdjacente);
			if(pierreAdjacente != null) { //Si il y a bien une pierre à coté
				if(pierreAdjacente.getCouleur() == couleurACapturer) { //Si la pierre d'a coté est une pierre a capturer
					System.err.println("\tLancer construireGroupe()");
					construireGroupe(pierreAdjacente, c); //on commence à construire un groupe à partir de la pierre à possiblement capturer
				}
			}
		}
		controlerGroupes(); //On regarde si on a pas un ou plusieurs groupes qui ne sont pas "encerclés"
		supprimerGroupes(); //On supprime les groupes possiblement construits
		
		return;
	}


	private void controlerGroupes() {
		int[] controle = {0, 0, 0, 0};
		for(List<Pierre> groupe : groupes) {//Pour les 4 groupes
			if(!groupe.isEmpty()) {
				for(Pierre p : groupe) { //Pour toutes les pierres du groupe
					for(Cote c : Cote.values()) { //Pour les 4 cotés de chaque pierre
						Pierre pierreACote = getPierre(p.getPosition(), c); 
						if(pierreACote != null) {//Si on regarde pas à l'exterieur du goban
							if(pierreACote.getCouleur() == p.getCouleur()) {//Si une pierre a coté de la pierre qu'on test a la même couleur que la pierre de la boucle
								if(compterLibertes(pierreACote) > 0) { //Si cette pierre à plus de 0 libertés
									controle[groupes.indexOf(groupe)] = 1; //alors le groupe est libre, puisqu'il y a une issue
									System.err.println("controlerGrp : Ajout du groupe cote " + c);
								}
							}
						}
					}
				}
			}
		}
		for(Cote c : Cote.values()) { //Pour tous les groupes
			if(controle[c.toInt()] == 1) { //Si un groupe est marqué
				groupes.get(c.toInt()).clear(); //On efface son contenu
				System.err.println("controlerGrp : clear du groupe cote " + c);
			}
		}
		return;
	}
	
	private void controlerTerritoires() {
		int noir = 0;
		int blanc = 0;
		for(List<Pierre> groupe : territoires) {//Pour les 4 groupes
			if(!groupe.isEmpty()) {
				for(Pierre p : groupe) { //Pour toutes les pierres du groupe
					for(Cote c : Cote.values()) { //Pour les 4 cotés de chaque pierre
						Pierre pierreACote = getPierre(p.getPosition(), c); 
						if(pierreACote != null) {//Si on regarde pas à l'exterieur du goban
							if(pierreACote.getCouleur() != p.getCouleur()) {//Si une pierre a coté de la pierre qu'on test a une couleur differente que la pierre de la boucle
								if(pierreACote.getCouleur().toString() == "Noir")
								{
									noir++;
								}else if(pierreACote.getCouleur().toString() == "Blanc")
								{
									blanc++;
								}
							}
						}
					}
				}
			}
		}
		for(Cote c : Cote.values()) { //Pour tous les groupes
			if((blanc != 0)&&(noir!=0)) { // entouré de pierres differentes
				groupes.get(c.toInt()).clear(); //On efface son contenu
				System.err.println("controlerGrp : clear du groupe cote " + c);
			}else if((blanc == 0)&&(noir!=0)) // entouré de pierres noires
			{
				pointN++;
			}else if((blanc != 0)&&(noir==0)) // entouré de pierres blanches
			{
				pointB++;
			}
		}
		return;
	}


	private void construireGroupe(Pierre pierre, Cote side) {
		List<Pierre> aConstruire = groupes.get(side.toInt());
		if(compterLibertes(pierre) != 0) {
			return;
		} else { //La pierre est entourée de pierres ou de murs
			aConstruire.add(pierre);
			System.err.println("construireGrp : ajout de " + pierre);
			for(Cote c : Cote.values()) { //Pour les 4 cotés
				Pierre p = getPierre(pierre.getPosition(), c);
				if(p != null) { //Si il y a bien une pierre
					if(pierre.getCouleur() == p.getCouleur()) { //Si la pierre d'a coté à la même couleur
						if(!aConstruire.contains(p)) { //Si on a pas déjà la pierre dans la liste
							System.err.println("construireGrp : meme couleur, on re construit à partir de " + p);
							construireGroupe(p, side);
						}
					}
				}
			}
		}
		return;
	}

	private void construireTerritoire(Pierre pierre, Cote side) { //fonction qui permet de cree un territoire
		List<Pierre> aConstruire = territoires.get(side.toInt());
		if(pierre.getCouleur()!= Couleur.Vide) {
			return;
		} else { 
			aConstruire.add(pierre);
			System.err.println("construireGrp : ajout de " + pierre);
			for(Cote c : Cote.values()) { //Pour les 4 cotés
				Pierre pAdjacente = getPierre(pierre.getPosition(), c);
				if(pAdjacente != null) { //Si il y a bien une pierre
					if(pierre.getCouleur() == pAdjacente.getCouleur()) { //Si la pierre d'a coté à la même couleur
						if(!aConstruire.contains(pAdjacente)) { //Si on a pas déjà la pierre dans la liste
							System.err.println("construireGrp : meme couleur, on re construit à partir de " + pAdjacente);
							construireTerritoire(pAdjacente, side);
						}
					}
				}
			}
		}
		return;
	}

	private int compterLibertes(Pierre pierre) {
		int nbLiberte = 0;
		if(pierre != null) {
			for(Cote c : Cote.values()) { //Pour les 4 cotés
				Pierre pierreAdjacente = getPierre(pierre.getPosition(), c); //On récupère la pierre d'à coté
				if(pierreAdjacente != null) { //Si il y a une case a coté
					if(pierreAdjacente.getCouleur() == Couleur.Vide) { //Si on a un emplacement vide à coté
						nbLiberte++;
					}
				}
			}
		}
		System.err.println("compterLibertes :" + pierre + " a nbLiberte = " + nbLiberte);
		return nbLiberte;
	}

	

	private void supprimerGroupes() {
		for(List<Pierre> groupe : groupes) {//Pour les 4 groupes
			for(Pierre p : groupe) { //Pour toutes les pierres du groupe
				ajouterCapture(p);
				System.err.println("suppression de " + p);
				p.supprimer();
			}
			groupe.clear();
		}
		return;
	}


	private Pierre getPierre(Position pos) {
		return pierres.get(pos.getY() * LARGEUR + pos.getX());
	}
	
	/**
	 * 
	 * @param pos position d'origine
	 * @param c direction par rapport à l'origine
	 * @return pierre obtenue en combinant origine + direction
	 */
	private Pierre getPierre(Position pos, Cote c) {
		if(c == Cote.HAUT && pos.getY() == 0 || c == Cote.DROITE && pos.getX() == 18 || c == Cote.BAS && pos.getY() == 18 || c == Cote.GAUCHE && pos.getX() == 0)
			return null; //Si on essaie de regarder en dehors du plateau
		else {
			Position newPos = new Position(pos.getX(), pos.getY());
			switch(c) {
			case HAUT:
				newPos.setY(pos.getY()-1);
				break;
			case DROITE:
				newPos.setX(pos.getX()+1);
				break;
			case BAS:
				newPos.setY(pos.getY()+1);
				break;
			case GAUCHE:
				newPos.setX(pos.getX()-1);
				break;
			default :
				return null;
			}
			return getPierre(newPos);
		}
	}
	public void ajouterCapture(Pierre p)
	{
		if(p.getCouleur().toString() == "Blanc")
		{
			capturedB.add(p);
		}else if (p.getCouleur().toString() == "Noir")
		{
			capturedN.add(p);
		}
	}
	public int nbCaptured(Couleur c)
	{
		if(c.toString() == "Noir")
		{
			return capturedB.size()+ pointN;
		}else if (c.toString() == "Blanc")
		{
			return capturedN.size()+ pointB;
		}
		return 0;
	}
}