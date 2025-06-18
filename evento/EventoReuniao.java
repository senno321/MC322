package evento;
public class EventoReuniao extends CaracteristicaEvento {
    private String participantes;
    private String objetivo;
    private boolean online;

    public EventoReuniao(String participantes, String objetivo, boolean online) {
        this.participantes = participantes;
        this.objetivo = objetivo;
        this.online = online;
    }

    @Override
    public String descricao() {
        String modo = online ? "Online" : "Presencial";
        return "Reunião com " + participantes + " - " + objetivo + " | " + modo;
    }
}