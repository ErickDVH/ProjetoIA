package agent;

import game.MazeState;
import searchalgorithms.*;

import java.util.ArrayList;

public class Agent<E extends State> {

    protected E environment;
    protected ArrayList<SearchAlgorithm> searchAlgorithms;
    protected SearchAlgorithm searchAlgorithm;
    protected ArrayList<Heuristic> heuristics;
    protected Heuristic heuristic;
    protected Solution solution;

    public Agent(E environment){
        this.environment = environment;
        searchAlgorithms = new ArrayList<>();
        searchAlgorithms.add(new BreadthFirstSearch());
        searchAlgorithms.add(new UniformCostSearch());
        searchAlgorithms.add(new DepthFirstSearch());
        searchAlgorithms.add(new DepthLimitedSearch());
        searchAlgorithms.add(new IterativeDeepeningSearch());
        searchAlgorithms.add(new GreedyBestFirstSearch());
        searchAlgorithms.add(new AStarSearch());
        searchAlgorithms.add(new BeamSearch());
        searchAlgorithms.add(new IDAStarSearch());
        searchAlgorithm = searchAlgorithms.get(0);
        heuristics = new ArrayList<>();
    }

    public Solution solveProblem(Problem problem){
        if(heuristic != null){
            problem.setHeuristic(heuristic);
            heuristic.setProblem(problem);
        }
        solution = searchAlgorithm.search(problem);
        return solution;
    }

    public SearchAlgorithm[] getSearchAlgorithmsArray() {
        SearchAlgorithm[] sa = new SearchAlgorithm[searchAlgorithms.size()];
        return searchAlgorithms.toArray(sa);
    }


    public SearchAlgorithm getSearchAlgorithm(){
        return searchAlgorithm;
    }


    public void stop(){
        getSearchAlgorithm().stop();
    }
    public boolean hasBeenStopped() {
        return getSearchAlgorithm().hasBeenStopped();
    }
    public boolean hasSolution() {
        return solution != null;
    }

    public void setSearchAlgorithms(SearchAlgorithm searchAlgorithm){
        this.searchAlgorithm = searchAlgorithm;
    }

    public Heuristic getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public Heuristic[] getHeuristicsArray() {
        Heuristic[] sm = new Heuristic[heuristics.size()];
        return heuristics.toArray(sm);
    }
    public void executeSolution() {
        for(Action action : solution.getActions()){
            environment.executeAction(action);
            //((MazeState) environment).executeActionShowSolution();
        }
    }
    public E getEnvironment() {
        return environment;
    }
    public String getSearchReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(searchAlgorithm + "\n");
        if (solution == null) {
            sb.append("No solution found\n");
        } else {
            sb.append("Solution cost: " + Double.toString(solution.getCost()) + "\n");
        }
        sb.append("Num of expanded nodes: " + searchAlgorithm.getStatistics().numExpandedNodes + "\n");
        sb.append("Max frontier size: " + searchAlgorithm.getStatistics().maxFrontierSize + "\n");
        sb.append("Num of generated nodes: " + searchAlgorithm.getStatistics().numGeneratedNodes+ "\n");

        return sb.toString();
    }
}
