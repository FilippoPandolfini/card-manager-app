package card_manager.cardapp.dto;

import card_manager.cardapp.model.CardColor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class CardDTO {

    @NotBlank(message = "Il nome è obbligatorio")
    private String name;

    @NotBlank(message = "Il codice è obbligatorio")
    private String code;

    @NotNull(message = "Il colore è obbligatorio")
    private CardColor color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
