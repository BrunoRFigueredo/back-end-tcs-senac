package com.senac.projetosocial.util;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Builder
public class Paginacao {
    private int tamanhoPagina;
    private int paginaSelecionada;
    private Boolean proximaPagina;
    private Long totalRegistros;
    private List<?> conteudo;
}
