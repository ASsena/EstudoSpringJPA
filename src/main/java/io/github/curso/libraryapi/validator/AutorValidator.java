package io.github.curso.libraryapi.validator;

import io.github.curso.libraryapi.exceptions.RegistroDupilcadoException;
import io.github.curso.libraryapi.model.Autor;
import io.github.curso.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private final AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor){
        if(existeAutorCadastrado(autor)){
            throw new RegistroDupilcadoException("Autor já cadastrado!");
        }
    }

    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncotrado = autorRepository.encontrarNomeNacionalidadeDataNasc(
                 autor.getNome(),autor.getNacionalidade(), autor.getData_nascimento()
        );

        if(autor.getId() == null){
            return autorEncotrado.isPresent();
        }

        return !autor.getId().equals(autorEncotrado.get().getId()) && autorEncotrado.isPresent();
    }
}
