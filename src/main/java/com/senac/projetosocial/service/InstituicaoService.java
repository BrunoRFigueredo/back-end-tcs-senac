package com.senac.projetosocial.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.model.Instituicao;
import com.senac.projetosocial.model.QInstituicao;
import com.senac.projetosocial.model.Usuario;
import com.senac.projetosocial.repository.InstituicaoRepository;
import com.senac.projetosocial.representation.InstituicaoRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InstituicaoService {
    private InstituicaoRepository instituicaoRepository;
    private UsuarioService usuarioService;

    public Instituicao cadastrarInstituicao(InstituicaoRepresentation.CriarOuAtualizar criarOuAtualizar, Usuario usuario){
        return this.instituicaoRepository.save(Instituicao.builder()
                .nome(criarOuAtualizar.getNome())
                .descricao((criarOuAtualizar.getDescricao()))
                .cnpj(criarOuAtualizar.getCnpj())
                .pix(criarOuAtualizar.getPix())
                .email(criarOuAtualizar.getEmail())
                .telefone(criarOuAtualizar.getTelefone())
                .pais(criarOuAtualizar.getPais())
                .estado(criarOuAtualizar.getEstado())
                .cidade(criarOuAtualizar.getCidade())
                .logradouro(criarOuAtualizar.getLogradouro())
                .numero(criarOuAtualizar.getNumero())
                .cep(criarOuAtualizar.getCep())
                .status(StatusEnum.ATIVO)
                .usuario(usuario)
                .build());
    }

    public Instituicao buscarInstituicao(Long id){
        BooleanExpression filtro = QInstituicao.instituicao.id.eq(id)
                .and(QInstituicao.instituicao.status.eq(StatusEnum.ATIVO));

        return this.instituicaoRepository.findOne(filtro)
                .orElseThrow(() -> new NotFoundException("Instituição " + id + " não encontrada"));
    }

    public Instituicao buscarInstituicaoUsuario(Long idUsuario){

        BooleanExpression filtro = QInstituicao.instituicao.usuario().id.eq(idUsuario)
                .and(QInstituicao.instituicao.status.eq(StatusEnum.ATIVO));

        return this.instituicaoRepository.findOne(filtro)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
    }

    public Instituicao atualizarInstituicao(Long id, InstituicaoRepresentation.CriarOuAtualizar criarOuAtualizar, Usuario usuario){
        Instituicao instituicaoAntiga = this.buscarInstituicao(id);
        Instituicao instituicaoNova = instituicaoAntiga.toBuilder()
                .nome(criarOuAtualizar.getNome())
                .descricao((criarOuAtualizar.getDescricao()))
                .cnpj(criarOuAtualizar.getCnpj())
                .pix(criarOuAtualizar.getPix())
                .email(criarOuAtualizar.getEmail())
                .telefone(criarOuAtualizar.getTelefone())
                .pais(criarOuAtualizar.getPais())
                .estado(criarOuAtualizar.getEstado())
                .cidade(criarOuAtualizar.getCidade())
                .logradouro(criarOuAtualizar.getLogradouro())
                .numero(criarOuAtualizar.getNumero())
                .cep(criarOuAtualizar.getCep())
                .status(StatusEnum.ATIVO)
                .usuario(usuario)
                .build();

        return this.instituicaoRepository.save(instituicaoNova);
    }

    public void apagarInstituicao(Long id){
        Instituicao instituicao = this.buscarInstituicao(id);
        instituicao.setStatus(StatusEnum.INATIVO);
        this.instituicaoRepository.save(instituicao);
    }
}
