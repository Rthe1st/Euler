public class Euler6 {
	/*
	 * Takes a single int argument, which is N, and computes
	 * sum(1...N)^2-sum(1^2...N^2)
	 * 
	 * Uses formalue for summing 1...N
	 * Doesnt use formulae for summing squares but only because
	 * I refused to google one and forgot how to math
	 */

	public static void main(String[] args) {
		int maxSquare;
		try {
			maxSquare = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.err.println("Argument" + args[0] + " must be an integer.");
			throw e;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err
					.println("Euler6 computes sum of squares vs sqaure of sums for 1-N, n must be supplied as an argument");
			throw e;
		}
		int sum1toN = (maxSquare * (maxSquare + 1)) / 2;
		int sqOfSum = sum1toN * sum1toN;
		int sumOfSquares = 0;
		for (int i = 1; i <= maxSquare; i++) {
			sumOfSquares += i * i;
		}
		int difference = sqOfSum - sumOfSquares;
		System.out.print("square of sum - sum of squares = " + difference);
	}

}