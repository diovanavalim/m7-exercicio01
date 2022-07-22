package dh.meli.joalheria.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "joias")
public class Joia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "material", nullable = false)
    private String material;

    @Column(name = "peso", nullable = false)
    private double peso;

    @Column(name = "quilates", nullable = false)
    private int quilates;

    @Column(name = "codigo_barras", nullable = false, unique = true)
    private String codigoBarras;
}
