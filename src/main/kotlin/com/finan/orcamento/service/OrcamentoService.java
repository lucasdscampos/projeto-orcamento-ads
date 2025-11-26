package com.finan.orcamento.service;

import com.finan.orcamento.model.OrcamentoModel;
import com.finan.orcamento.repositories.OrcamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrcamentoService {

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    public List<OrcamentoModel> buscarCadastro(){
        return orcamentoRepository.findAll();
    }

    public OrcamentoModel buscaId(Long id){
        Optional<OrcamentoModel> obj = orcamentoRepository.findById(id);
        if (obj.isPresent()) {
            return obj.get();
        } else {
            throw new RuntimeException("Orçamento não encontrado");
        }
    }

    public OrcamentoModel cadastrarOrcamento(OrcamentoModel orcamentoModel){
        orcamentoModel.calcularIcms();
        return orcamentoRepository.save(orcamentoModel);
    }

    public OrcamentoModel atualizaCadastro(OrcamentoModel orcamentoModel, Long id){
        OrcamentoModel newOrcamentoModel = buscaId(id);
        newOrcamentoModel.setValorOrcamento(orcamentoModel.getValorOrcamento());
        newOrcamentoModel.setValorICMS(orcamentoModel.getValorICMS());
        return orcamentoRepository.save(newOrcamentoModel);
    }
    
    public void deletaOrcamento(Long id){
        orcamentoRepository.deleteById(id);
    }

    public List<OrcamentoModel> buscarRelatorio(Long clienteId, LocalDate dataInicio, LocalDate dataFim) {
        if (clienteId != null && clienteId > 0) {
            return orcamentoRepository.findByClienteIdAndDataCadastroBetween(clienteId, dataInicio, dataFim);
        } else {
            return orcamentoRepository.findByDataCadastroBetween(dataInicio, dataFim);
        }
    }
}