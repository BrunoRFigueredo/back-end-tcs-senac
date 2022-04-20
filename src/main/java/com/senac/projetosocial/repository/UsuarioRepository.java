package com.senac.projetosocial.repository;


import com.senac.projetosocial.model.Usuario;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long>,
        QuerydslPredicateExecutor<Usuario> {

}
