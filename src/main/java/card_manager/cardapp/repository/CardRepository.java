package card_manager.cardapp.repository;

import card_manager.cardapp.model.CardColor;
import card_manager.cardapp.model.Cards;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Cards, Long> {

    Optional<Cards> findByCode(String code);
    Page<Cards> findAll(Pageable pageable);
    Page<Cards> findByColor(CardColor color, Pageable pageable);
    boolean existsByCode(String code);

    @Query("SELECT c FROM Cards c " +
            "WHERE (:name IS NULL OR c.name LIKE %:name%) " +
            "AND (:code IS NULL OR c.code LIKE %:code%) " +
            "AND (:color IS NULL OR c.color = :color) " +
            "ORDER BY c.code ASC")
    Page<Cards> findByFilters(@Param("name") String name,
                              @Param("code") String code,
                              @Param("color") CardColor color,
                              Pageable pageable);

    @Query("SELECT DISTINCT c.color FROM Cards c")
    List<CardColor> findDistinctColors();
}

