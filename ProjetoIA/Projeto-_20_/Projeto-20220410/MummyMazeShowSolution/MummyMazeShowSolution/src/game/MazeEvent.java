package game;

import java.util.EventObject;

public class MazeEvent extends EventObject {
    public MazeEvent(MazeState source) {
        super(source);
    }
}
