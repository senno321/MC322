package mc322.db;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import mc322.usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}