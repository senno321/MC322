package db;

import org.springframework.data.jpa.repository.JpaRepository;
import mc322.agendavel.ItemAgendavel;

public interface ItemAgendavelRepository extends JpaRepository<ItemAgendavel, Long> {
}