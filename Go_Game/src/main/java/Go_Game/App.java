package Go_Game;

import java.util.Scanner;

/**
 * Classe App, point de lancement de l'application
 *
 */
public class App 
{
	Joueur J1;
	Joueur J2;
	Goban goban;
	static Scanner sc;

	public static void main( String[] args )
    {
		App app = new App();
    	int choix = 0;
    
    	System.out.print("Bienvenu dans le Goban. ");
    	do {
    		System.out.println("Quel voulez vous faire ?\n\t1. Jouer\n\t2. Lire les règles\n\t3. Comment jouer ?\n\t-1. Quitter");
	    	sc = new Scanner(System.in); //L'utilisateur fait sont choix.
	    	if(sc.hasNextInt())
	    		choix = sc.nextInt();
	    	else
	    		System.out.println("Entrez un chiffre.");
	    	switch(choix) {
	    		case -1:
	    			choix = 1;
	    			break;
	    		case 1 :
	    			app.creationJoueurs();
	    			app.jouer();
	    			break;
	    		case 2:
	    			app.regles();
	    			break;
	    		case 3:
	    			app.commentJouer();
	    			break;
    			default:
    				break;
	    	}
    	}while(choix != 1); //On demande tant que l'on veut pas jouer ou arrêter le programme
    	
    	sc.close();
    	System.out.println("\nA bientôt !");
    	return;
    }

	/**
	 * Lance une partie de Goban
	 */
    private void jouer() {
    	int finDuGame = 0;
    	int valeurTour = -1;
    	goban = new Goban();
    	Joueur joueur = J1; //Le joueur qui joue est celui qui commence c'est le J1 (noir)
    	
    	System.out.println("\nDebut de la partie.");
    	
    	while(finDuGame != 2) { //Tant que deux joueurs n'ont pas joué à la suite.
    		goban.afficher(); //On affiche le plateau de jeu
			valeurTour = tour(joueur);
			if(valeurTour == 1) { //Si le joueur joue
				finDuGame = 0; //Alors on remet à 0
				afficherScore();
			} else if(valeurTour == 2) {
				finDuGame = 2;
				break;
			}
			else { //Si il ne joue pas, on enregistre
				finDuGame++;
				System.out.println(joueur + " passe son tour.");
			}
    		joueur = joueur.equals(J1) ? J2 : J1; //Fin du tours, on change de joueur
    	}
    	
    	if(valeurTour == 2) {
    		System.out.println(joueur + " a abandonné la partie. " + (joueur.equals(J1) ? J2 : J1) + " est donc déclaré gagnant !");
    	} else {
    		afficherScore();
    	}
    	System.out.println("Fin de la partie.");
		return;
	}
    
    /**
     * Effectue le tour d'un joueur
     * @param Joueur le joueur concerné par son tour
     * @return Renvoie 1 si le joueur à jouer, 0 si il passe
     */
    private int tour(Joueur joueur) {
    	int choix = -1;
    	
    	do {
	    	System.out.print("A " + joueur + " de jouer (0:paser 1:jouer 2:abandonner) : ");
			sc = new Scanner(System.in);
			if(sc.hasNextInt())
				choix = sc.nextInt();
			else
				System.out.print("Entrez un chiffre (0:paser 1:jouer) : ");
    	}while(choix != 0 && choix != 1 && choix != 2); //On demande de passer ou de jouer ou d'abandonner
    	
    	if((choix == 1)) {
    		//Si le joueur veut jouer
    		faireJouer(joueur);		
    	}
		return choix;
    }

    /**
     * fait jouer un joueur (demande de pos + poser la pierre + capturer)
     * @param joueur à faire jouer
     */
    private void faireJouer(Joueur joueur) {
    	Position pos;
    	boolean isNotOk = true;
    	int x, y;
    	do {
			System.out.print("Entre les coordonées (sous la forme x y) : ");
			sc = new Scanner(System.in);
			if(sc.hasNextInt()) { //Si il y a un entier à lire
				x = sc.nextInt(); //On le lit
				if(x >= 0 && x <= 18 && sc.hasNextInt()) { //Si l'entier lu est entre 0 et 18, et qu'il y a un deuxième entier à lire
					y = sc.nextInt(); //On lit le deuxième entier
					if(y >= 0 && y <= 18) { // si le deuxième entier est entre 0 et 18
						isNotOk = false; // Tout est bon
						pos = new Position(x, y); //On garde la position
						if(goban.poserPierre(joueur, pos)) {// On la donne au Goban pour la poser
							goban.capturerPierre(joueur, pos); //Si on a posé, on peut capturer
						}
						else { //Si on a pas posé, il faut redemander une pos
							isNotOk = true;
						}
					}
				}
			}
		}while(isNotOk); //Tant que les coordonnées ne sont pas valides, on redemande une pos.
    	return;
	}
    

