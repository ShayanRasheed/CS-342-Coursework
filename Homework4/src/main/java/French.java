public class French extends Language{

    @Override
    public String greeting() {
        return "Salut ! Ça me fait plaisir de te voir.";
    }

    @Override
    public String question() {
        return "Qu'est-ce que vous allez faire aujourd'hui ?";
    }

    @Override
    public String statement() {
        return "Je suis en train de finir mes devoirs pour cette matiére.";
    }

    @Override
    public String farewell() {
        return "Au revoir ! À la prochaine !";
    }
}
