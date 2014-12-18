import Data.Char

searchString = "73167176531330624919225119674426574742355349194934\
\96983520312774506326239578318016984801869478851843\
\85861560789112949495459501737958331952853208805511\
\12540698747158523863050715693290963295227443043557\
\66896648950445244523161731856403098711121722383113\
\62229893423380308135336276614282806444486645238749\
\30358907296290491560440772390713810515859307960866\
\70172427121883998797908792274921901699720888093776\
\65727333001053367881220235421809751254540594752243\
\52584907711670556013604839586446706324415722155397\
\53697817977846174064955149290862569321978468622482\
\83972241375657056057490261407972968652414535100474\
\82166370484403199890008895243450658541227588666881\
\16427171479924442928230863465674813919123162824586\
\17866458359124566529476545682848912883142607690042\
\24219022671055626321111109370544217506941658960408\
\07198403850962455444362981230987879927244284909188\
\84580156166097919133875499200524063689912560717606\
\05886116467109405077541002256983155200055935729725\
\71636269561882670428252483600823257530420752963450"

searchStringAsIntList = (map digitToInt searchString)
needleLength = 13

{-
Basic algorithm, literally move through the list, evaluating the product of the first n elements
If that product is larger then the previous largest, remember it.
-}
fakeMain1 = findMaxProductNaive searchStringAsIntList 0

findMaxProductNaive :: [Int] -> Int -> Int
findMaxProductNaive hayStack maxProduct
	| length hayStack < needleLength = maxProduct
	| otherwise = findMaxProductNaive (tail hayStack) (max currentProduct maxProduct)
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
	
fakeMain2 = findLargestMaxima (map digitToInt searchString)

findLargestMaxima :: [Int] -> Int
findLargestMaxima digitList = case digitList of
	[] -> 0
	_ -> max maxima (findLargestMaxima afterMaxima)
	where remaingDigitList = findFirstMaximaPosition (findFirstMinimaPosition digitList)
   	      afterMaxima = drop needleLength remaingDigitList
   	      maxima = product (take needleLength remaingDigitList)

findFirstMinimaPosition :: [Int] -> [Int]
findFirstMinimaPosition digitList
	| length digitList == needleLength+1 = digitList
	| head digitList < digitList!!needleLength = digitList
	| head digitList >= digitList!!needleLength = findFirstMinimaPosition (tail digitList)

findFirstMaximaPosition :: [Int] -> [Int]
findFirstMaximaPosition digitList
	| length digitList == needleLength+1 = tail digitList
	| head digitList > digitList!!needleLength = digitList
	| head digitList <= digitList!!needleLength = findFirstMaximaPosition (tail digitList)