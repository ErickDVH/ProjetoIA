package searchalgorithms;

public class Statistics {
    public int numExpandedNodes;
    public int numGeneratedNodes = 1; //due to the initial node
    public int maxFrontierSize;

    public void reset(){
        numExpandedNodes = 0;
        numGeneratedNodes = 1;
        maxFrontierSize = 0;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "numExpandedNodes=" + numExpandedNodes +
                ", numGeneratedNodes=" + numGeneratedNodes +
                ", maxFrontierSize=" + maxFrontierSize +
                '}';
    }
}
