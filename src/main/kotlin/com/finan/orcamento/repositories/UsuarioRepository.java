package com.finan.orcamento.repositories;

import com.finan.orcamento.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Import necessário

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    // ADICIONE ESTA LINHA PARA A FUNCIONALIDADE DE PESQUISA
    List<UsuarioModel> findByNomeUsuarioContainingIgnoreCase(String nome);

}