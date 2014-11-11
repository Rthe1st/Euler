import java.util.*;
public class Euler3
{
	//find the largest prime factor (of 600851475143)

	//if z is a factor of y, then for all numbers less then sqroot(z) any factor of y must also be a factor of z.
	//let y be the input number
	//starting from 2, x is tested for being a factor and then increased by 1
	//any discoverd factor is divided out of y, i.e. y = y/x
	//thus when a factor is found, it is guranteed to be prime
		//because all numbers less then it were not factors of y and thus also not factors of x.
	//additionaly, when x reaches a value more the sqroot(y), y is known to be prime and so must be the largest prime factor
	public static void main(String[] args)
	{
		long largestFactor;
		try {
			largestFactor = Long.parseLong(args[0]);
			if(largestFactor <= 1){
				throw new IllegalArgumentException();
			}
		}catch (NumberFormatException numberFormatException){
			System.out.println("This program an integer as a parameter");
			return;
		}catch(ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
			System.out.println("This program one parameter");
			return;
		}catch(IllegalArgumentException illegalArgumentException){
			System.out.println("This program expects a parameter >= 2");
			return;
		}
		long divisor = 2;
		boolean foundLargePrime = false;
		while(!foundLargePrime){
			// && largestFactor > divisor is need for edge cases where largestFactor == divisor i.e. for input 2
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