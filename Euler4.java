public class Euler4 
{
    public static void main(String[] args){ 
        int numOfDigits = 3;//Integer.parseInt(args[0]);//allowed in multipliers 
        double nines = 9+(1-Math.pow(10, -numOfDigits)); 
        int minFactor = (int)Math.pow(10, numOfDigits-1); 
        int minResultLength = digitCount((int)Math.pow(minFactor,2)); 
        int maxFactor = (int)Math.floor(nines*Math.pow(10, numOfDigits-1)); 
        int maxResultLength = digitCount((int)Math.pow(maxFactor,2)); 
        System.out.println("min digits: "+minResultLength); 
        System.out.println("max digits: "+maxResultLength);
        System.out.println("min factor: "+minFactor); 
        System.out.println("max factor: "+maxFactor);
        int[] palendromeDigits = new int[maxResultLength]; 
        int highestFirstHalfNines = (palendromeDigits.length-2)/2; 
        //hack is fucked for 2 digit factors or less
        //hack, 9...x9...=9...80...1, and is always even length 
        //so highest palendrome will be 9...779... 
        for(int digit=0;digit<highestFirstHalfNines;digit++){ 
            palendromeDigits[digit]=9; 
        } 
        palendromeDigits[highestFirstHalfNines] = 7; 
        palendromeDigits[highestFirstHalfNines+1] = 7; 
        for(int digit=highestFirstHalfNines+2;digit<palendromeDigits.length;digit++){ 
            palendromeDigits[digit]=9; 
        } 
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
    public static int digitCount(int number){ 
        return (Integer.toString(number)).length(); 
    } 
    public static int assemblePalendrome(int[] digits){ 
        int assembledPalendrome = 0; 
        for(int digit=0;digit<digits.length;digit+=1){ 
            assembledPalendrome += digits[digit]*(int)Math.pow(10,digit); 
        } 
        return assembledPalendrome; 
    } 
    public static int[] decreasePalendrom(int[] palendromeDigits){ 
        int midPoint = (int)Math.floor(palendromeDigits.length/2);
        boolean evenLength = palendromeDigits.length%2==0;
        if(palendromeDigits[0]==1){
            palendromeDigits= new int[palendromeDigits.length-1]; 
            for(int digit= 0;digit<palendromeDigits.length;digit++){ 
                palendromeDigits[digit]=9; 
            }
            return palendromeDigits;
        }
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