package io.github.curso.libraryapi.service;

import io.github.curso.libraryapi.dto.AutorDTO;
import io.github.curso.libraryapi.model.Autor;
import io.github.curso.libraryapi.repository.AutorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository){
        this.autorRepository = autorRepository;
    }

    public void autorSalvar(Autor autorDTO){
        autorRepository.save(autorDTO);
    }

    public Optional obterPorId(UUID id){
        return autorRepository.findById(id);
    }

    public void delAutor(UUID id){
        autorRepository.deleteById(id);
    }

    private List<AutorDTO> fomarAutorDto(List<Autor> autor){
        return autor.stream().map(autor1 -> new AutorDTO(
                autor1.getNome(),
                autor1.getData_nascimento(),
                autor1.getNacionalidade())).collect(Collectors.toList());
    }

    public List<AutorDTO> pesquisa(String nome, String nacionalidade){

        if(nome != null && nacionalidade != null){
            var listaAutor = autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
            return fomarAutorDto(listaAutor);

        } else if (nome != null) {
            var listaAutor = autorRepository.findByNome(nome);
            return fomarAutorDto(listaAutor);

        }else if (nacionalidade != null){
            var listaAutor = autorRepository.findByNacionalidade(nacionalidade);
            return fomarAutorDto(listaAutor);
        }

        var listaAutor = autorRepository.findAll();
        return fomarAutorDto(listaAutor);
    }
}
