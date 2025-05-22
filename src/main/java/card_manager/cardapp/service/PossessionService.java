package card_manager.cardapp.service;

import card_manager.cardapp.model.Cards;
import card_manager.cardapp.model.Possession;
import card_manager.cardapp.model.User;
import card_manager.cardapp.repository.CardRepository;
import card_manager.cardapp.repository.PossessionRepository;
import card_manager.cardapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PossessionService {

    @Autowired
    private PossessionRepository possessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @Transactional
    public void buyCard(String email, String code){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Cards card = cardRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Carta non trovata"));

        Possession possession = possessionRepository.findByUserAndCard(user, card)
                .orElse(new Possession(user, card, 0));

        possession.setCopies(possession.getCopies()+1);
        possessionRepository.save(possession);
    }

    @Transactional
    public void sellCard(String email, String code) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Cards card = cardRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Carta non trovata"));

        Possession possession = possessionRepository.findByUserAndCard(user, card)
                .orElseThrow(() -> new RuntimeException("L'utente " + user + " non possiede questa carta"));

        if (possession.getCopies() <= 0) {
            throw new RuntimeException("Nessuna copia disponibile");
        } else if (possession.getCopies() > 1) {
            possession.setCopies(possession.getCopies() - 1);
            possessionRepository.save(possession);
        } else if (possession.getCopies() == 1) {
            possessionRepository.delete(possession);
        }
    }

    @Transactional
    public void deleteCardCascade(String code){
        Cards card = cardRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Carta non trovata"));

        possessionRepository.deleteByCard(card);
        cardRepository.delete(card);
    }
}
