package io.github.curso.libraryapi.controller;

import io.github.curso.libraryapi.dto.AutorDTO;
import io.github.curso.libraryapi.dto.ErroResposta;
import io.github.curso.libraryapi.exceptions.RegistroDupilcadoException;
import io.github.curso.libraryapi.model.Autor;
import io.github.curso.libraryapi.repository.AutorRepository;
import io.github.curso.libraryapi.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("autores")
public class AutorController {

    private final AutorService autorService;
    private final AutorRepository autorRepository;

    public AutorController(AutorService autorService, AutorRepository autorRepository){
        this.autorService = autorService;
        this.autorRepository = autorRepository;
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody AutorDTO autor) {
       try{
           Autor autorEntidade = autor.mapearAutor();
           autorService.autorSalvar(autorEntidade);
           URI location = ServletUriComponentsBuilder
                   .fromCurrentRequest()
                   .path("/{id}")
                   .buildAndExpand(autorEntidade.getId())
                   .toUri();
           return ResponseEntity.created(location).build();
       }catch (RegistroDupilcadoException e){
           var erroDTO = ErroResposta.conflito(e.getMessage());
           return ResponseEntity.status(erroDTO.status()).body(erroDTO);
       }

    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorDTO autorDTO = new AutorDTO(autor.getNome(),autor.getData_nascimento(),autor.getNacionalidade());
            return ResponseEntity.ok(autorDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable String id){
        var encontraId = autorService.obterPorId(UUID.fromString(id));

        if(encontraId.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        autorService.delAutor(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){
        var autores = autorService.pesquisa(nome, nacionalidade);
        return ResponseEntity.ok(autores);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable("id") String idDto,
            @RequestBody AutorDTO autorDTO){

        autorService.atualizar(idDto, autorDTO);

        return ResponseEntity.noContent().build();

    }
}
