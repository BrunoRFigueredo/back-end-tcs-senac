package com.senac.projetosocial.repository;

import com.senac.projetosocial.model.InstituicaoPrefeitura;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface InstituicaoPrefeituraRepository extends CrudRepository<InstituicaoPrefeitura, Long>,
        QuerydslPredicateExecutor<InstituicaoPrefeitura> {

}
