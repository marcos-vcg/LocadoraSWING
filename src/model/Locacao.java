package model;

import java.io.Serializable;
import java.util.Date;

public class Locacao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Filme filme;
	private Date locacao;
	private Date devolucao;
	

	

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
}
