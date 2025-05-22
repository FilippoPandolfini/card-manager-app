package card_manager.cardapp.service;

import card_manager.cardapp.model.User;
import card_manager.cardapp.repository.PossessionRepository;
import card_manager.cardapp.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PossessionRepository possessionRepository;

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email già registrata");
        }
        String newUid = generateNextUid();
        user.setUid(newUid);
        return userRepository.save(user);
    }
    
    private String generateNextUid() {
        Optional<String> maxUidOpt = userRepository.findMaxUid();
        int nextUid = 1;
        
        if (maxUidOpt.isPresent()){
            try {
                nextUid = Integer.parseInt(maxUidOpt.get())+1;
            } catch (NumberFormatException e) {
            }
        }
        return String.format("%05d", nextUid);
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
