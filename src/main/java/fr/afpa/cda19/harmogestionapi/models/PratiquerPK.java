package fr.afpa.cda19.harmogestionapi.models;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PratiquerPK {

    @ManyToOne
    @JoinColumn(name = "id_instrument")
    private Instrument instrument;

    @ManyToOne
    @JoinColumn(name = "id_membre")
    private Membre membre;
}
