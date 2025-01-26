package io.github.curso.libraryapi.repository;

import io.github.curso.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    List<Autor> findByNome(String nome);
    List<Autor> findByNacionalidade(String nacionalidade);
    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);


   // @Query("""
   //     select a from Autor a
  //      where a.nome = ?1 and a.nacionalidade = ?2 and a.data_nascimento = ?3
//""")
   // Optional<Autor> encontrarNomeNacionalidadeDataNasc(String nome,
       //                                                String nacionalidade,
                   //                                    LocalDate dataNasc);
        @Modifying
        @Transactional
        @Query("""
            delete from Autor a
            where a.nome = ?1
""")
    void deletePorNome(String nome);

}
