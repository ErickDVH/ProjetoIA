package game;

import agent.Action;
import agent.State;

import java.util.ArrayList;
import java.util.Arrays;

public class MazeState extends State implements Cloneable {

    static LivingThings GOAL;
    public static final int SIZE = 6;
    private char[][] matrix;
    private Hero hero;
    private String state;
    static LivingThings goalHeroPosition;
    private WhiteMummy mummy;
    private int[] linesfinalMatrix = {};
    private int[] colsfinalMatrix ={};

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
                    case 'S':
                        GOAL = new LivingThings(i,j);
                        break;
                    case 'M':
                        mummy = new WhiteMummy(i,j);
                        break;
                }
            }
        }
        computeGoalPosition();
    }
    public MazeState(char[][] matrix){
        this.matrix = new char[matrix.length][matrix.length];
        for(int i=0;i<13;i++){
            for(int j=0;j<13;j++){
                this.matrix[i][j] = matrix[i][j];
                switch (matrix[i][j]){
                    case 'H':
                        hero = new Hero(i,j);
                        break;
                    case 'S':
                        GOAL = new LivingThings(i,j);
                        break;
                    case 'M':
                        mummy = new WhiteMummy(i,j);
                        break;
                }
            }
        }
        computeGoalPosition();
    }
    public boolean isGoal(){
        return goalHeroPosition.getLinea() == hero.getLinea() && goalHeroPosition.getColuna() == hero.getColuna();
    }

    private void computeGoalPosition(){
        if(GOAL.getColuna() == 0){
            goalHeroPosition = new LivingThings(GOAL.getLinea(),GOAL.getColuna()+1);
        }
        if(GOAL.getLinea() == 0){
            goalHeroPosition = new LivingThings(GOAL.getLinea()+1,GOAL.getColuna());
        }
        if(GOAL.getLinea() == 12){
            goalHeroPosition = new LivingThings(GOAL.getLinea()-1,GOAL.getColuna());
        }
        if(GOAL.getColuna() == 12){
            goalHeroPosition = new LivingThings(GOAL.getLinea(),GOAL.getColuna()-1);
        }

    }

    @Override
    public MazeState clone() {
        return new MazeState(matrix);
    }

    @Override
    public void executeAction(Action action) {
        action.execute(this);
        mazeChanged(null);
    }
    public boolean canMoveUp() {

        if(hero.getLinea() != 1 && matrix[hero.getLinea()-1][hero.getColuna()] != '-'|| doorOpen()){
            return true;
        }
        return false;
    }

    public boolean canMoveRight() {
        if(hero.getColuna() != 11 && matrix[hero.getLinea()][hero.getColuna()+1] != '|'|| doorOpen() ){
            return true;
        }
        return false;
    }

    public boolean canMoveDown() {
        if(hero.getLinea() != 11 && matrix[hero.getLinea()+1][hero.getColuna()] != '-' || doorOpen() ){
            return true;
        }
        return false;
    }

    public boolean canMoveLeft() {
        if(hero.getColuna() != 1 && matrix[hero.getLinea()][hero.getColuna()-1] != '|' || doorOpen()){
            return true;
        }

        return false;
    }

    public boolean cantMove() {
        return true;
    }


    public void moveUp() {
        matrix[hero.getLinea()][hero.getColuna()] = '.';
        matrix[hero.getLinea()-2][hero.getColuna()] = 'H';
        hero.setLinea(hero.getLinea()-2);

        /*matrix[mummy.getLinea()][mummy.getColuna()] = '.';
        matrix[mummy.getLinea()-2][mummy.getColuna()] = 'M';
        mummy.setLinea(mummy.getLinea()-2);*/
    }

    public void moveRight() {
        matrix[hero.getLinea()][hero.getColuna()] = '.';
        matrix[hero.getLinea()][hero.getColuna()+2] = 'H';
        hero.setColuna(hero.getColuna()+2);

        /*matrix[mummy.getLinea()][mummy.getColuna()] = '.';
        matrix[mummy.getLinea()][mummy.getColuna()+2] = 'M';
        mummy.setLinea(mummy.getColuna()+2);*/
    }

    public void moveDown() {
        matrix[hero.getLinea()][hero.getColuna()] = '.';
        matrix[hero.getLinea()+2][hero.getColuna()] = 'H';
        hero.setLinea(hero.getLinea() + 2);

        /*matrix[mummy.getLinea()][mummy.getColuna()] = '.';
        matrix[mummy.getLinea()+2][mummy.getColuna()] = 'M';
        mummy.setLinea(mummy.getLinea()+2);*/
    }

    public void moveLeft() {
        matrix[hero.getLinea()][hero.getColuna()] = '.';
        matrix[hero.getLinea()][hero.getColuna()-2] = 'H';
        hero.setColuna(hero.getColuna()-2);

        /*matrix[mummy.getLinea()][mummy.getColuna()] = '.';
        matrix[mummy.getLinea()][mummy.getColuna()-2] = 'M';
        mummy.setLinea(mummy.getColuna()-2);*/
    }

    public void dontMove() {
    }
    public boolean doorOpen(){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if(matrix[i][j] == '=' || matrix[i][j] == '"'){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MazeState)) {
            return false;
        }

        MazeState o = (MazeState) other;
        if (matrix.length != o.matrix.length) {
            return false;
        }

        return Arrays.deepEquals(matrix, o.matrix);
    }

    @Override
    public int hashCode() {
        return 97 * 7 + Arrays.deepHashCode(this.matrix);
    }


    private transient ArrayList<MazeListener> listeners = new ArrayList<MazeListener>(3);

    public synchronized void removeListener(MazeListener l) {
        if (listeners != null && listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    public synchronized void addListener(MazeListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }
    public void mazeChanged(MazeListener pe) {
        for (MazeListener listener : listeners) {
            listener.mazeChanged(null);
        }
    }

    public char[][] getMatrix() {
        return matrix;
    }


    public double computeTilesOutOfPlace(LivingThings goalState) {
        double h = 0;
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(matrix[i][j] != 0) {
                    if (matrix[i][j] != goalState.getLinea()) {
                        h += 1;
                    }
                }
            }
        }
        return h;
    }
}
