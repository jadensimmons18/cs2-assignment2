/*  Jaden Simmons
    Mystic Sudoku
    COP3503 Computer Science 2
    SudokuSolver.java
*/

public class SudokuSolver {

    int boardSize = 9;
    int totalSolutions = 0;
    int[][] boardCopy = new int[9][9];
    public int solve(int[][] board, int[][] forbiddenPairs){
        solveHelper(board, forbiddenPairs, 0, 0);

        if (totalSolutions == 1)
        {
            copyBoard(boardCopy, board);
        }
        return totalSolutions;
    }
    
    public int solveHelper(int[][] board, int[][] forbiddenPairs, int row, int col){

        if (row == boardSize) // if we move past the last row then the board is solved
        {
            return 1;
        }

        int nextRow, nextCol;

        if (col == 8) 
        { // We are at the end of the row
            nextRow = row + 1;       // Move down
            nextCol = 0;             // Reset to left
        } else 
        {        // Otherwise remain in the same row and incement normally
            nextRow = row;           
            nextCol = col + 1;       // Move right
        }

        // If the space already has a number then move to the next cell
        if (board[row][col] != 0) 
        {
            return solveHelper(board, forbiddenPairs, nextRow, nextCol);
        }

        // try numbers 1-9
        for (int num = 1; num <= 9; num++)
        {
            if (isValidClassic(board, row, col, num) && isValidKnight(board, row, col, num) && isValidForbidden(board, forbiddenPairs, row, col, num)) 
            {   //if its valid 
                board[row][col] = num; // place the number in the cell

                int result = solveHelper(board, forbiddenPairs, nextRow, nextCol); // returns 1 if the board was solved by going down this path otherwise it will return total

                if (result == 1) // If the board is solved copy the solution and add to the solution count
                {
                    totalSolutions++;
                    if (totalSolutions == 1)
                    {
                        copyBoard(board, boardCopy);
                    }
                }
            }
        }
        return 0;
    }

    // Copies the board
    public void copyBoard(int[][] board1, int[][] board2){
        for (int i = 0; i < board1.length; i++){
            for (int j = 0; j < board1[0].length; j++)
            {
                board2[i][j] = board1[i][j];
            }
        }
    }


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
