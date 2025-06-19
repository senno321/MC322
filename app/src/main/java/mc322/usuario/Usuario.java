package app.src.main.java.mc322.usuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import app.src.main.java.mc322.evento.Evento;
import app.src.main.java.mc322.materia.Materia;

public class Usuario {
    private String nome;
    private String email;
    private List<Materia> materias = new ArrayList<>();
    private List<Evento> eventos = new ArrayList<>();
    private String hashSenha;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.hashSenha = gerarHash(senha);
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Materia> getMaterias() {
        return this.materias;
    }

    public List<Evento> getEventos() {
        return this.eventos;
    }

    public void setSenha(String senhaAntiga, String senhaNova) {
        if (verificarSenha(senhaAntiga))
            this.hashSenha = gerarHash(senhaNova);

        else {
            System.out.println("Senha errada.");
        }
    }

    public boolean verificarSenha(String tentativa) {
        return gerarHash(tentativa).equals(this.hashSenha);
    }

    private String gerarHash(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(senha.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash da senha", e);
        }
    }

    public void adicionarEvento(Evento evento) {
        this.eventos.add(evento);
    }

    public void removerEvento(Evento evento) {
        this.eventos.remove(evento);
    }

    public void adicionarMateria(Materia materia) {
        this.materias.add(materia);
    }

    public void removerMateria(Materia materia) {
        this.materias.remove(materia);
    }

    public void visualizarCalendario(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {

        if (dataHoraInicio == null || dataHoraFim == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas.");
        }

        if (dataHoraInicio.isAfter(dataHoraFim)) {
            throw new IllegalArgumentException("Data de início não pode ser depois da data de fim.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Evento e : eventos) {
            LocalDateTime eventoInicio = e.getDataHoraInicio();

            if ((!eventoInicio.isBefore(dataHoraInicio)) && (!eventoInicio.isAfter(dataHoraFim))) {
                System.out.println(" - " + e.getNome() + " às " + eventoInicio.format(formatter));
            }
        }
    }

    @Override
    public String toString() {
        return "Usuario{nome='" + nome + "', email='" + email + "'}";
    }
}
