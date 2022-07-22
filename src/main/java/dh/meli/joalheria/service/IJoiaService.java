package dh.meli.joalheria.service;

import dh.meli.joalheria.model.Joia;

import java.util.List;
import java.util.Optional;

public interface IJoiaService {
    public Joia salvarJoia(Joia joia);

    public List<Joia> recuperarJoias();

    public Joia recuperarJoiaPorId(long id);

    public void deletarJoiaPorId(long id);

    public Joia atualizarJoia(Joia joia, long id);
}
