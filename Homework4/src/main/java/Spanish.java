public class Spanish extends Language{

    @Override
    public String greeting() {
        return "¿Qué pasa? Espero que estés bien!";
    }

    @Override
    public String question() {
        return "¿Has tenido algún viaje en otros países?";
    }

    @Override
    public String statement() {
        return "Camarón que se duerme, se lo lleva la corriente.";
    }

    @Override
    public String farewell() {
        return "Hasta luego! Nos vemos á la próxima vez!";
    }
}
