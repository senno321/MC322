/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322.materia;

import java.util.List;
import java.util.ArrayList;

/**
 * Representa uma matéria acadêmica com nome, professor responsável, quantidade de faltas,
 * lista de atividades e créditos.
 */
public class Materia {
    private String nome;
    private String professor;
    private int faltas;
    private List<Atividade> listaAtividades;
    private final int creditos;
    private int limiteFaltas;

    /**
     * Construtor que inicializa uma matéria com nome, professor e créditos.
     * A lista de atividades é inicializada vazia e as faltas começam em zero.
     * 
     * @param nome      nome da matéria.
     * @param professor nome do professor responsável.
     * @param creditos  quantidade de créditos da matéria.
     */
    public Materia(String nome, String professor, int creditos) {
        this.nome = nome;
        this.professor = professor;
        this.faltas = 0;
        this.creditos = creditos;
        listaAtividades = new ArrayList<>();
        this.limiteFaltas = this.getLimiteFaltas();
    }

    /**
     * Retorna o nome da matéria.
     * 
     * @return nome da matéria.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Retorna o nome do professor responsável pela matéria.
     * 
     * @return nome do professor.
     */
    public String getProfessor() {
        return this.professor;
    }

    /**
     * Retorna a quantidade atual de faltas do aluno na matéria.
     * 
     * @return número de faltas.
     */
    public int getFaltas() {
        return this.faltas;
    }

    /**
     * Define a quantidade de faltas do aluno na matéria.
     * 
     * @param faltas nova quantidade de faltas.
     */
    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    /**
     * Retorna a quantidade de créditos da matéria.
     * 
     * @return número de créditos.
     */
    public int getCreditos() {
        return this.creditos;
    }

    /**
     * Calcula e retorna o limite máximo de faltas permitidas para a matéria,
     * definido como 2 vezes o número de créditos menos 1.
     * 
     * @return limite máximo de faltas.
     */
    public int getLimiteFaltas() {
        return 2 * this.creditos - 1;
    }

    /**
     * Retorna a lista de atividades associadas à matéria.
     * 
     * @return lista de atividades.
     */
    public List<Atividade> getListaAtividades() {
        return listaAtividades;
    }

    /**
     * Adiciona uma atividade à lista de atividades da matéria.
     * 
     * @param atividade atividade a ser adicionada.
     */
    public void adicionaAtividade(Atividade atividade) {
        listaAtividades.add(atividade);
    }

    /**
     * Retorna uma representação textual da matéria, incluindo nome, professor
     * e situação atual de faltas com relação ao limite permitido.
     * 
     * @return string descritiva da matéria.
     */
    @Override
    public String toString() {
        return "Materia [Nome: " + nome + ", Professor: " + professor + ", Faltas: " + faltas + "/" + this.getLimiteFaltas() + "]";
    }
}
