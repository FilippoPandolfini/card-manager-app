package card_manager.cardapp.repository;

import card_manager.cardapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUid(String uid);
    Optional<User> findTopByOrderByUidDesc();

    boolean existsByEmail(String email);
    void deleteByEmail (String email);

    @Query("SELECT MAX(u.uid) FROM User u")
    Optional<String> findMaxUid();
}
