import java.util.Vector;

public class Euler5 {
	/*
	 * the minium multiple can be obtained by multiplying prime factors of all
	 * numbers in list. Care has to be taken to make sure overlapping prime
	 * factors are only counted once.
	 * 
	 * Stratargy is: Get top half of list, as their prime factors will be a
	 * superset of the lower halfs.For each number, find its prime factor 1)
	 * Test against each prime factors already found (for all number done in
	 * list so far), to prevent duplicates And divde any with a modules of 0
	 * into it 2) With the remaining number, test it againast all number less
	 * then it root Add any successes to the list of prime factors Times all
	 * prime factor together
	 * 
	 * Remeber the same prime factor may exist multplie times, i/e 2,2,3 is a
	 * valid list of prime factors
	 */
	public static void main(String[] args) {
		//for big lists, prime factors could be kept in order so that when
		//tempdivisor becomes too small parts of the list could be skipped
		Vector<Integer> primeFactors = new Vector<Integer>();
		int maxDivisor = 20;
		// all numbers below half are factors in a number above half
		int minTestDivisor = (int) Math.floor(maxDivisor / 2);
		// this wont work if min test is 4 or less
		// assumes sqrt of min is less then half
		for (int divisor = minTestDivisor; divisor <= maxDivisor; divisor++) {
			System.out.println("Factoring " + String.valueOf(divisor));
			// first test against existing listing
			int tempDivisor = divisor;
			System.out.println("testing existing factors");
			for (int existingPotentialFactor : primeFactors) {
				System.out.println("test factor: "+existingPotentialFactor);
				if (tempDivisor % existingPotentialFactor == 0) {
					System.out.println(String.valueOf(existingPotentialFactor)+" was a factor");
					tempDivisor = tempDivisor / existingPotentialFactor;
					System.out.println("unfactored remaining: "+String.valueOf(tempDivisor));
				}
			}
			// then brute force check if anymore factors
			int newPotentialFactor = 2;
			System.out.println("brute testing for new factors");
			while (newPotentialFactor <= Math.sqrt(tempDivisor)) {
				System.out.println("test factor: "+newPotentialFactor);
				if (tempDivisor % newPotentialFactor == 0) {
					System.out.println(String.valueOf(newPotentialFactor)+" was a factor");
					tempDivisor = tempDivisor / newPotentialFactor;
					System.out.println("unfactored remaining: "+String.valueOf(tempDivisor));
					primeFactors.add(newPotentialFactor);
				} else {
					// only increment when new_potential_factor!=0
					// incase it has 2 or more of the same prime factor, i.e. 8
					// has 2,2,2
					newPotentialFactor += 1;
				}
				//check performance, may get speed up by breaking if temp divisor is in primeFactor
			}
			//the remaining tempDivisor must be prime by now
			System.out.println(String.valueOf(tempDivisor)+" was a factor");
			primeFactors.add(tempDivisor);
		}
		int minMultiple = 1;
		for (int factor : primeFactors) {
			minMultiple *= factor;
		}
		System.out.println("Min multiple is " + String.valueOf(minMultiple));
	}
}