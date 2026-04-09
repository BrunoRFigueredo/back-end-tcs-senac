package com.senac.projetosocial.repository;

import com.senac.projetosocial.model.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ImagemRepository extends JpaRepository<Imagem, Long> {
}
