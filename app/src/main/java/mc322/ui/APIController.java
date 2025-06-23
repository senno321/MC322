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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mc322.exceptions.OperationInvalidException;
import mc322.agendavel.ItemAgendavel;
import mc322.db.ItemAgendavelRepository;
import mc322.db.MateriaRepository;
import mc322.db.UsuarioRepository;
import mc322.evento.Evento;
import mc322.evento.EventoFactory;
import mc322.materia.GerenciadorDeMaterias;
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
    public ResponseEntity<List<Materia>> getUserSubjects(Model model) {
        System.out.println("Buscando matérias do usuário atual");

        try {
            Usuario usuarioSingleton = Usuario.getInstance();

            Usuario usuarioAtual = usuarioRepository.findById(usuarioSingleton.getId()).orElse(null);

            if (usuarioAtual == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            List<Materia> materias = new ArrayList<>(usuarioAtual.getMaterias());
            System.out.println("Matérias encontradas: " + materias.size());
            return ResponseEntity.ok(materias);

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/api/atualizarFaltas")
    public ResponseEntity<Materia> atualizarFaltas(@RequestParam String codigo, @RequestParam String action) {
        Usuario usuario = Usuario.getInstance();
        boolean adicionar = "add".equals(action);

        for (Materia materia : usuario.getMaterias()) {
            if (materia.getCodigo().equals(codigo)) {
                int faltasAtuais = materia.getFaltas();
                
                if (adicionar) {
                    materia.setFaltas(faltasAtuais + 1);
                } else {
                    if (faltasAtuais > 0) {
                        materia.setFaltas(faltasAtuais - 1);
                    }
                }
                System.out.println("Faltas para " + codigo + " atualizadas para: " + materia.getFaltas());
                return ResponseEntity.ok(materia); 
            }
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
    public ResponseEntity<Materia> adicionarMateriaUsuario(@RequestParam String codigo) {
        Optional<Materia> materiaOptional = materiaRepository.findById(codigo);

        if (materiaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Materia materiaParaAdicionar = materiaOptional.get();

        Usuario usuarioSingleton = Usuario.getInstance();

        Usuario usuarioAtual = usuarioRepository.findById(usuarioSingleton.getId()).orElse(null);
        if (usuarioAtual == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (usuarioAtual.getMaterias().contains(materiaParaAdicionar)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(materiaParaAdicionar);
        }

        usuarioAtual.adicionarMateria(materiaParaAdicionar);

        Usuario usuarioSalvo = usuarioRepository.save(usuarioAtual);

        Usuario.setUsuarioAtual(usuarioSalvo);

        System.out.println("Matéria " + codigo + " adicionada ao usuário " + usuarioAtual.getEmail());
        
        return ResponseEntity.ok(materiaParaAdicionar);
    }

    /**
     * Endpoint POST para remover uma matéria do usuário atual.
     * 
     * @param codigo código da matéria a ser removida.
     * @return ResponseEntity com mensagem de sucesso ou erro apropriado.
     */
    @PostMapping("/api/removerMateriaUsuario")
    public ResponseEntity<String> removerMateriaUsuario(@RequestParam String codigo) {
        try {
            Usuario usuarioSingleton = Usuario.getInstance();
            
            Usuario usuarioAtual = usuarioRepository.findById(usuarioSingleton.getId()).orElse(null);

            if (usuarioAtual == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
            }

            boolean foiRemovida = usuarioAtual.getMaterias().removeIf(materia -> materia.getCodigo().equals(codigo));

            if (foiRemovida) {
                Usuario usuarioSalvo = usuarioRepository.save(usuarioAtual);
                
                Usuario.setUsuarioAtual(usuarioSalvo);
                
                System.out.println("Matéria " + codigo + " removida do usuário " + usuarioAtual.getEmail());
                return ResponseEntity.ok("Matéria removida com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Matéria não encontrada na lista do usuário.");
            }

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sessão de usuário inválida.");
        }
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
