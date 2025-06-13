const ordineCustom = ["RED", "GREEN", "BLUE", "PURPLE", "BLACK", "YELLOW"];

function popolaSelectColori(idSelect, colors) {
    const select = document.getElementById(idSelect);
    if (!select) {
        console.warn("Elemento select non trovato:", idSelect);
        return;
    }

    // Pulisce eventuali opzioni residue tranne la prima (placeholder)
    const defaultOption = select.querySelector("option");
    select.innerHTML = ""; // Rimuove tutte le opzioni
    if (defaultOption) select.appendChild(defaultOption); // Riaggiunge il placeholder

    colors.forEach(color => {
        const option = document.createElement('option');
        option.value = color;
        option.textContent = color;
        select.appendChild(option);
    });
}

window.addEventListener('DOMContentLoaded', () => {
    console.log("Caricamento colori avviato...");
    fetch("http://localhost:8080/carta/colors")
        .then(res => {
            if (!res.ok) throw new Error("Errore HTTP " + res.status);
            return res.json();
        })
        .then(colors => {
            console.log("Colori ricevuti dal server:", colors);
            colors.sort((a, b) => ordineCustom.indexOf(a) - ordineCustom.indexOf(b));
            popolaSelectColori('colore', colors);
            popolaSelectColori('coloreRicerca', colors);
        })
        .catch(err => console.error("Errore caricamento colori:", err));
});

window.addEventListener('DOMContentLoaded', () => {
    fetch("http://localhost:8080/carta/colors")
        .then(res => res.json())
        .then(colors => {
            const ordineCustom = ["RED", "GREEN", "BLUE", "PURPLE", "BLACK", "YELLOW"];
            colors.sort((a, b) => ordineCustom.indexOf(a) - ordineCustom.indexOf(b));

            // Popola select "colore" solo se esiste
            const selectAggiungi = document.getElementById('colore');
            if (selectAggiungi) {
                colors.forEach(color => {
                    const option = document.createElement('option');
                    option.value = color;
                    option.textContent = color;
                    selectAggiungi.appendChild(option);
                });
            }

            // Popola select "coloreRicerca" solo se esiste
            const selectRicerca = document.getElementById('coloreRicerca');
            if (selectRicerca) {
                colors.forEach(color => {
                    const option = document.createElement('option');
                    option.value = color;
                    option.textContent = color;
                    selectRicerca.appendChild(option);
                });
            }
        })
        .catch(err => console.error("Errore caricamento colori: ", err));
});

