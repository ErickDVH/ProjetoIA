package game;

import agent.Action;
import agent.State;

public class MazeState extends State implements Cloneable {

    static final String GOAL = "S";
    public static final int SIZE = 6;
    private final char[][] matrix;
    private Hero hero;
    private int heroCol;
    private String state;

    public MazeState(String state){
        this.state = state;
        String[] splitString = (state.split("\\n"));
        this.matrix = new char[splitString.length][splitString.length];
        for(int i=0;i<13;i++){
            for(int j=0;j<13;j++){
                matrix[i][j] = splitString[i].charAt(j);
                switch (splitString[i].charAt(j)){
                    case 'H':
                        hero = new Hero(i,j);
                    break;
                }
            }
        }
    }



    @Override
    public MazeState clone() {
        return new MazeState(state);
    }

    @Override
    public void executeAction(Action action) {
        action.execute(this);
    }
    public boolean canMoveUp() {
        if(matrix[hero.getX()][hero.getY()-1] != '|'){
            return true;
        }
        return false;
    }

    public boolean canMoveRight() {
        if(matrix[hero.getX()+1][hero.getY()] != '|'){
            return true;
        }
        return false;
    }

    public boolean canMoveDown() {
        if(matrix[hero.getX()][hero.getY()+1] != '|'){
            return true;
        }
        return false;
    }

    public boolean canMoveLeft() {
        if(matrix[hero.getX()-1][hero.getY()] != '|'){
            return true;
        }
        return false;
    }

    public boolean cantMove() {
        if(matrix[hero.getX()][hero.getY()] != '|'){
            return true;
        }
        return false;
    }


    public void moveUp() {
    }

    public void moveRight() {
        matrix[hero.getX()][hero.getY()] = matrix[hero.getX()][hero.getY()+1];
    }

    public void moveDown() {
    }

    public void moveLeft() {
    }

    public void dontMove() {

    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
