package com.senac.projetosocial.repository;

import com.senac.projetosocial.model.Projeto;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ProjetoRepository extends JpaRepository<Projeto, Long>,
        QuerydslPredicateExecutor<Projeto> {

}
