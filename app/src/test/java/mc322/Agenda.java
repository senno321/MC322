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

    /**
     * Testa o agendamento de uma nova reunião para o usuário.
     * Verifica se o item foi adicionado à lista e se o nome está correto.
     */
    @Test
    void testAgendarNovaReuniao() {
        assertDoesNotThrow(() ->
            agenda.agendarNovaReuniao(usuario, "Reunião Importante", "Sala de Reuniões", "22/06/2025", "15:00", 60, "Equipe de Devs", "Planejamento Sprint", false)
        );
        assertEquals(1, usuario.getItensAgendados().size());
        assertEquals("Reunião Importante", usuario.getItensAgendados().get(0).getNome());
    }

    /**
     * Testa o agendamento de uma nova atividade vinculada a uma matéria existente.
     * Verifica se a atividade foi adicionada tanto à agenda do usuário quanto à matéria.
     */
    @Test
    void testAgendarNovaAtividade() {
        Materia materia = new Materia("MC322", "MC322", "Professor X", 4);
        usuario.adicionarMateria(materia);

        assertDoesNotThrow(() ->
            agenda.agendarNovaAtividade(usuario, "Prova 1", "Anfiteatro", "25/06/2025", "10:00", 120, "MC322", 10.0, "Toda a matéria")
        );
        assertEquals(1, usuario.getItensAgendados().size());
        assertEquals(1, materia.getListaAtividades().size());
    }

    /**
     * Testa o comportamento do sistema ao tentar agendar uma atividade
     * para uma matéria que o usuário não possui. Deve lançar exceção.
     */
    @Test
    void testAgendarNovaAtividadeMateriaInexistente() {
        assertThrows(OperationInvalidException.class, () ->
            agenda.agendarNovaAtividade(usuario, "Prova 1", "Anfiteatro", "25/06/2025", "10:00", 120, "MC102", 10.0, "Toda a matéria")
        );
    }

    /**
     * Testa o agendamento de um novo evento de extensão.
     * Verifica se ele é adicionado corretamente à agenda do usuário.
     */
    @Test
    void testAgendarNovoEventoExtensao() {
        assertDoesNotThrow(() ->
            agenda.agendarNovoEventoExtensao(usuario, "Feira de Ciências", "Ginásio", "30/06/2025", "09:00", 480, "Apresentação de projetos", "Comunidade", true, 8)
        );
        assertEquals(1, usuario.getItensAgendados().size());
    }
}