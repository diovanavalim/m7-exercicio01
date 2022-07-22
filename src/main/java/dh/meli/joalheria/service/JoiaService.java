package dh.meli.joalheria.service;

import dh.meli.joalheria.exception.InternalServerError;
import dh.meli.joalheria.exception.JoiaAlreadyExists;
import dh.meli.joalheria.exception.JoiaNotFound;
import dh.meli.joalheria.model.Joia;
import dh.meli.joalheria.repository.IJoiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JoiaService<T> implements IJoiaService<T> {

    @Autowired
    private IJoiaRepository joiaRepository;

    @Override
    public Joia salvarJoia(Joia joia) {
        List<Joia> joias = new ArrayList<Joia>();

        joiaRepository.findAll().forEach(joias::add);

        List<Joia> joiaJaExiste = joias
                .stream()
                .filter(joiaJaExistente -> joiaJaExistente.getCodigoBarras().equals(joia.getCodigoBarras()))
                .collect(Collectors.toList());

        if (joiaJaExiste.size() != 0) {
            throw new JoiaAlreadyExists("Esta jóia já foi cadastrada.");
        }

        try {
            return joiaRepository.save(joia);
        } catch (Exception e) {
            throw new InternalServerError("Não foi possível salvar nova jóia.");
        }
    }

    @Override
    public List<Joia> recuperarJoias() {
        try {
            List<Joia> joias = new ArrayList<Joia>();

            joiaRepository.findAll().forEach(joias::add);

            return joias;
        } catch (Exception e) {
            throw new InternalServerError("Não foi possível recuperar as jóias existentes.");
        }
    }

    @Override
    public Joia recuperarJoiaPorId(long id) {
        Optional<Joia> joia;

        try {
            joia = joiaRepository.findById(id);
        } catch (Exception e) {
            throw new InternalServerError(String.format("Não foi possível recuperar a jóia de id %d", id));
        }

        if (joia.isEmpty()) {
            throw new JoiaNotFound(String.format("Não foi possível recuperar a jóia de id %d", id));
        }

        return joia.get();
    }

    @Override
    public void deletarJoiaPorId(long id) {
        if (joiaRepository.findById(id).isEmpty()) {
            throw new JoiaNotFound(String.format("Não foi possível encontrar a jóia de id %d", id));
        }

        try {
            joiaRepository.deleteById(id);
        } catch (Exception e) {
            throw new InternalServerError(String.format("Não foi possível deletar a jóia de id %d", id));
        }
    }

    @Override
    public Joia atualizarJoia(Map<String, T> data, long id) {
        Joia joia = joiaRepository.findById(id).orElse(null);

        if (joia == null) {
            throw new JoiaNotFound(String.format("Não foi possível encontrar a jóia de id %d", id));
        }

        List<Joia> joiaList = new ArrayList<Joia>();

        joiaRepository.findAll().forEach(joiaList::add);

        data.forEach((atributo, valor) -> {
            switch (atributo) {
                case "material": joia.setMaterial((String) valor); break;
                case "peso": joia.setPeso((double) valor); break;
                case "quilates": joia.setQuilates((int) valor); break;
                case "codigoBarras": joia.setCodigoBarras((String) valor); break;
            }
        });

        joiaList.forEach((joiaExistente -> {
            if (joiaExistente.getId() != joia.getId()) {
                if (joiaExistente.getCodigoBarras().equals(joia.getCodigoBarras())) {
                    throw new JoiaAlreadyExists("Está jóia já existe.");
                }
            }
        }));

        try {
            return joiaRepository.save(joia);
        } catch (Exception e) {
            throw new InternalServerError(String.format("Não foi possível atualizar a jóia de id %d", id));
        }
    }
}
