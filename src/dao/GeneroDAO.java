package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Genero;

public class GeneroDAO {
	private DataSource datasource;
	private String tabela;
	
	public GeneroDAO(DataSource datasource){
		this.datasource = datasource;
		this.tabela = "genero";
	}
	
	public ArrayList<Genero> readAll(){
		try {
			String SQL = "SELECT * FROM " + tabela + " ORDER BY nome";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			ArrayList<Genero> lista = new ArrayList<Genero>();
			
			while(rs.next()) {
				Genero gen = new Genero();
				gen.setId(rs.getInt("id"));
				gen.setNome(rs.getString("nome"));
		
				lista.add(gen);
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
	
	public void inserir(String nome) {
		try {
			
			String SQL = "INSERT INTO " + tabela + " (nome) VALUES ('" + nome + "');";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);						// Usado para fazer qualquer alteração. Não tem nenhum retorno
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	public void editar(Integer id, String nome) {
		try {
			
			String SQL = "UPDATE " + tabela + " SET nome = '" + nome + "' WHERE id = " + id + ";" ;			// id é int, não colocar aspassimples
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	public void apagar(Integer id) {
		try {
			
			String SQL = "DELETE FROM " + tabela + " WHERE id = " + id + ";" ;			// id é int, não colocar aspassimples
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);												// Usado para fazer qualquer alteração. Não tem nenhum retorno
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
