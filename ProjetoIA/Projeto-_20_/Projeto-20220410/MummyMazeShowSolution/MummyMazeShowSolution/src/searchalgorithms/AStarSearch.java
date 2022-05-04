package searchalgorithms;

import agent.State;
import searchalgorithms.InformedSearch;
import searchalgorithms.Node;

import java.util.List;

public class AStarSearch extends InformedSearch {

    //f = g + h
    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

        //TODO
        for(State successor : successors){
            double g = successor.getAction().getCost() + parent.getG();
            if(!frontier.containsState(successor)){
                if(!explored.contains(successor)){
                    double h = heuristic.compute(successor);
                    Node node = new Node(successor,parent,g,g+h);
                    frontier.add(node);
                }
            }else{
                if(g < frontier.getNode(successor).getG()){
                    frontier.removeNode(successor);
                    double h = heuristic.compute(successor)+g;
                    Node node = new Node(successor,parent,g,g+h);
                    frontier.add(node);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "A* search";
    }
}
