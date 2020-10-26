package model;

import java.io.Serializable;

public class Categoria implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private String preco;

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPreco() {
		return preco;
	}

	public void setPreco(String preco) {
		this.preco = preco;
	}
	
	
	
}