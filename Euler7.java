import java.util.*;

public class Euler7
{
	/*
	 * Uses euclidean sieve to get all primes up to Nth prime
	 * Then reports Nth prime
	 * Because no upper boundary for the nth primes is known,
	 * a section of length sectionSize is sieved at a time
	 * So 1 to x-1 is sieved, then x to (x*2)-1 is sieved and so on
	 * 
	 * Tested sections of 1000 vs sections of 1000000 (in which case only 1 section would be used)
	 * They take the same time
	 * 
	 */
	
	public static void main(String[] args)
	{
		int loopNum = 0;
		int sectionSize = 1000000;
		Vector<Integer> primes = new Vector<>();
		while(true){
			System.out.println("prime count is "+primes.size());
			boolean[] tests = new boolean[sectionSize];
			int sectionMax = ((loopNum+1)*sectionSize)-1;
			int sectionMin = loopNum*sectionSize;
			for(int prime : primes){
				tests = sieveMultiples(sectionMin,sectionMax,prime,tests,sectionSize);
			}
			for(int test=sectionMin; test<=sectionMax;test++){
				//bit of a hack, 1 and 0 are special cases
				//could be done by off setting all sections by 2 so for section size 100;
				// 2-101, 102-202 instead of 1-99, 100-199 as currently done
				if(test==1 || test==0){
					continue;
				}
				if(!tests[test%sectionSize]){
					primes.add(test);
					System.out.println("found prime  "+test);
					if(primes.size() == 10001){
						System.out.println("prime 10001 is "+test);
						return;
					}
                    tests = sieveMultiples(sectionMin,sectionMax,test,tests,sectionSize);
				}
			}
			loopNum+=1;
		}
	}
	
	private static boolean[] sieveMultiples(int sectionMin, int sectionMax, int test, boolean[] tests, int sectionSize){
		int lowestMultiple = test*(int)Math.ceil((double)sectionMin/(double)test);
		int highestMultiple = test*(int)Math.floor(sectionMax/test);
		for(int multiple=lowestMultiple;multiple<=highestMultiple;multiple+=test){
			tests[multiple%sectionSize] = true;
		}
		return tests;
	}
}