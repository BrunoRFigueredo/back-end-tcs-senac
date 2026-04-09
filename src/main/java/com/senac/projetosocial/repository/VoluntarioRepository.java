package com.senac.projetosocial.repository;

import com.senac.projetosocial.model.Voluntario;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoluntarioRepository extends JpaRepository<Voluntario, Long>,
        QuerydslPredicateExecutor<Voluntario> {
}
