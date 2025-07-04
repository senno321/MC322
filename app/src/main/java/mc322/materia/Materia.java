/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322.materia;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

/**
 * Representa uma matéria acadêmica com nome, professor responsável, quantidade
 * de faltas,
 * lista de atividades e créditos.
 */
@Entity
public class Materia {
    @Id
    private String codigo;
    
    private String nome;
    private String professor;

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Atividade> listaAtividades;

    private final int creditos;
    private final int limiteFaltas;
    

    /**
     * Construtor que inicializa uma matéria com nome, professor e créditos.
     * A lista de atividades é inicializada vazia e as faltas começam em zero.
     * 
     * @param code      o código da matéria
     * @param nome      nome da matéria.
     * @param professor nome do professor responsável.
     * @param creditos  quantidade de créditos da matéria.
     */
    public Materia(String codigo, String nome, String professor, int creditos) {
        this.codigo = codigo;
        this.nome = nome;
        this.professor = professor;
        this.creditos = creditos;
        listaAtividades = new ArrayList<>();
        this.limiteFaltas = this.getLimiteFaltas();
    }

    public Materia() {
        this.creditos = 0;
        this.limiteFaltas = 0;
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
     * Retorna o código da matéria.
     * 
     * @return código da matéria.
     */
    public String getCodigo() {
        return this.codigo;
    }

    /**
     * Define o nome da matéria.
     * 
     * @param nome novo nome da matéria.
     */
    public void setNome(String nome) {
        this.nome = nome;
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
    @JsonProperty("limiteFaltas")
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
        return "Materia [Nome: " + nome + ", Professor: " + professor 
                + this.getLimiteFaltas() + "]";
    }

    /**
     * Compara duas matérias para verificar se são iguais, baseando-se no código.
     * 
     * @param o objeto a ser comparado.
     * @return true se os códigos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Materia materia = (Materia) o;
        return Objects.equals(getCodigo(), materia.getCodigo());
    }

    /**
     * Gera um código hash para a matéria com base no seu código.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getCodigo());
    }
}