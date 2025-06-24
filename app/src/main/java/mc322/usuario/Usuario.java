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

import com.fasterxml.jackson.annotation.JsonManagedReference;

import mc322.agendavel.ItemAgendavel;
import mc322.evento.EventoFactory;
import mc322.exceptions.OperationInvalidException;
import mc322.inscricao.Inscricao;
import mc322.materia.Atividade;
import mc322.materia.Materia;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

/**
 * Representa um usuário do sistema com dados pessoais, matérias,
 * eventos agendados e gerenciamento seguro de senha.
 */
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    static Usuario usuarioAtual;

    @Column(unique = true, nullable = false)
    private String email;

    private String nome;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id") 
    private List<ItemAgendavel> itensAgendados = new ArrayList<>();
    private String hashSenha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference // Ajuda a gerenciar a serialização
    private List<Inscricao> inscricoes = new ArrayList<>();

    /**
     * Constrói um usuário com nome, email e senha (armazenada como hash).
     *
     * @param nome  nome do usuário
     * @param email email do usuário
     * @param senha senha em texto plano, será convertida para hash
     */

    public static Usuario getInstance(){
        if (usuarioAtual != null) {
            return usuarioAtual;
        } else {
            throw new IllegalStateException("Usuário não está definido.");
        }
    }

    public static void setUsuarioAtual(Usuario usuario) {
        usuarioAtual = usuario;
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.hashSenha = gerarHash(senha);
    }

    public Usuario() {
        
    }

    /** @return o nome do usuário */
    public String getNome() {
        return this.nome;
    }

    public Long getId() {
    return this.id;
}

    /** Define o nome do usuário */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** @return o email do usuário */
    public String getEmail() {
        return this.email;
    }

    /** Define o email do usuário */
    public void setEmail(String email) {
        this.email = email;
    }

    public List<Inscricao> getInscricoes() {
        return this.inscricoes;
    }


    /** @return a lista de itens agendados pelo usuário */
    public List<ItemAgendavel> getItensAgendados() {
        return this.itensAgendados;
    }

    /**
     * Altera a senha se a senha antiga for correta.
     *
     * @param senhaAntiga senha atual para validação
     * @param senhaNova   nova senha a ser armazenada
     */
    public void setSenha(String senhaAntiga, String senhaNova) {
        if (verificarSenha(senhaAntiga))
            this.hashSenha = gerarHash(senhaNova);
        else
            System.out.println("Senha errada.");
    }

    /**
     * Verifica se a senha fornecida confere com a senha armazenada.
     *
     * @param tentativa senha em texto plano
     * @return true se a senha estiver correta, false caso contrário
     */
    public boolean verificarSenha(String tentativa) {
        return gerarHash(tentativa).equals(this.hashSenha);
    }

    /**
     * Gera hash SHA-256 codificada em Base64 para a senha informada.
     *
     * @param senha senha em texto plano
     * @return hash codificado em Base64
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
     * Adiciona um item agendável (evento, lembrete, etc.) ao usuário,
     * evitando duplicatas.
     *
     * @param item item a adicionar
     * @throws OperationInvalidException se o item já estiver agendado
     */
    public void adicionarItemAgendado(ItemAgendavel item) {
        if (this.itensAgendados.contains(item)) {
            throw new OperationInvalidException("Item já está agendado.");
        }
        this.itensAgendados.add(item);
    }

    /**
     * Remove um item agendável do usuário.
     *
     * @param item item a remover
     * @throws OperationInvalidException se o item não estiver na lista
     */
    public void removerItemAgendado(ItemAgendavel item) {
        if (!this.itensAgendados.remove(item)) {
            throw new OperationInvalidException("Item não encontrado para remoção.");
        }
    }

    /**
     * Cria uma atividade em uma matéria do usuário e adiciona o evento
     * correspondente.
     *
     * @param nomeMateria   nome da matéria
     * @param nomeAtividade nome da atividade
     * @param peso          peso da atividade
     * @param conteudo      conteúdo cobrado
     * @param data          data no formato "dd/MM/yyyy"
     * @param horaInicio    hora no formato "HH:mm"
     */
    public void criarAtividade(String nomeMateria, String nomeAtividade, double peso, String conteudo, String data,
            String horaInicio) {
        for (Materia m : this.getMaterias()) {
            if (m.getNome().equalsIgnoreCase(nomeMateria)) {
                String dataHoraInicio = data + " " + horaInicio;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime ldt = LocalDateTime.parse(dataHoraInicio, formatter);
                Atividade atividade = new Atividade(nomeAtividade, peso, conteudo, ldt);
                m.adicionaAtividade(atividade);
                this.itensAgendados.add(EventoFactory.criarEventoDeAtividadeExistente(atividade, m));
            }
        }
    }

    /**
     * Exibe eventos agendados entre as datas informadas.
     *
     * @param dataHoraInicio data/hora inicial (inclusive)
     * @param dataHoraFim    data/hora final (inclusive)
     * @throws IllegalArgumentException se datas forem nulas ou mal ordenadas
     */
    public void visualizarCalendario(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {

        if (dataHoraInicio == null || dataHoraFim == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas.");
        }

        if (dataHoraInicio.isAfter(dataHoraFim)) {
            throw new IllegalArgumentException("Data de início não pode ser depois da data de fim.");
        }

        for (ItemAgendavel e : itensAgendados) {
            LocalDateTime itemAgendadoInicio = e.getDataHoraInicio();

            if ((!itemAgendadoInicio.isBefore(dataHoraInicio)) && (!itemAgendadoInicio.isAfter(dataHoraFim))) {
                System.out.println("Item: " + e.getNome());
                System.out.println("Data: " + e.getDataHoraFormatada());
                System.out.println("Detalhes: " + e.getDetalhes());
            }
        }
    }

    public List<Materia> getMaterias() {
        return inscricoes.stream()
                .map(Inscricao::getMateria)
                .toList();
    }

    /** @return representação textual do usuário */
    @Override
    public String toString() {
        return "Usuario{nome='" + nome + "', email='" + email + "'}";
    }
}