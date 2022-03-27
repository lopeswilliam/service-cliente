package br.com.cadastro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cadastro.entity.Cliente;


@Repository
public interface CadastroRepository extends JpaRepository<Cliente, Long>{

	List<Cliente> findByCpf(String cpf);

	Cliente deleteByCpf(String cpf);
}
