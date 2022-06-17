package game;

import agent.Action;
import agent.Problem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MazeProblem extends Problem<MazeState> {
    private final LivingThings goalState;

    public MazeProblem(MazeState initialState){
        super(initialState, new ArrayList<>(4));
        this.actions.add(new ActionUp());
        this.actions.add(new ActionRight());
        this.actions.add(new ActionDown());
        this.actions.add(new ActionLeft());
        this.actions.add(new ActionStatic());

        this.goalState = new LivingThings(MazeState.GOAL.getLinea(),MazeState.GOAL.getColuna());
    }


    @Override
    public List<MazeState> executeActions(MazeState state) {
        ArrayList<MazeState> successors =  new ArrayList<>(4);
        for(Action availableAction : actions){
            if(availableAction.isValid(state) && state.isHero()){
                MazeState successor = (MazeState) state.clone();
                successor.executeAction(availableAction);
                successors.add(successor);
            }
        }
        return successors;
    }

    @Override
    public List<Action<MazeState>> getActions(MazeState state) {
        List<Action<MazeState>> possibleActions = new LinkedList<>();

        for (Action action : actions) {
            if (action.isValid(state)) {
                possibleActions.add(action);
            }
        }
        return possibleActions;
    }



    public LivingThings getGoalState() {
        return goalState;
    }

    @Override
    public MazeState getSuccessor(MazeState state, Action action) {
        MazeState successor = state.clone();
        action.execute(successor);
        return successor;
    }

    @Override
    public boolean isGoal(MazeState state) {
        return state.isGoal();
    }


}
