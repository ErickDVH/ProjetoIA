package game;

import agent.Action;

public class ActionLeft extends Action<MazeState> {
    public ActionLeft(){
        super(1);
    }

    @Override
    public void execute(MazeState state){
        state.moveLeft();
        state.setAction(this);
    }

    @Override
    public boolean isValid(MazeState state){
        return state.canMoveLeft();
    }
}
