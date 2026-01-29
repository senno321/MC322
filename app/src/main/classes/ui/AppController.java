/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package classes.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador MVC para lidar com requisições HTTP da aplicação web.
 * 
 * Define o endpoint raiz ("/") que exibe a página inicial com uma mensagem de
 * boas-vindas.
 */
@Controller
public class AppController {

    /**
     * Mapeia a rota raiz ("/") e adiciona uma mensagem ao modelo para a view
     * "index".
     * 
     * @param model objeto para passar atributos à view.
     * @return nome da view a ser renderizada ("index").
     */
    @GetMapping("/")
    public String loginPage(Model model) {
        return "login"; 
    }

    @GetMapping("/main")
    public String indexPage(Model model) {
        return "index"; 
    }

    @GetMapping("/registrar")
    public String registrarPage(Model model) {
        return "registrar";
    }

}