package io.github.curso.libraryapi.dto;

import io.github.curso.libraryapi.model.Autor;

import java.time.LocalDate;

public record AutorDTO(String nome, LocalDate dataNascimento, String nacionalidade) {


    public Autor mapearAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setNacionalidade(this.nacionalidade);
        autor.setData_nascimento(this.dataNascimento);
        return autor;
    }
}
