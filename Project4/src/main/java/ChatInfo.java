import java.io.Serializable;
import java.util.ArrayList;

public class ChatInfo implements Serializable {
    String message;
    ArrayList<Integer> selected = new ArrayList<>();
    ArrayList<Integer> allClients = new ArrayList<>();
    boolean isClientUpdate;
}
