package fr.afpa.cda19.harmogestionapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pratiquer")
@IdClass(PratiquerPK.class)
public class Pratiquer {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_instrument")
    private Instrument instrument;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_membre")
    private Membre membre;

    @Column(name = "apprentissage_en_cours")
    private Boolean apprentissageEnCours;
}
