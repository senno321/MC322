/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322.notificacao;

import mc322.usuario.Usuario;
import mc322.evento.Evento;

/**
 * Interface para serviços de notificação.
 * Define um contrato para enviar uma mensagem a um usuário
 * a respeito de um evento específico.
 */
public interface Notificavel {

    /**
     * Envia uma notificação para o usuário.
     *
     * @param usuario  O destinatário da notificação.
     * @param evento   O evento relacionado à notificação.
     * @param mensagem O conteúdo da notificação.
     */
    void enviar(Usuario usuario, Evento evento, String mensagem);
}