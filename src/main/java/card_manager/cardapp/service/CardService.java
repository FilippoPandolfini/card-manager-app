package card_manager.cardapp.service;

import card_manager.cardapp.dto.CardFilterDTO;
import card_manager.cardapp.model.CardColor;
import card_manager.cardapp.model.Cards;
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

    public Cards createCard(Cards card){
        if (cardRepository.existsByCode(card.getCode())) {
            throw new IllegalArgumentException("Una carta con codice '" + card.getCode() + "' esiste già.");
        }
        return cardRepository.save(card);
    }

    public List<Cards> getAllCards(){
        return cardRepository.findAll();
    }

    public Optional<Cards> getCardByCode(String code){
        return cardRepository.findByCode(code);
    }

    public List<Cards> getCardByColor(String color){
        CardColor enumColor;
        try {
            enumColor = CardColor.valueOf(color.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Colore non valido: " + color + "\nInserisci un colore valido.");
        }
        return cardRepository.findByColor(enumColor);
    }

    @Transactional
    public void assignCardToUser(String cardCode, List<String> userEmails) {
        Cards card = cardRepository.findByCode(cardCode)
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

    public List<Cards> searchCards(CardFilterDTO filter) {
        String colorInput = filter.getColor();
        if (colorInput != null && !colorInput.isEmpty()) {
            try {
                CardColor enumColor = CardColor.valueOf(colorInput.toUpperCase());
                return cardRepository.findByColor(enumColor);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Colore non valido: " + colorInput + "\nInserisci un colore valido.");
            }
        }
        return cardRepository.findAll();
    }
}
