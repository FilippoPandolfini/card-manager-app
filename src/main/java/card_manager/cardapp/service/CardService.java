package card_manager.cardapp.service;

import card_manager.cardapp.model.Card;
import card_manager.cardapp.model.Possession;
import card_manager.cardapp.model.User;
import card_manager.cardapp.repository.CardRepository;
import card_manager.cardapp.repository.PossessionRepository;
import card_manager.cardapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PossessionRepository possessionRepository;

    public Card createCard(Card card){
        if (cardRepository.existsByCode(card.getCode())) {
            throw new IllegalArgumentException("Una carta con codice '" + card.getCode() + "' esiste già.");
        }
        return cardRepository.save(card);
    }

    public List<Card> getAllCards(){
        return cardRepository.findAll();
    }

    public Optional<Card> getCardByCode(String code){
        return cardRepository.findByCode(code);
    }

    @Transactional
    public void assignCardToUser(String cardCode, List<String> userEmails) {
        Card card = cardRepository.findByCode(cardCode)
                .orElseThrow(() -> new RuntimeException("Carta non trovata."));
        for (String email : userEmails){
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException(email + " mail non trovata"));
            Optional< Possession> existing = possessionRepository.findByUserAndCard(user, card);

            if (existing.isPresent()){
                Possession possession = existing.get();
                possession.setCopies(possession.getCopies() + 1);
                possessionRepository.save(possession);
            } else {
                Possession possession = new Possession();
                possession.setUser(user);
                possession.setCard(card);
                possession.setCopies(1);
                possessionRepository.save(possession);
            }
        }
    }
}
