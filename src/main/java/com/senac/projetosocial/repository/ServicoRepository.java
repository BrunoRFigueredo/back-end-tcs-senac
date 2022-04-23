package com.senac.projetosocial.repository;

import com.querydsl.core.types.Predicate;
import com.senac.projetosocial.model.Servico;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServicoRepository extends PagingAndSortingRepository<Servico, Long>,
        QuerydslPredicateExecutor<Servico> {

    List<Servico> findAll(Predicate filtro);
}
