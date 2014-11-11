public class Euler1
{
    //sum non-duplicate multiples of 3 and 5 below 1000
    //uses short cut based on 3+6+9=3(1)+3(2)+3(3)=3(1+2+3)
    //this allows you to use the sumation formuale n(n+1)/2
    //where n is the largest multiple of a factor below the limit

    //to remove duplicates repeat this trick on the smallest common multiple of both factors
    //and subtract the sum
    //WARNING this duplicate trick only works for 2 factors.
    //If x number of factors where used, then when subtracting duplicates,
    //one subtraction would be needed for each combination of factors
    //i.e. {2,3,5} would require, 2*3, 2*5, 3*5 and 2*3*5 to be dealt with
    public static void main(String[] args)
	{
		int[] factors = {3,5};
        if(factors.length != 2){
            System.out.println("This program will only run correctly for input of 2 factors");
            return;
        }
        int sumOfFactors = 0;
		int totalSum = 0;
		int limit = 1000-1;//-1 because multiples must be LESS THEN limit
		int smallestCommonFactor = 1;
		for(int factor : factors){
            int factorLimit = limit/factor;
			totalSum += factor*sum1To(factorLimit);
			smallestCommonFactor *= factor;
		}
		//subtract duplicates
		int scfLimit = limit/smallestCommonFactor;
		totalSum -= smallestCommonFactor*sum1To(scfLimit);
		System.out.println("sum is "+totalSum);
	}
    private static int sum1To(int n){
        return (n*(n+1))/2;
    }
}