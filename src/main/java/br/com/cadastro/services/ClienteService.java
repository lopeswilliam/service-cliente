package br.com.cadastro.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cadastro.entity.Cliente;
import br.com.cadastro.entity.Endereco;
import br.com.cadastro.models.ClienteRequest;
import br.com.cadastro.models.EnderecoRequest;
import br.com.cadastro.repositories.CadastroRepository;
import br.com.cadastro.repositories.EnderecoRepository;

@Service
public class ClienteService {

	@Autowired
	private CadastroRepository cadastroRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	private static final Logger logger = LogManager.getLogger(ClienteService.class);

	/**
	 * @param clienteRequest
	 * @return
	 */
	public ResponseEntity<Cliente> inclusao(ClienteRequest clienteRequest) {
		logger.info("Iniciando a Chamada da Inclusao do Cliente");

		Cliente cliente = new Cliente();
		
		try {

			cliente = addCliente(clienteRequest, cliente);
			cliente = cadastroRepository.save(cliente);

			Endereco endereco = new Endereco();
			endereco = addEndereco(clienteRequest, cliente, endereco);
			endereco = enderecoRepository.save(endereco);

			cliente.setEndereco(endereco);

			if(cliente != null && cliente != null) {
				return ResponseEntity.ok().body(cliente);
			}

		} catch (Exception e) {
			logger.info("Ocorreu um Erro na Execuçao" + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cliente);
		}

		return ResponseEntity.notFound().build();

	}


	/**
	 * @param cpf
	 * @return
	 */
	public ResponseEntity<Cliente> consultar(String cpf ) {
		logger.info("Iniciando a Chamada da Consulta do Cliente");

		Cliente cliente = new Cliente();

		List<Cliente> findByCpf = cadastroRepository.findByCpf(cpf);
		if(findByCpf != null && findByCpf.size() > 0 ) {
			for (Cliente cli : findByCpf) {
				cliente = cli;
				Optional<Endereco> findById = enderecoRepository.findById(cliente.getId());
				cliente.setEndereco(findById.orElse(null));
				return ResponseEntity.ok().body(cliente);
			}
		}
		return ResponseEntity.notFound().build();

	}

	/**
	 * @param id
	 * @return
	 */
	public ResponseEntity<String> deletar(String cpf ){
		logger.info("Iniciando a Chamada da Exclusao do Cliente");

		List<Cliente> findByCpf = cadastroRepository.findByCpf(cpf);
		if(findByCpf != null) {

			if(findByCpf != null && findByCpf.size() > 0 ) {
				for (Cliente cli : findByCpf) {
					cadastroRepository.delete(cli);
				}
			}

			String numeroCpf = findByCpf.get(0).getCpf();
			return ResponseEntity.ok().body("Cliente Excluido com sucesso ".concat(numeroCpf));
		}

		return ResponseEntity.notFound().build();
	}

	/**
	 * @param clienteRequest
	 * @return
	 */
	public ResponseEntity<Cliente> alterar(ClienteRequest clienteRequest) {
		logger.info("Iniciando a Chamada da Alteração do Cliente");

		Cliente cliente = new Cliente();
		Endereco endereco = new Endereco();

		try {
			List<Cliente> findByCpf = cadastroRepository.findByCpf(clienteRequest.getCpf());
			if(findByCpf != null && findByCpf.size() > 0 ) {
				for (Cliente client : findByCpf) {
					cliente = atualizarCliente(clienteRequest, client);
					cliente = cadastroRepository.save(cliente);
				}
			} else {
				return ResponseEntity.notFound().build();
			}

		} catch (Exception e) {
			logger.info("Ocorreu um erro atualizarCliente" + e.getCause());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cliente);
		}

