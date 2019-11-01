public class Point2d
{
	private double X;
	private double Y;
	public Point2d(double x, double y)
	{
		X=x; Y=y;	
	}
	public Point2d()
	{
		this(0,0);
	}
	public double getX()
	{
		return X;
	}
	public double getY()
	{
		return Y;
	}
	public void setX(double val)
	{
		X=val;
	}
	public void setY(double val)
	{
		Y=val;
	}
}