/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322.exceptions;

// Exceção personalizada para indicar operações inválidas em tempo de execução.
public class OperationInvalidException extends RuntimeException {
    public OperationInvalidException(String mensagem) {
        super(mensagem);
    }
}