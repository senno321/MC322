/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import mc322.evento.Evento;
import mc322.evento.EventoFactory;
import mc322.materia.Atividade;
import mc322.materia.Materia;

class EventoFactoryTest {

    /**
     * Testa a criação de evento do tipo atividade associada a uma matéria.
     */
    @Test
    void testCriarEventoAtividade() {
        Materia materia = new Materia("MC102", "MC102", "Prof. Y", 6);
        Evento evento = EventoFactory.criarEventoAtividade(materia, "Prova Final", "CB02", "10/07/2025", "08:00", 120,
                10.0, "Tudo");

        assertEquals("Prova Final", evento.getNome());
        assertTrue(evento.getDetalhes().contains("Prova/Atividade de(a) MC102"));
    }

    /**
     * Testa a criação de evento baseado em uma atividade já existente.
     */
    @Test
    void testCriarEventoDeAtividadeExistente() {
        Materia materia = new Materia("MC202", "MC202", "Prof. Z", 4);
        Atividade atividade = new Atividade("Lab 5", 2.0, "Grafos", LocalDateTime.of(2025, 7, 15, 23, 59));
        Evento evento = EventoFactory.criarEventoDeAtividadeExistente(atividade, materia);

        assertEquals("Lab 5", evento.getNome());
        assertEquals("Casa", evento.getLocal());
        assertEquals(0, evento.getDuracao());
    }

    /**
     * Testa a criação de evento do tipo reunião.
     */
    @Test
    void testCriarEventoReuniao() {
        Evento evento = EventoFactory.criarEventoReuniao("Alinhamento", "Online", "12/07/2025", "14:00", 45, "Time A",
                "Definir metas", true);

        assertEquals("Alinhamento", evento.getNome());
        assertTrue(evento.getDetalhes().contains("Reunião com Time A"));
        assertTrue(evento.getDetalhes().contains("Online"));
    }

    /**
     * Testa a criação de evento de extensão.
     */
    @Test
    void testCriarEventoExtensao() {
        Evento evento = EventoFactory.criarEventoExtensao("Palestra", "Auditório", "18/07/2025", "19:00", 90,
                "IA no mercado", "Estudantes", true, 2);

        assertEquals("Palestra", evento.getNome());
        assertTrue(evento.getDetalhes().contains("Extensão: IA no mercado"));
        assertTrue(evento.getDetalhes().contains("Vale 2h de extensão"));
    }

    /**
     * Testa a criação de eventos com datas inválidas, esperando exceções.
     */
    @Test
    void testCriarEventoDataInvalida() {
        Materia materia = new Materia("F128", "Fisica I", "Prof. Einstein", 4);
        assertThrows(IllegalArgumentException.class, () -> EventoFactory.criarEventoAtividade(materia, "Prova",
                "Sala 1", "32/01/2025", "10:00", 120, 10, "Mecânica"));
        assertThrows(IllegalArgumentException.class, () -> EventoFactory.criarEventoReuniao("Reunião", "Sala 2",
                "20/13/2025", "11:00", 60, "Equipe", "Pauta", false));
    }
}
