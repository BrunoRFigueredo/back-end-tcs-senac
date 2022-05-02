package com.senac.projetosocial.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.senac.projetosocial.enums.StatusEnum;
import com.senac.projetosocial.exceptions.NotFoundException;
import com.senac.projetosocial.model.QServico;
import com.senac.projetosocial.model.Servico;
import com.senac.projetosocial.repository.ServicoRepository;
import com.senac.projetosocial.representation.ServicoRepresentation;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServicoService {
    private ServicoRepository servicoRepository;
    public Servico salvarServico(ServicoRepresentation.CriarOuAtualizar criarOuAtualizar){
        return this.servicoRepository.save(Servico.builder()
                .nome(criarOuAtualizar.getNome())
                .descricao(criarOuAtualizar.getDescricao())
                .status(StatusEnum.ATIVO)
                .build());
    }
    public Servico buscarServico(Long id){
        BooleanExpression filtro = QServico.servico.status.eq(StatusEnum.ATIVO)
                .and(QServico.servico.id.eq(id));

        return this.servicoRepository.findOne(filtro)
                .orElseThrow(()-> new NotFoundException("Serviço " + id + " não encontrado."));
    }
    public Servico atualizarServico(Long id, ServicoRepresentation.CriarOuAtualizar criarOuAtualizar){
        Servico antigoServico = this.buscarServico(id);

        Servico novoServico = antigoServico.toBuilder()
                .nome(criarOuAtualizar.getNome())
                .descricao(criarOuAtualizar.getDescricao())
                .build();

        return this.servicoRepository.save(novoServico);
    }
    public void deletarServico(Long id){
        Servico servico = this.buscarServico(id);
        servico.setStatus(StatusEnum.INATIVO);
        this.servicoRepository.save(servico);
    }

}
