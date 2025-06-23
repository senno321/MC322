/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322.evento;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Representa uma atividade de extensão acadêmica, com informações específicas
 * como descrição, público-alvo, validação de horas e carga horária.
 * 
 * <p>
 * É uma subclasse de {@link CaracteristicaEvento}, sendo usada para marcar
 * eventos que fazem parte de projetos ou programas de extensão universitária.
 */
@Entity
@DiscriminatorValue("EXTENSAO")
public class EventoExtensao extends CaracteristicaEvento {
    private String descricaoAtividade; // Descrição da atividade de extensão
    private String publicoAlvo; // Público-alvo da atividade
    private boolean valeHoras; // Indica se a atividade vale horas para extensão
    private int cargaHoraria; // Quantidade de horas que a atividade vale

    /**
     * Construtor que inicializa completamente os dados da atividade de extensão.
     * 
     * @param descricaoAtividade descrição resumida da atividade.
     * @param publicoAlvo        grupo de pessoas para o qual a atividade é
     *                           destinada.
     * @param valeHoras          se {@code true}, a atividade conta como horas de
     *                           extensão.
     * @param cargaHoraria       quantidade de horas atribuídas à atividade (se
     *                           valer horas).
     */
    public EventoExtensao(String descricaoAtividade, String publicoAlvo,
            boolean valeHoras, int cargaHoraria) {
        this.descricaoAtividade = descricaoAtividade;
        this.publicoAlvo = publicoAlvo;
        this.valeHoras = valeHoras;
        this.cargaHoraria = cargaHoraria;
    }

    public EventoExtensao() {
        
    }

    /**
     * Retorna uma descrição formatada da atividade de extensão, incluindo o
     * público-alvo
     * e, se aplicável, a quantidade de horas que ela vale.
     * 
     * @return uma {@code String} descritiva da atividade de extensão.
     */
    @Override
    public String descricao() {
        String base = "Extensão: " + descricaoAtividade +
                " (Público: " + publicoAlvo + ")";
        if (valeHoras) {
            base += " [Vale " + cargaHoraria + "h de extensão]";
        }
        return base;
    }
}