		try {

			Optional<Endereco> findById = Optional.ofNullable(enderecoRepository.findByClienteId(cliente.getId()));
			if( findById.isPresent()) {
				endereco = addEndereco(clienteRequest, cliente, findById.get());
			} else {
				return ResponseEntity.notFound().build();
			}

			Endereco inserirEndereco = enderecoRepository.saveAndFlush(endereco);

			cliente.setEndereco(inserirEndereco);

			if(cliente != null && inserirEndereco != null) {
				return ResponseEntity.ok().body(cliente);
			}

		} catch (Exception e) {
			logger.error("Ocorreu um erro addEndereco" + e.getCause());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cliente);
		}

		return ResponseEntity.notFound().build();
	}


	/**
	 * @param clienteRequest
	 * @param cliente
	 * @return
	 */
	private Cliente addCliente(ClienteRequest clienteRequest, Cliente cliente) {
		cliente.setNome(clienteRequest.getNome());
		cliente.setEmail(clienteRequest.getEmail());
		cliente.setIdade(clienteRequest.getIdade());
		cliente.setSenha("q1234");
		cliente.setCpf(clienteRequest.getCpf());
		cliente.setRg(clienteRequest.getRg());

		return cliente;
	}

	/**
	 * @param clienteRequest
	 * @param cliente
	 * @return
	 */
	private Cliente atualizarCliente(ClienteRequest clienteRequest, Cliente cliente) {
		cliente.setNome(clienteRequest.getNome());
		cliente.setEmail(clienteRequest.getEmail());
		cliente.setIdade(clienteRequest.getIdade());
		cliente.setSenha(cliente.getSenha());
		cliente.setCpf(clienteRequest.getCpf());
		cliente.setRg(clienteRequest.getRg());

		return cliente;
	}


	/**
	 * @param clienteRequest
	 * @param inserir
	 * @return
	 */
	private Endereco addEndereco(ClienteRequest clienteRequest, Cliente inserir , Endereco endereco) {

		endereco.setClienteId(inserir.getId());
		endereco.setDepositoid(Long.valueOf(0));
		endereco.setFornecedorid(Long.valueOf(0));
		endereco.setRua(clienteRequest.getEndereco().getRua());
		endereco.setNumero(clienteRequest.getEndereco().getNumero());
		endereco.setComplemento(clienteRequest.getEndereco().getComplemento());
		endereco.setBairro(clienteRequest.getEndereco().getBairro());
		endereco.setCep(clienteRequest.getEndereco().getCep());
		endereco.setCidade(clienteRequest.getEndereco().getCidade());
		endereco.setEstado(clienteRequest.getEndereco().getEstado());
		return endereco;
	}

	public List<ClienteRequest> getClientes() {

		List<ClienteRequest> lists = new ArrayList<>();

		List<Cliente> cliente = new ArrayList<>();
		cliente =  this.cadastroRepository.findAll();

		for (Cliente listCliente : cliente) {
			ClienteRequest clienteDto = new ClienteRequest();
			clienteDto.setNome(listCliente.getNome());
			clienteDto.setIdade(listCliente.getIdade());
			clienteDto.setEmail(listCliente.getEmail());
			clienteDto.setCpf(listCliente.getCpf());
			clienteDto.setRg(listCliente.getRg());
			
			Optional<Endereco> findById = Optional.of(enderecoRepository.findByClienteId(listCliente.getId()));

			if(findById.isPresent()) {
				EnderecoRequest enderecoRequest = new EnderecoRequest();
				enderecoRequest.setId(findById.get().getId());
				enderecoRequest.setRua(findById.get().getRua());
				enderecoRequest.setNumero(findById.get().getNumero());
				enderecoRequest.setComplemento(findById.get().getComplemento() );
				enderecoRequest.setBairro(findById.get().getBairro() );
				enderecoRequest.setCep(findById.get().getCep());
				enderecoRequest.setCidade(findById.get().getCidade());
				enderecoRequest.setEstado(findById.get().getEstado());
				clienteDto.setEndereco(enderecoRequest);
			}
			
			lists.add(clienteDto);
		}

		return lists;
	}

}
