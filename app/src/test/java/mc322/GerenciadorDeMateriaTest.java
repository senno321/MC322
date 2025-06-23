/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mc322.exceptions.ResourceDataException;
import mc322.materia.GerenciadorDeMaterias;
import mc322.materia.Materia;
import mc322.usuario.Usuario;

class GerenciadorDeMateriasTest {

    private GerenciadorDeMaterias gerenciador;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        gerenciador = GerenciadorDeMaterias.getInstance();
        usuario = new Usuario("Aluno Teste", "aluno@teste.com", "123456");
    }

    @Test
    void testGetInstance() {
        assertNotNull(gerenciador);
        assertSame(gerenciador, GerenciadorDeMaterias.getInstance());
    }

    @Test
    void testBuscarMateriaPorCodigoExistente() {
        Materia materia = gerenciador.buscarMateriaPorCodigo("MC322");
        assertNotNull(materia);
        assertEquals("Programação Orientada a Objetos", materia.getNome());
    }

    @Test
    void testBuscarMateriaPorCodigoInexistente() {
        Materia materia = gerenciador.buscarMateriaPorCodigo("XX999");
        assertNull(materia);
    }

    @Test
    void testCriarMateria() {
        gerenciador.criarMateria(usuario, "MC102");
        assertEquals(1, usuario.getMaterias().size());
        assertEquals("Algoritmos e Programação de Computadores", usuario.getMaterias().get(0).getNome());
    }

    @Test
    void testGetCatalogoMaterias() {
        assertFalse(gerenciador.getCatalogoMaterias().isEmpty());
    }

        /**
     * Testa se o método privado `lerArquivoDeTexto` lança uma
     * ResourceDataException quando um caminho de arquivo inválido é fornecido.
     * Utiliza reflection para acessar e invocar o método privado.
     */
    @Test
    void testLerArquivoInexistente() {
        // Usa reflection para obter o método privado 'lerArquivoDeTexto'
        Exception exception = assertThrows(InvocationTargetException.class, () -> {
            Method metodoPrivado = GerenciadorDeMaterias.class.getDeclaredMethod("lerArquivoDeTexto", String.class);
            metodoPrivado.setAccessible(true); // Torna o método acessível

            // Invoca o método com um caminho de arquivo que não existe
            metodoPrivado.invoke(gerenciador, "caminho/invalido/arquivo.txt");
        });
        
        // A InvocatioTargetException "envolve" a exceção original lançada pelo método.
        // Verificamos se a causa raiz é a ResourceDataException que esperamos.
        Throwable causaRaiz = exception.getCause();
        assertNotNull(causaRaiz);
        assertTrue(causaRaiz instanceof ResourceDataException);
        assertTrue(causaRaiz.getMessage().contains("Arquivo não encontrado"));
    }
}