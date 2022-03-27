package br.com.cadastro.models;

import java.io.Serializable;

public class ClienteRequest implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String idade;
	private String email;
	private String senha;
	private String cpf;
	private String rg;
	
	private EnderecoRequest endereco;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getIdade() {
		return idade;
	}
	public void setIdade(String idade) {
		this.idade = idade;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public EnderecoRequest getEndereco() {
		return endereco;
	}
	public void setEndereco(EnderecoRequest endereco) {
		this.endereco = endereco;
	}

	
	
		

}
