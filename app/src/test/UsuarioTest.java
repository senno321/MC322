/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 * Testes unitários para a classe Usuario.
 */

package mc322;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import classes.agendavel.ItemAgendavel;
import classes.evento.EventoFactory;
import classes.exceptions.OperationInvalidException;
import classes.inscricao.Inscricao;
import classes.materia.Materia;
import classes.usuario.Usuario;

class UsuarioTest {

    private Usuario usuario;
    private Materia materia;
    private ItemAgendavel evento;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("Test User", "test@test.com", "password123");
        materia = new Materia("MC322", "MC322", "Test Professor", 4);
        evento = EventoFactory.criarEventoReuniao("Reunião de Teste", "Sala 1", "25/12/2024", "10:00", 60, "Equipe",
                "Discussão de Projeto", false);
    }

    /**
     * Testa a adição de uma nova inscrição à lista do usuário.
     */
    @Test
    void testAdicionarInscricao() {
        Inscricao inscricao = new Inscricao(usuario, materia);

        assertDoesNotThrow(() -> usuario.getInscricoes().add(inscricao));
        assertEquals(1, usuario.getInscricoes().size());
        assertTrue(usuario.getInscricoes().contains(inscricao));
    }

    /**
     * Testa a adição de uma inscrição duplicada.
     */
    @Test
    void testAdicionarInscricaoDuplicada() {
        Inscricao inscricao = new Inscricao(usuario, materia);
        usuario.getInscricoes().add(inscricao);

        usuario.getInscricoes().add(inscricao);

        assertEquals(2, usuario.getInscricoes().size());
    }

    /**
     * Testa a remoção de uma inscrição existente do usuário.
     */
    @Test
    void testRemoverInscricao() {
        Inscricao inscricao = new Inscricao(usuario, materia);
        usuario.getInscricoes().add(inscricao);
        assertEquals(1, usuario.getInscricoes().size());

        boolean foiRemovida = usuario.getInscricoes().remove(inscricao);

        assertTrue(foiRemovida);
        assertEquals(0, usuario.getInscricoes().size());
        assertFalse(usuario.getInscricoes().contains(inscricao));
    }

    /**
     * Testa a tentativa de remover uma inscrição que não existe na lista do
     * usuário.
     */
    @Test
    void testRemoverInscricaoInexistente() {
        assertEquals(0, usuario.getInscricoes().size());

        Inscricao inscricaoInexistente = new Inscricao(usuario, materia);
        boolean foiRemovida = usuario.getInscricoes().remove(inscricaoInexistente);

        assertFalse(foiRemovida);
        assertEquals(0, usuario.getInscricoes().size());
    }

    /** Testa adicionar um item agendável novo ao usuário. */
    @Test
    void testAdicionarItemAgendado() {
        assertDoesNotThrow(() -> usuario.adicionarItemAgendado(evento));
        assertTrue(usuario.getItensAgendados().contains(evento));
    }

    /** Testa adicionar um item agendável duplicado, deve lançar exceção. */
    @Test
    void testAdicionarItemAgendadoDuplicado() {
        usuario.adicionarItemAgendado(evento);
        assertThrows(OperationInvalidException.class, () -> usuario.adicionarItemAgendado(evento));
    }

    /** Testa remover um item agendável existente do usuário. */
    @Test
    void testRemoverItemAgendado() {
        usuario.adicionarItemAgendado(evento);
        assertDoesNotThrow(() -> usuario.removerItemAgendado(evento));
        assertFalse(usuario.getItensAgendados().contains(evento));
    }

    /** Testa tentar remover item agendável inexistente, deve lançar exceção. */
    @Test
    void testRemoverItemAgendadoInexistente() {
        assertThrows(OperationInvalidException.class, () -> usuario.removerItemAgendado(evento));
    }

    /** Testa verificação da senha correta. */
    @Test
    void testVerificarSenhaCorreta() {
        assertTrue(usuario.verificarSenha("password123"));
    }

    /** Testa verificação da senha incorreta. */
    @Test
    void testVerificarSenhaIncorreta() {
        assertFalse(usuario.verificarSenha("wrongpassword"));
    }

    /** Testa alteração da senha e verificação com a nova senha. */
    @Test
    void testSetSenha() {
        usuario.setSenha("password123", "newpassword");
        assertTrue(usuario.verificarSenha("newpassword"));
    }
}