/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package materia;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonIgnore; // Importante para evitar loops infinitos


/**
 * Representa uma atividade acadêmica associada a uma matéria,
 * com nome, peso, conteúdo, data de entrega e status de conclusão.
 */
@Entity
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "materia_id") 
    @JsonIgnore
    private Materia materia;

    private String nome;
    private double peso;
    private String conteudo;
    private LocalDateTime dataDeEntrega;
    private boolean completa;

    /**
     * Inicializa a atividade com nome, peso, conteúdo e data de entrega.
     * A atividade inicia como não concluída.
     *
     * @param nome     nome da atividade
     * @param peso     peso da atividade na média final (deve ser >= 0)
     * @param conteudo conteúdo cobrado na atividade
     * @param data     data e hora de entrega da atividade
     */
    public Atividade(String nome, double peso, String conteudo, LocalDateTime data) {
        this.nome = nome;
        this.peso = peso;
        this.conteudo = conteudo;
        this.dataDeEntrega = data;
        this.completa = false;
    }

    public Atividade() {
        
    }

    /**
     * Retorna o nome da atividade.
     * 
     * @return nome da atividade
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Atualiza o nome da atividade.
     * 
     * @param nome novo nome da atividade (não pode ser nulo ou vazio)
     * @throws IllegalArgumentException se nome for nulo ou vazio
     */
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome da atividade não pode ser vazio.");
        this.nome = nome;
    }

    /**
     * Retorna o peso da atividade na média final.
     * 
     * @return peso da atividade
     */
    public double getPeso() {
        return this.peso;
    }

    /**
     * Atualiza o peso da atividade.
     * 
     * @param peso novo peso (deve ser >= 0)
     * @throws IllegalArgumentException se peso for negativo
     */
    public void setPeso(double peso) {
        if (peso < 0)
            throw new IllegalArgumentException("Peso da atividade não pode ser negativo.");
        this.peso = peso;
    }

    /**
     * Retorna o conteúdo cobrado na atividade.
     * 
     * @return conteúdo da atividade
     */
    public String getConteudo() {
        return this.conteudo;
    }

    /**
     * Retorna a data e hora de entrega da atividade.
     * 
     * @return data e hora da entrega
     */
    public LocalDateTime getData() {
        return this.dataDeEntrega;
    }

    /**
     * Define a data e hora de início do item usando strings separadas.
     *
     * @param data       Data no formato "dd/MM/yyyy".
     * @param horaInicio Hora no formato "HH:mm".
     * @throws IllegalArgumentException se os parâmetros forem inválidos ou mal
     *                                  formatados.
     */
    public void setDataHoraInicio(String data, String horaInicio) {
        if (data == null || data.trim().isEmpty()) {
            throw new IllegalArgumentException("A data fornecida não pode ser nula ou vazia.");
        }
        if (horaInicio == null || horaInicio.trim().isEmpty()) {
            throw new IllegalArgumentException("A hora de início fornecida não pode ser nula ou vazia.");
        }

        String dataDeEntrega = data.trim() + " " + horaInicio.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try {
            LocalDateTime ldt = LocalDateTime.parse(dataDeEntrega, formatter);
            this.dataDeEntrega = ldt;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Formato de data ou hora inválido. Esperado: 'dd/MM/yyyy HH:mm'. Valor recebido: '" +
                            dataDeEntrega + "'",
                    e);
        }
    }

    /**
     * Indica se a atividade foi concluída.
     * 
     * @return {@code true} se concluída, {@code false} caso contrário
     */
    public boolean getCompleta() {
        return this.completa;
    }

    /**
     * Marca a atividade como concluída.
     */
    public void marcarComoConcluida() {
        this.completa = true;
    }
}