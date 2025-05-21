package card_manager.cardapp.dto;

import card_manager.cardapp.model.CardColor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class CardDTO {

    @NotBlank(message = "Il codice è obbligatorio")
    private String code;

    @NotNull(message = "Il colore è obbligatorio")
    private CardColor color;

    @PositiveOrZero(message = "Il numero di copie non può essere negativo")
    private int copies;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }
}
