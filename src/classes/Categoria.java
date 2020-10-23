package classes;

import java.io.Serializable;

public class Categoria implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private String preco;
	private Integer id;
	static Integer contador = 0;
	
	public Categoria(String nome, String preco) {
		super();
		this.nome = nome;
		this.preco = preco;
		this.id = contador++;
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
	
	public Integer getId() {
		return id;
	}

	public static void setContador(int cont) {
		contador = cont;
	}
}