package card_manager.cardapp.controller;

import card_manager.cardapp.dto.CardDTO;
import card_manager.cardapp.dto.CardFilterDTO;
import card_manager.cardapp.model.CardColor;
import card_manager.cardapp.model.Cards;
import card_manager.cardapp.service.CardService;
import card_manager.cardapp.service.PossessionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carta")
@CrossOrigin(origins = "*")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private PossessionService possessionService;

    @PostMapping
    public ResponseEntity<Cards> createCard(@Valid @RequestBody CardDTO cardDTO){
        Cards card = new Cards();
        card.setName(cardDTO.getName());
        card.setCode(cardDTO.getCode());
        card.setColor(cardDTO.getColor());
        Cards created = cardService.createCard(card);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public Page<Cards> getAllCards(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String color,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size) {

        return cardService.searchCards(name, code, color, page, size);
    }


    @GetMapping("/{code}")
    public ResponseEntity<Cards> getCardByCode(@PathVariable String code){
        return cardService.getCardByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{code}/owners")
    public ResponseEntity<String> assignCardToUsers(
            @PathVariable String code,
            @RequestBody List<String> userEmails){

        try {
            cardService.assignCardToUser(code, userEmails);
            return ResponseEntity.ok("Carta assegnata al giocatore");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteCard(@PathVariable String code){
        try {
            possessionService.deleteCardCascade(code);
            return ResponseEntity.ok("Carta eliminata con successo");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public Page<Cards> searchCards(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String color,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size){

        return cardService.searchCards(name, code, color, page, size);
    }

    @GetMapping("/colors")
    public List<CardColor> getAvaiableColors(){
        return cardService.getAvaiableColors();
    }
}
