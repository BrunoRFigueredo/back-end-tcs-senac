package com.senac.projetosocial.repository;

import com.querydsl.core.types.Predicate;
import com.senac.projetosocial.model.Categoria;
import com.senac.projetosocial.model.Insumo;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InsumoRepository extends PagingAndSortingRepository<Insumo, Long>,
        QuerydslPredicateExecutor<Insumo> {

    List<Insumo> findAll(Predicate filtro);
}
