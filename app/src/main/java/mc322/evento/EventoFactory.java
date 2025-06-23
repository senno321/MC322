/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322.evento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import mc322.materia.Atividade;
import mc322.materia.Materia;

/**
 * {@code EventoFactory} é uma classe utilitária responsável por criar
 * instâncias
 * de {@link Evento} com diferentes características, como atividades acadêmicas,
 * reuniões ou atividades de extensão.
 * 
 * <p>
 * Essa classe segue o padrão de projeto <strong>Factory Method</strong>,
 * centralizando
 * a lógica de construção dos eventos e encapsulando detalhes de formatação de
 * data e hora.
 * </p>
 * 
 * <p>
 * Todos os métodos são estáticos e a classe não pode ser instanciada.
 * </p>
 * 
 * <p>
 * Formato esperado para data: <strong>"dd/MM/yyyy"</strong><br>
 * Formato esperado para hora: <strong>"HH:mm"</strong>
 * </p>
 * 
 */
public final class EventoFactory {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Construtor privado para prevenir a criação de instâncias da fábrica.
     */
    private EventoFactory() {
    }

    /**
     * Converte uma data e hora em string para {@link LocalDateTime}.
     * 
     * @param data       a data no formato "dd/MM/yyyy".
     * @param horaInicio a hora no formato "HH:mm".
     * @return um objeto {@code LocalDateTime} representando o instante de início.
     * @throws IllegalArgumentException se a data ou hora forem nulas ou estiverem
     *                                  em formato inválido.
     */
    private static LocalDateTime parseDataHora(String data, String horaInicio) {
        if (data == null || horaInicio == null) {
            throw new IllegalArgumentException("Data e hora não podem ser nulos.");
        }

        String dataHoraConcatenada = data.trim() + " " + horaInicio.trim();
        try {
            return LocalDateTime.parse(dataHoraConcatenada, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Formato de data ou hora inválido. Use 'dd/MM/yyyy' e 'HH:mm'. Valor recebido: '"
                            + dataHoraConcatenada + "'",
                    e);
        }
    }

    /**
     * Cria um evento do tipo "Atividade Acadêmica", como provas ou trabalhos.
     * 
     * @param materia    a matéria associada à atividade.
     * @param nome       o nome do evento.
     * @param local      o local onde o evento ocorrerá.
     * @param data       a data no formato "dd/MM/yyyy".
     * @param horaInicio a hora de início no formato "HH:mm".
     * @param duracao    a duração do evento em minutos.
     * @param peso       o peso da atividade na média final.
     * @param conteudo   o conteúdo que será cobrado na atividade.
     * @return um objeto {@link Evento} configurado com a característica de
     *         atividade.
     * @throws IllegalArgumentException se a data/hora forem inválidas.
     */
    public static Evento criarEventoAtividade(Materia materia, String nome, String local, String data,
            String horaInicio, int duracao,
            double peso, String conteudo) {

        LocalDateTime ldt = parseDataHora(data, horaInicio);
        CaracteristicaEvento caracteristica = new EventoAtividade(materia, peso, conteudo);
        return new Evento(nome, local, ldt, duracao, caracteristica);
    }

    /**
     * Cria um evento baseado em uma {@link Atividade} já existente.
     * 
     * <p>
     * O local é definido como "Casa" e a duração como 0 por padrão.
     * </p>
     * 
     * @param atividade a atividade cadastrada na matéria.
     * @param materia   a matéria associada à atividade.
     * @return um objeto {@link Evento} com os dados da atividade.
     */
    public static Evento criarEventoDeAtividadeExistente(Atividade atividade, Materia materia) {
        CaracteristicaEvento caracteristica = new EventoAtividade(materia, atividade.getPeso(),
                atividade.getConteudo());
        // Assume "Casa" como local padrão e duração 0 para atividades/prazos.
        return new Evento(atividade.getNome(), "Casa", atividade.getData(), 0, caracteristica);
    }

    /**
     * Cria um evento do tipo "Reunião".
     * 
     * @param nome          o nome da reunião.
     * @param local         o local onde ocorrerá.
     * @param data          a data no formato "dd/MM/yyyy".
     * @param horaInicio    a hora de início no formato "HH:mm".
     * @param duracao       a duração da reunião em minutos.
     * @param participantes os participantes da reunião.
     * @param objetivo      o objetivo da reunião.
     * @param online        indica se a reunião será realizada online.
     * @return um objeto {@link Evento} configurado como reunião.
     * @throws IllegalArgumentException se a data/hora forem inválidas.
     */
    public static Evento criarEventoReuniao(String nome, String local, String data, String horaInicio, int duracao,
            String participantes, String objetivo, boolean online) {

        LocalDateTime ldt = parseDataHora(data, horaInicio);
        CaracteristicaEvento caracteristica = new EventoReuniao(participantes, objetivo, online);
        return new Evento(nome, local, ldt, duracao, caracteristica);
    }

    /**
     * Cria um evento do tipo "Extensão".
     * 
     * @param nome               o nome da atividade de extensão.
     * @param local              o local de realização.
     * @param data               a data no formato "dd/MM/yyyy".
     * @param horaInicio         a hora de início no formato "HH:mm".
     * @param duracao            a duração total da atividade em minutos.
     * @param descricaoAtividade descrição da atividade de extensão.
     * @param publicoAlvo        público-alvo da atividade.
     * @param valeHoras          se {@code true}, a atividade vale horas para
     *                           extensão.
     * @param cargaHoraria       a carga horária atribuída, se aplicável.
     * @return um objeto {@link Evento} com característica de extensão.
     * @throws IllegalArgumentException se a data/hora forem inválidas.
     */
    public static Evento criarEventoExtensao(String nome, String local, String data, String horaInicio, int duracao,
            String descricaoAtividade, String publicoAlvo, boolean valeHoras, int cargaHoraria) {

        LocalDateTime ldt = parseDataHora(data, horaInicio);
        CaracteristicaEvento caracteristica = new EventoExtensao(descricaoAtividade, publicoAlvo, valeHoras,
                cargaHoraria);
        return new Evento(nome, local, ldt, duracao, caracteristica);
    }
}