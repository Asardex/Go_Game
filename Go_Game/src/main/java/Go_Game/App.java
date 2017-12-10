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
	    			app.jouer(app.choixCouleur());
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
    	}while(choix != 1); //On demande tant que le

    	sc.close();
    	System.out.println("\nA bientôt !");
    	return;
    }

    
    private void jouer(Couleur couleur) {
    	goban = new Goban();
    	Joueur joueur;
    	
    	if(couleur == Couleur.Blanc) {
        	J1 = new Joueur("Marine", Couleur.Blanc);
        	J2 = new Joueur("Quentin", Couleur.Noir);
    	} else {
        	J1 = new Joueur("Marine", Couleur.Noir);
        	J2 = new Joueur("Quentin", Couleur.Blanc);
    	}
    	
    	joueur = J2;
    	int finDuGame = 0;
    	
    	System.out.println("\nDebut de la partie.");
    	
    	while(finDuGame != 2) { //Tant que deux joueurs n'ont pas joué à la suite.
    		goban.afficher(); //On affiche le plateau de jeu
    		
			if(joueur.equals(J1)) //Détermine quel joueur doit jouer
				joueur = J2;
			else
				joueur = J1;
			
			if(tour(joueur) == 0) { //Si le joueur joue
				finDuGame = 0; //Alors on remet à 0
				AfficherScore();
			} else { //Si il ne joue pas, on enregistre
				finDuGame++;
				System.out.println(joueur + " passe son tour.");
			}
    	}
    	
    	calculerScore();
    	if(J1.getScore() > J2.getScore())
			System.out.println(J1 + " gagne avec " + J1.getScore() + " points, contre " + J2.getScore() + " points pour " + J2 + ".");
		else if(J1.getScore() < J2.getScore())
			System.out.println(J2 + " gagne avec " + J2.getScore() + " points, contre " + J1.getScore() + " points pour " + J1 + ".");
		else
			System.out.println(J1 + " et " + J2 + " sont à égalité avec " + J1.getScore() + " points chacuns.");
    	
    	System.out.println("Fin de la partie.");
		return;
	}
    
    private int tour(Joueur joueur) {
    	//renvoie 0 si le joueur à jouer, 1 si il passe
    	int choix = 0;
    	int x, y;
    	Position pos;
    	boolean isOk = false;
    	
    	do {
	    	System.out.println("A " + joueur + " de jouer (0:jouer 1:passer).");
			sc = new Scanner(System.in);
			if(sc.hasNextInt())
				choix = sc.nextInt();
			else
				System.out.println("Entrez un chiffre");
    	}while(choix != 0 && choix != 1); //On demande de jouer ou de passer
    	
    	if(choix == 0) {
    		//Si le joueur veut jouer
    		do {
	    		System.out.print("Entre les coordonées (sous la forme '(x;y)') : ");
	    		sc = new Scanner(System.in);
	    		if(sc.hasNextInt()) { //Si il y a un entier à lire
	    			x = sc.nextInt(); //On le lit
	    			if(x >= 0 && x <= 18 && sc.hasNextInt()) { //Si l'entier lu est entre 0 et 18, et qu'il y a un deuxième entier à lire
	    				y = sc.nextInt(); //On lit le deuxième entier
	    				if(y >= 0 && x <= 18) { // si le deuxième entier est entre 0 et 18
	    					isOk = true; // Tout est bon
	    					pos = new Position(x, y); //On garde la position
	    					if(!goban.poserPierre(joueur, pos)) // On la donne au Goban pour la poser : si on ne peut pas, isNotOk
	    						isOk = false;
	    				}
	    			}
	    		}
    		}while(isOk == false); //Tant que les coordonnées ne sont pas valides, on redemande une pos.
    			
    	}
		return choix;
    }

	private void AfficherScore() {
		calculerScore();
		if(J1.getScore() > J2.getScore())
			System.out.println(J1 + " est en tête avec " + J1.getScore() + " points, contre " + J2.getScore() + " points pour " + J2 + ".");
		else if(J1.getScore() < J2.getScore())
			System.out.println(J2 + " est en tête avec " + J2.getScore() + " points, contre " + J1.getScore() + " points pour " + J1 + ".");
		else
			System.out.println(J1 + " et " + J2 + " sont à égalité avec " + J1.getScore() + " points chacuns.");
		return;
	}
	
	private void calculerScore() {
		J1.calculerScore(goban);
		J2.calculerScore(goban);
		return;
	}
	private Couleur choixCouleur() {
		Couleur couleur = Couleur.Vide;
		String str;
		do {
	    	System.out.println("De quelle couleur veut être le premier joueur ? ('Blanc' ou 'Noir')");
			sc = new Scanner(System.in);
			if(sc.hasNextLine()) {
				 str = new String(sc.nextLine());
				if(str.compareToIgnoreCase("Blanc") == 0) {
					couleur = Couleur.Blanc;
				}else if(str.compareToIgnoreCase("Noir") == 0) {
					couleur = Couleur.Noir;
				}
			} else {
				System.out.println("Entrez 'Blanc' ou 'Noir'.");
			}
		}while(couleur == Couleur.Vide);
		return couleur;
	}
	
	final private void commentJouer() {
    	System.out.println("\nEntrez les coordonées de la pierre que vous voulez placer sous la forme (1;19).\n\n");
    	return;
	}

	final private void regles() {
    	System.out.println("\nDeux camps s'opposent pour obtenir le plus grand territoire : "
    			+ "les pierres blanches et les pierres noires.\n"
    			+ "Les joueurs peuvent passer leur tour.\n"
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
