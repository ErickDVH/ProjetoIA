package searchalgorithms;

import agent.Heuristic;
import agent.Problem;
import agent.Solution;
import utils.NodePriorityQueue;

public abstract class InformedSearch extends GraphSearch<NodePriorityQueue>{

    protected Heuristic heuristic;

    public InformedSearch(){
        frontier = new NodePriorityQueue();
    }

    // TODO: 11/04/2022
    public Solution search(Problem problem) {
        statistics.reset();
        stopped = false;
        this.heuristic = problem.getHeuristic();
        return graphSearch(problem);
    }
}
