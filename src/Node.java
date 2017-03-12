/**
 * Created by Ben on 3/8/2017.
 */

public class Node {

    private int [][] board;
    private Node parent;

    public Node(int[][] board, Node parent) {
        this.board = board;
        this.parent = parent;
    }

    public int[][] getBoard() {
        int[][] temporary = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                temporary[i][j] = board[i][j];
            }
        }
        return temporary;
    }

    public Node getParent() {
        return parent;
    }

    public void printBoard() {
        System.out.println();
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

    public Node[] getChildren() {
        //Finding the 0
        int i = 0;
        int j = 0;
        int tempI = 0;
        int tempJ = 0;
        for (tempI = 0; tempI < board.length; tempI++) {
            for (tempJ = 0; tempJ < board[tempI].length; tempJ++) {
                if (board[tempI][tempJ] == 0) {
                    i = tempI;
                    j = tempJ;
                }
            }
        }

        //count variable to tell how big the array should be
        int count = 0;
        //Booleans to tell which nodes to add;
        boolean hasRight = false;
        boolean hasLeft = false;
        boolean hasUp = false;
        boolean hasDown = false;
        //int matrices
        int[][] tempRight = getBoard();
        int[][] tempLeft = getBoard();
        int[][] tempUp = getBoard();
        int[][] tempDown = getBoard();

        //To the right:
        if (isValidSpace(i, j + 1)) {
            count++;
            //swap the temp
            int tempInt = tempRight[i][j + 1];
            tempRight[i][j + 1] = tempRight[i][j];
            tempRight[i][j] = tempInt;
            hasRight = true;
        }

        //To the left:
        if (isValidSpace(i, j - 1)) {
            count++;
            //swap the temp
            int tempInt = tempLeft[i][j - 1];
            tempLeft[i][j - 1] = tempLeft[i][j];
            tempLeft[i][j] = tempInt;
            hasLeft = true;
        }

        //To the top:
        if (isValidSpace(i - 1, j)) {
            count++;
            //swap the temp
            int tempInt = tempUp[i - 1][j];
            tempUp[i - 1][j] = tempUp[i][j];
            tempUp[i][j] = tempInt;
            hasUp = true;
        }

        //To the bottom:
        if (isValidSpace(i + 1, j)) {
            count++;
            //swap the temp
            int tempInt = tempDown[i + 1][j];
            tempDown[i + 1][j] = tempDown[i][j];
            tempDown[i][j] = tempInt;
            hasDown = true;
        }

        //Create returned array
        Node[] finalArray = new Node[count];
        count -= count;

        //Adds all valid Nodes
        if (hasRight) {
            Node n = new Node(tempRight, this);
            finalArray[count] = n;
            count++;
        }

        if (hasLeft) {
            Node n = new Node(tempLeft, this);
            finalArray[count] = n;
            count++;
        }

        if (hasUp) {
            Node n = new Node(tempUp, this);
            finalArray[count] = n;
            count++;
        }

        if (hasDown) {
            Node n = new Node(tempDown, this);
            finalArray[count] = n;
            count++;
        }

        return finalArray;
    }

    private boolean isValidSpace(int i, int j) {
        if (i <= 2 && i >= 0 && j <= 2 && j >= 0) {
            return true;
        } else {
            return false;
        }
    }
}