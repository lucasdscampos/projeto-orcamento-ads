package com.finan.orcamento.repositories;

import com.finan.orcamento.model.OrcamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrcamentoRepository extends JpaRepository<OrcamentoModel, Long> {

    List<OrcamentoModel> findByDataCadastroBetween(LocalDate dataInicio, LocalDate dataFim);

    List<OrcamentoModel> findByClienteIdAndDataCadastroBetween(Long clienteId, LocalDate dataInicio, LocalDate dataFim);
}