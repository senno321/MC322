import java.time.LocalDateTime;

public class Evento {
    private String nome;
    private String descricao;
    private String local;
    private LocalDateTime dataHoraInicio;
    private int duracao;

    public Evento(String nome, String descricao, String local, LocalDateTime dataHoraInicio, int duracao) {
        this.nome = nome;
        this.descricao = descricao;
        this.local = local; 
        this.dataHoraInicio = dataHoraInicio;
        this.duracao = duracao;
    }

    public String getNome() {
        return this.nome;
    }

    public LocalDateTime getDataHoraInicio() {
        return this.dataHoraInicio;
    }

    public void visualizar() {

    }

    public void editar() {

    }

    public void excluir() {

    }

}
