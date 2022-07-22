package dh.meli.joalheria.service;

import dh.meli.joalheria.exception.InternalServerError;
import dh.meli.joalheria.exception.JoiaAlreadyExists;
import dh.meli.joalheria.exception.JoiaNotFound;
import dh.meli.joalheria.model.Joia;
import dh.meli.joalheria.repository.IJoiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JoiaService implements IJoiaService {

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
    public Joia atualizarJoia(Joia joia, long id) {
        List<Joia> joiaList = new ArrayList<Joia>();

        joiaRepository.findAll().forEach(joiaList::add);

        if (joia.getCodigoBarras() != null) {
            joiaList.forEach(joiaExistente -> {
                if (joiaExistente.getCodigoBarras().equals(joia.getCodigoBarras())) {
                    throw new JoiaAlreadyExists("Atributo código de barras já cadastrado");
                }
            });
        }

        try {
            return joiaRepository.save(joia);
        } catch (Exception e) {
            throw new InternalServerError(String.format("Não foi possível atualizar a jóia de id %d", id));
        }
    }
}
