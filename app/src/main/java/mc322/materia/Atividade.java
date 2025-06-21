/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322.materia;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa uma atividade acadêmica associada a uma matéria,
 * com nome, peso, conteúdo, data de entrega e status de conclusão.
 */
public class Atividade {
    private String nome;
    private double peso;
    private String conteudo;
    private LocalDateTime dataDeEntrega;
    private boolean completa;

    /**
     * Construtor que inicializa uma atividade com nome, peso, conteúdo e data de entrega.
     * Inicialmente, a atividade não está concluída.
     * 
     * @param nome         nome da atividade.
     * @param peso         peso da atividade na média final.
     * @param conteudo     conteúdo cobrado na atividade.
     * @param data         data e hora da entrega da atividade.
     */
    public Atividade(String nome, double peso, String conteudo, LocalDateTime data) {
        this.nome = nome;
        this.peso = peso;
        this.conteudo = conteudo;
        this.dataDeEntrega = data;
        this.completa = false;
    }

    /**
     * Retorna o nome da atividade.
     * 
     * @return o nome da atividade.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Define o nome da atividade.
     * 
     * @param nome novo nome da atividade.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o peso da atividade na média.
     * 
     * @return o peso da atividade.
     */
    public double getPeso() {
        return this.peso;
    }
    
    /**
     * Define o peso da atividade.
     * 
     * @param peso novo peso da atividade.
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }

    /**
     * Retorna o conteúdo cobrado na atividade.
     * 
     * @return o conteúdo da atividade.
     */
    public String getConteudo() {
        return this.conteudo;
    }

    /**
     * Retorna a data e hora de entrega da atividade.
     * 
     * @return um {@code LocalDateTime} representando o prazo de entrega.
     */
    public LocalDateTime getData() {
        return this.dataDeEntrega;
    }

    /**
     * Define a data e hora de entrega da atividade a partir de strings.
     * 
     * @param data       data no formato "dd/MM/yyyy".
     * @param horaInicio hora no formato "HH:mm".
     */
    public void setData(String data, String horaInicio) {
        String dataHoraInicio = data + " " + horaInicio;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(dataHoraInicio, formatter);
        this.dataDeEntrega = ldt;
    }

    /**
     * Verifica se a atividade já foi concluída.
     * 
     * @return {@code true} se a atividade foi concluída; {@code false} caso contrário.
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
