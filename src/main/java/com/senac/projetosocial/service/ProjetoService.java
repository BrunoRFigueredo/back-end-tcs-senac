package com.senac.projetosocial.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.model.Instituicao;
import com.senac.projetosocial.model.Projeto;
import com.senac.projetosocial.model.QProjeto;
import com.senac.projetosocial.repository.ProjetoRepository;
import com.senac.projetosocial.representation.ProjetoRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class ProjetoService {
    private ProjetoRepository projetoRepository;

    public Projeto salvarProjeto(ProjetoRepresentation.CriarOuAtualizar criarOuAtualizar,
                                Instituicao instituicao){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String texto = criarOuAtualizar.getData_inicio().format(formatter);
        LocalDate dateParsed = LocalDate.parse(texto, formatter);

        Projeto projeto = Projeto.builder()
                .nome(criarOuAtualizar.getNome())
                .descricao(criarOuAtualizar.getDescricao())
                .data_inicio(dateParsed)
                .data_final(criarOuAtualizar.getData_final())
                .status(StatusEnum.ATIVO)
                .instituicao(instituicao)
                .build();

        return this.projetoRepository.save(projeto);
    }

    public Projeto buscarProjeto(Long id){
        BooleanExpression filtro = QProjeto.projeto.id.eq(id)
                .and(QProjeto.projeto.status.eq(StatusEnum.ATIVO));

        return this.projetoRepository.findOne(filtro)
                .orElseThrow(() -> new NotFoundException("Projeto " + id + " nao encontrado"));
    }

    public Projeto atualizarProjeto(Long id,
                                    ProjetoRepresentation.CriarOuAtualizar criarOuAtualizar,
                                    Instituicao instituicao){
        Projeto projetoAntigo = this.buscarProjeto(id);

        Projeto projetoNovo = projetoAntigo.toBuilder()
                .nome(criarOuAtualizar.getNome())
                .descricao(criarOuAtualizar.getDescricao())
                .data_inicio(criarOuAtualizar.getData_inicio())
                .data_final(criarOuAtualizar.getData_final())
                .instituicao(instituicao)
                .status(StatusEnum.ATIVO)
                .build();

        return this.projetoRepository.save(projetoNovo);
    }

    public void apagarProjeto(Long id){
        Projeto projeto = this.buscarProjeto(id);
        projeto.setStatus(StatusEnum.INATIVO);
        this.projetoRepository.save(projeto);
    }

}
