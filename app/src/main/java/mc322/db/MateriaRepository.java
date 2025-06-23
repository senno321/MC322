package mc322.db;

import org.springframework.data.jpa.repository.JpaRepository;
import mc322.materia.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
    
}