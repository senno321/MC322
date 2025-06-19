package app.src.main.java.mc322.evento;
import java.time.LocalDateTime;

public class Evento {
    private String nome;
    private String local;
    private LocalDateTime dataHoraInicio;
    private int duracao;
    private CaracteristicaEvento caracteristica;

    public Evento(String nome, String local, LocalDateTime dataHoraInicio, int duracao,
            CaracteristicaEvento caracteristica) {
        this.nome = nome;
        this.local = local;
        this.dataHoraInicio = dataHoraInicio;
        this.duracao = duracao;
        this.caracteristica = caracteristica;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return this.local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public LocalDateTime getDataHoraInicio() {
        return this.dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public int getDuracao() {
        return this.duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getCaracteristicaEvento() {
        return this.caracteristica.descricao();
    }

    public void editar(String nome, String local, LocalDateTime dataHoraInicio, int duracao) {
        if (nome != null)
            this.nome = nome;
        if (local != null)
            this.local = local;
        if (dataHoraInicio != null)
            this.dataHoraInicio = dataHoraInicio;
        if (duracao > 0) 
            this.duracao = duracao;
    }
}
