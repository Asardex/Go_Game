package Go_Game;

public class Goban {
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

	Goban ()
	{
		
	}
	
	public void afficherGrille ()
	{
		for(int i = 0; i < grille.length; i++)
		{
			for(int j = 0; j < grille.length; j++)
			{
				System.out.print(" " + grille[i][j]);
			}
			System.out.println("\n");
		}
	}
	
}
