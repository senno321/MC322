/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package notificacao;

import evento.Evento;
import usuario.Usuario;

/**
 * Implementa o envio de notificação via e-mail.
 */
public class Email implements Notificavel {

    /**
     * Simula o envio de um e-mail com informações do evento.
     *
     * @param usuario  destinatário
     * @param evento   evento relacionado
     * @param mensagem corpo da mensagem
     */
    @Override
    public void enviar(Usuario usuario, Evento evento, String mensagem) {
        System.out.println("Para: " + usuario.getEmail());
        System.out.println("Assunto: Lembrete de Evento - " + evento.getNome());
        System.out.println("Mensagem: " + mensagem);
    }
}
