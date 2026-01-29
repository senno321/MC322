/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package agenda;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import evento.Evento;
import evento.EventoFactory;
import exceptions.OperationInvalidException;
import materia.Atividade;
import materia.Materia;
import usuario.Usuario;

/**
 * Serviço singleton responsável por orquestrar operações da agenda,
 * como criação e agendamento de eventos para os usuários.
 */
public class Agenda {

        /** Instância única da classe Agenda (Singleton). */
        private static final Agenda INSTANCE = new Agenda();

        /** Construtor privado para evitar instanciação externa. */
        private Agenda() {
        }

        /**
         * Retorna a instância única da classe Agenda.
         * 
         * @return instância singleton da agenda
         */
        public static Agenda getInstance() {
                return INSTANCE;
        }

        /**
         * Cria e agenda uma nova reunião para o usuário.
         * 
         * @param usuario       Usuário que receberá a reunião agendada
         * @param nome          Nome da reunião
         * @param local         Local da reunião
         * @param data          Data no formato "dd/MM/yyyy"
         * @param horaInicio    Hora no formato "HH:mm"
         * @param duracao       Duração em minutos
         * @param participantes Descrição dos participantes
         * @param objetivo      Objetivo da reunião
         * @param online        Se a reunião será online (true) ou presencial (false)
         */
        public void agendarNovaReuniao(Usuario usuario, String nome, String local, String data, String horaInicio,
                        int duracao, String participantes, String objetivo, boolean online) {

                Evento novaReuniao = EventoFactory.criarEventoReuniao(nome, local, data, horaInicio, duracao,
                                participantes,
                                objetivo, online);
                usuario.adicionarItemAgendado(novaReuniao);

                System.out.println(
                                "Reunião '" + novaReuniao.getNome() + "' agendada com sucesso para " + usuario.getNome()
                                                + ".");
        }

        /**
         * Cria e agenda uma nova atividade acadêmica para um usuário.
         * 
         * @param usuario     Usuário que receberá a atividade agendada
         * @param nome        Nome da atividade
         * @param local       Local da atividade
         * @param data        Data no formato "dd/MM/yyyy"
         * @param horaInicio  Hora no formato "HH:mm"
         * @param duracao     Duração em minutos
         * @param nomeMateria Nome da matéria associada à atividade
         * @param peso        Peso da atividade na média
         * @param conteudo    Conteúdo da atividade
         * @throws OperationInvalidException se a matéria não for encontrada no perfil
         *                                   do usuário
         */
        public void agendarNovaAtividade(Usuario usuario, String nome, String local, String data, String horaInicio,
                        int duracao, String nomeMateria, double peso, String conteudo) {

                Materia materia = usuario.getMaterias().stream()
                                .filter(m -> m.getNome().equalsIgnoreCase(nomeMateria))
                                .findFirst()
                                .orElseThrow(() -> new OperationInvalidException(
                                                "Matéria " + nomeMateria + " não encontrada para o usuário."));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime ldt = LocalDateTime.parse(data.trim() + " " + horaInicio.trim(), formatter);
                Atividade atividade = new Atividade(nome, peso, conteudo, ldt);
                materia.adicionaAtividade(atividade);

                System.out.println("Atividade '" + atividade.getNome() + "' registrada na matéria '" + materia.getNome()
                                + "'.");

                Evento novoEvento = EventoFactory.criarEventoAtividade(materia, nome, local, data, horaInicio, duracao,
                                peso, conteudo);

                usuario.adicionarItemAgendado(novoEvento);
                System.out.println("Evento '" + novoEvento.getNome() + "' agendado com sucesso para "
                                + usuario.getNome() + ".");
        }

        /**
         * Cria e agenda um novo evento de extensão para um usuário.
         * 
         * @param usuario            Usuário que receberá o evento
         * @param nome               Nome do evento de extensão
         * @param local              Local do evento
         * @param data               Data no formato "dd/MM/yyyy"
         * @param horaInicio         Hora no formato "HH:mm"
         * @param duracao            Duração em minutos
         * @param descricaoAtividade Descrição da atividade de extensão
         * @param publicoAlvo        Público-alvo da atividade
         * @param valeHoras          Se a atividade vale horas para extensão
         * @param cargaHoraria       Carga horária da atividade
         */
        public void agendarNovoEventoExtensao(Usuario usuario, String nome, String local, String data,
                        String horaInicio,
                        int duracao, String descricaoAtividade, String publicoAlvo, boolean valeHoras,
                        int cargaHoraria) {

                Evento novoEventoExtensao = EventoFactory.criarEventoExtensao(nome, local, data, horaInicio, duracao,
                                descricaoAtividade, publicoAlvo, valeHoras, cargaHoraria);
                usuario.adicionarItemAgendado(novoEventoExtensao);

                System.out.println(
                                "Evento de extensão '" + novoEventoExtensao.getNome() + "' agendado com sucesso para "
                                                + usuario.getNome() + ".");
        }
}