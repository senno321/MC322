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
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario novoUsuario) {
        // 1. Verifica se o email já existe usando o repositório
        if (usuarioRepository.findByEmail(novoUsuario.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("O email fornecido já está em uso.");
        }
        
        // 2. Salva o novo usuário no banco de dados
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

        // Usa o método verificarSenha que compara os hashes
        if (usuario.verificarSenha(senha)) {
            // Define o usuário logado como a instância atual do singleton
            // NOTA: Esta abordagem com singleton pode não ser ideal para um ambiente web real
            // com múltiplos usuários, mas segue o padrão do seu projeto atual.
            Usuario.innit(usuario.getNome(), usuario.getEmail(), senha);
            
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos.");
        }
    }
}