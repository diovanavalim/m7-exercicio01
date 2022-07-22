package dh.meli.joalheria.controller;

import dh.meli.joalheria.dto.JoiaDto;
import dh.meli.joalheria.model.Joia;
import dh.meli.joalheria.service.JoiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class JoiaController {

    @Autowired
    private JoiaService joiaService;

    @PostMapping("/joia")
    public ResponseEntity<JoiaDto> salvarJoia(@RequestBody Joia joia) {
        Joia newJoia = joiaService.salvarJoia(joia);

        return new ResponseEntity<JoiaDto>(new JoiaDto(newJoia), HttpStatus.OK);
    }

    @GetMapping("/joia")
    public ResponseEntity<List<JoiaDto>> recuperarJoias() {
        List<Joia> joiaList = joiaService.recuperarJoias();

        List<JoiaDto> joiaDtoList = new ArrayList<JoiaDto>();

        joiaList.forEach(joia -> {
            JoiaDto joiaDto = new JoiaDto(joia);

            joiaDtoList.add(joiaDto);
        });

        return new ResponseEntity<List<JoiaDto>>(joiaDtoList, HttpStatus.OK);
    }

    @GetMapping("/joia/{id}")
    public ResponseEntity<JoiaDto> recuperarJoia(@PathVariable long id) {
        Joia joia = joiaService.recuperarJoiaPorId(id);

        return new ResponseEntity<JoiaDto>(new JoiaDto(joia), HttpStatus.OK);
    }

    @DeleteMapping("/joia/{id}")
    public ResponseEntity<Void> deletarJoia(@PathVariable long id) {
        joiaService.deletarJoiaPorId(id);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping("/joia/{id}")
    public ResponseEntity<JoiaDto> atualizarJoia(@PathVariable long id, @RequestBody Joia joia) {
        Joia joiaAtualizada = joiaService.atualizarJoia(joia, id);

        return new ResponseEntity<JoiaDto>(new JoiaDto(joiaAtualizada), HttpStatus.OK);
    }
}
