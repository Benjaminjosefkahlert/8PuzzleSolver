import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Created by Ben on 3/8/2017.
 */
public class PuzzleSolver {

    public static ArrayList<Node> visited = new ArrayList<Node>();
    Queue<Node> frontier = new LinkedList<Node>();

    private int [][] goalState = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    private int [][] initialState = new int[3][3];

    public boolean breathFirstSearch(int[][] initialState) {
        Node initial = new Node(initialState, null);
        frontier.add(initial);

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
        while (n.getParent() != null) {
            n = n.getParent();
            n.printBoard();
        }
    }

    public static void main(String[] args) {
        //Test
        int[][] test = {{1,2,5},
                        {3,4,0},
                        {6,7,8}};
        PuzzleSolver ps = new PuzzleSolver();
    }
}
