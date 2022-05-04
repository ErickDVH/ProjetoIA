package agent;

import java.util.ArrayList;
import java.util.List;

public abstract class Problem <S extends State> {

    protected S initialState;
    protected Heuristic heuristic;
    protected List<Action<S>> actions;


    public Problem(S initialState, ArrayList<Action<S>> actions){
        this.initialState = initialState;
        this.actions = actions;
    }

    public abstract S getSucessor(S state,Action action);

    public abstract boolean isGoal(S state);

    public Heuristic getHeuristic(){
        return heuristic;
    }

    public void setHeuristic(Heuristic heuristic){
        this.heuristic = heuristic;
    }

    public S getInitialState(){
        return initialState;
    }

    public double computePathCost(List<Action> path){
        double pathCost = 0;

        for (Action a:path){
             pathCost += a.getCost();
        }
        return pathCost;
    }
    public abstract List<S> executeActions(S state);
    public abstract List<Action<S>> getActions(S state);
    public abstract S getSuccessor(S state, Action action);

}
