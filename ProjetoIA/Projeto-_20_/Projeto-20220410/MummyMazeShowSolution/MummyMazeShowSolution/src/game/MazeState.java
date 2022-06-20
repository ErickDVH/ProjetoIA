package game;

import agent.Action;
import agent.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MazeState extends State implements Cloneable {

    static LivingThings GOAL;
    public static final int SIZE = 6;
    private char[][] matrix;
    private Hero hero;
    private String state;
    static LivingThings goalHeroPosition;
    private WhiteMummy whiteMummy;
    private RedMummy redMummy;
    private Scorpion scorpion;
    private LinkedList<WhiteMummy> whiteMummies;
    private LinkedList<RedMummy> redMummies;
    private LinkedList<Scorpion> scorpions;

    public MazeState(String state){
        this.state = state;
        this.whiteMummies = new LinkedList<>();
        this.redMummies = new LinkedList<>();
        this.scorpions = new LinkedList<>();
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
                        whiteMummy = new WhiteMummy(i,j);
                        whiteMummies.add(whiteMummy);
                        break;
                    case 'V':
                        redMummy = new RedMummy(i,j);
                        redMummies.add(redMummy);
                        break;
                    case 'E':
                        scorpion = new Scorpion(i,j);
                        scorpions.add(scorpion);
                        break;
                }
            }
        }
        computeGoalPosition();
    }
    public MazeState(char[][] matrix){
        this.whiteMummies = new LinkedList<>();
        this.redMummies = new LinkedList<>();
        this.scorpions = new LinkedList<>();
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
                        whiteMummy = new WhiteMummy(i,j);
                        whiteMummies.add(whiteMummy);
                        break;
                    case 'V':
                        redMummy = new RedMummy(i,j);
                        redMummies.add(redMummy);
                        break;
                    case 'E':
                        scorpion = new Scorpion(i,j);
                        scorpions.add(scorpion);
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
        for(WhiteMummy mummy : whiteMummies){
            if(whiteMummies.contains(mummy)){
                moveWhiteMummy();
                mazeChanged(null);
            }
        }
        for(RedMummy mummy : redMummies){
            if(redMummies.contains(mummy)){
                moveRedMummy();
                mazeChanged(null);
            }
        }
        for(Scorpion scorpion : scorpions){
            if(scorpions.contains(scorpion)){
                moveScorpion();
                mazeChanged(null);
            }
        }
        System.out.println(this);
    }
    public boolean canMoveUp() {
        if(hero.getLinea() != 1 && matrix[hero.getLinea()-1][hero.getColuna()] != '-' && matrix[hero.getLinea()-2][hero.getColuna()] == '.' && matrix[hero.getLinea()-1][hero.getColuna()] != '='){
            return true;
        }
        return false;
    }

    public boolean canMoveRight() {
        if(hero.getColuna() != 11 && matrix[hero.getLinea()][hero.getColuna()+1] != '|' && matrix[hero.getLinea()][hero.getColuna()+2] == '.' && matrix[hero.getLinea()][hero.getColuna()+1] != '"'){
            return true;
        }
        return false;
    }

    public boolean canMoveDown() {
        if(hero.getLinea() != 11 && matrix[hero.getLinea()+1][hero.getColuna()] != '-' && matrix[hero.getLinea()+2][hero.getColuna()] == '.' && matrix[hero.getLinea()+1][hero.getColuna()] != '='){
            return true;
        }
        return false;
    }

    public boolean canMoveLeft() {
        if(hero.getColuna() != 1 && matrix[hero.getLinea()][hero.getColuna()-1] != '|' && matrix[hero.getLinea()][hero.getColuna()-2] == '.' && matrix[hero.getLinea()][hero.getColuna()-1] != '"'){
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
        if(whiteMummy.getLinea() != 1 && matrix[whiteMummy.getLinea()-1][whiteMummy.getColuna()] != '-' && matrix[whiteMummy.getLinea()-1][whiteMummy.getColuna()] != '='){
            return true;
        }
        return false;
    }

    public boolean canMoveRightMummy() {
        if(whiteMummy.getColuna() != 11 && matrix[whiteMummy.getLinea()][whiteMummy.getColuna()+1] != '|' && matrix[whiteMummy.getLinea()][whiteMummy.getColuna()+1] != '"'){
            return true;
        }
        return false;
    }

    public boolean canMoveDownMummy() {
        if(whiteMummy.getLinea() != 11 && matrix[whiteMummy.getLinea()+1][whiteMummy.getColuna()] != '-' && matrix[whiteMummy.getLinea()+1][whiteMummy.getColuna()] != '='){
            return true;
        }
        return false;
    }

    public boolean canMoveLeftMummy() {
        if(whiteMummy.getColuna() != 1 && matrix[whiteMummy.getLinea()][whiteMummy.getColuna()-1] != '|' && matrix[whiteMummy.getLinea()][whiteMummy.getColuna()-1] != '"'){
            return true;
        }

        return false;
    }

    public boolean cantMoveMummy() {
        return true;
    }


    public void moveUpMummy() {
        matrix[whiteMummy.getLinea()][whiteMummy.getColuna()] = '.';
        matrix[whiteMummy.getLinea()-2][whiteMummy.getColuna()] = 'M';
        whiteMummy.setLinea(whiteMummy.getLinea()-2);
    }

    public void moveRightMummy() {
        matrix[whiteMummy.getLinea()][whiteMummy.getColuna()] = '.';
        matrix[whiteMummy.getLinea()][whiteMummy.getColuna()+2] = 'M';
        whiteMummy.setColuna(whiteMummy.getColuna()+2);
    }

    public void moveDownMummy() {
        matrix[whiteMummy.getLinea()][whiteMummy.getColuna()] = '.';
        matrix[whiteMummy.getLinea()+2][whiteMummy.getColuna()] = 'M';
        whiteMummy.setLinea(whiteMummy.getLinea() + 2);
    }

    public void moveLeftMummy() {
        matrix[whiteMummy.getLinea()][whiteMummy.getColuna()] = '.';
        matrix[whiteMummy.getLinea()][whiteMummy.getColuna()-2] = 'M';
        whiteMummy.setColuna(whiteMummy.getColuna()-2);
    }

    public void dontMoveMummy() {
    }

    public void moveWhiteMummy(){
        if(hero.getColuna() < whiteMummy.getColuna()){
            if(canMoveLeftMummy()){
                moveLeftMummy();
            }
        }
        else if(hero.getLinea() < whiteMummy.getLinea()){
            if(canMoveUpMummy()){
                moveUpMummy();
            }
        }
        else if(hero.getColuna() > whiteMummy.getColuna()){
            if(canMoveRightMummy()){
                moveRightMummy();
            }
        }

        else if(hero.getLinea() > whiteMummy.getLinea()){
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
    public boolean canMoveUpRedMummy() {
        if(redMummy.getLinea() != 1 && matrix[redMummy.getLinea()-1][redMummy.getColuna()] != '-' && matrix[redMummy.getLinea()-1][redMummy.getColuna()] != '='){
            return true;
        }
        return false;
    }

    public boolean canMoveRightRedMummy() {
        if(redMummy.getColuna() != 11 && matrix[redMummy.getLinea()][redMummy.getColuna()+1] != '|' && matrix[redMummy.getLinea()][redMummy.getColuna()+1] != '"'){
            return true;
        }
        return false;
    }

    public boolean canMoveDownRedMummy() {
        if(redMummy.getLinea() != 11 && matrix[redMummy.getLinea()+1][redMummy.getColuna()] != '-' && matrix[redMummy.getLinea()+1][redMummy.getColuna()] != '='){
            return true;
        }
        return false;
    }

    public boolean canMoveLeftRedMummy() {
        if(redMummy.getColuna() != 1 && matrix[redMummy.getLinea()][redMummy.getColuna()-1] != '|' && matrix[redMummy.getLinea()][redMummy.getColuna()-1] != '"'){
            return true;
        }

        return false;
    }

    public boolean cantMoveRedMummy() {
        return true;
    }


    public void moveUpRedMummy() {
        matrix[redMummy.getLinea()][redMummy.getColuna()] = '.';
        matrix[redMummy.getLinea()-2][redMummy.getColuna()] = 'V';
        redMummy.setLinea(redMummy.getLinea()-2);
    }

    public void moveRightRedMummy() {
        matrix[redMummy.getLinea()][redMummy.getColuna()] = '.';
        matrix[redMummy.getLinea()][redMummy.getColuna()+2] = 'V';
        redMummy.setColuna(redMummy.getColuna()+2);
    }

    public void moveDownRedMummy() {
        matrix[redMummy.getLinea()][redMummy.getColuna()] = '.';
        matrix[redMummy.getLinea()+2][redMummy.getColuna()] = 'V';
        redMummy.setLinea(redMummy.getLinea() + 2);
    }

    public void moveLeftRedMummy() {
        matrix[redMummy.getLinea()][redMummy.getColuna()] = '.';
        matrix[redMummy.getLinea()][redMummy.getColuna()-2] = 'V';
        redMummy.setColuna(redMummy.getColuna()-2);
    }

    public void dontMoveRedMummy() {
    }

    public void moveRedMummy() {
        if (hero.getLinea() < redMummy.getLinea()) {
            if (canMoveUpRedMummy()) {
                moveUpRedMummy();
            }
        } else if (hero.getColuna() < redMummy.getColuna()) {
            if (canMoveLeftRedMummy()) {
                moveLeftRedMummy();
            }
        } else if (hero.getLinea() > redMummy.getLinea()) {
            if (canMoveDownRedMummy()) {
                moveDownRedMummy();
            }
        } else if (hero.getColuna() > redMummy.getColuna()) {
            if (canMoveRightRedMummy()) {
                moveRightRedMummy();
            }
        }
    }


        public boolean canMoveUpScorpion () {
            if (scorpion.getLinea() != 1 && matrix[scorpion.getLinea() - 1][scorpion.getColuna()] != '-' && matrix[scorpion.getLinea()-1][scorpion.getColuna()] != '=') {
                return true;
            }
            return false;
        }

        public boolean canMoveRightScorpion () {
            if (scorpion.getColuna() != 11 && matrix[scorpion.getLinea()][scorpion.getColuna() + 1] != '|' && matrix[scorpion.getLinea()][scorpion.getColuna()+1] != '"') {
                return true;
            }
            return false;
        }

        public boolean canMoveDownScorpion () {
            if (scorpion.getLinea() != 11 && matrix[scorpion.getLinea() + 1][scorpion.getColuna()] != '-' && matrix[scorpion.getLinea()+1][scorpion.getColuna()] != '=') {
                return true;
            }
            return false;
        }

        public boolean canMoveLeftScorpion () {
            if (scorpion.getColuna() != 1 && matrix[scorpion.getLinea()][scorpion.getColuna() - 1] != '|' && matrix[scorpion.getLinea()][scorpion.getColuna()-1] != '"') {
                return true;
            }

            return false;
        }

        public boolean cantMoveScorpion () {
            return true;
        }


        public void moveUpScorpion () {
            matrix[scorpion.getLinea()][scorpion.getColuna()] = '.';
            matrix[scorpion.getLinea() - 1][scorpion.getColuna()] = 'E';
            scorpion.setLinea(scorpion.getLinea() - 1);
        }

        public void moveRightScorpion () {
            matrix[scorpion.getLinea()][scorpion.getColuna()] = '.';
            matrix[scorpion.getLinea()][scorpion.getColuna() + 1] = 'E';
            scorpion.setColuna(scorpion.getColuna() + 1);
        }

        public void moveDownScorpion () {
            matrix[scorpion.getLinea()][scorpion.getColuna()] = '.';
            matrix[scorpion.getLinea() + 1][scorpion.getColuna()] = 'E';
            scorpion.setLinea(scorpion.getLinea() + 1);
        }

        public void moveLeftScorpion () {
            matrix[scorpion.getLinea()][scorpion.getColuna()] = '.';
            matrix[scorpion.getLinea()][scorpion.getColuna() - 1] = 'E';
            scorpion.setColuna(scorpion.getColuna() - 1);
        }

        public void dontMoveScorpion () {
        }

        public void moveScorpion () {
            if (hero.getColuna() < scorpion.getColuna()) {
                if (canMoveLeftScorpion()) {
                    moveLeftScorpion();
                }
            }
            else if (hero.getLinea() < scorpion.getLinea()) {
                if (canMoveUpScorpion()) {
                    moveUpScorpion();
                }
            }
            else if (hero.getColuna() > scorpion.getColuna()) {
                if (canMoveRightScorpion()) {
                    moveRightScorpion();
                }
            }
            else if (hero.getLinea() > scorpion.getLinea()) {
                if (canMoveDownScorpion()) {
                    moveDownScorpion();
                }
            }
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

    public double computeDistance(LivingThings goalState){
        double h=0;
        for (int i = 0;i<matrix.length;i++){
            for(int j = 0; j<matrix.length;j++){

                if(this.matrix[i][j] !=0 && this.whiteMummy!=null){
                    h+=Math.abs(hero.getLinea()- whiteMummy.getLinea()) + Math.abs(hero.getColuna()- whiteMummy.getColuna());
                }else{
                    if(this.matrix[i][j] !=0 && this.redMummy!=null){
                        h+=Math.abs(hero.getLinea()- redMummy.getLinea()) + Math.abs(hero.getColuna()- redMummy.getColuna());
                    }else{
                        if(this.matrix[i][j] !=0 && this.scorpion!=null){
                            h+=Math.abs(hero.getLinea()- scorpion.getLinea()) + Math.abs(hero.getColuna()- scorpion.getColuna());
                        }
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
