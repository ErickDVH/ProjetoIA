package game;

public class LivingThings {

    private int linea,coluna;

    public LivingThings(int linea,int coluna){
        this.linea = linea;
        this.coluna = coluna;
    }

    public int getLinea(){
        return linea;
    }
    public int getColuna(){
        return coluna;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
}
