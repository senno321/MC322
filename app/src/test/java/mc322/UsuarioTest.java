/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
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

    @Test
    void testAdicionarMateria() {
        assertDoesNotThrow(() -> usuario.adicionarMateria(materia));
        assertTrue(usuario.getMaterias().contains(materia));
    }

    @Test
    void testAdicionarMateriaDuplicada() {
        usuario.adicionarMateria(materia);
        assertThrows(OperationInvalidException.class, () -> usuario.adicionarMateria(materia));
    }

    @Test
    void testRemoverMateria() {
        usuario.adicionarMateria(materia);
        assertDoesNotThrow(() -> usuario.removerMateria(materia));
        assertFalse(usuario.getMaterias().contains(materia));
    }

    @Test
    void testRemoverMateriaInexistente() {
        assertThrows(OperationInvalidException.class, () -> usuario.removerMateria(materia));
    }

    @Test
    void testAdicionarItemAgendado() {
        assertDoesNotThrow(() -> usuario.adicionarItemAgendado(evento));
        assertTrue(usuario.getItensAgendados().contains(evento));
    }

    @Test
    void testAdicionarItemAgendadoDuplicado() {
        usuario.adicionarItemAgendado(evento);
        assertThrows(OperationInvalidException.class, () -> usuario.adicionarItemAgendado(evento));
    }

    @Test
    void testRemoverItemAgendado() {
        usuario.adicionarItemAgendado(evento);
        assertDoesNotThrow(() -> usuario.removerItemAgendado(evento));
        assertFalse(usuario.getItensAgendados().contains(evento));
    }

    @Test
    void testRemoverItemAgendadoInexistente() {
        assertThrows(OperationInvalidException.class, () -> usuario.removerItemAgendado(evento));
    }

    @Test
    void testVerificarSenhaCorreta() {
        assertTrue(usuario.verificarSenha("password123"));
    }

    @Test
    void testVerificarSenhaIncorreta() {
        assertFalse(usuario.verificarSenha("wrongpassword"));
    }

    @Test
    void testSetSenha() {
        usuario.setSenha("password123", "newpassword");
        assertTrue(usuario.verificarSenha("newpassword"));
    }
}