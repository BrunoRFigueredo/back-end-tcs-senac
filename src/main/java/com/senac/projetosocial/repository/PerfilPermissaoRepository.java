package com.senac.projetosocial.repository;

import com.senac.projetosocial.model.PerfilPermissao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilPermissaoRepository extends CrudRepository<PerfilPermissao, Long> {

}
