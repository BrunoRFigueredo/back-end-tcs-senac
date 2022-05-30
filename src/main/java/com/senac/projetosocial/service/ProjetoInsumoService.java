package com.senac.projetosocial.service;

import com.querydsl.core.types.Predicate;
import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.model.Insumo;
import com.senac.projetosocial.model.Projeto;
import com.senac.projetosocial.model.ProjetoInsumo;
import com.senac.projetosocial.repository.ProjetoInsumoRepository;
import com.senac.projetosocial.representation.ProjetoInsumoRepresentation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjetoInsumoService {
    private final ProjetoInsumoRepository projetoInsumoRepository;

    public ProjetoInsumo salvarProjetoInsumo(Insumo insumo, Projeto projeto) {
        ProjetoInsumo projetoInsumo = ProjetoInsumo.builder()
                .insumo(insumo)
                .projeto(projeto)
                .build();

        return this.projetoInsumoRepository.save(projetoInsumo);
    }

    public ProjetoInsumo atualizarProjetoInsumo(Long id, Insumo insumo, Projeto projeto) {
        ProjetoInsumo projetoInsumoAntigo = this.buscarProjetoInsumo(id);
        ProjetoInsumo projetoInsumoAtualizado = projetoInsumoAntigo.toBuilder()
                .insumo(insumo)
                .projeto(projeto)
                .build();

        return this.projetoInsumoRepository.save(projetoInsumoAtualizado);
    }



    public ProjetoInsumo buscarProjetoInsumo(Long id) {
        return this.projetoInsumoRepository.findById(id).orElseThrow(() -> new NotFoundException("Insumo não encontrado."));
    }

    public void deletarProjetoInsumo(Long id) {
        this.buscarProjetoInsumo(id);
        this.projetoInsumoRepository.deleteById(id);
    }

}
