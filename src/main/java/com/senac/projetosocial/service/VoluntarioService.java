package com.senac.projetosocial.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.model.*;
import com.senac.projetosocial.repository.VoluntarioRepository;
import com.senac.projetosocial.representation.VoluntarioRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VoluntarioService {
    private VoluntarioRepository voluntarioRepository;

    public Voluntario salvarVoluntario(VoluntarioRepresentation.CriarOuAtualizar criarOuAtualizar,
                                        Usuario usuario){
        Voluntario voluntario = Voluntario.builder()
                .nome(criarOuAtualizar.getNome())
                .biografia(criarOuAtualizar.getBiografia())
                .cpf(criarOuAtualizar.getCpf())
                .telefone(criarOuAtualizar.getTelefone())
                .pais(criarOuAtualizar.getPais())
                .estado(criarOuAtualizar.getEstado())
                .cidade(criarOuAtualizar.getCidade())
                .bairro(criarOuAtualizar.getBairro())
                .logradouro(criarOuAtualizar.getLogradouro())
                .numero(criarOuAtualizar.getNumero())
                .cep(criarOuAtualizar.getCep())
                .status(StatusEnum.ATIVO)
                .usuario(usuario)
                .build();

        return this.voluntarioRepository.save(voluntario);
    }

    public Voluntario buscarVoluntario(Long id){
        BooleanExpression filtro = QVoluntario.voluntario.status.eq(StatusEnum.ATIVO)
                .and(QVoluntario.voluntario.id.eq(id));

        return this.voluntarioRepository.findOne(filtro)
                .orElseThrow(() -> new NotFoundException("Voluntário " + id + " nao encontrado!"));
    }

    public Voluntario atualizarVoluntario(Long id, VoluntarioRepresentation.CriarOuAtualizar criarOuAtualizar, Usuario usuario){
        Voluntario antigoVoluntario = this.buscarVoluntario(id);

        Voluntario voluntarioAtualizado = antigoVoluntario.toBuilder()
                .nome(criarOuAtualizar.getNome())
                .biografia(criarOuAtualizar.getBiografia())
                .cpf(criarOuAtualizar.getCpf())
                .telefone(criarOuAtualizar.getTelefone())
                .pais(criarOuAtualizar.getPais())
                .estado(criarOuAtualizar.getEstado())
                .cidade(criarOuAtualizar.getCidade())
                .bairro(criarOuAtualizar.getBairro())
                .logradouro(criarOuAtualizar.getLogradouro())
                .numero(criarOuAtualizar.getNumero())
                .cep(criarOuAtualizar.getCep())
                .status(StatusEnum.ATIVO)
                .usuario(usuario)
                .build();

        return this.voluntarioRepository.save(voluntarioAtualizado);
    }

    public Voluntario buscarVoluntarioUsuario(Long idUsuario){

        BooleanExpression filtro = QVoluntario.voluntario.usuario().id.eq(idUsuario)
                .and(QVoluntario.voluntario.status.eq(StatusEnum.ATIVO));

        return this.voluntarioRepository.findOne(filtro)
                .orElseThrow(() -> new NotFoundException("Instituicao não encontrada para este usuário."));
    }


    public void apagarVoluntario(Long id){
        Voluntario voluntario = this.buscarVoluntario(id);
        voluntario.setStatus(StatusEnum.INATIVO);
        this.voluntarioRepository.save(voluntario);
    }
}
