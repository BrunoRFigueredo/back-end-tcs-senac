package com.senac.projetosocial.controller;

import com.senac.projetosocial.model.Imagem;
import com.senac.projetosocial.service.ImagemService;
import com.senac.projetosocial.util.UploadFileResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
@RequestMapping("/imagem/")
@AllArgsConstructor
@CrossOrigin("*")
public class ImagemController {
    private ImagemService imagemService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Imagem imagem = imagemService.cadastrarImagem(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .toUriString();

        return UploadFileResponse.builder()
                .idImagem(imagem.getId())
                .fileName(imagem.getFileName())
                .fileType(file.getContentType())
                .fileDownloadUri(fileDownloadUri)
                .size(file.getSize()).build();
    }


    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        Imagem dbFile = imagemService.buscarImagem(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getFile()));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        imagemService.apagarImagem(id);
    }


}
