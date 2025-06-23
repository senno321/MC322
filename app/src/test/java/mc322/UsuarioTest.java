/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 * Testes unitários para a classe Usuario.
 */

package mc322;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mc322.agendavel.ItemAgendavel;
import mc322.evento.EventoFactory;
import mc322.exceptions.OperationInvalidException;
import mc322.materia.Materia;
import mc322.usuario.Usuario;

class UsuarioTest {

    private Usuario usuario;
    private Materia materia;
    private ItemAgendavel evento;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("Test User", "test@test.com", "password123");
        materia = new Materia("MC322", "MC322", "Test Professor", 4);
        evento = EventoFactory.criarEventoReuniao("Reunião de Teste", "Sala 1", "25/12/2024", "10:00", 60, "Equipe", "Discussão de Projeto", false);
    }

    /** Testa adicionar uma matéria nova ao usuário. */
    @Test
    void testAdicionarMateria() {
        assertDoesNotThrow(() -> usuario.adicionarMateria(materia));
        assertTrue(usuario.getMaterias().contains(materia));
    }

    /** Testa adicionar uma matéria já existente, deve lançar exceção. */
    @Test
    void testAdicionarMateriaDuplicada() {
        usuario.adicionarMateria(materia);
        assertThrows(OperationInvalidException.class, () -> usuario.adicionarMateria(materia));
    }

    /** Testa remover uma matéria existente do usuário. */
    @Test
    void testRemoverMateria() {
        usuario.adicionarMateria(materia);
        assertDoesNotThrow(() -> usuario.removerMateria(materia));
        assertFalse(usuario.getMaterias().contains(materia));
    }

    /** Testa tentar remover matéria que o usuário não possui, deve lançar exceção. */
    @Test
    void testRemoverMateriaInexistente() {
        assertThrows(OperationInvalidException.class, () -> usuario.removerMateria(materia));
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