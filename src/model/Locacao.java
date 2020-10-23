package model;

import java.io.Serializable;
import java.util.Date;

public class Locacao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Filme filme;
	private Date locacao;
	private Date devolucao;
	private Integer id;
	static int contador = 0;
	
	public Locacao(Filme filme, Date locacao) {
		super();
		this.filme = filme;
		this.locacao = locacao;
		this.id = contador++;
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}
	
	
	public Date getLocacao() {
		return locacao;
	}

	public void setLocacao(Date locacao) {
		this.locacao = locacao;
	}
	
	public Date getDevolucao() {
		return devolucao;
	}

	public void setDevolucao(Date devolucao) {
		this.devolucao = devolucao;
	}
	
	public Integer getId() {
		return id;
	}
	
	public static void setContador(int cont) {
		contador = cont;
	}
}
