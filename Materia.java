public class Materia {
    private String nome;
    private String professor;
    private int faltas;
    private final int limiteFaltas;

    public Materia(String nome, String professor, int limiteFaltas) {
        this.nome = nome;
        this.professor = professor;
        this.faltas = 0;
        this.limiteFaltas = limiteFaltas;
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

    public int getLimiteFaltas() {
        return this.limiteFaltas;
    }
}
