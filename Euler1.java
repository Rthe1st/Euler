public class Euler1
{
	public static void main(String[] args)
	{
		int[] factors = {3,5};
		int sum = 0;
		int limit = 1000-1;
		int dupVal = 1;
		for(int factor : factors){
			int factorLimit = limit/factor;
			System.out.println("limit for "+factor+" is "+factorLimit);
			//n*(n+1)/2
			int factorSum = (factorLimit*(factorLimit+1))/2;
		    factorSum *= factor;
			sum += factorSum;
			dupVal *= factor;
		}
		//subtract duplicates
		int dupLimit = limit/dupVal;
	    int dupSum = (dupLimit*(dupLimit+1))/2;
		dupSum *= dupVal;
		sum -= dupSum;
		System.out.println("sum is "+sum);
	}
}