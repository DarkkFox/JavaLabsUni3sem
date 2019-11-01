public class Point3d
{
	private double X;
	private double Y;
	private double Z;
	public Point3d(double x, double y,double z)
	{
		X=x; Y=y; Z=z;	
	}
	public Point3d(String[] coords)//double x, double y,double z)
	{
		this(Double.parseDouble(coords[0]),
			Double.parseDouble(coords[1]),
			Double.parseDouble(coords[2]));
	}
	public Point3d()
	{
		this(0,0,0);
	}
	public double getX()
	{
		return X;
	}
	public double getY()
	{
		return Y;
	}
	public double getZ()
	{
		return Z;
	}
	public void setX(double val)
	{
		X=val;
	}
	public void setY(double val)
	{
		Y=val;
	}
	public void setZ(double val)
	{
		Z=val;
	}
	public boolean isEqual(Point3d OtherPoint3d)
	{
		if (X==OtherPoint3d.getX() && Y==OtherPoint3d.getY() && Z==OtherPoint3d.getZ())
			return true;
		return false;
	}
	public double distanceTo(Point3d OtherPoint3d)
	{
		double distanceX,distanceY,distanceZ;
		distanceX=(X-OtherPoint3d.getX())*(X-OtherPoint3d.getX());
		distanceY=(Y-OtherPoint3d.getY())*(Y-OtherPoint3d.getY());
		distanceZ=(Z-OtherPoint3d.getZ())*(Z-OtherPoint3d.getZ());
		return Math.sqrt(distanceX+distanceY+distanceZ);
	}
}