package searchalgorithms;

import agent.Problem;
import agent.Solution;
import agent.State;
import game.MazeState;
import utils.NodeLinkedList;

import java.util.List;

public class BreadthFirstSearch extends GraphSearch<NodeLinkedList>{


    public BreadthFirstSearch() {
        frontier = new NodeLinkedList();
    }

    // TODO: 11/04/2022
    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

        //TODO
        /*
            para cada sucessor se ainda nao estiver na fronteria nem na lista de mas explorados adicionar no Fim da frointeira
        */
        for(State successor : successors){
            if(!frontier.containsState(successor) && !explored.contains(successors)){
                frontier.addLast(new Node(successor,parent));   //implementar o comportamento FIFO
            }
        }
    }

    @Override
    public String toString() {
        return "Breadth first search";
    }


}
