/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mc322.materia.Atividade;
import mc322.materia.Materia;

class MateriaTest {

    private Materia materia;

    @BeforeEach
    void setUp() {
        materia = new Materia("Programação Orientada a Objetos", "Prof. Anonimo", 4);
    }

    @Test
    void testGetNome() {
        assertEquals("Programação Orientada a Objetos", materia.getNome());
    }

    @Test
    void testGetProfessor() {
        assertEquals("Prof. Anonimo", materia.getProfessor());
    }

    @Test
    void testGetCreditos() {
        assertEquals(4, materia.getCreditos());
    }

    @Test
    void testGetLimiteFaltas() {
        assertEquals(7, materia.getLimiteFaltas());
    }

    @Test
    void testSetFaltas() {
        materia.setFaltas(5);
        assertEquals(5, materia.getFaltas());
    }

    @Test
    void testAdicionaAtividade() {
        Atividade atividade = new Atividade("Trabalho 1", 10.0, "Desenvolver um sistema de agenda", LocalDateTime.now());
        materia.adicionaAtividade(atividade);
        assertTrue(materia.getListaAtividades().contains(atividade));
        assertEquals(1, materia.getListaAtividades().size());
    }
}