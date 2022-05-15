package com.senac.projetosocial.repository;

import com.senac.projetosocial.model.Imagem;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

@Service
public interface ImagemRepository extends PagingAndSortingRepository<Imagem, Long> {
}
