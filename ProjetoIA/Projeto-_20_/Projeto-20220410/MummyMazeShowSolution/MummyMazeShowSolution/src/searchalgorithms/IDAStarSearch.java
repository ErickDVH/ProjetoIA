package searchalgorithms;

import agent.Action;
import agent.Problem;
import agent.Solution;
import agent.State;

import java.util.List;

public class IDAStarSearch extends InformedSearch{


    private double limit;
    private double newLimit;
    private double limiteAnterior;

    @Override
    public Solution search(Problem problem) {
        statistics.reset();
        stopped = false;
        this.heuristic = problem.getHeuristic();
        limit = heuristic.compute(problem.getInitialState());

        Solution solution;
        do {
            solution = graphSearch(problem);
            if (limit==newLimit)
                return solution;
            limiteAnterior = limit;
            limit = newLimit;
        } while (solution == null && !stopped);
        return solution;
    }

    @Override
    protected Solution graphSearch(Problem problem) {
        frontier.clear();
        frontier.add(new Node(problem.getInitialState()));
        statistics.numGeneratedNodes++; //specific to this algorithm
        newLimit = Double.POSITIVE_INFINITY;
        while (!frontier.isEmpty() && !stopped) {

            Node n = (Node) frontier.poll();
            State state = n.getState();
            if (n.getF() > limiteAnterior && problem.isGoal(state)) {
                return new Solution(problem, n);
            }
            /*if(problem.isGoal(state)){
                return new Solution(problem, n);
            }*/
            List<Action> actions = problem.getActions(state);
            int numSuccessorsSize = actions.size();
            for(Action action : actions){
                State successor = problem.getSuccessor(state, action);
                addSuccessorToFrontier(successor, n);
            }
            computeStatistics(numSuccessorsSize);
        }
        return null;
    }

    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

    }

    public void addSuccessorToFrontier(State successor, Node parent) {

        double g = parent.getG() + successor.getAction().getCost();
        if (!frontier.containsState(successor)) {
            double h = heuristic.compute(successor);
            double f = g + h;
            if (f <= limit) {
                if (!parent.isCycle(successor)) {
                    frontier.add(new Node(successor, parent, g, f));
                }
            } else {
                newLimit = Math.min(newLimit, f);
            }
        } else if (frontier.getNode(successor).getG() > g) {
            frontier.removeNode(successor);
            frontier.add(new Node(successor, parent, g, g + heuristic.compute(successor)));
        }
    }

    @Override
    public String toString() {
        return "IDA* search";
    }


}
