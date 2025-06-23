/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mc322.exceptions.OperationInvalidException;
import mc322.materia.Materia;
import mc322.servico.Agenda;
import mc322.usuario.Usuario;

class AgendaTest {

    private Agenda agenda;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        agenda = Agenda.getInstance();
        usuario = new Usuario("Usuário Teste", "usuario@teste.com", "senha");
    }

    @Test
    void testAgendarNovaReuniao() {
        assertDoesNotThrow(() -> agenda.agendarNovaReuniao(usuario, "Reunião Importante", "Sala de Reuniões", "22/06/2025", "15:00", 60, "Equipe de Devs", "Planejamento Sprint", false));
        assertEquals(1, usuario.getItensAgendados().size());
        assertEquals("Reunião Importante", usuario.getItensAgendados().get(0).getNome());
    }

    @Test
    void testAgendarNovaAtividade() {
        Materia materia = new Materia("MC322", "Professor X", 4);
        usuario.adicionarMateria(materia);

        assertDoesNotThrow(() -> agenda.agendarNovaAtividade(usuario, "Prova 1", "Anfiteatro", "25/06/2025", "10:00", 120, "MC322", 10.0, "Toda a matéria"));
        assertEquals(1, usuario.getItensAgendados().size());
        assertEquals(1, materia.getListaAtividades().size());
    }

    @Test
    void testAgendarNovaAtividadeMateriaInexistente() {
        assertThrows(OperationInvalidException.class, () -> agenda.agendarNovaAtividade(usuario, "Prova 1", "Anfiteatro", "25/06/2025", "10:00", 120, "MC102", 10.0, "Toda a matéria"));
    }

    @Test
    void testAgendarNovoEventoExtensao() {
        assertDoesNotThrow(() -> agenda.agendarNovoEventoExtensao(usuario, "Feira de Ciências", "Ginásio", "30/06/2025", "09:00", 480, "Apresentação de projetos", "Comunidade", true, 8));
        assertEquals(1, usuario.getItensAgendados().size());
    }
}