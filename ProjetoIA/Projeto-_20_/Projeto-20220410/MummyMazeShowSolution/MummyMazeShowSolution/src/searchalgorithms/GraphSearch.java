package searchalgorithms;

import agent.Problem;
import agent.Solution;
import agent.State;
import game.MazeState;
import utils.NodeCollection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class GraphSearch<L extends NodeCollection> implements SearchAlgorithm {

    protected L frontier;
    protected Set<State> explored = new HashSet<>();
    protected Statistics statistics = new Statistics();
    protected boolean stopped;

    // TODO: 11/04/2022
    @Override
    public Solution search(Problem problem) {
        statistics.reset();
        stopped = false;
        return graphSearch(problem);
    }
    protected Solution graphSearch(Problem problem) {
        // TODO
        explored.clear();
        frontier.clear();
        State initialState = problem.getInitialState();
        frontier.add(new Node(initialState));
        while (!frontier.isEmpty() && !stopped) {
            Node node = frontier.poll();
            if(problem.isGoal(node.getState())){
                //o node serve para encapsular o estado
                statistics.toString();

                return new Solution(problem,node);

            }
            explored.add(node.getState());
            List<State> successors = problem.executeActions(node.getState());
            addSuccessorsToFrontier(successors,node);

            computeStatistics(successors.size());
        }

        statistics.toString();
        return null;
    }

    public abstract void addSuccessorsToFrontier(List<State> successors, Node parent);

    protected void computeStatistics(int successorsSize) {
        statistics.numExpandedNodes++;
        statistics.numGeneratedNodes += successorsSize;
        statistics.maxFrontierSize = Math.max(statistics.maxFrontierSize, frontier.size());
    }

    @Override
    public Statistics getStatistics(){
        return statistics;
    }

    @Override
    public void stop() {
        stopped = true;
    }

    @Override
    public boolean hasBeenStopped() {
        return stopped;
    }
}
