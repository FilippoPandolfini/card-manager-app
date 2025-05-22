package card_manager.cardapp.repository;

import card_manager.cardapp.model.Cards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Cards, Long> {
    Optional<Cards> findByCode(String code);
    boolean existsByCode(String code);
}
