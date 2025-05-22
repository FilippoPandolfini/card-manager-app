package card_manager.cardapp.controller;

import card_manager.cardapp.dto.UserDTO;
import card_manager.cardapp.model.Possession;
import card_manager.cardapp.model.User;
import card_manager.cardapp.repository.PossessionRepository;
import card_manager.cardapp.service.PossessionService;
import card_manager.cardapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PossessionRepository possessionRepository;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Possession>> getUserPossession(@PathVariable String email){
        return userService.getByEmail(email)
                .map(user -> ResponseEntity.ok(possessionRepository.findByUser(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            User user = new User();
            user.setName(userDTO.getName());
            user.setSurname(userDTO.getSurname());
            user.setEmail(userDTO.getEmail());

            User createdUser = userService.createUser(user);

            return ResponseEntity.status(201).body(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email){
        try {
            userService.deleteUser(email);
            return ResponseEntity.ok("Utente eliminato con successo");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
