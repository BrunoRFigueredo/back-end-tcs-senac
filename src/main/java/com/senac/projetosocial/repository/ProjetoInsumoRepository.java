package com.senac.projetosocial.repository;

import com.querydsl.core.types.Predicate;
import com.senac.projetosocial.model.Insumo;
import com.senac.projetosocial.model.ProjetoInsumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjetoInsumoRepository extends PagingAndSortingRepository<ProjetoInsumo, Long>,
        QuerydslPredicateExecutor<ProjetoInsumo> {
    
    Page<ProjetoInsumo> findAll(Predicate predicate, Pageable pageable);
}
