/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import mc322.materia.Atividade;

class AtividadeTest {

    @Test
    void testConstrutorEGetters() {
        LocalDateTime data = LocalDateTime.of(2025, 1, 1, 10, 0);
        Atividade atividade = new Atividade("Prova 1", 8.5, "Cálculo", data);

        assertEquals("Prova 1", atividade.getNome());
        assertEquals(8.5, atividade.getPeso());
        assertEquals("Cálculo", atividade.getConteudo());
        assertEquals(data, atividade.getData());
        assertFalse(atividade.getCompleta());
    }

    @Test
    void testSetNomeValido() {
        Atividade atividade = new Atividade("Nome Antigo", 5.0, "Conteúdo", LocalDateTime.now());
        atividade.setNome("Nome Novo");
        assertEquals("Nome Novo", atividade.getNome());
    }

    @Test
    void testSetNomeInvalido() {
        Atividade atividade = new Atividade("Nome", 5.0, "Conteúdo", LocalDateTime.now());
        assertThrows(IllegalArgumentException.class, () -> atividade.setNome(null));
        assertThrows(IllegalArgumentException.class, () -> atividade.setNome(" "));
    }

    @Test
    void testSetPesoValido() {
        Atividade atividade = new Atividade("Atividade", 0.0, "Conteúdo", LocalDateTime.now());
        atividade.setPeso(10.0);
        assertEquals(10.0, atividade.getPeso());
    }

    @Test
    void testSetPesoInvalido() {
        Atividade atividade = new Atividade("Atividade", 5.0, "Conteúdo", LocalDateTime.now());
        assertThrows(IllegalArgumentException.class, () -> atividade.setPeso(-1.0));
    }

    @Test
    void testMarcarComoConcluida() {
        Atividade atividade = new Atividade("Atividade", 5.0, "Conteúdo", LocalDateTime.now());
        assertFalse(atividade.getCompleta());
        atividade.marcarComoConcluida();
        assertTrue(atividade.getCompleta());
    }
}