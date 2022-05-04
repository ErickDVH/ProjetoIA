package game;

import agent.Action;

public class ActionUp extends Action<MazeState> {
    public ActionUp(){
        super(1);
    }

    @Override
    public void execute(MazeState state){
        state.moveUp();
        state.setAction(this);
    }

    @Override
    public boolean isValid(MazeState state){
        return state.canMoveUp();
    }
}
