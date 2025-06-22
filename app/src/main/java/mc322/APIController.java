package mc322;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import mc322.materia.GerenciadorDeMaterias;
import mc322.materia.Materia;

@RestController
public class APIController {

    @PostMapping("/api/addEvent")
    public String home(Model model) {
        model.addAttribute("mensagem", "Bem-vindo ao meu site com Spring Boot!");
        return "index";
    }

    @GetMapping("/api/buscarMateria")
    public Materia buscarMateria(@RequestParam String nomeMateria, Model model) {
        Materia newMateria = GerenciadorDeMaterias.getInstance().buscarMateriaPorCodigo(nomeMateria);
        return newMateria;
    }
}