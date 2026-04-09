package com.senac.projetosocial.repository;

import com.querydsl.core.types.Predicate;
import com.senac.projetosocial.model.PrestacaoConta;
import com.senac.projetosocial.model.ProjetoInsumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface PrestacaoContaRepository extends JpaRepository<PrestacaoConta, Long>,
        QuerydslPredicateExecutor<PrestacaoConta> {
    
    Page<PrestacaoConta> findAll(Predicate predicate, Pageable pageable);
}
