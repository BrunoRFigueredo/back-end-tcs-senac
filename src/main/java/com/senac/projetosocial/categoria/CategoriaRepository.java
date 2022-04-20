package com.senac.projetosocial.categoria;

import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoriaRepository extends PagingAndSortingRepository<Categoria, Long>,
        QuerydslPredicateExecutor<Categoria> {

    List<Categoria> findAll(Predicate filtro);
}
