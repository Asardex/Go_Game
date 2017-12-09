package Go_Game;

public class Position {
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

	public int getX()
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
	
	public int getY()
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
}
