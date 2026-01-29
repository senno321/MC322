/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package exceptions;

// Exceção personalizada para erros relacionados a dados de recursos (ex: arquivos).
public class ResourceDataException extends RuntimeException {
    public ResourceDataException(String mensagem) {
        super(mensagem);
    }

    public ResourceDataException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}