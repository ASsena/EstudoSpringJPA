package io.github.curso.libraryapi.repository;

import io.github.curso.libraryapi.model.Autor;
import io.github.curso.libraryapi.model.GeneroLivro;
import io.github.curso.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    List<Livro> findByAutor(Autor autor);

    @Query("""
        select l.genero 
        from Livro l 
        join l.autor a
        where a.nacionalidade = 'Brasileira'  
""")
    List<String> listarGenerosAutorBrasileiro();

    @Query("""
    select l from Livro l 
    where l.genero = :genero
""")
    List<Livro> livrosPorGenero(@Param("genero") GeneroLivro generoLivro);

    List<Livro> findByGenero(GeneroLivro generoLivro);

}
