package mc322.evento;

public class EventoExtensao extends CaracteristicaEvento {
    private String descricaoAtividade;
    private String publicoAlvo;
    private boolean valeHoras;
    private int cargaHoraria;

    public EventoExtensao(String descricaoAtividade, String publicoAlvo,
                          boolean valeHoras, int cargaHoraria) {
        this.descricaoAtividade = descricaoAtividade;
        this.publicoAlvo = publicoAlvo;
        this.valeHoras = valeHoras;
        this.cargaHoraria = cargaHoraria;
    }

    @Override
    public String descricao() {
        String base = "Extensão: " + descricaoAtividade +
                      " (Público: " + publicoAlvo + ")";
        if (valeHoras) {
            base += " [Vale " + cargaHoraria + "h de extensão]";
        }
        return base;
    }
}