import java.lang.Integer;
import java.util.Arrays;

public class Euler4 {
    //Find the largest palindrome from the product of two 3 digit numbers

    //done by treating palindromes as arrays of digits rather then numbers
    //1 find highest palindrome (9..9779..9)
    //2 attempt to factorise
    //      if valid factors, this will be your highest valid palindrome
    //3 decrease the value of the left and right halves by the 1
    //      99977999, 89977998, so on
    //repeat 2 and 3
    //if a palindrome with factor lengths below the specifed number of digits occurs
    //then no valid palindrome exists
    public static void main(String[] args) {
        int numOfDigits;
        try {
            numOfDigits = Integer.parseInt(args[0]);//allowed in multipliers
            if (numOfDigits < 3) {
                throw new IllegalArgumentException("too small");
            } else if (numOfDigits > 4) {
                //switch to using longs to get round this
                //99999*99999 is more then 2^32
                throw new IllegalArgumentException("too big");
            }
        } catch (NumberFormatException numberFormatException) {
            System.out.println("This program an integer as a parameter");
            return;
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.out.println("This program takes one parameter");
            return;
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("This program expects a parameter >= 3");
            return;
        }
        String nines = "";
        for (int i = 0; i < numOfDigits; i++) {
            nines += "9";
        }
        int maxFactor = Integer.parseInt(nines);
        System.out.println("max factor: " + maxFactor);
        int maxResultLength = numOfDigits * 2;
        System.out.println("max digits: " + maxResultLength);
        int minFactor = (int) Math.pow(10, numOfDigits - 1);
        System.out.println("min factor: " + minFactor);
        int minResultLength = numOfDigits + (numOfDigits - 1);
        System.out.println("min digits: " + minResultLength);
        //doesnt work for 2 or less digits in factors
        //highest palindrome will be 9..9779..9
        int[] palindromeDigits = new int[maxResultLength];
        Arrays.fill(palindromeDigits, 9);
        palindromeDigits[(maxResultLength / 2) - 1] = 7;
        palindromeDigits[maxResultLength / 2] = 7;
        int testPalindrome = assemblePalindrome(palindromeDigits);
        boolean nonePossible = Integer.toString(testPalindrome).length() < minResultLength;
        System.out.println("First palindrome is: " + testPalindrome);
        while (!hasValidFactor(testPalindrome, minFactor, maxFactor) && !nonePossible) {
            palindromeDigits = decreasePalindrome(palindromeDigits);
            testPalindrome = assemblePalindrome(palindromeDigits);
            System.out.println("next palindrome is: " + testPalindrome);
            nonePossible = Integer.toString(testPalindrome).length() < minResultLength;
        }
        if (nonePossible) {
            System.out.println("No valid palindromes found");
        } else {
            System.out.println("Highest valid palindrome is " + testPalindrome);
        }
    }

    public static int assemblePalindrome(int[] digits) {
        int assembledPalindrome = 0;
        for (int digit = 0; digit < digits.length; digit += 1) {
            assembledPalindrome += digits[digit] * (int) Math.pow(10, digit);
        }
        return assembledPalindrome;
    }

    public static int[] decreasePalindrome(int[] palindromeDigits) {
        if(palindromeDigits[0] == 0){
            throw new IllegalArgumentException("first digit was 0");
        }
        int midPoint = (int) Math.floor(palindromeDigits.length / 2);
        boolean isOdd = palindromeDigits.length % 2 == 1;
        if (palindromeDigits[0] == 1) {
            palindromeDigits = new int[palindromeDigits.length - 1];
            Arrays.fill(palindromeDigits, 9);
        }else if (!isOdd && palindromeDigits[midPoint] == 0){
            palindromeDigits[midPoint] -= 1;
        }else {
            //starting from the centre, going outward (left and right simultaneously)
            //set all 0s to 9s
            //as soon as you find a non-zero, decrease it and stop
            if(isOdd) {
                palindromeDigits[midPoint] = 9;
            }
            for (int digit = midPoint - 1; digit >= 0; digit--) {
                int mirrorDigit = palindromeDigits.length - 1 - digit;
                if (palindromeDigits[digit] == 0) {
                    palindromeDigits[digit] = 9;
                    palindromeDigits[mirrorDigit] = 9;
                } else {
                    palindromeDigits[digit] -= 1;
                    palindromeDigits[mirrorDigit] -= 1;
                    break;
                }
            }
            assert false;//should never reach here
        }
        return palindromeDigits;
    }

    public static boolean hasValidFactor(int number, int minFactor, int maxFactor) {
        double root = Math.sqrt(number);
        int startUpperFactor = (int) Math.ceil(root);
        int startLowerFactor = (int) Math.floor(root);
        if (root == startLowerFactor) {
            System.out.println("Found valid factors, was sqrt " + startLowerFactor);
            return true;
        }
        //from here factors will always be on opposite sides of the square root
        for (int lowerFactor = startLowerFactor; lowerFactor >= minFactor; lowerFactor--) {
            //shortcut, as lowerFactor is in the outer loop
            //test numbers only get smaller with each iteration
            //if they're already too small, no point in continuing
            if (lowerFactor * maxFactor < number) {
                System.out.println("None possible, lowerFactor (" + lowerFactor + ")*maxFactor(" + maxFactor + ") too low");
                return false;
            }
            for (int upperFactor = startUpperFactor; upperFactor <= maxFactor; upperFactor++) {
                int result = upperFactor * lowerFactor;
                if (result == number) {
                    System.out.println("Found valid factors, lower: " + lowerFactor + ", upper: " + upperFactor);
                    return true;
                }else if (result > number) {
                    //similar shortcut to outer loop, but checking for too large instead of too small
                    //because lowerFactor will reduce results size in future iterations
                    //only a break can be done (not a return)
                    System.out.println("breaking, lowerFactor (" + lowerFactor + ")*upperFactor(" + upperFactor + ") too high");
                    break;
                }
            }
        }
        //this only occurs is the palindrome is less then minFactor*maxFactor
        //and has no valid factors, so none of the shortcuts apply
        System.out.println("None possible, no short cuts taken");
        return false;
    }
} 