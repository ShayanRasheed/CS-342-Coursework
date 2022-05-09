import java.io.Serializable;

class MorraInfo implements Serializable {
    public int p1Points;
    public int p2Points;
    public int p1Guess;
    public int p2Guess;
    public int p1Num;
    public int p2Num;
    public boolean hasTwoPlayers;
    public boolean isPlayerOne;

    public MorraInfo() {
        p1Points = 0;
        p2Points = 0;
        p1Guess = 0;
        p1Num = 0;
        p2Num = 0;
        p2Guess = 0;
        hasTwoPlayers = false;
        isPlayerOne = false;
    }
}