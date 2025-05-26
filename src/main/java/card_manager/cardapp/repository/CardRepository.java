package card_manager.cardapp.repository;

import card_manager.cardapp.model.CardColor;
import card_manager.cardapp.model.Cards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Cards, Long> {
    Optional<Cards> findByCode(String code);
    List<Cards> findByColor(CardColor color);
    boolean existsByCode(String code);
}
