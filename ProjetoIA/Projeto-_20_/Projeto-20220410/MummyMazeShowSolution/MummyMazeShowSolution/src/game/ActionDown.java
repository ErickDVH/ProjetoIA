package game;

import agent.Action;

public class ActionDown extends Action<MazeState> {
    public ActionDown(){
        super(1);
    }

    @Override
    public void execute(MazeState state){
        state.moveDown();
        state.setAction(this);
    }
    @Override
    public boolean isValid(MazeState state){
        return state.canMoveDown();
    }
}
