package mc322.materia;

public class Atividade {
    private String nome;
    private boolean completa;

    public Atividade(String nome) {
        this.nome = nome;
        this.completa = false;
    }

    public String getNome() {
	    return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void marcarComoConcluida() {
        this.completa = true;
    }
}
