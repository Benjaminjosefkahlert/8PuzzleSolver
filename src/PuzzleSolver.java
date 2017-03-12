import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by Ben on 3/8/2017.
 */

public class PuzzleSolver {

    public static ArrayList<Node> visited = new ArrayList<Node>();
    Queue<Node> frontier = new LinkedList<Node>();

    private int [][] goalState = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private int [][] initialState = new int[3][3];

    public boolean breathFirstSearch(int[][] initialState) {
        Node initial = new Node(initialState, null);
        frontier.add(initial);
        int timeOut = 0;
        while (!frontier.isEmpty()) {
            Node state = frontier.remove();
            visited.add(state);

            if (goalTest(state.getBoard())) {
                printFinalPath(state);
                return true;
            }

            Node[] stateChildren = state.getChildren();
            for (int i = 0; i < stateChildren.length; i++) {
                boolean foundFrontier = false;
                boolean foundVisited = false;
                for (int j = 0; j < visited.size(); j++) {
                    if (compareMatrices(stateChildren[i].getBoard(), visited.get(j).getBoard())) {
                        foundVisited = true;
                    }
                }
                for(Node item : frontier){
                    if (compareMatrices(stateChildren[i].getBoard(), item.getBoard())) {
                        foundFrontier = true;
                    }
                }

                if (!foundVisited && !foundFrontier){
                    frontier.add(stateChildren[i]);
                }
            }
            timeOut++;
            if (timeOut > 5000) {
                System.out.println("Oops! The solution if either too far away for this program to feasibly find with " +
                        "a DFS, or a solution doesn't exist. Interrupting all processes...");
                System.exit(1);
            }
        }

        return false;
    }

    private boolean goalTest(int[][] other) {
        for (int i = 0; i < goalState.length; i++) {
            for (int j = 0; j < goalState[i].length; j++) {
                if (goalState[i][j] != other[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean compareMatrices(int[][] first, int[][] other) {
        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < first[i].length; j++) {
                if (first[i][j] != other[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void printFinalPath(Node n) {
        n.printBoard();
        System.out.println("   |");
        System.out.print("   |");
        while (n.getParent() != null) {
            n = n.getParent();
            n.printBoard();
        }
        System.out.println("Puzzle solved!\nFollow the trail upward to see the solution to the puzzle...");
    }

    private void printBoard(int[][] board) {
        System.out.println("-------");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print("|");
                if (board[i][j] == 0) {
                    System.out.print(" ");
                } else {
                    System.out.printf("%1d", board[i][j]);
                }
            }
            System.out.print("|");
            System.out.println("\n-------");
        }
    }

    private int[][] run() {
        int [][] matrix = new int[3][3];
        boolean[] hasAdded = new boolean[9];
        System.out.println("Welcome to 8-puzzle solver!\nCreated by Benjamin Kahlert using Java");
        System.out.println("Currently manufactured board:");
        printBoard(matrix);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println("Enter this coordinate in the tile grid:");
                System.out.println("Row = [" + (i + 1) + "], Column = [" + (j + 1) + "]");
                Scanner s = new Scanner(System.in);
                String stringInput = s.nextLine();
                try {
                    int input = Integer.parseInt(stringInput);
                    if (!hasAdded[input] && input >= 0 && input <= 8) {
                        hasAdded[input] = true;
                        matrix[i][j] = input;
                    } else {
                        System.out.println("That's not valid input! Please make sure you enter an integer between 0 and 8" +
                                " and than you have not previously entered that integer!");
                        j--;
                    }
                } catch (Exception e) {
                    System.out.println("That's not valid input! Please make sure you enter an integer between 0 and 8" +
                            " and than you have not previously entered that integer!");
                    j--;
                }
                System.out.println("Currently manufactured board:");
                printBoard(matrix);
            }
        }
        System.out.println("Board has been created\n----------------------\nSolving puzzle...");

        return matrix;
    }

    public static void main(String[] args) {
        PuzzleSolver ps = new PuzzleSolver();
        int [][] test = ps.run();
        ps.breathFirstSearch(test);
    }
}