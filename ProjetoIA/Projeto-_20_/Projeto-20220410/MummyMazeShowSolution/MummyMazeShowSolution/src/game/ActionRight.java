package game;

import agent.Action;

public class ActionRight extends Action<MazeState> {
    public ActionRight(){
        super(1);
    }

    @Override
    public void execute(MazeState state){
        state.moveRight();
        state.setAction(this);
    }

    @Override
    public boolean isValid(MazeState state){
        return state.canMoveRight();
    }
}
