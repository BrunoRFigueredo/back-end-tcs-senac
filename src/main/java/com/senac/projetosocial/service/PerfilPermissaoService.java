package com.senac.projetosocial.service;

import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.model.PerfilPermissao;
import com.senac.projetosocial.repository.PerfilPermissaoRepository;
import com.senac.projetosocial.representation.PerfilPermissaoRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PerfilPermissaoService {
    private final PerfilPermissaoRepository perfilPermissaoRepository;

    public PerfilPermissao salvarPerfilPermissao(PerfilPermissaoRepresentation.CriarOuAtualizar criarOuAtualizar) {
        PerfilPermissao perfilPermissao = PerfilPermissao.builder()
                .perfil(criarOuAtualizar.getPerfil())
                .permissao(criarOuAtualizar.getPermissao())
                .build();

        return this.perfilPermissaoRepository.save(perfilPermissao);
    }

    public PerfilPermissao atualizarPerfilPermissao(Long id, PerfilPermissaoRepresentation.CriarOuAtualizar criarOuAtualizar) {
        PerfilPermissao perfilPermissaoAntigo = this.buscarPerfilPermissao(id);
        PerfilPermissao perfilPermissaoAtualizado = perfilPermissaoAntigo.toBuilder()
                .perfil(criarOuAtualizar.getPerfil())
                .permissao(criarOuAtualizar.getPermissao())
                .build();

        return this.perfilPermissaoRepository.save(perfilPermissaoAtualizado);
    }

    public Iterable<PerfilPermissao> buscarPerfilPermissoes() {
        return this.perfilPermissaoRepository.findAll();
    }

    public PerfilPermissao buscarPerfilPermissao(Long id) {
        return this.perfilPermissaoRepository.findById(id).orElseThrow(() -> new NotFoundException("Perfil não encontrado."));
    }

    public void deletarPerfilPermissao(Long id) {
        this.buscarPerfilPermissao(id);
        this.perfilPermissaoRepository.deleteById(id);
    }

}
