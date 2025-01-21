package io.github.curso.libraryapi.service;


import io.github.curso.libraryapi.model.Autor;
import io.github.curso.libraryapi.model.GeneroLivro;
import io.github.curso.libraryapi.model.Livro;
import io.github.curso.libraryapi.repository.AutorRepository;
import io.github.curso.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void executar(){
        var livro = new Livro();
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setIsbn("12211-93721");
        livro.setData_publicacao(LocalDate.of(1893,9,2));
        livro.setPreco(BigDecimal.valueOf(75));
        livro.setTitulo("Assim Falou Zaratustra");

        Autor autor = new Autor();
        autor.setData_nascimento(LocalDate.of(1844, 10, 15));
        autor.setNome("Friedrich Nietzsche");
        autor.setNacionalidade("Alemão");

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);

        livro.setAutor(autor);
        autorRepository.save(autor);

        if(autor.getNome().equals("Friedrich Nietzsche")){
            throw new RuntimeException("");
        }



    }

}
