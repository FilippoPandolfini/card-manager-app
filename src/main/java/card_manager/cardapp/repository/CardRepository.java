package card_manager.cardapp.repository;

import card_manager.cardapp.model.CardColor;
import card_manager.cardapp.model.Cards;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Cards, Long> {
    Optional<Cards> findByCode(String code);
    Page<Cards> findAll(Pageable pageable);
    Page<Cards> findByColor(CardColor color, Pageable pageable);
    boolean existsByCode(String code);
}
