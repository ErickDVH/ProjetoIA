package game;

import agent.Action;

public class ActionStatic extends Action<MazeState> {

    public ActionStatic(){
        super(1);
    }

    @Override
    public void execute(MazeState state){
        state.dontMove();
        state.setAction(this);
    }

    @Override
    public boolean isValid(MazeState state){
        return state.cantMove();
    }
}
