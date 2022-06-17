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
        moveMummy();
        mazeChanged(null);
        System.out.println(this);
    }
    public boolean canMoveUp() {
        if(hero.getLinea() != 1 && matrix[hero.getLinea()-1][hero.getColuna()] != '-' && matrix[hero.getLinea()-2][hero.getColuna()] == '.'){
            return true;
        }
        return false;
    }

    public boolean canMoveRight() {
        if(hero.getColuna() != 11 && matrix[hero.getLinea()][hero.getColuna()+1] != '|' && matrix[hero.getLinea()][hero.getColuna()+2] == '.'){
            return true;
        }
        return false;
    }

    public boolean canMoveDown() {
        if(hero.getLinea() != 11 && matrix[hero.getLinea()+1][hero.getColuna()] != '-' && matrix[hero.getLinea()+2][hero.getColuna()] == '.'){
            return true;
        }
        return false;
    }

    public boolean canMoveLeft() {
        if(hero.getColuna() != 1 && matrix[hero.getLinea()][hero.getColuna()-1] != '|' && matrix[hero.getLinea()][hero.getColuna()-2] == '.'){
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
    }

    public void moveRight() {
        matrix[hero.getLinea()][hero.getColuna()] = '.';
        matrix[hero.getLinea()][hero.getColuna()+2] = 'H';
        hero.setColuna(hero.getColuna()+2);
    }

    public void moveDown() {
        matrix[hero.getLinea()][hero.getColuna()] = '.';
        matrix[hero.getLinea()+2][hero.getColuna()] = 'H';
        hero.setLinea(hero.getLinea() + 2);
    }

    public void moveLeft() {
        matrix[hero.getLinea()][hero.getColuna()] = '.';
        matrix[hero.getLinea()][hero.getColuna()-2] = 'H';
        hero.setColuna(hero.getColuna()-2);
    }

    public void dontMove() {
    }

    public boolean canMoveUpMummy() {
        if(mummy.getLinea() != 1 && matrix[mummy.getLinea()-1][mummy.getColuna()] != '-'){
            return true;
        }
        return false;
    }

    public boolean canMoveRightMummy() {
        if(mummy.getColuna() != 11 && matrix[mummy.getLinea()][mummy.getColuna()+1] != '|'){
            return true;
        }
        return false;
    }

    public boolean canMoveDownMummy() {
        if(mummy.getLinea() != 11 && matrix[mummy.getLinea()+1][mummy.getColuna()] != '-'){
            return true;
        }
        return false;
    }

    public boolean canMoveLeftMummy() {
        if(mummy.getColuna() != 1 && matrix[mummy.getLinea()][mummy.getColuna()-1] != '|'){
            return true;
        }

        return false;
    }

    public boolean cantMoveMummy() {
        return true;
    }


    public void moveUpMummy() {
        matrix[mummy.getLinea()][mummy.getColuna()] = '.';
        matrix[mummy.getLinea()-2][mummy.getColuna()] = 'M';
        mummy.setLinea(mummy.getLinea()-2);
    }

    public void moveRightMummy() {
        matrix[mummy.getLinea()][mummy.getColuna()] = '.';
        matrix[mummy.getLinea()][mummy.getColuna()+2] = 'M';
        mummy.setColuna(mummy.getColuna()+2);
    }

    public void moveDownMummy() {
        matrix[mummy.getLinea()][mummy.getColuna()] = '.';
        matrix[mummy.getLinea()+2][mummy.getColuna()] = 'M';
        mummy.setLinea(mummy.getLinea() + 2);
    }

    public void moveLeftMummy() {
        matrix[mummy.getLinea()][mummy.getColuna()] = '.';
        matrix[mummy.getLinea()][mummy.getColuna()-2] = 'M';
        mummy.setColuna(mummy.getColuna()-2);
    }

    public void dontMoveMummy() {
    }

    public boolean doorOpen(){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if(matrix[i][j] == '=' || matrix[i][j] == '"'){
                    return false;
                }
            }
        }
        return true;
    }
    public void moveMummy(){
        if(hero.getColuna() < mummy.getColuna()){
            if(canMoveLeftMummy()){
                moveLeftMummy();
            }
        }
        else if(hero.getLinea() < mummy.getLinea()){
            if(canMoveUpMummy()){
                moveUpMummy();
            }
        }
        else if(hero.getColuna() > mummy.getColuna()){
            if(canMoveRightMummy()){
                moveRightMummy();
            }
        }

        else if(hero.getLinea() > mummy.getLinea()){
            if(canMoveDownMummy()){
                moveDownMummy();
            }
        }
    }
    public boolean isHero(){
        for(int i=0;i<13;i++){
            for(int j=0;j<13;j++){
                if(matrix[i][j] == 'H'){
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
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            buffer.append('\n');
            for (int j = 0; j < matrix.length; j++) {
                buffer.append(matrix[i][j]);
                buffer.append(' ');
            }
        }
        return buffer.toString();
    }
}
