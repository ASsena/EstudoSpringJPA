package io.github.curso.libraryapi.repository;

import io.github.curso.libraryapi.model.Autor;
import io.github.curso.libraryapi.model.GeneroLivro;
import io.github.curso.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTeste {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @Test
    public void salvarAutorTest(){
        Autor autor = new Autor();
        autor.setNome("Stephen King");
        autor.setNacionalidade("Estadunidense");
        autor.setData_nascimento(LocalDate.of(1987,9,21));
        autorRepository.save(autor);
    }

    @Test
    public void editarAutorTest(){

        var id = UUID.fromString("71d50b0b-5c64-47b6-b38d-35b41f68e087");
        var autor = autorRepository.findById(id);

        if(autor.isPresent()){
            var autorEncontrado = autor.get();
            autorEncontrado.setNome("José alicerce");
            autorEncontrado.setData_nascimento(LocalDate.of(2007,9,2));
            autorRepository.save(autorEncontrado);
        }

    }

    @Test
    public void contarAutorTest(){
        System.out.println("Quantidade de autores na tabela: " + autorRepository.count());
    }

    @Test
    public void listarAutor(){
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void deletarPorIDTest(){
        var id = UUID.fromString("71d50b0b-5c64-47b6-b38d-35b41f68e087");
        autorRepository.deleteById(id);
    }

    @Test
    public void deletarTest(){
        var id = UUID.fromString("71d50b0b-5c64-47b6-b38d-35b41f68e087");
        var autor = autorRepository.findById(id).get();
        autorRepository.delete(autor);
    }

    @Test
    @Transactional
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Arthur");
        autor.setNacionalidade("Estadunidense");
        autor.setData_nascimento(LocalDate.of(1970,8,5));


        Livro livro = new Livro();
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setIsbn("13212-12312");
        livro.setData_publicacao(LocalDate.of(1999 ,1,28));
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setTitulo("O Roubo da Casa Assombrada");
        livro.setAutor(autor);

        Livro livretos = new Livro();
        livretos.setGenero(GeneroLivro.MISTERIO);
        livretos.setIsbn("13212-09876");
        livretos.setData_publicacao(LocalDate.of(2006 ,1,28));
        livretos.setPreco(BigDecimal.valueOf(80));
        livretos.setTitulo("O Roubo da Casa Assombrada 2");
        livretos.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livretos);
        autor.getLivros().add(livro);

        // livroRepository.saveAll(autor.getLivros());

       autorRepository.save(autor);
       
    }

    @Test
    void verLivrosAutor(){
        var autor = autorRepository.findByNome("Stephen King");
        var livros = livroRepository.findByAutor(autor.get());
        livros.forEach(l -> System.out.println(l.getTitulo()));

    }
}


