package net.lahlalia.previsions.repositories;

import net.lahlalia.previsions.entities.BacItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacItemRepository extends JpaRepository<BacItem, Long> {
}
