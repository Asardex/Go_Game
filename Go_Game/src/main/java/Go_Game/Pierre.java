package Go_Game;

import javax.swing.text.html.ImageView;

public class Pierre {
	private Couleur couleur;
	private ImageView image;
	
	Pierre(Couleur couleur)
	{
		this.couleur= couleur;
	}
	public Couleur getCouleur()
	{
		return couleur;
	}

}
