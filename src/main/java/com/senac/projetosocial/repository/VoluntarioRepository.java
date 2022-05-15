package com.senac.projetosocial.repository;

import com.senac.projetosocial.model.Voluntario;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VoluntarioRepository extends PagingAndSortingRepository<Voluntario, Long>,
        QuerydslPredicateExecutor<Voluntario> {
}
