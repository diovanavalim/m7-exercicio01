package dh.meli.joalheria.service;

import dh.meli.joalheria.model.Joia;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IJoiaService <T> {
    public Joia salvarJoia(Joia joia);

    public List<Joia> recuperarJoias();

    public Joia recuperarJoiaPorId(long id);

    public void deletarJoiaPorId(long id);

    public Joia atualizarJoia(Map<String, T> data, long id);
}
