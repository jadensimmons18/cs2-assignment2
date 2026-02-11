/*  Jaden Simmons
    Mystic Sudoku
    COP3503 Computer Science 2
    SudokuSolver.java
*/




public class SudokuSolver {

    int boardSize = 9;
    public int solve(int[][] board, int[][] forbiddenPairs);


    // Returns true if number is valid via classic constraint
    public boolean isValidClassic(int[][] board, int row, int col, int num){

        // check col
        for (int i = 0; i < boardSize; i++){
            if (board[i][col] == num){
                return false;
            }
        }

        // check row
        for (int i = 0; i < boardSize; i++){
            if (board[row][i] == num){
                return false;
            }
        }

        // check 3x3
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num){
                    return false;
                }
            }
        }
        return true;
    }

    // returns true if number is valid via knights constraint
    public boolean isValidKnight(int[][] board, int row, int col, int num){
        
        // {row,col}
        int[][] possibleMoves = {
            {1,2},
            {2,1},
            {2,-1},
            {1,-2},
            {-1,-2},
            {-2,-1},
            {-2, 1},
            {-1, 2},

        };

        // 8 possible paths that a knight can move
        for (int i = 0; i < possibleMoves.length; i++){
            int targetRow = (row + possibleMoves[i][0] + 9) % 9;
            int targetCol = (col + possibleMoves[i][1] + 9) % 9;

            if (board[targetRow][targetCol] == num) {
                return false;
            }
        }
        return true;
    }

    public boolean isValidForbidden(int[][] board, int[][] forbiddenPairs, int row, int col, int num){
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        
        for (int i = 0; i < directions.length; i++) {
            int neighborRow = (row + directions[i][0] + 9) % 9;
            int neighborCol = (col + directions[i][1] + 9) % 9;
            int neighborVal = board[neighborRow][neighborCol];

            // if neighborval is empty then no need to check forbidden pairs
            if (neighborVal == 0) continue;

            // loops through all forbidden pairs
            for (int j = 0; j < forbiddenPairs.length; j++) {
                int a = forbiddenPairs[j][0];
                int b = forbiddenPairs[j][1];

                // checks if the number mathes the forbidden pair
                if ((num == a && neighborVal == b) || (num == b && neighborVal == a)) {
                    return false;
                }
            }
        }
        return true; 
    }
}
