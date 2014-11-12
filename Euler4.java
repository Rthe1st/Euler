import java.lang.Integer;
import java.util.Arrays;

public class Euler4
{
    //Find the largest palindrome from the product of two 3 digit numbers

    //done by treating palendromes as arrays of digits rather then numbers
    //1 find highest palendrome (9..9779..9)
    //2 attempt to factorise
        //if valid factors, this will be your highest valid palendrome
    //3 decrease the value of the left and right halves by the 1
        //99977999, 89977998, so on
    //repeat 2 and 3
    //if a palendrome with factor lengths below the specifed number of digits occurs
    //then no valid palendrome exists
    public static void main(String[] args){
        int numOfDigits;
        try {
            numOfDigits = Integer.parseInt(args[0]);//allowed in multipliers
            if(numOfDigits < 3){
                throw new IllegalArgumentException("too small");
            }else if (numOfDigits > 4){
                //switch to using longs to get round this
                //99999*99999 is more then 2^32
                throw new IllegalArgumentException("too big");
            }
        }catch (NumberFormatException numberFormatException){
            System.out.println("This program an integer as a parameter");
            return;
        }catch(ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            System.out.println("This program one parameter");
            return;
        }catch(IllegalArgumentException illegalArgumentException){
            System.out.println("This program expects a parameter >= 3");
            return;
        }
        String nines = "";
        for(int i=0; i<numOfDigits;i++){
            nines += "9";
        }
        int maxFactor = Integer.parseInt(nines);
        System.out.println("max factor: "+maxFactor);
        int maxResultLength = numOfDigits*2;
        System.out.println("max digits: "+maxResultLength);
        int minFactor = (int)Math.pow(10, numOfDigits-1);
        System.out.println("min factor: "+minFactor);
        int minResultLength = numOfDigits+(numOfDigits-1);
        System.out.println("min digits: "+minResultLength);
        //doesnt work for 2 or less digits in factors
        //highest palendrome will be 9..9779..9
        int[] palendromeDigits = new int[maxResultLength];
        Arrays.fill(palendromeDigits, 9);
        palendromeDigits[(maxResultLength/2)-1] = 7;
        palendromeDigits[maxResultLength/2] = 7;
        int testPalendrome = assemblePalendrome(palendromeDigits);
        boolean nonePossible = Integer.toString(testPalendrome).length() < minResultLength;
        System.out.println("First palendrome is: "+testPalendrome);
        while(!hasValidFactor(testPalendrome, minFactor, maxFactor) && !nonePossible){ 
            palendromeDigits = decreasePalendrom(palendromeDigits);
            testPalendrome = assemblePalendrome(palendromeDigits);
            System.out.println("next palendrome is: "+testPalendrome);
            nonePossible = Integer.toString(testPalendrome).length() < minResultLength;
        }
        if(nonePossible){
        	System.out.println("No valid palendromes found");
        }else{
        	System.out.println("Highest valid palendrome is "+testPalendrome);
        }
    }
    public static int assemblePalendrome(int[] digits){
        int assembledPalendrome = 0;
        for(int digit=0;digit<digits.length;digit+=1){
            assembledPalendrome += digits[digit]*(int)Math.pow(10,digit);
        }
        return assembledPalendrome;
    }
    public static int[] decreasePalendrom(int[] palendromeDigits){
        if(palendromeDigits[0]==1){
            palendromeDigits= new int[palendromeDigits.length-1];
            for(int digit= 0;digit<palendromeDigits.length;digit++){
                palendromeDigits[digit]=9;
            }
            return palendromeDigits;
        }
        boolean evenLength = palendromeDigits.length%2==0;
        int midPoint = (int)Math.floor(palendromeDigits.length/2);
        if(!evenLength){
       		if(palendromeDigits[midPoint] != 0){
       			palendromeDigits[midPoint] -= 1;
       			return palendromeDigits;
       		}else{
       			palendromeDigits[midPoint] = 9;
       		}
       	}
       	for(int digit=midPoint-1;digit>=0;digit--){
         	int mirrorDigit = palendromeDigits.length-1-digit;
            if(palendromeDigits[digit]==0){
          	    palendromeDigits[digit]=9;
           	    palendromeDigits[mirrorDigit]=9;
           	}else{
           	    palendromeDigits[digit]-=1;
               	palendromeDigits[mirrorDigit]-=1;
               	return palendromeDigits;
           	}
       	}
        //should never reach here
        return palendromeDigits;
    }
    public static boolean hasValidFactor(int number, int minFactor, int maxFactor){
        double root = Math.sqrt(number);
        int startUpperFactor = (int)Math.ceil(root);
        int startLowerFactor = (int)Math.floor(root);
        if(root==startLowerFactor){
        	System.out.println("Found valid factors, was sqrt "+startLowerFactor);
            return true;
        }
        for(int lowerFactor = startLowerFactor; lowerFactor >= minFactor;lowerFactor--){
            if(lowerFactor*maxFactor < number){
            	System.out.println("None possible, lowerFactor ("+lowerFactor+")*maxFactor("+maxFactor+") too low");
                return false;
            }
            for(int upperFactor=startUpperFactor; upperFactor<= maxFactor;upperFactor++){
                int result = upperFactor*lowerFactor;
                if(result == number){
                	System.out.println("Found valid factors, lower: "+lowerFactor+", upper: "+upperFactor);
                    return true;
                }
                if(result > number){
                	System.out.println("breaking, lowerFactor ("+lowerFactor+")*upperFactor("+upperFactor+") too high");
                    break;
                } 
            } 
        } 
        //shouldnt get here 
        return false;
    } 
} 