import Data.List

{-
Find sum of all primes less then 2 million
-}

getNumList :: [Int]
getNumList = [2..2000000]

removeMultiples :: Int -> [Int] -> [Int]
removeMultiples prime numList = [prime, prime*2..maxMultiple]
    where lastInt = last numList
          maxMultiple = floor (toRational lastInt / toRational prime)