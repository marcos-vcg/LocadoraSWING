package model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


public class Locacao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Cliente cliente;
	private Filme filme;
	private Date locacao;
	private Date devolucao;
	

	
	
	public Locacao() {
		
	}
	
	public Locacao(Cliente cliente, Filme filme, Date locacao) {
		
		this.cliente = cliente;
		this.filme = filme;
		this.locacao = locacao;
	}

	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
