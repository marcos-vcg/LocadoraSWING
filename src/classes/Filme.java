package classes;

import java.io.Serializable;

import javax.swing.Icon;

public class Filme implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String titulo;
	private Genero genero;
	private Integer copias;
	private String sinopse;
	private String duracao;
	private String lancamento;
	private Icon imagem;
	private Categoria categoria;
	private Integer id;
	static int contador = 0;

	public Filme(String titulo, Genero genero, Integer copias) {
		super();
		this.titulo = titulo;
		this.genero = genero;
		this.copias = copias;
		this.id = contador++;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Integer getCopias() {
		return copias;
	}

	public void setCopias(Integer copias) {
		this.copias = copias;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public String getLancamento() {
		return lancamento;
	}

	public void setLancamento(String lancamento) {
		this.lancamento = lancamento;
	}

	public Icon getImagem() {
		return imagem;
	}

	public void setImagem(Icon imagem) {
		this.imagem = imagem;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public Integer getId() {
		return id;
	}
	
	public static void setContador(int cont) {
		contador = cont;
	}

}
