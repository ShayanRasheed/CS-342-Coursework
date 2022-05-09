import java.nio.charset.StandardCharsets;

public class Hindi extends Language{

    @Override
    public String greeting() {
        return "Namaste, aap kaise hain?";
    }

    @Override
    public String question() {
        return "Dukaan se doodh la sakate hain?";
    }

    @Override
    public String statement() {
        return "Main hindi to samajh sakata hoon, lekin bol nahin sakata.";
    }

    @Override
    public String farewell() {
        return "Phir milenge!";
    }
}
