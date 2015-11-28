{-
Strategy:

Parse input into 20*20 array structure
Find the max product for each:
    Row
    Column
    Diagonal
    Perpendicular diagonal

Nothing fancy, for each Row/column.diagonal find max for current "line", then compare to next line.
Each diagonal is done twice, from corner to middle, then middle to corner

-}
import Data.Char
import System.IO
import Data.List
import System.Environment

main = do
    args <- getArgs
    case args of
        [searchFile,needleLength] -> do
            content <- readFile searchFile
            let stringGrid = map words (lines content)
            let grid = map (map readInt) stringGrid
            let height = length grid
            let width = length (grid!!0)
            let maxProduct = maximum [findLargestLine grid [(+1), (+0)] [(+0), (+1)] [0,0] 4,
                                      findLargestLine grid [(+0), (+1)] [(+1), (+0)] [0,0] 4,
                                      findLargestLine grid [(+1), (+0)] [(subtract 1), (+1)] [0,0] 4,
                                      findLargestLine grid [(+0), (+1)] [(subtract 1), (+1)] [width,0] 4,
                                      findLargestLine grid [(+0), (subtract 1)] [(+1), (+1)] [0,height] 4,
                                      findLargestLine grid [(+1), (+0)] [(+1), (+1)] [0,0] 4]
            putStrLn "Max product was :"
            print maxProduct
        _ -> putStrLn "Requires arguments: fileName needleLength [algorithm]\n where algorithm must be one of: naive, maxima. Defaults to naive"

readInt :: String -> Int
readInt = read

findLargestLine :: [[Int]] -> [Int -> Int] -> [Int -> Int] -> [Int] -> Int -> Int
findLargestLine grid changeStart changePosition cords@([x,y]) needleLength
    | not $ areCordsValid grid cords = 0
    | otherwise = max (findLargestProduct grid changePosition cords needleLength) (findLargestLine grid changeStart changePosition nextCords needleLength)
    where nextCords = cordChange changeStart cords

findLargestProduct :: [[Int]] -> [Int -> Int] -> [Int] -> Int -> Int
findLargestProduct grid changePosition cords@([x,y]) needleLength
    | not $ areCordsValid grid cords = 0
    | otherwise = max (getProduct grid changePosition cords needleLength) (findLargestProduct grid changePosition nextCord needleLength)
    where nextCord@([nX, nY]) = cordChange changePosition cords

getProduct :: [[Int]] -> [Int -> Int] -> [Int] -> Int -> Int
getProduct grid changePosition cords@([x,y]) remainingNeedle
    | remainingNeedle == 0 = 1
    | not $ areCordsValid grid cords = 1
    | otherwise = currentInt * (getProduct grid changePosition nextCords (remainingNeedle - 1))
    where currentInt = (grid!!y)!!x
          nextCords = cordChange changePosition cords

cordChange :: [Int -> Int] -> [Int] -> [Int]
cordChange changeFunctions cords = zipWith (\a b -> a b) changeFunctions cords

areCordsValid :: [[a]] -> [Int] -> Bool
areCordsValid grid cords@([x,y]) = (0 <= x && x < width && 0 <= y && y < height )
    where width = (length (grid!!0))
          height = (length grid)