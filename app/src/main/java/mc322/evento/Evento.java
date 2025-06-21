/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322.evento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa um evento genérico com informações básicas como nome, local,
 * data, duração e uma {@link CaracteristicaEvento} que define seu tipo específico.
 * 
 * <p>Essa classe é reutilizável para diferentes contextos (prova, reunião, etc.),
 * dependendo da {@code CaracteristicaEvento} fornecida.
 */
public class Evento {
    private String nome;
    private String local;
    private LocalDateTime dataHoraInicio;
    private int duracao; // em minutos
    private CaracteristicaEvento caracteristica;

    /**
     * Construtor para inicializar um evento com todas as informações relevantes.
     *
     * @param nome            o nome do evento.
     * @param local           o local onde o evento ocorrerá.
     * @param dataHoraInicio  o instante de início do evento.
     * @param duracao         a duração do evento em minutos.
     * @param caracteristica  a característica específica do evento (ex: prova, reunião).
     */
    public Evento(String nome, String local, LocalDateTime dataHoraInicio, int duracao,
            CaracteristicaEvento caracteristica) {
        this.nome = nome;
        this.local = local;
        this.dataHoraInicio = dataHoraInicio;
        this.duracao = duracao;
        this.caracteristica = caracteristica;
    }

    /**
     * Retorna o nome do evento.
     *
     * @return o nome do evento.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Define o nome do evento.
     *
     * @param nome o novo nome do evento.
     */
    public void setNome(String nome) {
        this.nome = nome;
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
     */
    public void setLocal(String local) {
        this.local = local;
    }

    /**
     * Retorna a data e hora de início do evento.
     *
     * @return um {@code LocalDateTime} representando o início do evento.
     */
    public LocalDateTime getDataHoraInicio() {
        return this.dataHoraInicio;
    }

    /**
     * Define a data e hora de início do evento a partir de strings separadas.
     * 
     * @param data        uma {@code String} no formato "dd/MM/yyyy".
     * @param horaInicio  uma {@code String} no formato "HH:mm".
     */
    public void setDataHoraInicio(String data, String horaInicio) {
        String dataHoraInicio = data + " " + horaInicio;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(dataHoraInicio, formatter);
        this.dataHoraInicio = ldt;
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
     */
    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    /**
     * Retorna uma descrição da característica específica do evento.
     * 
     * @return uma {@code String} descritiva da {@code CaracteristicaEvento}.
     */
    public String getCaracteristicaEvento() {
        return this.caracteristica.descricao();
    }

    /**
     * Permite editar os campos do evento de forma opcional.
     * Campos nulos ou inválidos são ignorados.
     * 
     * @param nome        novo nome (ou {@code null} para manter o atual).
     * @param local       novo local (ou {@code null} para manter o atual).
     * @param data        nova data no formato "dd/MM/yyyy" (ou {@code null}).
     * @param horaInicio  nova hora no formato "HH:mm" (ou {@code null}).
     * @param duracao     nova duração em minutos (apenas se for maior que 0).
     */
    public void editar(String nome, String local, String data, String horaInicio, int duracao) {
        if (nome != null)
            this.nome = nome;
        if (local != null)
            this.local = local;
        if (data != null && horaInicio != null) {
            String dataHoraInicio = data + " " + horaInicio;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime ldt = LocalDateTime.parse(dataHoraInicio, formatter);
            this.dataHoraInicio = ldt;
        }
        if (duracao > 0)
            this.duracao = duracao;
    }
}