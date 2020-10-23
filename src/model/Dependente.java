package model;

import java.io.Serializable;

public class Dependente implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private Grau grau;
	private Integer id;
	static int contador = 0;
	
	public Dependente(String nome, Grau grau) {
		super();
		this.nome = nome;
		this.grau = grau;
		this.id = contador++;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Grau getGrau() {
		return grau;
	}

	public void setGrau(Grau grau) {
		this.grau = grau;
	}
	
	public Integer getId() {
		return id;
	}
	
	public static void setContador(int cont) {
		contador = cont;
	}
}
