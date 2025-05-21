package card_manager.cardapp.repository;

import card_manager.cardapp.model.Card;
import card_manager.cardapp.model.Possession;
import card_manager.cardapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PossessionRepository extends JpaRepository<Possession, Long> {

    List<Possession> findByUser(User user);
    Optional<Possession> findByUserAndCard (User user, Card card);
    List<Possession> findByCard (Card card);
    void deleteByUser (User user);
    void deleteByCard (Card card);
}
