/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package evento;

import materia.Materia;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

/**
 * Representa uma atividade avaliativa (como uma prova ou trabalho) associada a
 * uma {@link Materia}.
 * 
 * <p>
 * É uma subclasse de {@link CaracteristicaEvento}, utilizada para identificar
 * eventos
 * do tipo "atividade acadêmica" no sistema.
 */
@Entity
@DiscriminatorValue("ATIVIDADE")
public class EventoAtividade extends CaracteristicaEvento {
    @ManyToOne 
    private Materia materia; // Matéria relacionada à prova

    private double peso; // Peso da nota
    private String conteudo; // Conteúdo que será cobrado

    /**
     * Construtor para inicializar os dados da atividade.
     * 
     * @param materia  a matéria associada à atividade.
     * @param peso     o peso da atividade na média final.
     * @param conteudo o conteúdo que será cobrado na atividade.
     */
    public EventoAtividade(Materia materia, double peso, String conteudo) {
        this.materia = materia;
        this.peso = peso;
        this.conteudo = conteudo;
    }

    public EventoAtividade() {
        
    }

    /**
     * Retorna uma descrição textual da atividade, incluindo matéria, peso e
     * conteúdo.
     * 
     * @return uma {@code String} formatada com os dados da atividade.
     */
    @Override
    public String descricao() {
        return "Prova/Atividade de(a) " + materia.getNome() +
                " com peso " + peso +
                ". Conteúdo: " + conteudo + ".";
    }
}
