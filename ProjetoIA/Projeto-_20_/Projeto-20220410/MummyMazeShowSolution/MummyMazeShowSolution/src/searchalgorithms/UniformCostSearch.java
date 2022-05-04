package searchalgorithms;

import agent.Problem;
import agent.Solution;
import agent.State;
import utils.NodePriorityQueue;

import java.util.List;

public class UniformCostSearch extends GraphSearch<NodePriorityQueue>{
    public UniformCostSearch(){
        frontier = new NodePriorityQueue();
    }    

    /*
    f -> prioridade
    g -> custo
    f = g
     */
    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {
    }
    // TODO: 11/04/2022

    @Override
    public String toString() {
        return "Uniform cost search";
    }


}
