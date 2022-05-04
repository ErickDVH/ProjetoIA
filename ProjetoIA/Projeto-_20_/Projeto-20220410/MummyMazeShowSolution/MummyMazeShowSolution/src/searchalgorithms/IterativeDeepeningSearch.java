package searchalgorithms;

import agent.Problem;
import agent.Solution;
import agent.State;
import searchalgorithms.DepthFirstSearch;
import searchalgorithms.Node;

import java.util.List;

public class IterativeDeepeningSearch extends DepthFirstSearch {
    /*
     * We do not use the code from DepthLimitedSearch because we can optimize
     * so that the algorithm only verifies if a state is a goal if its depth is
     * equal to the limit. Note that given a limit X we are sure not to
     * encounter a solution below this limit because a (failed) limited depth
     * search has already been done. That's why we do not extend this class from
     * DepthLimitedSearch. We extend from DepthFirstSearch so that we don't need
     * to rewrite method insertSuccessorsInFrontier again.
     * After the class, please see a version of the search algorithm without
     * this optimization.
     */

    private int limit;

    @Override
    public Solution search(Problem problem) {
        statistics.reset();
        stopped = false;
        limit = 0;

        //TODO
        Solution solution;
        do {
            solution = graphSearch(problem);
            limit += 1;
        }while(solution == null);

        return solution;
    }

    @Override
    protected Solution graphSearch(Problem problem) {

        //TODO
        //adaptar o graphSearch para apenas verificar se um estado e o objetivo quando esta na profundidade limite
        //alem disso se estiver na profundidade limite nao adicionar a fronteira como na DepthLimitedSearch
        frontier.clear();
        State initialState = problem.getInitialState();
        frontier.add(new Node(initialState));
        while (!frontier.isEmpty() && !stopped) {
            Node node = frontier.poll();
            if(node.getDepth() == limit && problem.isGoal(node.getState())){            //o node serve para encapsular o estado
                return new Solution(problem,node);
            }
            if(node.getDepth() != limit){
                List<State> successors = problem.executeActions(node.getState());
                addSuccessorsToFrontier(successors,node);
                computeStatistics(successors.size());
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Iterative deepening search";
    }
}


/*
 *
 public class IterativeDeepeningSearch implements SearchMethod {

    @Override
    public Solution search(Problem problem) {
        DepthLimitedSearch dls = new DepthLimitedSearch();
        Solution solution;
        for (int i = 0;; i++) {
            dls.setLimit(i);
            solution = dls.search(problem);
            if (solution != null) {
                return solution;
            }
        }
    }

    @Override
    public String toString() {
        return "Iterative deepening search";
    }
 *
 */