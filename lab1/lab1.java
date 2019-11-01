import java.io.*;
import java.util.Scanner;
public class lab1
{
		private static Scanner scanner;
		public static String[] getCoord()
		{
			String point = scanner.nextLine();
			return point.split(" ");
		}
		public static double computeArea(Point3d APoint, Point3d BPoint, Point3d CPoint)
		{
			double a, b, c, p;
			a=APoint.distanceTo(BPoint);
			b=BPoint.distanceTo(CPoint);
			c=CPoint.distanceTo(APoint);
			p=(a+b+c)/2.0;
			if (a < b+c && b < a+c && c < a+b)
				return Math.sqrt(p*(p-a)*(p-b)*(p-c));
			else
				System.out.println("It is not a triangle");
			return 0;
		}
		public static void main(String[] args)
		{
			scanner = new Scanner(System.in);
			Scanner scanner = new Scanner(System.in);
			Point3d FirstPoint=new Point3d(getCoord());
			Point3d SecondPoint=new Point3d(getCoord());
			Point3d ThirdPoint=new Point3d(getCoord());
			System.out.println(computeArea(FirstPoint,SecondPoint,ThirdPoint));
			if (FirstPoint.isEqual(SecondPoint))
				System.out.println(FirstPoint.getX());
			System.out.println(FirstPoint.distanceTo(ThirdPoint));
			scanner.close();
		}
}