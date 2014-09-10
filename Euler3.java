
import java.util.*;
public class Euler3
{
	public static void main(String[] args)
	{
		long largestFactor = Long.parseLong(args[0]);
		long divisor = 2;
		boolean foundLargePrime = false;
		while(!foundLargePrime){
			if(largestFactor%divisor == 0 && largestFactor > divisor){
				largestFactor = largestFactor/divisor;
				System.out.println("New factor: "+largestFactor);
				System.out.println("Non max prime factor: "+divisor);
			}else if(divisor >= Math.sqrt(largestFactor)){
				foundLargePrime = true;
			}
			else{
				divisor += 1;
			}
		}
		System.out.println("largest prime factor is:"+largestFactor);
	}
}