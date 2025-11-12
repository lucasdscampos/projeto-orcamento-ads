package com.finan.orcamento.service;

import com.finan.orcamento.model.ClienteModel;
import com.finan.orcamento.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteModel> buscarTodos() {
        return clienteRepository.findAll();
    }

    public ClienteModel buscarPorId(Long id) {
        Optional<ClienteModel> optional = clienteRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Cliente não encontrado!");
        }
    }

    public ClienteModel cadastrar(ClienteModel clienteModel) {
        return clienteRepository.save(clienteModel);
    }

    public ClienteModel atualizar(Long id, ClienteModel clienteModel) {
        ClienteModel cliente = buscarPorId(id);
        cliente.setNome(clienteModel.getNome());
        cliente.setCpf(clienteModel.getCpf());
        return clienteRepository.save(cliente);
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    public List<ClienteModel> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<ClienteModel> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpfContaining(cpf);
    }
}