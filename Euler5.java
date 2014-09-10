public class Euler5{
	/*
	the minium multiple can be obtained by multiplying prime factors of all numbers in list.
	Care has to be taken to make sure overlapping prime factors are only counted once.

	Stratagy is:
		Get top half of list, as their prime factors will be a superset of the lower halfs.
		For each number, find its prime factor
			1) Test against each prime factors already found (for all number done in list so far), to prevent duplicates
				And divde any with a modules of 0 into it
			2) With the remaining number, test it againast all number less then it root
				Add any successes to the list of prime factors
		Times all prime factor together

	Remeber the same prime factor may exist multplie times, i/e 2,2,3 is a valid list of prime factors
			
	*/
	public static void main(String[] args)
	{
		Vector<Integer> primeFactors;
		int maxDivisor = 20;
		//all numbers below half are factors in a number above half
		int minTestDivisor = (int)Math.floor(maxDivisor/2);
		//this wont work if min test is 4 or less
		//assumes sqrt of min is less then half
		for(int divisor=minTestDivisor;divisor<=maxDivisor;divisor++){
			//find all prime factor
			//first test against existing listing
			int tempDivisor = divisor;
			for(int existingPotentialFactor:primeFactors){
				if(tempDivisor%existingPotentialFactor == 0){
					tempDivisor = tempDivisor/existingPotentialFactor;
				}
			}
			//then test against all factors it could still have
            int newPotentialFactor = 2;
			while(newPotentialFactor <= Math.sqrt(tempDivisor)){
			  if(tempDivisor%newPotentialFactor==0){
				  tempDivisor = tempDivisor/potentialFactor;
			  	primeFactors.add(newPotentialFactor);
			  }else{
			  	//only increment when new_potential_factor!=0
			  	//incase it has 2 or more of the same prime factor, i.e. 8 has 2,2,2
			  	newPotentialFactor += 1
			  }
		  }
		}
		int minMultiple = 1;
		for(factor : primeFactors){
			minMultiple *= factor
		}
		System.out.println("Min multiple is "+minMultiple.to_string());

