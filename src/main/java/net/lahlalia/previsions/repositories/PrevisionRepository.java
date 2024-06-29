package net.lahlalia.previsions.repositories;

import net.lahlalia.previsions.entities.Prevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrevisionRepository extends JpaRepository<Prevision, Long> {
}
