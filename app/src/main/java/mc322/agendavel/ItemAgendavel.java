/*
 * Material usado na disciplina MC322 - Programação Orientada a Objetos.
 */

package mc322.agendavel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jakarta.persistence.*;

/**
 * Classe abstrata que representa um item agendável no calendário,
 * como eventos ou lembretes. Contém nome e data/hora de início.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO_ITEM", discriminatorType = DiscriminatorType.STRING)
public abstract class ItemAgendavel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome do item agendável (ex: nome do evento ou lembrete). */
    protected String nome;

    /** Data e hora de início do item agendável. */
    protected LocalDateTime dataHoraInicio;

    /**
     * Construtor base para inicializar o item agendável.
     *
     * @param nome           Nome do item.
     * @param dataHoraInicio Data e hora de início.
     */
    public ItemAgendavel(String nome, LocalDateTime dataHoraInicio) {
        this.nome = nome;
        this.dataHoraInicio = dataHoraInicio;
    }

    public ItemAgendavel() {
        
    }

    /**
     * Retorna o nome do item agendável.
     *
     * @return nome do item.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Define o nome do item agendável.
     *
     * @param nome Novo nome a ser atribuído.
     * @throws IllegalArgumentException se o nome for nulo ou vazio.
     */
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do evento não pode ser nulo ou vazio.");
        }
        this.nome = nome;
    }

    /**
     * Retorna a data e hora de início do item.
     *
     * @return data e hora de início como {@code LocalDateTime}.
     */
    public LocalDateTime getDataHoraInicio() {
        return this.dataHoraInicio;
    }

    /**
     * Retorna a data e hora de início formatadas como texto.
     *
     * @return data formatada no estilo "dd/MM/yyyy às HH:mm".
     */
    public String getDataHoraFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
        return this.dataHoraInicio.format(formatter);
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

        String dataHoraInicio = data.trim() + " " + horaInicio.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try {
            LocalDateTime ldt = LocalDateTime.parse(dataHoraInicio, formatter);
            this.dataHoraInicio = ldt;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Formato de data ou hora inválido. Esperado: 'dd/MM/yyyy HH:mm'. Valor recebido: '" +
                            dataHoraInicio + "'",
                    e);
        }
    }

    /**
     * Retorna uma descrição detalhada do item agendável.
     * Deve ser implementado pelas subclasses.
     *
     * @return descrição completa do item.
     */
    public abstract String getDetalhes();
}
