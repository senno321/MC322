/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322.usuario;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import mc322.evento.Evento;
import mc322.evento.GerenciadorDeEventos;
import mc322.materia.Atividade;
import mc322.materia.Materia;

/**
 * Representa um usuário do sistema com nome, email, lista de matérias, eventos
 * e gerenciamento seguro de senha através de hash.
 */
public class Usuario {
    private String nome;
    private String email;
    private List<Materia> materias = new ArrayList<>();
    private List<Evento> eventos = new ArrayList<>();
    private String hashSenha;

    /**
     * Construtor que inicializa usuário com nome, email e senha.
     * A senha é armazenada de forma segura, como hash.
     *
     * @param nome  Nome do usuário
     * @param email Email do usuário
     * @param senha Senha em texto plano (será convertida em hash)
     */
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.hashSenha = gerarHash(senha);
    }

    /**
     * Obtém o nome do usuário.
     *
     * @return nome do usuário
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Define o nome do usuário.
     *
     * @param nome novo nome do usuário
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o email do usuário.
     *
     * @return email do usuário
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Define o email do usuário.
     *
     * @param email novo email do usuário
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna a lista de matérias associadas ao usuário.
     *
     * @return lista de matérias
     */
    public List<Materia> getMaterias() {
        return this.materias;
    }

    /**
     * Retorna a lista de eventos do usuário.
     *
     * @return lista de eventos
     */
    public List<Evento> getEventos() {
        return this.eventos;
    }

    /**
     * Altera a senha do usuário após verificar se a senha antiga está correta.
     *
     * @param senhaAntiga senha atual para verificação
     * @param senhaNova  nova senha que será definida
     */
    public void setSenha(String senhaAntiga, String senhaNova) {
        if (verificarSenha(senhaAntiga))
            this.hashSenha = gerarHash(senhaNova);
        else
            System.out.println("Senha errada.");
    }

    /**
     * Verifica se a senha fornecida corresponde à senha armazenada (hash).
     *
     * @param tentativa senha em texto plano a ser verificada
     * @return true se a senha estiver correta, false caso contrário
     */
    public boolean verificarSenha(String tentativa) {
        return gerarHash(tentativa).equals(this.hashSenha);
    }

    /**
     * Gera hash SHA-256 codificada em Base64 para a senha informada.
     *
     * @param senha senha em texto plano
     * @return hash da senha codificado em Base64
     */
    private String gerarHash(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(senha.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash da senha", e);
        }
    }

    /**
     * Adiciona um evento à lista de eventos do usuário.
     *
     * @param evento evento a ser adicionado
     */
    public void adicionarEvento(Evento evento) {
        this.eventos.add(evento);
    }

    /**
     * Remove um evento da lista de eventos do usuário.
     *
     * @param evento evento a ser removido
     */
    public void removerEvento(Evento evento) {
        this.eventos.remove(evento);
    }

    /**
     * Adiciona uma matéria à lista de matérias do usuário.
     *
     * @param materia matéria a ser adicionada
     */
    public void adicionarMateria(Materia materia) {
        this.materias.add(materia);
    }

    /**
     * Remove uma matéria da lista de matérias do usuário.
     *
     * @param materia matéria a ser removida
     */
    public void removerMateria(Materia materia) {
        this.materias.remove(materia);
    }

    /**
     * Cria uma atividade em uma matéria do usuário, adiciona essa atividade na matéria
     * e cria um evento correspondente usando o GerenciadorDeEventos.
     *
     * @param nomeMateria   nome da matéria onde será criada a atividade
     * @param nomeAtividade nome da atividade
     * @param peso          peso da atividade
     * @param conteudo      conteúdo cobrado na atividade
     * @param data          data da atividade no formato "dd/MM/yyyy"
     * @param horaInicio    hora da atividade no formato "HH:mm"
     */
    public void criarAtividade(String nomeMateria, String nomeAtividade, double peso, String conteudo, String data,
            String horaInicio) {
        for (Materia m : this.materias) {
            if (m.getNome().equalsIgnoreCase(nomeMateria)) {
                String dataHoraInicio = data + " " + horaInicio;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime ldt = LocalDateTime.parse(dataHoraInicio, formatter);
                Atividade atividade = new Atividade(nomeAtividade, peso, conteudo, ldt);
                m.adicionaAtividade(atividade);
                GerenciadorDeEventos gerenciador = new GerenciadorDeEventos();
                gerenciador.criarEvento(this, atividade, m, peso, conteudo);
            }
        }
    }

    /**
     * Exibe no console os eventos que ocorrem entre as datas fornecidas.
     *
     * @param dataHoraInicio data/hora inicial do intervalo (inclusive)
     * @param dataHoraFim    data/hora final do intervalo (inclusive)
     * @throws IllegalArgumentException se as datas forem nulas ou a data inicial for depois da final
     */
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

    /**
     * Retorna uma string representando o usuário com nome e email.
     *
     * @return string representando o usuário
     */
    @Override
    public String toString() {
        return "Usuario{nome='" + nome + "', email='" + email + "'}";
    }
}
