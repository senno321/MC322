package app.src.main.java.mc322.evento;
import java.time.LocalDateTime;

import app.src.main.java.mc322.materia.Materia;
import app.src.main.java.mc322.usuario.Usuario;

public class GerenciadorDeEventos {

    public void criarEvento(Usuario usuario, String nome, String local, LocalDateTime dataHoraInicio, int duracao,
            Materia materia,
            String tipo, double peso, String conteudo) {

        CaracteristicaEvento caracteristica = new EventoProva(materia, tipo, peso, conteudo);
        Evento evento = new Evento(nome, local, dataHoraInicio, duracao, caracteristica);
        usuario.adicionarEvento(evento);
    }

    public void criarEvento(Usuario usuario, String nome, String local, LocalDateTime dataHoraInicio, int duracao,
            String participantes, String objetivo, boolean online) {

        CaracteristicaEvento caracteristica = new EventoReuniao(participantes, objetivo, online);
        Evento evento = new Evento(nome, local, dataHoraInicio, duracao, caracteristica);
        usuario.adicionarEvento(evento);
    }

    public void criarEvento(Usuario usuario, String nome, String local, LocalDateTime dataHoraInicio, int duracao,
            String descricaoAtividade, String publicoAlco, boolean valeHoras, int cargaHoraria) {

        CaracteristicaEvento caracteristica = new EventoExtensao(descricaoAtividade, publicoAlco, valeHoras,
                cargaHoraria);
        Evento evento = new Evento(nome, local, dataHoraInicio, duracao, caracteristica);
        usuario.adicionarEvento(evento);
    }
}
