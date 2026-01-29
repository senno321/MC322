/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 * Testes unitários para a classe Materia.
 */

package mc322;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import classes.materia.Atividade;
import classes.materia.Materia;

class MateriaTest {

    private Materia materia;

    @BeforeEach
    void setUp() {
        materia = new Materia("MC322", "Programação Orientada a Objetos", "Prof. Anonimo", 4);
    }

    /** Verifica o nome da matéria. */
    @Test
    void testGetNome() {
        assertEquals("Programação Orientada a Objetos", materia.getNome());
    }

    /** Verifica o nome do professor da matéria. */
    @Test
    void testGetProfessor() {
        assertEquals("Prof. Anonimo", materia.getProfessor());
    }

    /** Verifica o número de créditos da matéria. */
    @Test
    void testGetCreditos() {
        assertEquals(4, materia.getCreditos());
    }

    /** Verifica o limite de faltas padrão da matéria. */
    @Test
    void testGetLimiteFaltas() {
        assertEquals(7, materia.getLimiteFaltas());
    }

    /** Verifica se uma atividade é adicionada corretamente à lista da matéria. */
    @Test
    void testAdicionaAtividade() {
        Atividade atividade = new Atividade("Trabalho 1", 10.0, "Desenvolver um sistema de agenda", LocalDateTime.now());
        materia.adicionaAtividade(atividade);
        assertTrue(materia.getListaAtividades().contains(atividade));
        assertEquals(1, materia.getListaAtividades().size());
    }
}