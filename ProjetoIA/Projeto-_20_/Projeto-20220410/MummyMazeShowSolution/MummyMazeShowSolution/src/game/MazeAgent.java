package game;

import agent.Agent;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MazeAgent extends Agent<MazeState> {

    protected MazeState initialEnvironment;

    public MazeAgent(MazeState environment){
        super(environment);
        initialEnvironment = environment.clone();
        heuristics.add(new HeuristicTilesOutOfPlace());
        heuristics.add(new HeuristicMummyDistance());
        heuristic = heuristics.get(0);
    }

    public MazeState resetEnvironment(){
        environment = initialEnvironment.clone();
        return environment;
    }

    public MazeState readInitialStateFromFile(File file) throws IOException{
        Scanner scanner = new Scanner(file);

        char[][] matrix = new char [13][13];

        for(int i = 0;i<13;i++){
            String linea = scanner.nextLine();
            matrix[i] = linea.toCharArray();

        }
        initialEnvironment = new MazeState(matrix);
        resetEnvironment();
        return environment;

    }


    public MazeState getInitialState() {
        return initialEnvironment;
    }
}
