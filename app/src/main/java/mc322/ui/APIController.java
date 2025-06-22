/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322.ui;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mc322.materia.GerenciadorDeMaterias;
import mc322.materia.Materia;

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
     * 
     * @param nomeMateria código/nome da matéria a ser buscada.
     * @param model       objeto Model para manipular atributos da resposta.
     * @return objeto Materia correspondente ao código informado, ou null se não
     *         existir.
     */
    @GetMapping("/api/buscarMateria")
    public Materia buscarMateria(@RequestParam String nomeMateria, Model model) {
        Materia newMateria = GerenciadorDeMaterias.getInstance().buscarMateriaPorCodigo(nomeMateria);
        return newMateria;
    }
}