	/**
     * Affiche le score des joueurs
     */
	final private void afficherScore() {
		Joueur premier = J1, deuxieme = J2;
		calculerScore();
		System.out.println("");
		if(J1.getScore() == J2.getScore()) {
			System.out.println(premier + " et " + deuxieme + " sont à égalité avec " + J1.getScore() + " points chacuns.");	
		} else {
			if(J1.getScore() < J2.getScore()) {
				premier = J2;
				deuxieme = J1;
			}
			System.out.println(premier + " est en tête avec " + premier.getScore() + " points, contre " + deuxieme.getScore() + " points pour " + deuxieme + ".");
		}		
		return;
	}
	
	/**
	 * Calcule le score des joueurs à partir du plateau de jeu
	 */
	private void calculerScore() {
		J1.calculerScore(goban);
		J2.calculerScore(goban);
		return;
	}
	
	/**
	 * Initialise les joueurs via la console
	 */
	private void creationJoueurs() {
    	System.out.print("Saisissez le nom du joueur qui va commencer (jouera en noir) : ");
       	J1 = new Joueur(saisirNom(), Couleur.Noir);
    	
    	System.out.print("Saisissez le nom du deuxième joueur (jouera en blanc) : ");
    	J2 = new Joueur(saisirNom(), Couleur.Blanc);
		return;
	}
	
	/**
	 * Demande un nom, jusqu'à ce que le nom soit correct
	 * @return Renvoie le nom saisi
	 */
	private String saisirNom() {
		String nom = new String();
		boolean correct = false;
		do {
			sc = new Scanner(System.in);
			if(sc.hasNextLine()) {
				nom = sc.nextLine();
				System.out.print(nom + " est bien le nom du joueur ? (0:Non, 1:Oui) : ");
				sc = new Scanner(System.in);
				if(sc.hasNextInt()) {
					if(sc.nextInt() == 1) {
						correct = true;
					} else {
						System.out.print("Saisissez à nouveau le nom du joueur : ");
					}
				}
			}
		}while(!correct);
		
		return nom;
	}

	/**
	 * Explique comment jouer
	 */
	final private void commentJouer() {
    	System.out.println("\nEntrez les coordonées de la pierre que vous voulez placer sous la forme x y.\n"
    			+ "0 0 se situe en haut a gauche.\n"
    			+ "0 18 se situe en haut a droite.\n"
    			+ "18 0 se situe en bas a gauche.\n"
    			+ "18 18 se situe en bas a droite.\n");
    	return;
	}

	/**
	 * Donne les règles du jeu
	 */
	final private void regles() {
    	System.out.println("\nDeux camps s'opposent pour obtenir le plus grand territoire : "
    			+ "les pierres blanches et les pierres noires.\n"
    			+ "Le premier joueur pose des pierres noires.\n"
    			+ "Les joueurs peuvent passer leur tour,"
    			+ "cependant si l'autre joueur ne passe pas on revient au joueur précédent \n"
    			+ "Si les deux joueurs passent leur tour l'un apres l'autre, "
    			+ "le jeu est terminé et les scores sont comptés.\n"
    			+ "Les pierres sont jouées sur les intersections.\n"
    			+ "Un territoire est un espace entouré par des pierres d'une seule couleur.\n"
    			+ "La valeur d'un territoire se compte en nombre d'emplacements libre (les intersections) pour des pierres.\n"
    			+ "La valeur de chaque territoire est sommé, et on y ajoute les pierres que l'on a capturé "
    			+ "à l'adversaire.\nCelui qui a le plus grand score gagne.\n"
    			+ "\nLa règle de capture de pierres :\n"
    			+ "si une pierre, ou un groupe de pierres est entouré "
    			+ "par des pierres de l'autre couleur, les pierres entourées sont capturées, enlevée du "
    			+ "plateau, et le nombre final de pierres capturées sera ajouté au score du joueur qui les "
    			+ "a capturés\n\n");
    	
    	return;
    }
}
