package com.senac.projetosocial.repository;

import com.querydsl.core.types.Predicate;
import com.senac.projetosocial.model.Servico;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServicoRepository extends JpaRepository<Servico, Long>,
        QuerydslPredicateExecutor<Servico> {

    List<Servico> findAll(Predicate filtro);
}
