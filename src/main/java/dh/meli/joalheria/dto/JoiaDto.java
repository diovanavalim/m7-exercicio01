package dh.meli.joalheria.dto;

import dh.meli.joalheria.model.Joia;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoiaDto {
    private long id;
    private String material;
    private double peso;
    private int quilates;

    public JoiaDto (Joia joia) {
        this.id = joia.getId();
        this.material = joia.getMaterial();
        this.peso = joia.getPeso();
        this.quilates = joia.getQuilates();
    }
}
