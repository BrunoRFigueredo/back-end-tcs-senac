package com.senac.projetosocial.repository;

import com.querydsl.core.types.Predicate;
import com.senac.projetosocial.model.Instituicao;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface InstituicaoRepository extends PagingAndSortingRepository<Instituicao, Long>,
        QuerydslPredicateExecutor<Instituicao> {

    List<Instituicao> findAll(Predicate predicate);
}
