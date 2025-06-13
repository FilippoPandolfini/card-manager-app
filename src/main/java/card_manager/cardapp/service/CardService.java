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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

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

    public Cards createCard(Cards card) {
        if (cardRepository.existsByCode(card.getCode())) {
            throw new IllegalArgumentException("Una carta con codice '" + card.getCode() + "' esiste già.");
        }
        return cardRepository.save(card);
    }

    public List<Cards> getAllCards() {
        return cardRepository.findAll(Sort.by(Sort.Direction.ASC, "code"));
    }

    public Page<Cards> findCardsByFilters(String name, String code, String colorStr, int page, int size) {
        CardColor enumColor = null;

        if (colorStr != null && !colorStr.isEmpty()) {
            try {
                enumColor = CardColor.valueOf(colorStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Colore non valido: " + colorStr);
            }
        }

        name = (name != null && !name.trim().isEmpty()) ? name:null;
        code = (code != null && !code.trim().isEmpty()) ? code:null;

        Pageable pageable = PageRequest.of(page, size);
        return cardRepository.findByFilters(name, code, enumColor, pageable);
    }

    public Optional<Cards> getCardByCode(String code) {
        return cardRepository.findByCode(code);
    }

    public List<Cards> getCardByColor(String color) {
        CardColor enumColor = CardColor.valueOf(color.toUpperCase());
        Pageable pageable = Pageable.unpaged();
        try {
            enumColor = CardColor.valueOf(color.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Colore non valido: " + color + "\nInserisci un colore valido.");
        }
        return cardRepository.findByColor(enumColor, pageable).getContent();
    }

    @Transactional
    public void assignCardToUser(String cardCode, List<String> userEmails) {
        Cards card = cardRepository.findByCode(cardCode)
                .orElseThrow(() -> new RuntimeException("Carta non trovata."));
        for (String email : userEmails) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException(email + " mail non trovata"));
            Optional<Possession> existing = possessionRepository.findByUserAndCard(user, card);

            if (existing.isPresent()) {
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

    public Page<Cards> searchCards(String name, String code, String color, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        CardColor colorEnum = null;
        if (color != null && !color.isEmpty()) {
         try {
             colorEnum = CardColor.valueOf(color.toUpperCase());
         } catch (IllegalArgumentException e) {
             throw new RuntimeException("Colore non valido: " + color);
         }
        }
        return cardRepository.findByFilters(name, code, colorEnum, pageable);
    }

    public List<CardColor> getAvaiableColors() {
        return cardRepository.findDistinctColors();
    }
}
