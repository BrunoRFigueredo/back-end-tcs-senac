package com.senac.projetosocial.repository;

import com.querydsl.core.types.Predicate;
import com.senac.projetosocial.model.PrestacaoConta;
import com.senac.projetosocial.model.ProjetoServico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

@Service
public interface ProjetoServicoRepository extends PagingAndSortingRepository<ProjetoServico, Long>,
        QuerydslPredicateExecutor<ProjetoServico> {

    Page<ProjetoServico> findAll(Predicate predicate, Pageable pageable);


}
