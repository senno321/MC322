/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322.evento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import mc322.materia.Atividade;
import mc322.materia.Materia;
import mc322.usuario.Usuario;

/**
 * Classe responsável pela criação e gerenciamento de diferentes tipos de eventos
 * associados a um {@link Usuario}.
 * 
 * <p>Essa classe centraliza a criação de eventos genéricos, bem como eventos com
 * características específicas, como atividades acadêmicas, reuniões e eventos de extensão.
 */
public class GerenciadorDeEventos {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Cria um evento do tipo atividade acadêmica para um usuário, vinculando-o a uma matéria específica.
     * 
     * <p>Se a matéria não for encontrada no conjunto de matérias do usuário, exibe mensagem de erro e não cria o evento.
     * 
     * @param usuario     o usuário ao qual o evento será associado.
     * @param nome        nome do evento.
     * @param local       local onde o evento ocorrerá.
     * @param data        data no formato "dd/MM/yyyy".
     * @param horaInicio  hora no formato "HH:mm".
     * @param duracao     duração do evento em minutos.
     * @param nomeMateria nome da matéria associada à atividade.
     * @param peso        peso da atividade na média.
     * @param conteudo    conteúdo cobrado na atividade.
     */
    public static void criarEvento(Usuario usuario, String nome, String local, String data, String horaInicio, int duracao,
            String nomeMateria, double peso, String conteudo) {

        LocalDateTime ldt = LocalDateTime.parse(data + " " + horaInicio, FORMATTER);

        Materia materia = usuario.getMaterias().stream()
                .filter(m -> m.getNome().equalsIgnoreCase(nomeMateria))
                .findFirst()
                .orElse(null);

        if (materia == null) {
            System.out.println("Matéria '" + nomeMateria + "' não encontrada para o usuário.");
            return;
        }

        CaracteristicaEvento caracteristica = new EventoAtividade(materia, peso, conteudo);
        Evento evento = new Evento(nome, local, ldt, duracao, caracteristica);
        usuario.adicionarEvento(evento);

        System.out.println("Evento criado com sucesso!");
    }

    /**
     * Cria um evento do tipo atividade acadêmica com base em uma {@link Atividade} já existente.
     * 
     * <p>Esse método assume que o evento ocorrerá na "Casa" do usuário e sem duração definida (0).
     * 
     * @param usuario   usuário ao qual o evento será associado.
     * @param atividade atividade acadêmica relacionada ao evento.
     * @param materia   matéria vinculada à atividade.
     * @param peso      peso da atividade na média.
     * @param conteudo  conteúdo cobrado na atividade.
     */
    public static void criarEvento(Usuario usuario, Atividade atividade, Materia materia, double peso, String conteudo) {
        CaracteristicaEvento caracteristica = new EventoAtividade(materia, peso, conteudo);
        Evento evento = new Evento(atividade.getNome(), "Casa", atividade.getData(), 0, caracteristica);
        usuario.adicionarEvento(evento);
    }

    /**
     * Cria um evento do tipo reunião com os dados fornecidos.
     * 
     * @param usuario       usuário ao qual o evento será associado.
     * @param nome          nome da reunião.
     * @param local         local da reunião.
     * @param data          data no formato "dd/MM/yyyy".
     * @param horaInicio    hora no formato "HH:mm".
     * @param duracao       duração da reunião em minutos.
     * @param participantes descrição dos participantes da reunião.
     * @param objetivo      objetivo ou pauta da reunião.
     * @param online        {@code true} se a reunião for online; {@code false} caso contrário.
     */
    public static void criarEvento(Usuario usuario, String nome, String local, String data, String horaInicio, int duracao,
            String participantes, String objetivo, boolean online) {

        LocalDateTime ldt = LocalDateTime.parse(data + " " + horaInicio, FORMATTER);
        CaracteristicaEvento caracteristica = new EventoReuniao(participantes, objetivo, online);
        Evento evento = new Evento(nome, local, ldt, duracao, caracteristica);
        usuario.adicionarEvento(evento);
    }

    /**
     * Cria um evento do tipo extensão com os dados fornecidos.
     * 
     * @param usuario          usuário ao qual o evento será associado.
     * @param nome             nome do evento de extensão.
     * @param local            local do evento.
     * @param data             data no formato "dd/MM/yyyy".
     * @param horaInicio       hora no formato "HH:mm".
     * @param duracao          duração do evento em minutos.
     * @param descricaoAtividade descrição da atividade de extensão.
     * @param publicoAlvo      público-alvo da atividade.
     * @param valeHoras        {@code true} se o evento valer horas de extensão.
     * @param cargaHoraria     quantidade de horas atribuídas ao evento (se valer horas).
     */
    public static void criarEvento(Usuario usuario, String nome, String local, String data, String horaInicio, int duracao,
            String descricaoAtividade, String publicoAlvo, boolean valeHoras, int cargaHoraria) {

        LocalDateTime ldt = LocalDateTime.parse(data + " " + horaInicio, FORMATTER);
        CaracteristicaEvento caracteristica = new EventoExtensao(descricaoAtividade, publicoAlvo, valeHoras, cargaHoraria);
        Evento evento = new Evento(nome, local, ldt, duracao, caracteristica);
        usuario.adicionarEvento(evento);
    }
}