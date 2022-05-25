package com.senac.projetosocial.repository;

import com.senac.projetosocial.model.ProjetoServico;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

@Service
public interface ProjetoServicoRepository extends PagingAndSortingRepository<ProjetoServico, Long>,
        QuerydslPredicateExecutor<ProjetoServico> {

}
