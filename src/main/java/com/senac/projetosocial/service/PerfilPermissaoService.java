package com.senac.projetosocial.service;

import com.senac.projetosocial.exeptions.NotFoundException;
import com.senac.projetosocial.model.PerfilPermissao;
import com.senac.projetosocial.repository.PerfilPermissaoRepository;
import com.senac.projetosocial.representation.PerfilPermissaoRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PerfilPermissaoService {

    private final PerfilPermissaoRepository perfilPermissaoRepository;

    public PerfilPermissao salvar(PerfilPermissaoRepresentation.CreateOrUpdate createOrUpdate) {
        PerfilPermissao perfilPermissao = PerfilPermissao.builder()
                .perfil(createOrUpdate.getPerfil())
                .permissao(createOrUpdate.getPermissao())
                .build();

        return this.perfilPermissaoRepository.save(perfilPermissao);
    }


    public PerfilPermissao update(Long id, PerfilPermissaoRepresentation.CreateOrUpdate createOrUpdate) {
        PerfilPermissao perfilPermissaoAntigo = this.getPerfilPermissao(id);
        PerfilPermissao perfilPermissaoAtualizado = perfilPermissaoAntigo.toBuilder()
                .perfil(createOrUpdate.getPerfil())
                .permissao(createOrUpdate.getPermissao())
                .build();

        return this.perfilPermissaoRepository.save(perfilPermissaoAtualizado);
    }

    public Iterable<PerfilPermissao> getAllPerfilPermissao() {
        return this.perfilPermissaoRepository.findAll();
    }

    public PerfilPermissao getPerfilPermissao(Long id) {
        return this.perfilPermissaoRepository.findById(id).orElseThrow(() -> new NotFoundException("Perfil não encontrado."));
    }

    public void deletePerfilPermissao(Long id) {
        this.perfilPermissaoRepository.deleteById(id);
    }

}
