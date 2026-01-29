package db;

import org.springframework.data.jpa.repository.JpaRepository;
import mc322.evento.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    
}