package classes;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.Icon;

public class Cliente implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private String cpf;
	private String telefone;
	private String email;
	private String nascimento;
	private String endereco;
	private Icon imagem;
	private ArrayList<Dependente> dependentes = new ArrayList<>();
	private ArrayList<Locacao> locacoes = new ArrayList<>();
	private Integer id;
	static int contador = 0;

	public Cliente(String nome, String cpf, String telefone) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.id = contador++;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNascimento() {
		return nascimento;
	}

	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Icon getImagem() {
		return imagem;
	}

	public void setImagem(Icon imagem) {
		this.imagem = imagem;
	}

	public ArrayList<Dependente> getDependentes() {
		return dependentes;
	}

	public void setDependentes(ArrayList<Dependente> dependentes) {
		this.dependentes = dependentes;
	}

	public ArrayList<Locacao> getLocacoes() {
		return locacoes;
	}

	public void setLocacoes(ArrayList<Locacao> locacoes) {
		this.locacoes = locacoes;
	}

	public Integer getId() {
		return id;
	}

	public static void setContador(int cont) {
		contador = cont;
	}
		
}