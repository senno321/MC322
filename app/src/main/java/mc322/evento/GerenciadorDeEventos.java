package mc322.evento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import mc322.materia.Materia;
import mc322.usuario.Usuario;

public class GerenciadorDeEventos {

    public void criarEvento(Usuario usuario, String nome, String local, String data, String horaInicio, int duracao,
            Materia materia,
            String tipo, double peso, String conteudo) {

        String dataHoraInicio = data + " " + horaInicio;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(dataHoraInicio, formatter);
        CaracteristicaEvento caracteristica = new EventoProva(materia, tipo, peso, conteudo);
        Evento evento = new Evento(nome, local, ldt, duracao, caracteristica);
        usuario.adicionarEvento(evento);
    }

    public void criarEvento(Usuario usuario, String nome, String local, String data, String horaInicio, int duracao,
            String participantes, String objetivo, boolean online) {

        String dataHoraInicio = data + " " + horaInicio;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(dataHoraInicio, formatter);
        CaracteristicaEvento caracteristica = new EventoReuniao(participantes, objetivo, online);
        Evento evento = new Evento(nome, local, ldt, duracao, caracteristica);
        usuario.adicionarEvento(evento);
    }

    public void criarEvento(Usuario usuario, String nome, String local, String data, String horaInicio, int duracao,
            String descricaoAtividade, String publicoAlco, boolean valeHoras, int cargaHoraria) {

        String dataHoraInicio = data + " " + horaInicio;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(dataHoraInicio, formatter);
        CaracteristicaEvento caracteristica = new EventoExtensao(descricaoAtividade, publicoAlco, valeHoras,
                cargaHoraria);
        Evento evento = new Evento(nome, local, ldt, duracao, caracteristica);
        usuario.adicionarEvento(evento);
    }
}
