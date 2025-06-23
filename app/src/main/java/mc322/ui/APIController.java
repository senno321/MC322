/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322.ui;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mc322.exceptions.OperationInvalidException;
import mc322.materia.GerenciadorDeMaterias;
import mc322.materia.Materia;
import mc322.usuario.Usuario;

/**
 * Controlador REST responsável por endpoints da API para operações básicas,
 * como adicionar eventos e buscar matérias.
 */
@RestController
public class APIController {

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
     * * @param codigo código/nome da matéria a ser buscada.
     * @param model       objeto Model para manipular atributos da resposta.
     * @return objeto Materia correspondente ao código informado, ou null se não
     * existir.
     */
    @GetMapping("/api/buscarMateria")
    public Materia buscarMateria(@RequestParam String codigo, Model model) {
        System.out.println("Buscando matéria com código: " + codigo);
        Materia newMateria = GerenciadorDeMaterias.getInstance().buscarMateriaPorCodigo(codigo);
        
        // Verificação para evitar NullPointerException
        if (newMateria != null) {
            System.out.println("Matéria encontrada: " + newMateria.toString());
        } else {
            System.out.println("Nenhuma matéria encontrada com o código: " + codigo);
        }
        
        return newMateria;
    }

    @GetMapping("/api/debug")
    public void debug(Model model) {
        System.out.println("PARADA");
    }

    @GetMapping("api/getUserSubjects")
    public ArrayList<Materia> getUserSubjects(Model model) {
        System.out.println("Buscando matérias do usuário atual");
        ArrayList<Materia> materias = new ArrayList<>(mc322.usuario.Usuario.getInstance().getMaterias());
        System.out.println("Matérias encontradas: " + materias);
        System.out.println("Total de matérias: " + materias.size());

        for (Materia materia : materias) {
            System.out.println(materia);
        }
        return materias;
    }

    @PostMapping("/api/atualizarFaltas")
    public ResponseEntity<Materia> atualizarFaltas(@RequestParam String codigo, @RequestParam String action) {
        Usuario usuario = Usuario.getInstance();
        boolean adicionar = "add".equals(action);

        for (Materia materia : usuario.getMaterias()) {
            // Usa getCodigo() para garantir consistência
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
                return ResponseEntity.ok(materia); // Retorna a matéria atualizada com status 200 OK
            }
        }

        // Se a matéria não for encontrada, retorna um erro 404 Not Found
        return ResponseEntity.notFound().build();
    }

   @PostMapping("/api/adicionarMateriaUsuario")
    public ResponseEntity<Materia> adicionarMateriaUsuario(@RequestParam String codigo) {
        Materia materiaParaAdicionar = GerenciadorDeMaterias.getInstance().buscarMateriaPorCodigo(codigo);

        if (materiaParaAdicionar == null) {
            return ResponseEntity.notFound().build(); // 404: Matéria não existe no catálogo
        }

        try {
            // Tenta adicionar a matéria ao usuário
            Usuario.getInstance().adicionarMateria(materiaParaAdicionar);
            System.out.println("Matéria " + codigo + " adicionada ao usuário.");
            return ResponseEntity.ok(materiaParaAdicionar); // 200: Sucesso
        } catch (OperationInvalidException e) {
            System.out.println("Erro ao adicionar matéria (provavelmente duplicada): " + e.getMessage());
            return ResponseEntity.status(409).body(materiaParaAdicionar); // 409: Conflito (já existe)
        }
    }


}