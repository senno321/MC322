import java.time.LocalDateTime;

import evento.GerenciadorDeEventos;
import usuario.Usuario;

public class Main {
    public static void main(String[] args) {
        GerenciadorDeEventos gerenciador = new GerenciadorDeEventos();
        Usuario usuario = new Usuario("Bruno", "sabasbruno132@gmail.com", "AmargoAzedo");
        LocalDateTime dataHora = LocalDateTime.of(2025, 6, 21, 19, 0);
        gerenciador.criarEvento(usuario, "Reunião", "CB03", dataHora, 120, "Lehilton", "IC", false);
        System.out.println(usuario.getEventos().get(0).getCaracteristicaEvento());
    }
}
