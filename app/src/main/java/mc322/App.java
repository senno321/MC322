/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package mc322;

import org.springframework.boot.autoconfigure.SpringBootApplication;


import mc322.materia.GerenciadorDeMaterias;
import mc322.usuario.Usuario;

import org.springframework.boot.SpringApplication;

/**
 * Contém a estrutura de implementação da aplicação.
 * 
 * @author Bruno Medeiros Saback - 281746
 * @author Lucas
 * @author Filipe
 * @author Kauan Cunha da Silva - 240030
 * 
 *         Comentários foram gerados em sua maioria por IA
 * 
 */

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        //Debuga as paradas
        System.out.println(GerenciadorDeMaterias.getInstance().buscarMateriaPorCodigo("MA211")); 
        
        //Setando o usuario atual e os gerenciadores.
        Usuario.innit("Testador", "test123@gmail.com", "12345");
        Usuario.getInstance().adicionarMateria(GerenciadorDeMaterias.getInstance().buscarMateriaPorCodigo("MA211"));
        //Inicia a aplicação Spring Boot
        SpringApplication.run(App.class, args);
    }
}