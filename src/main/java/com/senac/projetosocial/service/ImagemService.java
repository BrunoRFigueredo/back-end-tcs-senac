package com.senac.projetosocial.service;

import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.model.Imagem;
import com.senac.projetosocial.repository.ImagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImagemService {
    private ImagemRepository imagemRepository;

    public Imagem cadastrarImagem(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));


        Imagem imagem = new Imagem(null, file.getBytes(), fileName, file.getContentType());
        return imagemRepository.save(imagem);
    }

    public Imagem buscarImagem(Long id) {

        return this.imagemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Imagem " + id + " não encontrada"));
    }

    public void apagarImagem(Long id) {
        Optional<Imagem> imagem = this.imagemRepository.findById(id);
        imagem.ifPresent(value -> this.imagemRepository.delete(value));
    }
}
