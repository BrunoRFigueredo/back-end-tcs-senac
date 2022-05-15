package com.senac.projetosocial.repository;

import com.senac.projetosocial.model.PerfilPermissao;
import com.senac.projetosocial.model.Projeto;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

@Service
public interface ProjetoRepository extends PagingAndSortingRepository<Projeto, Long>,
        QuerydslPredicateExecutor<Projeto> {

}
