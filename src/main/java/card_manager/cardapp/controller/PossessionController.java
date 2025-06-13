package card_manager.cardapp.controller;

import card_manager.cardapp.service.PossessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/possessions")
@CrossOrigin(origins = "*")
public class PossessionController {

    @Autowired
    private PossessionService possessionService;

    @PostMapping("/buy")
    public ResponseEntity<String> buyCard(@RequestBody PossessionRequest request){
        try {
            possessionService.buyCard(request.getEmail(), request.getCode(), request.getCopies());
            return ResponseEntity.ok("Carta acquistata");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/sell")
    public ResponseEntity<String> sellCard(@RequestBody PossessionRequest request){
        try {
            possessionService.sellCard(request.getEmail(), request.getCode(), request.getCopies());
            return ResponseEntity.ok("Carta venduta");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public static class PossessionRequest {
        private String email;
        private String code;
        private int copies;

        public PossessionRequest(String email, String code, int copies) {
            this.email = email;
            this.code = code;
            this.copies = copies;
        }

        public PossessionRequest(){
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getCopies() {
            return copies;
        }

        public void setCopies(int copies) {
            this.copies = copies;
        }
    }


}
