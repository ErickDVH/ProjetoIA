package searchalgorithms;

import agent.State;

import java.util.List;

public class DepthLimitedSearch extends DepthFirstSearch{
    protected int limit;

    public DepthLimitedSearch() {
        this(28);
    }

    public DepthLimitedSearch(int limit) {
        this.limit = limit;
    }
    // TODO: 11/04/2022
    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {

        //TODO
        /*
            Se a profundidade do no pai for menor ou igual que o limite
            usa o addSuccessorsToFrontier da superclasse DepthFirstSearch
        */
        if(parent.getDepth() < limit){
            super.addSuccessorsToFrontier(successors,parent);
        }

    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Limited depth first search";
    }

}
