package br.com.cadastro.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cadastro.entity.Endereco;


@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
	
	Optional<Endereco> findById(Long id);
	
	Endereco findByClienteId(Long clienteId);
	
	
}
