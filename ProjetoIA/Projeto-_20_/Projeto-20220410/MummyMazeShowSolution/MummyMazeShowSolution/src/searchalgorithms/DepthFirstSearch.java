package searchalgorithms;

import agent.Problem;
import agent.Solution;
import agent.State;
import utils.NodeLinkedList;

import java.util.List;

public class DepthFirstSearch extends GraphSearch<NodeLinkedList>{

    public DepthFirstSearch() {
        frontier = new NodeLinkedList();
    }
    //Graph Search without explored list

    // TODO: 11/04/2022

    @Override
    protected Solution graphSearch(Problem problem) {

        //TODO
        //igual ao graphsearch da superclasse mas sem lista de nos explorados

        frontier.clear();
        State initialState = problem.getInitialState();
        frontier.add(new Node(initialState));
        while (!frontier.isEmpty() && !stopped) {
            Node node = frontier.poll();
            if(problem.isGoal(node.getState())){            //o node serve para encapsular o estado
                return new Solution(problem,node);
            }
            List<State> successors = problem.executeActions(node.getState());
            addSuccessorsToFrontier(successors,node);

            computeStatistics(successors.size());
        }
        return null;
    }

    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

        //TODO
        //parecido com o addSuccessorsToFrontier do BFD, mas a nodeLinkedList e utilizada como sendo uma pilha, nao e utilizada  a lista dde nos explolrados
        //utiliar o metodo do Node isCycle para verificar se o no ainda nao apareceu no caminho
        for(State successor : successors){
            if(!frontier.containsState(successor) && !parent.isCycle(successor)){
                frontier.addFirst(new Node(successor, parent));   //implementar o comportamento LIFO
            }
        }
    }

    @Override
    public String toString() {
        return "Depth first search";
    }


}
