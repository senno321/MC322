package mc322;

import java.util.ArrayList;

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
    public Materia buscarMateria(@RequestParam String codigo, Model model) {
        System.out.println("Buscando matéria com código: " + codigo);
        Materia newMateria = GerenciadorDeMaterias.getInstance().buscarMateriaPorCodigo(codigo);
        System.out.println("Matéria encontrada: " + newMateria.toString());
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
        System.out.println(materias);
        for (Materia materia : materias) {
            System.out.println(materia);
        }
        return materias;
    }
}