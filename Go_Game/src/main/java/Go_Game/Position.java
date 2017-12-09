package Go_Game;

public class Position {
	private int x;
	private int y;
	
	Position(int a, int b)
	{
		this.x = a;
		this.y = b;
	}

	public int getX()
	{
		return x;
	}
	public void setX(int a)
	{
		if ((a>=0)&&(a<=18))
		{
			this.x=a;
		}
	}
	public int getY()
	{
		return y;
	}
	public void setY(int a)
	{
		if ((a>=0)&&(a<=18))
		{
			this.y=a;
		}
	}
	
}
