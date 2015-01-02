{-
	Given that:
	a + b + c = 1000
	a^2 + b^2 = c^2
	and a < b < c
	find a*b*c
	
	First, note that a <  b < 333 < c
	because 1000/3 == 333
	If c is less then 333, then a and b be too and a + b + c < 1000
	If a > 333 then b and c will be too, so sum will be more then 1000
	
	Merge the equations to reduce number of unknowns from 3 to 2
	1000 - a - b = c
	a^2 + b^2 = (1000 - a - b)^2
	a^2 + b^2 = a^2 + b^2 + 10^6 + 2ab - 2000a - 2000b
	0 = 2ab - 2000a - 2000b + 10^6
	2000a - 10^6 = 2ab - 2000b
	2000a - 10^6 = 2b(a-1000)
	(1000a - 5*10^5)/(a-1000) = b
	
	If a can be found such that b is an integer, the equation is solved
	Thus, a-1000 must be a factor of 1000a-5*10^5
	The top of the left hand side can be rewritten
	1000a - 5*10^5 = 500(2a-1000) = 500(a + a - 1000) = 500a + 500(a-1000)
	= 500a/(a-1000) + 500 = b
	Find the value of a for which 500a/(a-1000) is an integer
	by checking all values 1 to 333.
	Then plug it into:
	(1000a - 5*10^5)/(a-1000) = b
	1000 - a - b = c
-}

findA :: Int -> Int
findA a
    | aTest == toRational (round aTest) = a
    | otherwise = findA (a-1)
    where aRational = toRational a
          aTest = (500*aRational)/(aRational-1000)

findB :: (Integral n) => n -> n
findB a = (1000*a - (5*10^5)) `div` (a - 1000)

findC :: Int -> Int -> Int
findC a b = 1000 - a - b

findProduct :: Int
findProduct = a*b*c
    where a = findA 333
          b = findB a
          c = findC a b
