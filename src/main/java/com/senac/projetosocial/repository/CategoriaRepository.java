package com.senac.projetosocial.repository;

import com.querydsl.core.types.Predicate;
import com.senac.projetosocial.model.Categoria;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoriaRepository extends JpaRepository<Categoria, Long>,
        QuerydslPredicateExecutor<Categoria> {

    List<Categoria> findAll(Predicate filtro);
}
