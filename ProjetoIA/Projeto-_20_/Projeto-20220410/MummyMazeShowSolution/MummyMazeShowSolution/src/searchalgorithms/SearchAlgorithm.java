package searchalgorithms;

import agent.Problem;
import agent.Solution;
import showSolution.SolutionPanel;

public interface SearchAlgorithm {

    Solution search(Problem problem);
    Statistics getStatistics();
    void stop();

    boolean hasBeenStopped();
}
