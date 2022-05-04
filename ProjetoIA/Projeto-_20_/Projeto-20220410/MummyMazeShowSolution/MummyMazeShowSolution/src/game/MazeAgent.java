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

    }

    public MazeState resetEnvironment(){
        environment = initialEnvironment.clone();
        return environment;
    }

    public MazeState readInitialStateFromFile(File file) throws IOException{
        Scanner scanner = new Scanner(file);

        String state = "";

        for(int i = 0;i<file.length();i++){
            state = state+"\n"+scanner.next().substring(i)+"\n";
            scanner.nextLine();
        }
        initialEnvironment = new MazeState(state);
        resetEnvironment();
        return environment;
    }

}
