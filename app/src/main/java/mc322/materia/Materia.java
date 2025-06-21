package mc322.materia;

import java.util.List;
import java.util.ArrayList;

public class Materia {
    private String nome;
    private String professor;
    private int faltas;
    private List<Atividade> listaAtividades;
    private final int creditos;

    public Materia(String nome, String professor, int creditos) {
        this.nome = nome;
        this.professor = professor;
        this.faltas = 0;
        this.creditos = creditos;
        listaAtividades = new ArrayList<>();
    }

    public String getNome() {
        return this.nome;
    }

    public String getProfessor() {
        return this.professor;
    }

    public int getFaltas() {
        return this.faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public int getCreditos() {
	    return this.creditos;
    }

    public int getLimiteFaltas() {
        return 2 * this.creditos - 1;
    }

    public List<Atividade> getListaAtividades() {
        return listaAtividades;
    }

    public void adicionaAtividade(Atividade atividade) {
        listaAtividades.add(atividade);
    }

    // caso precise do da matéria em texto
    @Override
    public String toString() {
        return "Materia [Nome: " + nome + ", Professor: " + professor + ", Faltas: " + faltas + "/" + this.getLimiteFaltas() + "]";
    }
}
