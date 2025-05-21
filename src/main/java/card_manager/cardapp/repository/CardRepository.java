package card_manager.cardapp.repository;

import card_manager.cardapp.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByCode(String code);
}
