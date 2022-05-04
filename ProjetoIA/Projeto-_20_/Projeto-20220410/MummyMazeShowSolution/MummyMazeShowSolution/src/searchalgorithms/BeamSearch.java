package searchalgorithms;

import agent.State;
import java.util.List;

import searchalgorithms.Node;
import utils.NodePriorityQueue;

public class BeamSearch extends AStarSearch {

    private int beamSize;

    public BeamSearch() {
        this(100);
    }

    public BeamSearch(int beamSize) {
        this.beamSize = beamSize;
    }

    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

        //TODO
        //adicionaros succssores a fronteira
        //"aparar" a fronteira de de forma a que fique apenas os beamSize com melhor prioridade
        super.addSuccessorsToFrontier(successors, parent);
        if(frontier.size() > beamSize) {
            NodePriorityQueue aux = new NodePriorityQueue();
            for(int i = 0; i < beamSize; i++){
                aux.add(frontier.poll());
            }
            frontier = aux;
        }

    }

    public void setBeamSize(int beamSize) {
        this.beamSize = beamSize;
    }

    public int getBeamSize() {
        return beamSize;
    }

    @Override
    public String toString() {
        return "Beam search";
    }
}
