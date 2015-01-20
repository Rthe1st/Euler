import java.util.*;

public class Euler10
{
	/*
	 * Uses euclidean sieve to get sum of all primes up to N
     * Based on Euler 7, but simplified because we know
     * the upper bound for this problem
	 */
	
	public static void main(String[] args)
	{
		long primeSum = 0;
        int primeCount = 0;
        int maxTest = 2000000;
		boolean[] tests = new boolean[maxTest+1];//0 based
        tests[0] = true;
        tests[1] = true;
		for(int test = 2; test <= maxTest; test++){
			if(!tests[test]){
				primeSum += test;
                primeCount++;
                tests = sieveMultiples(test,tests);
            }
		}
        System.out.println("Sum of primes is "+primeSum);
        System.out.println("Prime count is "+primeCount);
        return;
	}
	
	private static boolean[] sieveMultiples(int prime, boolean[] tests){
		int highestMultiple = prime*((tests.length-1)/prime);
		for(int multiple=prime*2;multiple<=highestMultiple;multiple+=prime){
			tests[multiple] = true;
		}
		return tests;
	}
}