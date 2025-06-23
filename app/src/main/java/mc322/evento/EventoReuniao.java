/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322.evento;

/**
 * Representa uma reunião como característica de um evento, incluindo
 * participantes,
 * objetivo e o modo de realização (online ou presencial).
 * 
 * <p>
 * Essa classe é uma subclasse de {@link CaracteristicaEvento} e é usada
 * para eventos do tipo reunião no sistema.
 */
public class EventoReuniao extends CaracteristicaEvento {
    private String participantes; // Quem participa da reunião
    private String objetivo; // Objetivo da reunião
    private boolean online; // Indica se é online ou presencial

    /**
     * Construtor que inicializa os dados da reunião.
     * 
     * @param participantes uma descrição dos participantes da reunião.
     * @param objetivo      o objetivo ou pauta principal da reunião.
     * @param online        {@code true} se a reunião for online; {@code false} se
     *                      for presencial.
     */
    public EventoReuniao(String participantes, String objetivo, boolean online) {
        this.participantes = participantes;
        this.objetivo = objetivo;
        this.online = online;
    }

    /**
     * Retorna uma descrição resumida da reunião, incluindo participantes, objetivo
     * e modo.
     * 
     * @return uma {@code String} descritiva da reunião.
     */
    @Override
    public String descricao() {
        String modo = online ? "Online" : "Presencial";
        return "Reunião com " + participantes + " - " + objetivo + " | " + modo;
    }
}
