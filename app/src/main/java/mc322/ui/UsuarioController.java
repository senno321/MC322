package mc322.ui;

import java.util.Optional;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import mc322.db.UsuarioRepository;
import mc322.usuario.Usuario;


@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registrarUsuario(@RequestBody Map<String, String> dadosRegistro) {
        String email = dadosRegistro.get("email");
        String nome = dadosRegistro.get("nome");
        String senha = dadosRegistro.get("senha");

        // 1. Verifica se o email já existe
        if (usuarioRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("O email fornecido já está em uso.");
        }
        
        // 2. Cria a instância de Usuário usando o construtor que gera o hash da senha
        Usuario novoUsuario = new Usuario(nome, email, senha);
        
        // 3. Salva o novo usuário no banco de dados, agora com o hash correto
        usuarioRepository.save(novoUsuario);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUsuario(@RequestBody Map<String, String> credenciais) {
        String email = credenciais.get("email");
        String senha = credenciais.get("password");

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos.");
        }

        Usuario usuario = usuarioOptional.get();

        if (usuario.verificarSenha(senha)) {
            // **AQUI ESTÁ A CORREÇÃO**
            // Chame o novo método para DEFINIR o usuário atual, em vez de inicializar.
            Usuario.setUsuarioAtual(usuario);
            
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos.");
        }
    }
}