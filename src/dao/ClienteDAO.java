package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Cliente;

public class ClienteDAO {
	private DataSource dataSource;
	private String tabela;
	//private DependenteDAO dependenteDao;

	
	public ClienteDAO(DataSource datasource){
		this.dataSource = datasource;
		this.tabela = "cliente";
		//dependenteDao = new DependenteDAO(dataSource);
	}
	
	
	public Cliente busca(Integer id){
		try {
			String SQL = "SELECT * FROM " + tabela + " WHERE id = '" + id + "';";
			java.sql.PreparedStatement ps = dataSource.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			Cliente cliente = new Cliente();
			
			while(rs.next()) {
				
				cliente.setId(rs.getInt("id"));
				cliente.setNome(rs.getString("nome"));
				cliente.setCpf(rs.getString("cpf"));
				cliente.setTelefone(rs.getString("telefone"));
				cliente.setEmail(rs.getString("email"));
				cliente.setNascimento(rs.getString("nascimento"));
				cliente.setEndereco(rs.getString("endereco"));
				cliente.setImagem(rs.getBytes("imagem"));
				
				//cliente.setDependentes(dependenteDao.readAll(rs.getInt("id")));
				
				System.out.println("Cliente lido");
				
			}
			ps.close();
			return cliente;
			
		} catch(SQLException ex) {
			System.err.println("Erro ao Recuperar filme " + ex.getMessage());
		} catch(Exception ex) {
			System.err.println("Erro Geral " + ex.getMessage());
		}
		return null;
	}
	
	
	public ArrayList<Cliente> readAll(){
		try {
			String SQL = "SELECT * FROM " + tabela + "  ORDER BY nome;";
			java.sql.PreparedStatement ps = dataSource.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			ArrayList<Cliente> lista = new ArrayList<Cliente>();
			
			while(rs.next()) {
				Cliente cliente = new Cliente();
				
				cliente.setId(rs.getInt("id"));
				cliente.setNome(rs.getString("nome"));
				cliente.setCpf(rs.getString("cpf"));
				cliente.setTelefone(rs.getString("telefone"));
				cliente.setEmail(rs.getString("email"));
				cliente.setNascimento(rs.getString("nascimento"));
				cliente.setEndereco(rs.getString("endereco"));
				cliente.setImagem(rs.getBytes("imagem"));
				
				System.out.println("Filme lido");
				
				
				if(cliente.getId() != null) {
					lista.add(cliente);
				}
				
			}
			ps.close();
			return lista;
			
		} catch(SQLException ex) {
			System.err.println("Erro ao Recuperar cliente " + ex.getMessage());
		} catch(Exception ex) {
			System.err.println("Erro Geral " + ex.getMessage());
		}
		return null;
	}
	
	
	
	public Boolean inserir(Cliente c) {
		Boolean retorno = false;
		try {
			
			String SQL = "INSERT INTO " + tabela + " VALUES (DEFAULT,  '" + c.getNome() + "', '" + c.getCpf() + "', '" + c.getTelefone() + "', '" + c.getEmail() + "', '" + c.getNascimento() + "', '" + c.getEndereco() + "', '" + c.getImagem() + "');";
			java.sql.PreparedStatement ps = dataSource.getConnection().prepareStatement(SQL);

			ps.executeUpdate(SQL);						// Usado para fazer qualquer alteração. Não tem nenhum retorno
			ps.close();
			retorno = true;
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return retorno;
	}
	
	
	/*
	public Boolean inserir(Filme f) {
		
		Boolean retorno = false;
		
		try {
			
			String SQL = "INSERT INTO filme (titulo, genero, copias, sinopse, duracao, lancamento, imagem , categoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			java.sql.PreparedStatement ps = dataSource.getConnection().prepareStatement(SQL);
			
			//ps.setString(1, tabela);
			ps.setString(1, f.getTitulo());
			ps.setInt(2, f.getGenero().getId());
			ps.setInt(3, f.getCopias());
			ps.setString(4, f.getSinopse());
			ps.setString(5, f.getDuracao());
			ps.setString(6, f.getLancamento());
			ps.setBytes(7, f.getImagem());
			ps.setInt(8, f.getCategoria().getId());
			
			ps.executeUpdate(SQL);						// Usado para fazer qualquer alteração. Não tem nenhum retorno
			ps.close();
			
			retorno = true;
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		
		return retorno;
	}*/
	
	
	public void editar(Cliente c) {
		try {
			
			String SQL = "UPDATE " + tabela + " SET nome = '" + c.getNome() + "', cpf = '" + c.getCpf() + "', telefone = '" + c.getTelefone() + "', email = '" + c.getEmail() + "', nascimento = '" + c.getNascimento() + "', endereco = '" + c.getEndereco() + "', imagem = '" + c.getImagem() + "' WHERE id = " + c.getId() + ";" ;			// id é int, não colocar aspassimples
			java.sql.PreparedStatement ps = dataSource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	public void apagar(Integer id) {
		try {
			
			String SQL = "DELETE FROM " + tabela + " WHERE id = " + id + ";" ;			// id é int, não colocar aspassimples
			java.sql.PreparedStatement ps = dataSource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);												// Usado para fazer qualquer alteração. Não tem nenhum retorno
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	
	
	
}
