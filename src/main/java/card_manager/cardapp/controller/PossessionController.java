package card_manager.cardapp.controller;

import card_manager.cardapp.service.PossessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/possessions")
public class PossessionController {

    @Autowired
    private PossessionService possessionService;

    @PostMapping("/buy")
    public ResponseEntity<String> buyCard(@RequestParam String email, @RequestParam String code){
        try {
            possessionService.buyCard(email, code);
            return ResponseEntity.ok("Carta acquistata");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/sell")
    public ResponseEntity<String> sellCard(@RequestParam String email, @RequestParam String code){
        try {
            possessionService.sellCard(email, code);
            return ResponseEntity.ok("Carta venduta");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
