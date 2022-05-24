package searchalgorithms;

import agent.State;
import searchalgorithms.GraphSearch;
import searchalgorithms.Node;
import utils.NodePriorityQueue;

import java.util.List;

public class UniformCostSearch extends GraphSearch<NodePriorityQueue> {

    public UniformCostSearch(){
        frontier = new NodePriorityQueue();
    }

    // f = g
    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

        //TODO
        /*
            f=g -> prioridade = custo
            Para cada estado successor, custo e igual ao custo acumulado das acoes que levaram aquele estado.
             Se a fronteira nao contiver o estado
             Se os explorados nao contiver o estado entao adicionar successor a fronteria com prioridade igual ao custo
             senao(se a fronteira ja contiver o estado)
             se o custo do estado que esta na fronteira for maior que o custo deste estado
             remover o estado que esta na fronteira
             adicionar successor a fronteira com prioridade igual ao custo
        */
        for(State successor : successors){
            double g = successor.getAction().getCost() + parent.getG();
            if(!frontier.containsState(successor)){
                if(!explored.contains(successor)){
                    Node node = new Node(successor,parent,g,g);
                    frontier.add(node);
                }
            }else{
                if(g < frontier.getNode(successor).getG()){
                    frontier.removeNode(successor);
                    Node node = new Node(successor,parent,g,g);
                    frontier.add(node);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Uniform cost search";
    }
}
