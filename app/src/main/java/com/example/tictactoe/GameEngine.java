package com.example.tictactoe;

import java.util.Random;

class GameEngine {
    private static final Random RANDOM= new Random();
    private char[][] elts;
    private char currentPlayer;
    private char player= 'O';
    private char opponent='X';
    private boolean ended;

    GameEngine() {
        elts= new char[3][3];
        newGame();
    }

    boolean isEnded() {
        return ended;
    }

    char play(int x, int y) {
        if (!ended ) {
            if (elts[y][x] == ' ') {
                elts[y][x] = currentPlayer;
                changePlayer();
            }
            else return 'e';
        }

        return checkEnd();
    }

    private void changePlayer() {
        currentPlayer= (currentPlayer== 'X' ? 'O': 'X');

    }

    char elt(int x, int y) {
        return elts[y][x];
    }

    void newGame() {
        for (int i= 0; i< 3; i++)
            for (int j=0; j<3; j++)
            elts[i][j]= ' ';

        currentPlayer= 'X';
        ended= false;
    }

    private char checkEnd() {
        for (int i= 0; i< 3; i++) {
            if (elt(i, 0)!= ' ' && elt(i,0)== elt(i, 1) && elt(i, 1)== elt(i,2)) {
                ended= true;
                return elt(i,0);
            }

            if (elt(0, i)!= ' ' && elt(0,i)== elt(1, i) && elt(1, i)== elt(2,i)) {
                ended= true;
                return elt(0,i);
            }
        }

        if (elt(0, 0)!= ' ' && elt(0,0)== elt(1, 1) && elt(1, 1)== elt(2,2)) {
            ended= true;
            return elt(0,0);
        }

        if (elt(2, 0)!= ' ' && elt(2,0)== elt(1, 1) && elt(1, 1)== elt(0,2)) {
            ended= true;
            return elt(2,0);
        }

        for (int i= 0; i< 3; i++)
            for (int j=0; j<3; j++)
            if (elts[i][j]== ' ')
                return ' ';

        return 'T';
    }

    char computer() {
        if(!ended) {
            if (MainActivity.difficulty==1) {
                int position ;

                do {
                    position = RANDOM.nextInt(9);
                }
                while (elts[position%3][position/3] != ' ');
                elts[position%3][position/3]= currentPlayer;
            }

            else if (MainActivity.difficulty==2) {
                if(RANDOM.nextBoolean()) {
                 Move Bestmove= findBestMove(elts);
                 elts[Bestmove.row][Bestmove.col]= currentPlayer;
                }
                else {
                    int position ;

                    do {
                        position = RANDOM.nextInt(9);
                    }
                    while (elts[position%3][position/3] != ' ');
                    elts[position%3][position/3]= currentPlayer;
                }

            }

            else {
                Move Bestmove= findBestMove(elts);
                elts[Bestmove.row][Bestmove.col]= currentPlayer;
            }
            changePlayer();
        }

        return checkEnd();
    }

    class Move
    {
        int row, col;
    }

    private boolean isMovesLeft(char[][] board) {
        for (int i = 0; i<3; i++)
            for (int j = 0; j<3; j++)
                if (board[i][j]==' ')
                    return true;
        return false;
    }

    private int evaluate(char[][] b) {
        for (int row = 0; row<3; row++)
        {
            if (b[row][0]==b[row][1] &&
                    b[row][1]==b[row][2])
            {
                if (b[row][0]==player)
                    return +10;
                else if (b[row][0]==opponent)
                    return -10;
            }
        }

        for (int col = 0; col<3; col++)
        {
            if (b[0][col]==b[1][col] &&
                    b[1][col]==b[2][col])
            {
                if (b[0][col]==player)
                    return +10;

                else if (b[0][col]==opponent)
                    return -10;
            }
        }

        if (b[0][0]==b[1][1] && b[1][1]==b[2][2])
        {
            if (b[0][0]==player)
                return +10;
            else if (b[0][0]==opponent)
                return -10;
        }

        if (b[0][2]==b[1][1] && b[1][1]==b[2][0])
        {
            if (b[0][2]==player)
                return +10;
            else if (b[0][2]==opponent)
                return -10;
        }

        return 0;
    }

    private int minimax(char[][] board, int depth, boolean isMax) {
        int score = evaluate(board);

        if (score == 10)
            return score-depth;

        if (score == -10)
            return score+depth;

        if (!isMovesLeft(board))
            return 0;

        if (isMax)
        {
            int best = -1000;

            for (int i = 0; i<3; i++)
            {
                for (int j = 0; j<3; j++)
                {
                    if (board[i][j]==' ')
                    {
                        board[i][j] = player;
                        best = Math.max( best,
                                minimax(board, depth+1, !isMax) );
                        board[i][j] = ' ';
                    }
                }
            }
            return best;
        }

        else
        {
            int best = 1000;

            for (int i = 0; i<3; i++)
            {
                for (int j = 0; j<3; j++)
                {
                    if (board[i][j]==' ')
                    {
                        board[i][j] = opponent;

                        best = Math.min(best,
                                minimax(board, depth+1, !isMax));

                        board[i][j] = ' ';
                    }
                }
            }
            return best;
        }
    }

    private Move findBestMove(char[][] board) {
        int bestVal = -1000;
        Move bestMove= new Move();
        bestMove.row = -1;
        bestMove.col = -1;

        for (int i = 0; i<3; i++)
        {
            for (int j = 0; j<3; j++)
            {
                if (board[i][j]==' ')
                {
                    board[i][j] = player;

                    int moveVal = minimax(board, 0, false);

                    board[i][j] = ' ';

                    if (moveVal > bestVal)
                    {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

}
