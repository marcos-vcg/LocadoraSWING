package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Categoria;

public class CategoriaDAO {
	private DataSource datasource;
	
	public CategoriaDAO(DataSource datasource){
		this.datasource = datasource;
	}
	
	public ArrayList<Categoria> readAll(){
		try {
			String SQL = "SELECT * FROM categoria ORDER BY nome";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			ArrayList<Categoria> lista = new ArrayList<Categoria>();
			
			while(rs.next()) {
				Categoria categ = new Categoria();
				categ.setId(rs.getInt("id"));
				categ.setNome(rs.getString("nome"));
				categ.setPreco(rs.getString("preco"));
		
				lista.add(categ);
			}
			ps.close();
			return lista;
			
		} catch(SQLException ex) {
			System.err.println("Erro ao Recuperar " + ex.getMessage());
		} catch(Exception ex) {
			System.err.println("Erro Geral " + ex.getMessage());
		}
		return null;
	}
	
	public void inserirCategoria(String nome, String preco) {
		try {
			
			String SQL = "INSERT INTO categoria (nome, preco) VALUES ('" + nome + "', '" + preco + "');";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);						// Usado para fazer qualquer altera��o. N�o tem nenhum retorno
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	public void editarCategoria(Integer id, String nome, String preco) {
		try {
			
			String SQL = "UPDATE categoria SET nome = '" + nome + "', preco = '" + preco + "' WHERE id = " + id + ";" ;			// id � int, n�o colocar aspassimples
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	public void apagarCategoria(Integer id) {
		try {
			
			String SQL = "DELETE FROM categoria WHERE id = " + id + ";" ;			// id � int, n�o colocar aspassimples
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);												// Usado para fazer qualquer altera��o. N�o tem nenhum retorno
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
