package io.github.curso.libraryapi.repository;

import io.github.curso.libraryapi.model.Autor;
import io.github.curso.libraryapi.model.GeneroLivro;
import io.github.curso.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository autorRepository;

    @Test
    void salvarLivro(){
        var livro = new Livro();
        livro.setGenero(GeneroLivro.TERROR);
        livro.setIsbn("12312-12312");
        livro.setData_publicacao(LocalDate.of(1977,1,28));
        livro.setPreco(BigDecimal.valueOf(75));
        livro.setTitulo("The Shining");

        Autor autor = autorRepository
                .findById(UUID.fromString("c06f5e0c-f99f-44f2-8bee-1c96c90d41a9"))
                .orElse(null);

        livro.setAutor(autor);
        livroRepository.save(livro);
    }

    @Test
    void salvarCascadeTest(){
        var livro = new Livro();
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setIsbn("75676-12131");
        livro.setData_publicacao(LocalDate.of(1949,6,8));
        livro.setPreco(BigDecimal.valueOf(75));
        livro.setTitulo("1984");

        Autor autor = new Autor();
        autor.setData_nascimento(LocalDate.of(1903, 6, 25));
        autor.setNome("Geoger Orwell");
        autor.setNacionalidade("Indiano");

        livro.setAutor(autor);
        livroRepository.save(livro);
    }

    @Test
    void deleteLivroId(){
        var livro = livroRepository
                .findById(UUID.fromString("f1048630-9c44-476d-b1d4-78c306114171")).get();
        livroRepository.delete(livro);
    }


    @Test
    void verAutorEspecifico(){

        UUID id = UUID.fromString("49f05630-6133-4388-a071-28e580d754d1");
        var autor = autorRepository.findById(id).get();
        var livroAutor = livroRepository.findByAutor(autor);
        livroAutor.forEach(livro -> System.out.println(livro.getTitulo()));
    }


    @Test
    void salvarLivro2(){
        var livro = new Livro();
        livro.setGenero(GeneroLivro.TERROR);
        livro.setIsbn("09878-76754");
        livro.setData_publicacao(LocalDate.of(1986,9,15));
        livro.setPreco(BigDecimal.valueOf(83));
        livro.setTitulo("It");

        Autor autor = autorRepository.findByNome("Stephen King").orElse(null);

        livro.setAutor(autor);
        livroRepository.save(livro);
    }

    @Test
    void buscaComQuery(){
        var generos = livroRepository.listarGenerosAutorBrasileiro();
        generos.forEach(System.out::println);
    }

    @Test
    void buscaGenero(){
        var livro = livroRepository.findByGenero(GeneroLivro.TERROR);
        livro.forEach(livro1 -> System.out.println(livro1.getTitulo()));
    }
}