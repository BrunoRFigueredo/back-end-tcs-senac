package com.senac.projetosocial.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UploadFileResponse {
    private Long idImagem;
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
