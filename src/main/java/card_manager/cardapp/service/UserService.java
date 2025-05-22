package card_manager.cardapp.service;

import card_manager.cardapp.model.User;
import card_manager.cardapp.repository.PossessionRepository;
import card_manager.cardapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PossessionRepository possessionRepository;

    public User createUser(User user){
        if (userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email già registrata");
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void deleteUser(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        possessionRepository.deleteByUser(user);
        userRepository.delete(user);
    }
}
