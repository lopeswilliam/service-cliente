package br.com.cadastro.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadastro.entity.Cliente;
import br.com.cadastro.models.ClienteRequest;
import br.com.cadastro.services.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Cliente")
@RestController
@CrossOrigin
@RequestMapping("/cliente/v1")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	private static final Logger logger = LogManager.getLogger(ClienteController.class);

	@ApiOperation(value = "Inclui Cliente")
	@PostMapping(path = "/incluir" , produces = {"application/json"})
	public ResponseEntity<Cliente> inclusao(@Valid @RequestBody ClienteRequest clienteRequest, @RequestHeader("authorization") String authorization) {
		logger.info("Iniciando a Inclusao do Cliente");
		return clienteService.inclusao(clienteRequest );
	}

	@ApiOperation(value = "Consultar Cliente")
	@GetMapping(path = "/consultar" , produces = {"application/json"})
	public ResponseEntity<Cliente> consultar(@RequestParam("cpf") String cpf, @RequestHeader("authorization") String authorization) {
		logger.info("Iniciando a Consulta do Cliente");
		return clienteService.consultar(cpf);
	}

	@ApiOperation(value = "Deletar Cliente")
	@DeleteMapping(path = "/deletar" , produces = {"application/json"})
	public ResponseEntity<String> deletar(@RequestParam("cpf") String cpf, @RequestHeader("authorization") String authorization) {
		logger.info("Iniciando a Exclusao do Cliente");
		return clienteService.deletar(cpf);
	}
	
	
	@ApiOperation(value = "Atualizar Cliente")
	@PutMapping(path = "/atualizar" , produces = {"application/json"})
	public ResponseEntity<Cliente> alterar(@RequestBody ClienteRequest clienteRequest,@RequestHeader("authorization") String authorization) {
		logger.info("Iniciando a Alteração do Cliente");
		return clienteService.alterar(clienteRequest );
	}
	
	@ApiOperation(value = "Listar clientes")
	@GetMapping(value = "/listarclientes")
	public ResponseEntity<List<ClienteRequest>> listar(@RequestHeader("authorization") String authorization){
		System.out.println("Inicio do get cadastro de Clientes");

		List<ClienteRequest>  cliente = new ArrayList<ClienteRequest>();
		cliente = clienteService.getClientes();
		return ResponseEntity.status(HttpStatus.OK).body(cliente);

	}
}