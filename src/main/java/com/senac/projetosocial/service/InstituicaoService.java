package com.senac.projetosocial.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.exceptions.BusinessException;
import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.model.*;
import com.senac.projetosocial.repository.InstituicaoPrefeituraRepository;
import com.senac.projetosocial.repository.InstituicaoRepository;
import com.senac.projetosocial.repository.UsuarioRepository;
import com.senac.projetosocial.representation.InstituicaoRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InstituicaoService {
    private InstituicaoRepository instituicaoRepository;
    private InstituicaoPrefeituraRepository instituicaoPrefeituraRepository;
    private PerfilPermissaoService perfilPermissaoService;
    private UsuarioRepository usuarioRepository;

    public Instituicao cadastrarInstituicao(InstituicaoRepresentation.CriarOuAtualizar criarOuAtualizar, Usuario usuario){
        // this.validaInstituicao(criarOuAtualizar);
        PerfilPermissao perfilPermissao = this.perfilPermissaoService.buscarPerfilPermissao(2L);
        usuario.setPerfilPermissao(perfilPermissao);

        this.usuarioRepository.save(usuario);

        return this.instituicaoRepository.save(Instituicao.builder()
                .nome(criarOuAtualizar.getNome())
                .descricao((criarOuAtualizar.getDescricao()))
                .cnpj(criarOuAtualizar.getCnpj())
                .agencia(criarOuAtualizar.getAgencia())
                .conta(criarOuAtualizar.getConta())
                .email(criarOuAtualizar.getEmail())
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
                .orElseThrow(() -> new NotFoundException("Instituicao não encontrada para este usuário."));
    }


    public Boolean existeInstitituicaoByUsuario(Long idUsuario){
        BooleanExpression filtro = QInstituicao.instituicao.usuario().id.eq(idUsuario)
                .and(QInstituicao.instituicao.status.eq(StatusEnum.ATIVO));

        return this.instituicaoRepository.exists(filtro);
    }

    public Instituicao atualizarInstituicao(Long id, InstituicaoRepresentation.CriarOuAtualizar criarOuAtualizar, Usuario usuario){
        this.validaInstituicao(criarOuAtualizar);

        Instituicao instituicaoAntiga = this.buscarInstituicao(id);
        Instituicao instituicaoNova = instituicaoAntiga.toBuilder()
                .nome(criarOuAtualizar.getNome())
                .descricao((criarOuAtualizar.getDescricao()))
                .cnpj(criarOuAtualizar.getCnpj())
                .agencia(criarOuAtualizar.getAgencia())
                .conta(criarOuAtualizar.getConta())
                .email(criarOuAtualizar.getEmail())
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

        return this.instituicaoRepository.save(instituicaoNova);
    }

    public void apagarInstituicao(Long id){
        Instituicao instituicao = this.buscarInstituicao(id);
        instituicao.setStatus(StatusEnum.INATIVO);
        this.instituicaoRepository.save(instituicao);
    }

    public void validaInstituicao(InstituicaoRepresentation.CriarOuAtualizar criarOuAtualizar){

        BooleanExpression filtro = QInstituicaoPrefeitura.instituicaoPrefeitura.cnpj.eq(criarOuAtualizar.getCnpj());

        InstituicaoPrefeitura instituicaoPrefeitura = this.instituicaoPrefeituraRepository.findOne(filtro)
                .orElseThrow(() -> new NotFoundException("Cnpj da instituição não encontrado no cadastro da prefeitura!"));

        if(!criarOuAtualizar.getNome().equals(instituicaoPrefeitura.getNome()))
            throw new BusinessException("Nome da instituição diferente do cadastrado da prefeitura");

        if(!criarOuAtualizar.getCnpj().equals(instituicaoPrefeitura.getCnpj()))
            throw new BusinessException("Cnpj da instituicao diferente do cadastrado na prefeitura");

        if(!criarOuAtualizar.getEmail().equals(instituicaoPrefeitura.getEmail()))
            throw new BusinessException("Email da instituicao diferente do cadastrado na prefeitura");

        if(!criarOuAtualizar.getTelefone().equals(instituicaoPrefeitura.getTelefone()))
            throw new BusinessException("Telefone da instituicao diferente do cadastrado na prefeitura");

        if(!criarOuAtualizar.getAgencia().equals(instituicaoPrefeitura.getAgencia()))
            throw new BusinessException("Agência da instituicao diferente do cadastrado na prefeitura");

        if(!criarOuAtualizar.getConta().equals(instituicaoPrefeitura.getConta()))
            throw new BusinessException("Conta da instituicao diferente do cadastrado na prefeitura");

        if(!criarOuAtualizar.getLogradouro().equals(instituicaoPrefeitura.getLogradouro()))
            throw new BusinessException("Logradouro da instituicao diferente do cadastrado na prefeitura");

        if(!criarOuAtualizar.getBairro().equals(instituicaoPrefeitura.getBairro()))
            throw new BusinessException("Bairro da instituicao diferente do cadastrado na prefeitura");

        if(!criarOuAtualizar.getCep().equals(instituicaoPrefeitura.getCep()))
            throw new BusinessException("Cep da instituicao diferente do cadastrado na prefeitura");

        if(!criarOuAtualizar.getPais().equals(instituicaoPrefeitura.getPais()))
            throw new BusinessException("País da instituicao diferente do cadastrado na prefeitura");

        if(!criarOuAtualizar.getEstado().equals(instituicaoPrefeitura.getEstado()))
            throw new BusinessException("Estado da instituicao diferente do cadastrado na prefeitura");

        if(!criarOuAtualizar.getCidade().equals(instituicaoPrefeitura.getCidade()))
            throw new BusinessException("Cidade da instituicao diferente do cadastrado na prefeitura");

        if(criarOuAtualizar.getNumero() != instituicaoPrefeitura.getNumero())
            throw new BusinessException("Numero da instituicao diferente do cadastrado na prefeitura");
    }
}
