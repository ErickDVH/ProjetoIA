package game;

import java.util.LinkedList;

public class Mummy extends LivingThings{
    private LinkedList<LivingThings> mummies;

    public Mummy(int x, int y){
        super(x,y);
    }

    public LinkedList<LivingThings> getMummies() {
        return mummies;
    }
}
