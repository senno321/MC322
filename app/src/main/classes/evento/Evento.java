/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package evento;

import java.time.LocalDateTime;

import agendavel.ItemAgendavel;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;

/**
 * Representa um evento genérico com informações básicas como nome, local,
 * data, duração e uma {@link CaracteristicaEvento} que define seu tipo
 * específico.
 * 
 * <p>
 * Essa classe é reutilizável para diferentes contextos (prova, reunião, etc.),
 * dependendo da {@code CaracteristicaEvento} fornecida.
 */
@Entity
@DiscriminatorValue("EVENTO")
public class Evento extends ItemAgendavel {
    private String local;
    private int duracao; // em minutos

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "caracteristica_id", referencedColumnName = "id")
    private CaracteristicaEvento caracteristica;

    /**
     * Construtor para inicializar um evento com todas as informações relevantes.
     *
     * @param nome           o nome do evento.
     * @param local          o local onde o evento ocorrerá.
     * @param dataHoraInicio o instante de início do evento.
     * @param duracao        a duração do evento em minutos.
     * @param caracteristica a característica específica do evento (ex: prova,
     *                       reunião).
     */
    public Evento(String nome, String local, LocalDateTime dataHoraInicio, int duracao,
            CaracteristicaEvento caracteristica) {
        super(nome, dataHoraInicio);
        this.local = local;
        this.duracao = duracao;
        this.caracteristica = caracteristica;
    }

    public Evento() {
        
    }

    /**
     * Retorna o local do evento.
     *
     * @return o local do evento.
     */
    public String getLocal() {
        return this.local;
    }

    /**
     * Define o local do evento.
     *
     * @param local o novo local do evento.
     * @throws IllegalArgumentException se o local for nulo ou uma string vazia.
     */
    public void setLocal(String local) {
        if (local == null || local.trim().isEmpty()) {
            throw new IllegalArgumentException("O local do evento não pode ser nulo ou vazio.");
        }
        this.local = local;
    }

    /**
     * Retorna a duração do evento em minutos.
     *
     * @return a duração do evento.
     */
    public int getDuracao() {
        return this.duracao;
    }

    /**
     * Define a duração do evento.
     *
     * @param duracao nova duração do evento em minutos.
     * @throws IllegalArgumentException se a duração for um número negativo.
     */
    public void setDuracao(int duracao) {
        // Valida se a duração é um valor positivo ou zero.
        if (duracao < 0) {
            throw new IllegalArgumentException("A duração do evento não pode ser um valor negativo.");
        }
        this.duracao = duracao;
    }

    // Implemente o método abstrato getDetalhes()
    @Override
    public String getDetalhes() {
        String detalhe = "Local: " + this.local;
        if (this.duracao > 0) {
            detalhe += " (Duração: " + this.duracao + " min)";
        }
        detalhe += " | " + this.caracteristica.descricao();
        return detalhe;
    }

    /**
     * Permite editar os campos do evento de forma opcional.
     * Campos nulos ou inválidos são ignorados.
     * 
     * @param nome       novo nome (ou {@code null} para manter o atual).
     * @param local      novo local (ou {@code null} para manter o atual).
     * @param data       nova data no formato "dd/MM/yyyy" (ou {@code null}).
     * @param horaInicio nova hora no formato "HH:mm" (ou {@code null}).
     * @param duracao    nova duração em minutos (apenas se for maior que 0).
     */
    public void editar(String nome, String local, String data, String horaInicio, int duracao) {
        if (nome != null)
            this.setNome(nome);
        if (local != null)
            this.setLocal(local);
        if (data != null && horaInicio != null)
            this.setDataHoraInicio(data, horaInicio);
        if (duracao >= 0)
            this.setDuracao(duracao);
    }
}