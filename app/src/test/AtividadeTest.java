/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import classes.materia.Atividade;

class AtividadeTest {

    /**
     * Verifica se o construtor e os getters funcionam corretamente.
     */
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

    /**
     * Verifica se é possível alterar o nome da atividade com valor válido.
     */
    @Test
    void testSetNomeValido() {
        Atividade atividade = new Atividade("Nome Antigo", 5.0, "Conteúdo", LocalDateTime.now());
        atividade.setNome("Nome Novo");
        assertEquals("Nome Novo", atividade.getNome());
    }

    /**
     * Verifica se o método setNome lança exceção para valores inválidos.
     */
    @Test
    void testSetNomeInvalido() {
        Atividade atividade = new Atividade("Nome", 5.0, "Conteúdo", LocalDateTime.now());
        assertThrows(IllegalArgumentException.class, () -> atividade.setNome(null));
        assertThrows(IllegalArgumentException.class, () -> atividade.setNome(" "));
    }

    /**
     * Verifica se o peso pode ser alterado corretamente com valor válido.
     */
    @Test
    void testSetPesoValido() {
        Atividade atividade = new Atividade("Atividade", 0.0, "Conteúdo", LocalDateTime.now());
        atividade.setPeso(10.0);
        assertEquals(10.0, atividade.getPeso());
    }

    /**
     * Verifica se o método setPeso lança exceção para valor negativo.
     */
    @Test
    void testSetPesoInvalido() {
        Atividade atividade = new Atividade("Atividade", 5.0, "Conteúdo", LocalDateTime.now());
        assertThrows(IllegalArgumentException.class, () -> atividade.setPeso(-1.0));
    }

    /**
     * Verifica se a atividade é marcada como concluída corretamente.
     */
    @Test
    void testMarcarComoConcluida() {
        Atividade atividade = new Atividade("Atividade", 5.0, "Conteúdo", LocalDateTime.now());
        assertFalse(atividade.getCompleta());
        atividade.marcarComoConcluida();
        assertTrue(atividade.getCompleta());
    }
}
