/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package evento;

import jakarta.persistence.*;

/**
 * Classe abstrata que define uma característica genérica de um evento.
 * 
 * <p>
 * Essa classe serve como base para subclasses que representam tipos
 * específicos de eventos, como {@code EventoProva}, {@code EventoReuniao}, etc.
 * 
 * <p>
 * Ela define a interface que todas as características de eventos devem
 * implementar.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO_CARACTERISTICA", discriminatorType = DiscriminatorType.STRING)
public abstract class CaracteristicaEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Retorna uma descrição textual da característica do evento.
     * 
     * <p>
     * Cada subclasse deve implementar esse método para fornecer uma
     * descrição específica do tipo de evento que representa.
     * 
     * @return uma {@code String} contendo a descrição da característica do evento.
     */
    public abstract String descricao();
}