package inf112.skeleton.app;

public class Card {
    private Turn turn;
    private int moves;
    private int priority;
    private boolean isBackup;
    
    public Card (Turn turn, int moves, int priority, boolean isBackup) {
        this.turn = turn;
        this.moves = moves;
        this.priority = priority;
        this.isBackup = isBackup;
    }
    
    public enum Turn {
        LEFT, RIGHT, UTURN, NONE
    }
    
    public Turn getTurn() {
        return this.turn;
    }
    
    public int getMoves() {
        return this.moves;
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    public boolean isBackup() {
        return this.isBackup;
    }
    
    public boolean isMove() {
        return this.moves > 0;
    }
    
    public boolean isRotate() {
        return this.turn != Turn.NONE;
    }
    
}
