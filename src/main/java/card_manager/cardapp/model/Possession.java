package card_manager.cardapp.model;

import jakarta.persistence.*;

@Entity
public class Possession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "card_id")
    private Cards card;

    @Column(nullable = false)
    private int copies;

    public Possession() {
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public Cards getCard(){
        return card;
    }

    public void setCard(Cards card){
        this.card = card;
    }

    public int getCopies(){
        return copies;
    }

    public void setCopies(int copies){
        this.copies = copies;
    }

    public Possession(User user, Cards card, int copies) {
        this.user = user;
        this.card =  card;
        this.copies = copies;
    }

}
