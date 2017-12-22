package Go_Game;

import java.io.Serializable;

public class Position implements Serializable {

	private static final long serialVersionUID = -9185885797101421650L;
	private int x;
	private int y;
	
	Position(int x, int y)
	{
		if ((x >= 0) && (x <= 18))
		{
			this.x = x;
		}
		if ((y >= 0) && (y <= 18))
		{
			this.y = y;
		}
	}

	final public int getX()
	{
		return x;
	}
	public void setX(int x)
	{
		if ((x >= 0) && (x <= 18))
		{
			this.x = x;
		}
		return;
	}
	
	final public int getY()
	{
		return y;
	}
	public void setY(int y)
	{
		if ((y >= 0) && (y <= 18))
		{
			this.y = y;
		}
		return;
	}
	
	public String toString() {
		return "(" + x + ";" + y + ")";
	}
}
