package Go_Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Goban implements Serializable {

	private static final long serialVersionUID = 2473059806084092696L;
	/*données necessaires a construire le goban*/
	final private int HAUTEUR = 19;
	final private int LARGEUR = 19;
	private List<Pierre> pierres = new ArrayList<Pierre>();
	
	/*données necessaires a calculer le score*/
	private int pointB =0;
	private int pointN =0;
	ArrayList<Integer> noir = new ArrayList<Integer>();
	ArrayList<Integer> blanc = new ArrayList<Integer>();
	private List<Pierre> capturedB = new ArrayList<Pierre>();
	private List<Pierre> capturedN = new ArrayList<Pierre>();
	private List<ArrayList<Pierre>> territoires = new ArrayList<ArrayList<Pierre>>();
	
	/*données necessaires a capturer les pierres*/
	private List<ArrayList<Pierre>> groupes = new ArrayList<ArrayList<Pierre>>();
	
	/***
	 * summary
	 * Constructeur qui permet d'initialiser les goban et de creer 4 groupes de pierres dans groupes
	 */
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

	/***
	 * summary
	 * Fonction qui permet l'affichage du goban
	 */
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

	/***
	 * summary
	 * fonction qui permet de poser une pierre
	 * @param joueur permet de savoir qui joue
	 * @param pos position du coup souhaité
	 * @return boolean pour savoir si la pierre a bien ete posee
	 */
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

/**********************PARTIE CAPTURE DE PIERRE*******************************/
	
	/***
	 * summary
	 * fonction qui permet de capturer une pierre
	 * @param pos position de la pierre que j'ai teste a l'origine
	 */
	public void capturerPierre(Position pos) {
		Pierre p = getPierre(pos);
		Couleur couleurACapturer = p.getCouleur().equals(Couleur.Blanc) ? Couleur.Noir : Couleur.Blanc; //couleur ennemie de la pierre à la pos envoyée
		//System.err.println("capturerPierre : couleurACapturer = " + couleurACapturer);
		for(Cote c : Cote.values()) { //Pour les 4 cotés
			Pierre pierreAdjacente = getPierre(pos, c); //On récupère la pierre d'à coté
			//System.err.println("capturerPierre : Cote = " + c + " pierreAdjacente = " + pierreAdjacente);
			if(pierreAdjacente != null) { //Si il y a bien une pierre à coté
				if(pierreAdjacente.getCouleur() == couleurACapturer) { //Si la pierre d'a coté est une pierre a capturer
					//System.err.println("\tLancer construireGroupe()");
					construireGroupe(pierreAdjacente, c); //on commence à construire un groupe à partir de la pierre à possiblement capturer
				}
			}
		}
		controlerGroupes(); //On regarde si on a pas un ou plusieurs groupes qui ne sont pas "encerclés"
		supprimerGroupes(); //On supprime les groupes possiblement construits
		return;
	}

	/***
	 * summary
	 * fonction qui permet de controler si c'est bien un groupe a capturer
	 */
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
									//System.err.println("controlerGrp : Ajout du groupe cote " + c);
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
				//System.err.println("controlerGrp : clear du groupe cote " + c);
			}
		}
		return;
	}
	
	private void construireGroupe(Pierre pierre, Cote side) { //fonction qui permet de cree un groupe recursivement
		List<Pierre> aConstruire = groupes.get(side.toInt());
		if(compterLibertes(pierre) != 0) return;
		else { //La pierre est entourée de pierres ou de murs
			aConstruire.add(pierre);
			//System.err.println("construireGrp : ajout de " + pierre);
			for(Cote c : Cote.values()) { //Pour les 4 cotés
				Pierre p = getPierre(pierre.getPosition(), c);
				if(p != null) { //Si il y a bien une pierre
					if(pierre.getCouleur() == p.getCouleur()) { //Si la pierre d'a coté à la même couleur
						if(!aConstruire.contains(p)) { //Si on a pas déjà la pierre dans la liste
							//System.err.println("construireGrp : meme couleur, on re construit à partir de " + p);
							construireGroupe(p, side);
						}
					}
				}
			}
		}
		return;
	}

	private int compterLibertes(Pierre pierre) { //fonction qui permet de savoir si un groupe de pierre peut s'enfuire ou pas
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
		//System.err.println("compterLibertes :" + pierre + " a nbLiberte = " + nbLiberte);
		return nbLiberte;
	}

	private void supprimerGroupes() { /*fonction qui supprime tous les groupes*/
		for(List<Pierre> groupe : groupes) {//Pour les 4 groupes
			for(Pierre p : groupe) { //Pour toutes les pierres du groupe
				ajouterCapture(p);
				//System.err.println("suppression de " + p);
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
	
/**********************PARTIE CALCUL DU SCORE*******************************/
	
	/***
	 * summary
	 * fonction qui permet de calculer les points de chaque joueur en fonction des territoires
	 * a la fin de la partie
	 */
	public void calculerTerritoires() {
		boolean contient = false;
		for(int index = 0; index < HAUTEUR*LARGEUR; index++) {
			if(pierres.get(index).getCouleur().toString() == "Vide"){
				if(!territoires.isEmpty()) {
					for(List<Pierre> groupe : territoires) {//Pour les groupes
						if(!groupe.isEmpty()) {
							if(groupe.contains(pierres.get(index))) contient = true; //Si on a déjà la pierre dans la liste true
						}
					}
					if(contient == false) contruireTer(index);
					else { contient = false;}
				}else {contruireTer(index);}		
			}	
		}
		controlerTerritoires();
		calculPoints();
	}
	
	private void contruireTer(int index) { /*fonction qui ajoute et construit une nouvelle liste de pierre dans territoires*/
		territoires.add(new ArrayList<Pierre>());
		construireTerritoire(pierres.get(index), territoires.size()-1);
	}
	
	private void construireTerritoire(Pierre pierre, int side) { //fonction qui permet de cree un territoire
		List<Pierre> aConstru = territoires.get(side);
		if(pierre.getCouleur()!= Couleur.Vide) return;
		else { 
			aConstru.add(pierre);
			//System.err.println("construireGrp : ajout de " + pierre);
			for(Cote c : Cote.values()) { //Pour les 4 cotés
				Pierre pAdjacente = getPierre(pierre.getPosition(), c);
				if(pAdjacente != null) { //Si il y a bien une pierre
					if(pierre.getCouleur() == pAdjacente.getCouleur()) { //Si la pierre d'a coté à la même couleur
						if(!aConstru.contains(pAdjacente)) { //Si on a pas déjà la pierre dans la liste
							//System.err.println("construireGrp : meme couleur, on re construit à partir de " + pAdjacente);
							construireTerritoire(pAdjacente, side);
						}
					}
				}
			}
		}
	}
	
	/***
	 * summary
	 * fonction qui permet de savoir si les pierres entourant le groupe de pierre vides sont mixtes ou unies
	 */
	private void controlerTerritoires() {
		for(List<Pierre> groupe : territoires) {//Pour les groupes
			int n=0,b=0;
			if(!groupe.isEmpty()) {
				for(Pierre p : groupe) { //Pour toutes les pierres du groupe
					for(Cote c : Cote.values()) { //Pour les 4 cotés de chaque pierre
						Pierre pierreACote = getPierre(p.getPosition(), c); 
						if(pierreACote != null) {//Si on regarde pas à l'exterieur du goban
							if(pierreACote.getCouleur() != p.getCouleur()) {//Si une pierre a coté de la pierre qu'on test a une couleur differente que la pierre de la boucle
								if(pierreACote.getCouleur().toString() == "Noir"){
									n++;
								}else if(pierreACote.getCouleur().toString() == "Blanc") b++;
							}
						}
					}
				}
				noir.add(n); //on ajoute a la liste de noir a la meme position que le groupe
				blanc.add(b); //on ajoute a la liste de blanc a la meme position que le groupe
			}
		}
	}

	/***
	 * summary
	 * fonction qui permet de controler si c'est bien un territoire a comptabiliser
	 */
	private void calculPoints()
	{
		int index=0;
		for(List<Pierre> groupe : territoires) { //Pour tous les groupes
			if((blanc.get(index) != 0)&&(noir.get(index)!=0)) { // entouré de pierres differentes
				territoires.get(index).clear(); //On efface son contenu
			}else if((blanc.get(index) == 0)&&(noir.get(index)!=0)) // entouré de pierres noires
			{
				pointN+=groupe.size();
			}else if((blanc.get(index) != 0)&&(noir.get(index)==0)) // entouré de pierres blanches
			{
				pointB+=groupe.size();
			}
			index++;
		}
	}
	
	/**
	 * summary 
	 * ajout des pierres capture dans le decompte de points
	 * @param p pierre capturée a ajouter au decompte de point
	 */
	public void ajouterCapture(Pierre p)
	{
		if(p.getCouleur().toString() == "Blanc") capturedB.add(p);
		else capturedN.add(p);		
	}
	
	/**
	 * summary 
	 * decompte de points pour chaque couleur
	 * @param c couleur du joueur
	 * @return points du joueur de la couleur c
	 */
	public int nbCaptured(Couleur c){
		if(c.toString() == "Noir") return capturedB.size()+ pointN;
		else return capturedN.size()+ pointB;
	}
}