package com.senac.projetosocial.repository;

import com.querydsl.core.types.Predicate;
import com.senac.projetosocial.model.PerfilPermissao;
import com.senac.projetosocial.model.Servico;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilPermissaoRepository extends PagingAndSortingRepository<PerfilPermissao, Long>,
        QuerydslPredicateExecutor<PerfilPermissao> {
    
    //PerfilPermissao findAll(Predicate predicate);
}
