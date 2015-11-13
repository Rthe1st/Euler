import Data.Char
import System.IO
import Data.List
import System.Environment

main = do
    args <- getArgs
    case args of
        [searchFile,needleLength,"maxima"] ->
            runMaxima searchFile needleLength
        [searchFile,needleLength,"naive"] ->
            runNaive searchFile needleLength
        [searchFile,needleLength] ->
            runNaive searchFile needleLength
        _ -> putStrLn "Requires arguments: fileName needleLength [algorithm]\n where algorithm must be one of: naive, maxima. Defaults to naive"

runMaxima :: String -> String -> IO()
runMaxima searchFile needleLength = do
    contents <- readFile searchFile
    let intList = map digitToInt contents
    output (findLargestMaxima intList (read needleLength))

runNaive :: String -> String -> IO()
runNaive searchFile needleLength = do
    contents <- readFile searchFile
    let intList = map digitToInt contents
    output (findMaxProductNaive intList (read needleLength) 0)

output :: Int -> IO()
output maxProduct = do
    putStrLn "Max product was :"
    print maxProduct

{-
Basic algorithm, literally move through the list, evaluating the product of the first n elements
If that product is larger then the previous largest, remember it.
-}
findMaxProductNaive :: [Int] -> Int -> Int -> Int
findMaxProductNaive hayStack needleLength maxProduct
	| length hayStack < needleLength = maxProduct
	| otherwise = findMaxProductNaive (tail hayStack) needleLength (max currentProduct maxProduct)
	where currentProduct = product (take needleLength hayStack)

{-
More complex
At a given point in the string let the current "needle" be n, n+1... n+m and have product p(n, n+m)
If the next digit in the string (n+m+1) is less then n, then p(n+1,n+m+1) will be less then p(n, n+m)
By induction then all following products will be less then the original p(n, n+m) until a n+m+1 > n.
This can be seen as a local minima.
The reverse applies for finding maximas, if n+m+1 > n, then p(n+1, n+m+1) > p(n, n+m)
Thus, from the start
1) Evaluate the next digit (n+m+1) against n until a local minima is reached
2) Then repeat until a maxima is reached.
3) Then remember this local maxima and repeat
4) Repeat 1, 2 and 3 until end of search string
Eventually all local maximas will be found, at which point the largest can be selected as the answer

Haven't profiled, but should (maybe...) be faster then naive method because for each digit advanced in the search string
only one > or < comparison is being done,
verses n multiply operations in the naive method
-}

findLargestMaxima :: [Int] -> Int -> Int
findLargestMaxima digitList needleLength = case digitList of
	[] -> 0
	_ -> max maxima (findLargestMaxima afterMaxima needleLength)
	where remaingDigitList = findFirstMaximaPosition (findFirstMinimaPosition digitList needleLength) needleLength
   	      afterMaxima = drop needleLength remaingDigitList
   	      maxima = product (take needleLength remaingDigitList)

findFirstMinimaPosition :: [Int] -> Int -> [Int]
findFirstMinimaPosition digitList needleLength
	| length digitList == needleLength+1 = digitList
	| head digitList < digitList!!needleLength = digitList
	| head digitList >= digitList!!needleLength = findFirstMinimaPosition (tail digitList) needleLength

findFirstMaximaPosition :: [Int] -> Int -> [Int]
findFirstMaximaPosition digitList needleLength
	| length digitList == needleLength+1 = tail digitList
	| head digitList > digitList!!needleLength = digitList
	| head digitList <= digitList!!needleLength = findFirstMaximaPosition (tail digitList) needleLength