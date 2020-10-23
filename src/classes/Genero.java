package classes;

import java.io.Serializable;

public class Genero implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private Integer id;
	static int contador = 0;
	
	public Genero(String nome) {
		super();
		this.nome = nome;
		this.id = contador++;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public Integer getId() {
		return id;
	}

	public static void setContador(int cont) {
		contador = cont;
	}
	
}