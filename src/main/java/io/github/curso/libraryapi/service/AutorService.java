package io.github.curso.libraryapi.service;

import io.github.curso.libraryapi.dto.AutorDTO;
import io.github.curso.libraryapi.model.Autor;
import io.github.curso.libraryapi.repository.AutorRepository;
import io.github.curso.libraryapi.validator.AutorValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator validator;

    public AutorService(AutorRepository autorRepository, AutorValidator validator){
        this.autorRepository = autorRepository;
        this.validator = validator;
    }

    public void autorSalvar(Autor autorDTO){
        validator.validar(autorDTO);
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

    public void atualizar(String id, AutorDTO autorDTO){
        var autorEncontrar = autorRepository.findById(UUID.fromString(id));

        if(autorEncontrar.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        var autor = autorEncontrar.get();
        autor.setNome(autorDTO.nome());
        autor.setNacionalidade(autorDTO.nacionalidade());
        autor.setData_nascimento(autorDTO.dataNascimento());
        validator.validar(autor);
        autorRepository.save(autor);

    }
}
