import java.util.*;

public class Connect4 {
    public static final int ROWS = 6;
    public static final int COLUMNS = 7;
    public static String[][] board = new String[ROWS][COLUMNS];
    public enum Players{X, O}

    private static void newBoard() { // creates a new board each new game
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLUMNS; ++j) {
                board[i][j] = "_";
            }
        }
    }

    private static void printBoard() { // prints out the current board
        System.out.println();
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLUMNS; ++j) {
                System.out.print("| " + board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println();
    }

    private static void gameRestart(Scanner scnr) { // restarts the game 
        Players player = null;
        String playerTurn = "";

        System.out.print("\nLet's Play Connect-4!\n\nX - Player X\n\nO - Player O\n\nr - Random\n\nWho would like to go first Player X or O?\n\n");
        String input;

        boolean invalidInput = true;
        while (invalidInput) {
            try {
                input = scnr.next();
                
                if (input.equalsIgnoreCase("X")) {
                    player = Players.X;
                    playerTurn = "X";
                    invalidInput = false;
                }
                else if (input.equalsIgnoreCase("O")) {
                    player = Players.O;
                    playerTurn = "O";
                    invalidInput = false;
                }
                else if (input.equalsIgnoreCase("r")) {
                    Random randGen = new Random();
                    int randNum = randGen.nextInt();

                    if (Math.abs(randNum % 2) == 0) {
                        player = Players.X;
                        playerTurn = "X";
                    }
                    else if (Math.abs(randNum % 2) == 1) {
                        player = Players.O;
                        playerTurn = "O";
                    }

                    invalidInput = false;
                }
                else {
                    System.out.print("Please input a valid option:\n\n");
                    scnr.nextLine();
                }
            }
            catch (InputMismatchException e) {
                System.out.print("Please input a valid option:\n\n");
                scnr.nextLine();
            }
        }
        
        newBoard();
        
        game(scnr, player, playerTurn);
    }

    private static void gameAgain(Scanner scnr) { // asks players if they want to play again
        System.out.print("Would you like to play again?\n\n");
        boolean invalidInput = true;
        while (invalidInput) {
            try {
                String input = scnr.next();

                if (input.equalsIgnoreCase("Yes")) {
                    gameRestart(scnr);
                }
                else if (input.equalsIgnoreCase("No")) {

                }
                else {
                    System.out.print("Please input a valid answer:\n\n");
                    scnr.nextLine();
                }
            }
            catch (InputMismatchException e) {
                System.out.print("Please input a valid answer:\n\n");
                scnr.nextLine();
            }
        }
    }

    private static boolean winCondition() { // checks if any player has won
        boolean win = false;
        for (int i = 0; i < ROWS - 3; ++i) {
            for (int j = 0; j < COLUMNS; ++j) {
                if (board[i][j].equals(board[i + 1][j]) && board[i + 1][j].equals(board[i + 2][j]) && board[i + 3][j].equals(board[i + 3][j]) && !(board[i][j].equals("_"))) {
                    win = true;
                    return win;
                }
            }
        }

        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLUMNS - 3; ++j) {
                if (board[i][j].equals(board[i][j + 1]) && board[i][j + 1].equals(board[i][j + 2]) && board[i][j + 2].equals(board[i][j + 3]) && !(board[i][j].equals("_"))) {
                    win = true;
                    return win;
                }
            }
        }
        
        for (int i = 0; i < ROWS - 3; ++i) {
            for (int j = 0; j < COLUMNS - 3; ++j) {
                if (board[i][j].equals(board[i + 1][j + 1]) && board[i + 1][j + 1].equals(board[i + 2][j + 2]) && board[i + 2][j + 2].equals(board[i + 3][j + 3]) && !(board[i][j].equals("_"))) {
                    win = true;
                    return win;
                }
                if (board[i + 3][j].equals(board[i + 2][j + 1]) && board[i + 2][j + 1].equals(board[i + 1][j + 2]) && board[i + 1][j + 2].equals(board[i][j + 3]) && !(board[i + 3][j].equals("_"))) {
                    win = true;
                    return win;
                }
            }
        }

        return win;
    }

    private static void game(Scanner scnr, Players player, String playerTurn) { // goes through Connect-4 game mechanisms
        final int TOTAL_TURNS = ROWS * COLUMNS;

        for (int i = 0; i < TOTAL_TURNS; ++i) {
            printBoard();

            System.out.print("Player " + player + ", which column do you choose? (1 - 7)\n\n");

            boolean invalidInput = true;
            while (invalidInput) {
                try {
                    int input = scnr.nextInt();

                    if (input >= 1 && input <= 7) {
                        int column = input - 1;
                        for (int j = 5; j >= 0; --j) {
                            if (board[j][column] != "X" && board[j][column] != "O") {
                                board[j][column] = playerTurn;
                                
                                invalidInput = false;
                                break;
                            }
                        }
                        if (invalidInput) {
                            System.out.print("\nPlease input a valid  column:\n\n");
                        }
                    }
                    else {
                        System.out.print("\nPlease input a number between 1 and 7:\n\n");
                        scnr.nextLine(); 
                    }
                }
                catch (InputMismatchException e) {
                    System.out.print("\nPlease input a number between 1 and 7:\n\n");
                    scnr.nextLine();
                }
            }

            if (winCondition()) {
                printBoard();
                System.out.print("Player " + player + " WINS!\n\n");
                gameAgain(scnr);;
            }
            
            Players playerCurrent = player;

            if (playerCurrent == Players.X) {
                player = Players.O;
                playerTurn = "O";
            }
            else if (playerCurrent == Players.O) {
                player = Players.X;
                playerTurn = "X";
            }
        }
        System.out.print("It's a TIE!\n\n");
        gameAgain(scnr);
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        Players player = null;
        String playerTurn = "";

        System.out.print("\nLet's Play Connect-4!\n\nX - Player X\n\nO - Player O\n\nr - Random\n\nWho would like to go first Player X or O?\n\n");
        String input;

        boolean invalidInput = true;
        while (invalidInput) {
            try {
                input = scnr.next();
                
                if (input.equalsIgnoreCase("X")) {
                    player = Players.X;
                    playerTurn = "X";
                    invalidInput = false;
                }
                else if (input.equalsIgnoreCase("O")) {
                    player = Players.O;
                    playerTurn = "O";
                    invalidInput = false;
                }
                else if (input.equalsIgnoreCase("r")) {
                    Random randGen = new Random();
                    int randNum = randGen.nextInt();

                    if (Math.abs(randNum % 2) == 0) {
                        player = Players.X;
                        playerTurn = "X";
                    }
                    else if (Math.abs(randNum % 2) == 1) {
                        player = Players.O;
                        playerTurn = "O";
                    }

                    invalidInput = false;
                }
                else {
                    System.out.print("Please input a valid option:\n\n");
                    scnr.nextLine();
                }
            }
            catch (InputMismatchException e) {
                System.out.print("Please input a valid option:\n\n");
                scnr.nextLine();
            }
        }
        
        newBoard();
        
        game(scnr, player, playerTurn);
    }
}