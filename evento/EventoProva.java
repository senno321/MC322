package evento;
import materia.Materia;

public class EventoProva extends CaracteristicaEvento {
    private Materia materia;
    private String tipo; // p1, p2, substitutiva, exame
    private double peso;
    private String conteudo;

    public EventoProva(Materia materia, String tipo, double peso, String conteudo) {
        this.materia = materia;
        this.tipo = tipo;
        this.peso = peso;
        this.conteudo = conteudo;
    }

    @Override
    public String descricao() {
        return "Prova (" + tipo.toUpperCase() + ") de " + materia.getNome() +
                " com peso " + peso +
                ". Conteúdo: " + conteudo + ".";
    }
}