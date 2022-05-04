package utils;

import agent.State;
import searchalgorithms.Node;

import java.util.Queue;

public interface NodeCollection extends Queue<Node> {
    public boolean containsState(State e);
}
