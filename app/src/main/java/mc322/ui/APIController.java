/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mc322.agendavel.ItemAgendavel;
import mc322.db.ItemAgendavelRepository;
import mc322.db.MateriaRepository;
import mc322.db.UsuarioRepository;
import mc322.evento.Evento;
import mc322.evento.EventoFactory;
import mc322.inscricao.Inscricao;
import mc322.materia.Materia;
import mc322.usuario.Usuario;

/**
 * Controlador REST responsável por endpoints da API para operações básicas,
 * como adicionar eventos e buscar matérias.
 */
@RestController
public class APIController {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired // ADICIONE ESTA INJEÇÃO DE DEPENDÊNCIA
    private UsuarioRepository usuarioRepository;


    @Autowired
    private ItemAgendavelRepository itemAgendavelRepository;
    /**
     * Endpoint POST para adicionar um evento (simulação).
     * 
     * @param model objeto Model para adicionar atributos à resposta.
     * @return nome da view (página) a ser exibida.
     */
    @PostMapping("/api/addEvent")
    public String home(Model model) {
        model.addAttribute("mensagem", "Bem-vindo ao meu site com Spring Boot!");
        return "index";
    }


    /**
     * Endpoint GET para buscar uma matéria pelo código.
     * @param codigo código/nome da matéria a ser buscada.
     * @param model       objeto Model para manipular atributos da resposta.
     * @return objeto Materia correspondente ao código informado, ou null se não
     * existir.
     */
    @GetMapping("/api/buscarMateria")
    public ResponseEntity<Materia> buscarMateria(@RequestParam String codigo, Model model) {
        System.out.println("Buscando matéria com código: " + codigo);

        Optional<Materia> MateriaOptional = materiaRepository.findById(codigo);

        if(MateriaOptional.isPresent()){
            Materia newMateria = MateriaOptional.get();
            System.out.println("Matéria encontrada: " + newMateria.toString());
            return ResponseEntity.ok(newMateria);
        }
        else{
            System.out.println("Matéria com código " + codigo + " não encontrada.");
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Usado para depuração, imprime uma mensagem no console.
     * @param model
     */
    @GetMapping("/api/debug")
    public void debug(Model model) {
        System.out.println("PARADA");
    }

    /**
     * Endpoint GET para buscar todas as matérias do usuário atual.
     * 
     * @param model objeto Model para manipular atributos da resposta.
     * @return lista de matérias do usuário atual ou erro apropriado.
     */
    @GetMapping("api/getUserSubjects")
    public ResponseEntity<List<Map<String, Object>>> getUserSubjects(Model model) {
        System.out.println("Buscando matérias do usuário atual");
        Usuario usuarioAtual = usuarioRepository.findById(Usuario.getInstance().getId()).orElse(null);

        if (usuarioAtual == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Transforma a lista de Inscrições em uma lista de Mapas (DTOs) para o frontend
        List<Map<String, Object>> materiasDTO = usuarioAtual.getInscricoes().stream().map(inscricao -> {
            Materia m = inscricao.getMateria();
            Map<String, Object> dto = new java.util.HashMap<>();
            dto.put("codigo", m.getCodigo());
            dto.put("nome", m.getNome());
            dto.put("professor", m.getProfessor());
            dto.put("creditos", m.getCreditos());
            dto.put("limiteFaltas", m.getLimiteFaltas());
            dto.put("faltas", inscricao.getFaltas()); // A falta vem da Inscrição!
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(materiasDTO);
    }

    @PostMapping("/api/atualizarFaltas")
    public ResponseEntity<?> atualizarFaltas(@RequestParam String codigo, @RequestParam String action) {
        Usuario usuarioAtual = usuarioRepository.findById(Usuario.getInstance().getId()).orElse(null);
        if (usuarioAtual == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Encontra a inscrição específica para esta matéria e usuário
        Optional<Inscricao> inscricaoOpt = usuarioAtual.getInscricoes().stream()
                .filter(i -> i.getMateria().getCodigo().equals(codigo))
                .findFirst();

        if (inscricaoOpt.isPresent()) {
            Inscricao inscricao = inscricaoOpt.get();
            int faltasAtuais = inscricao.getFaltas();
            if ("add".equals(action)) {
                inscricao.setFaltas(faltasAtuais + 1);
            } else {
                if (faltasAtuais > 0) {
                    inscricao.setFaltas(faltasAtuais - 1);
                }
            }
            usuarioRepository.save(usuarioAtual); // Salva o usuário, que cascata para a inscrição
            System.out.println("Faltas para " + codigo + " atualizadas para: " + inscricao.getFaltas());
            return ResponseEntity.ok(Map.of("faltas", inscricao.getFaltas())); // Retorna só a info necessária
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Endpoint POST para adicionar uma matéria ao usuário atual.
     * 
     * @param codigo código da matéria a ser adicionada.
     * @return ResponseEntity com a matéria adicionada ou erro apropriado.
     */
    @PostMapping("/api/adicionarMateriaUsuario")
    public ResponseEntity<?> adicionarMateriaUsuario(@RequestParam String codigo) {
        Usuario usuarioAtual = usuarioRepository.findById(Usuario.getInstance().getId()).orElse(null);
        if (usuarioAtual == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Verifica se o usuário já está inscrito na matéria
        boolean jaInscrito = usuarioAtual.getInscricoes().stream()
                .anyMatch(i -> i.getMateria().getCodigo().equals(codigo));
        if (jaInscrito) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já inscrito nesta matéria.");
        }
        
        Optional<Materia> materiaOpt = materiaRepository.findById(codigo);
        if (materiaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Cria a nova inscrição e a associa ao usuário e à matéria
        Inscricao novaInscricao = new Inscricao(usuarioAtual, materiaOpt.get());
        usuarioAtual.getInscricoes().add(novaInscricao);
        
        usuarioRepository.save(usuarioAtual);

        System.out.println("Matéria " + codigo + " adicionada ao usuário " + usuarioAtual.getEmail());
        return ResponseEntity.ok(materiaOpt.get()); // Retorna a matéria para o frontend
    }

    /**
     * Endpoint POST para remover uma matéria do usuário atual.
     * 
     * @param codigo código da matéria a ser removida.
     * @return ResponseEntity com mensagem de sucesso ou erro apropriado.
     */
    @PostMapping("/api/removerMateriaUsuario")
    public ResponseEntity<String> removerMateriaUsuario(@RequestParam String codigo) {
        Usuario usuarioAtual = usuarioRepository.findById(Usuario.getInstance().getId()).orElse(null);
        if (usuarioAtual == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Remove a inscrição da lista do usuário
        boolean foiRemovida = usuarioAtual.getInscricoes()
                .removeIf(inscricao -> inscricao.getMateria().getCodigo().equals(codigo));
        
        if (foiRemovida) {
            usuarioRepository.save(usuarioAtual);
            return ResponseEntity.ok("Matéria removida com sucesso.");
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Matéria não encontrada na lista do usuário.");
    }
    /**
     * Retorna a lista de todos os itens agendáveis (eventos) para o usuário logado.
     */
    @GetMapping("/api/events")
    public ResponseEntity<List<ItemAgendavel>> getEvents() {
        try {
            Usuario usuarioAtual = usuarioRepository.findById(Usuario.getInstance().getId()).orElse(null);
            if (usuarioAtual == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            return ResponseEntity.ok(usuarioAtual.getItensAgendados());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Cria um novo evento ou atualiza um existente.
     */
    @PostMapping("/api/events/save")
    public ResponseEntity<ItemAgendavel> saveEvent(@RequestBody Map<String, String> payload) {
        try {
            Usuario usuarioAtual = usuarioRepository.findById(Usuario.getInstance().getId()).orElse(null);
            if (usuarioAtual == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String eventId = payload.get("id");
            String nome = payload.get("nome");
            String local = payload.get("local");
            String data = payload.get("data"); 
            String horaInicio = payload.get("horaInicio"); 
            int duracao = Integer.parseInt(payload.getOrDefault("duracao", "0"));

            String dataFormatada = "";
            if (data != null && !data.isEmpty()) {
                dataFormatada = LocalDate.parse(data).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }

            if (eventId != null && !eventId.isEmpty()) {
                Long id = Long.parseLong(eventId);
                Evento evento = (Evento) itemAgendavelRepository.findById(id).orElse(null);

                if (evento == null) {
                    return ResponseEntity.notFound().build();
                }
                
                evento.editar(nome, local, dataFormatada, horaInicio, duracao);
                ItemAgendavel eventoSalvo = itemAgendavelRepository.save(evento);
                return ResponseEntity.ok(eventoSalvo);

            } else { 
                Evento novoEvento = EventoFactory.criarEventoReuniao(
                    nome, local, dataFormatada, horaInicio, duracao, 
                    "Participantes", "Objetivo", false);
                
                usuarioAtual.adicionarItemAgendado(novoEvento);
                usuarioRepository.save(usuarioAtual); 
                return ResponseEntity.status(HttpStatus.CREATED).body(novoEvento);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
