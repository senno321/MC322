/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322;

import mc322.evento.GerenciadorDeEventos;
import mc322.usuario.Usuario;

/**
 * Contém a estrutura de implementação da aplicação.
 * 
 * @author Bruno Medeiros Saback - 281746
 * @author Lucas
 * @author Filipe
 * @author Kauan Cunha da Silva - 240030
 * 
 *         Comentários foram gerados em sua maioria por IA
 * 
 */

public class App {
    public static void main(String[] args) {
        GerenciadorDeEventos gerenciador = new GerenciadorDeEventos();
        Usuario usuario = new Usuario("Bruno", "sabasbruno132@gmail.com", "AmargoAzedo");
        gerenciador.criarEvento(usuario, "Reunião", "CB03", "06/05/2025", "15:00", 120, "Lehilton", "IC", false);
        System.out.println(usuario.getEventos().get(0).getCaracteristicaEvento());
    }
}
