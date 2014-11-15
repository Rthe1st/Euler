import java.util.Vector;

public class Euler5 {
	/*
	 * the minimum multiple can be obtained by multiplying prime factors of all
	 * numbers in list. Care has to be taken to make sure overlapping prime
	 * factors are only counted once.
	 *
	 * For each number in divisor list
	 * 1) Remove any prime factors that have already been found from the divisor
	 * 2) Add all remaining prime factors to prime factor list
	 *	Then multiply all prime factors in list to get the solution
	 *
	 * Shortcut:
	 * Because every divisor in the lower half of the list is a factor
	 * of one in the upper half, only the upper half need be considered
	 *
	 * Remember the same prime factor may exist multiple times, i/e 2,2,3 is a
	 * valid list of prime factors and not the same as 2,2,2,3 (for example)
	 *
	 * Idea: for big lists, prime factors could be kept in order so that when
     * temp divisor becomes too small parts of the list could be skipped
     * Would have to test because insertion operation may be expensive
	 */
	public static void main(String[] args) {
		int maxDivisor;
		try {
			maxDivisor = Integer.parseInt(args[0]);
			if(maxDivisor <= 2){
				throw new IllegalArgumentException();
			}
		}catch (NumberFormatException numberFormatException){
			System.out.println("This program an integer as a parameter");
			return;
		}catch(ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
			System.out.println("This program take one parameter");
			return;
		}catch(IllegalArgumentException illegalArgumentException){
			System.out.println("This program expects a parameter > 2");
			return;
		}
		Vector<Integer> primeFactors = new Vector<Integer>();
		// all numbers below half are factors in a number above half
		int minTestDivisor = (int) Math.floor(maxDivisor / 2);
		// assumes sqrt of min is less then half
		for (int divisor = minTestDivisor; divisor <= maxDivisor; divisor++) {
			System.out.println("Factoring " + String.valueOf(divisor));
			int remainingDivisor = removeOverlappingFactors(divisor, primeFactors);
			primeFactors.addAll(findPrimeFactors(remainingDivisor));
		}
		int minMultiple = 1;
		for (int factor : primeFactors) {
			minMultiple *= factor;
		}
		System.out.println("Min multiple is " + String.valueOf(minMultiple));
	}

	static int removeOverlappingFactors(int divisor, Vector<Integer> primeFactors) {
		for (int existingPotentialFactor : primeFactors) {
			if (divisor % existingPotentialFactor == 0) {
				divisor = divisor / existingPotentialFactor;
			}
		}
		return divisor;
	}

	static Vector<Integer> findPrimeFactors(int number) {
		int newPotentialFactor = 2;
		Vector<Integer> primeFactors = new Vector<Integer>(0);
		while (newPotentialFactor <= Math.sqrt(number)) {
			// only increment when new_potential_factor != 0
			// in case it has 2 or more of the same prime factor
			// i.e. 8 has 2,2,2
			if (number % newPotentialFactor == 0) {
				number = number / newPotentialFactor;
				primeFactors.add(newPotentialFactor);
			} else {
				newPotentialFactor += 1;
			}
		}
		//the remaining tempDivisor must be prime by now
		primeFactors.add(number);
		return primeFactors;
	}
}