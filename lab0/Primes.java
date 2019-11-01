public class Primes{
	public static boolean isPrime(int n)
	{
		for (int i =2;i<=Math.sqrt(n)+1;i++)
			if (n%i==0)
				return true;
		return false;
	}
	public static void main(String[] args)
	{
		for (int n=3;n<=100;n++)
			if  (!isPrime(n))
				System.out.println(n);
	}
}