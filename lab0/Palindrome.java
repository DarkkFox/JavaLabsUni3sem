public class Palindrome
{
	public static String reverseString(String s)
	{
		String new_s="";
		for(int i=s.length()-1;i>=0;i--)
			new_s += s.charAt(i);
		return new_s;
	}
	public static boolean isPalindrome(String s)
	{
		if (s.equals(reverseString(s)))
			return true;
		return false;
	}
	public static void main(String[] args)
	{
		for(int i=0;i<args.length;i++)
			if (isPalindrome(args[i]))
				System.out.println(args[i]);
	}
}