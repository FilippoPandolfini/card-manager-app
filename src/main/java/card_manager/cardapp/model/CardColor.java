package card_manager.cardapp.model;

import jakarta.validation.constraints.NotNull;

@NotNull(message = "Colore carta obbligatorio")
public enum CardColor {
    RED,
    GREEN,
    BLUE,
    PURPLE,
    BLACK,
    YELLOW
}
