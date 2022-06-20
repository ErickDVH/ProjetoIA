package game;

import agent.Heuristic;

public class HeuristicMummyDistance extends Heuristic<MazeProblem, MazeState> {

    public double compute(MazeState state) {
        return state.computeDistance(problem.getGoalState());
    }

    @Override
    public String toString(){
        return "Distance from mummy";
    }
}
