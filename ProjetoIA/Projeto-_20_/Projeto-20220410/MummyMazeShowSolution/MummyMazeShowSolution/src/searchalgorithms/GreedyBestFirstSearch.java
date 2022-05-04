package searchalgorithms;

import agent.Problem;
import agent.Solution;
import agent.State;

import java.util.List;

public class GreedyBestFirstSearch extends InformedSearch{

    //f=h

    // TODO: 11/04/2022
    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

        //TODO
        for(State successor : successors){
            double g = successor.getAction().getCost() + parent.getG();
            if(!frontier.containsState(successor)){
                if(!explored.contains(successor)){
                    double h = heuristic.compute(successor);
                    Node node = new Node(successor,parent,g,h);
                    frontier.add(node);
                }
            }else{
                if(g < frontier.getNode(successor).getG()){
                    frontier.removeNode(successor);
                    double h = heuristic.compute(successor);
                    Node node = new Node(successor,parent,g,h);
                    frontier.add(node);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Greedy best first search";
    }


}
