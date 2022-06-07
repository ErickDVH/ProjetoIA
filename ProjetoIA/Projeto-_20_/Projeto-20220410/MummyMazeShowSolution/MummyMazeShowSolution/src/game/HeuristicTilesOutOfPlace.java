package game;

import agent.Heuristic;

public class HeuristicTilesOutOfPlace extends Heuristic<MazeProblem, MazeState> {

    @Override
    public double compute(MazeState state) {
        return state.computeTilesOutOfPlace(problem.getGoalState());
    }

    @Override
    public String toString(){
        return "Tiles out of place";
    }
}
