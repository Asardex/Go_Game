package Go_Game;
/**
 * Classe App, point de lancement de l'application
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Joueur J1 = new Joueur(Couleur.Blanc);
    	Joueur J2 = new Joueur(Couleur.Blanc);
    	regles();
    	
    	return;
    }
    
    public static void regles() {
    	System.out.println("Deux camps s'opposent pour obtenir le plus grand territoire : "
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
    			+ "a capturés");
    	
    	return;
    }
}
