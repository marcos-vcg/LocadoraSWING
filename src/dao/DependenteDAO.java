package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Dependente;
import model.Grau;

public class DependenteDAO {
	private DataSource datasource;
	private ClienteDAO clienteDao; 
	private String tabela;
	
	public DependenteDAO(DataSource datasource){
		this.datasource = datasource;
		this.clienteDao = new ClienteDAO(datasource);
		this.tabela = "dependente";
	}
	
	public Dependente busca(int id) {
		try {
			String SQL = "SELECT * FROM " + tabela + " WHERE id = '" + id + "'";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			Dependente dependente = new Dependente();
			while(rs.next()) {
				dependente.setId(rs.getInt("id"));
				dependente.setNome(rs.getString("nome"));
				dependente.setGrau(Grau.valueOf(rs.getString("grau")));
				dependente.setTitular(clienteDao.busca(rs.getInt("titular")));
			}
			
			ps.close();
			return dependente;
			
		} catch(SQLException ex) {
			System.err.println("Erro ao Recuperar dependente " + ex.getMessage());
		} catch(Exception ex) {
			System.err.println("Erro Geral DependenteDAO" + ex.getMessage());
		}
		return null;
	}
	
	public ArrayList<Dependente> readAll(int id){
		try {
			String SQL = "SELECT * FROM " + tabela + " WHERE titular = '" + id + "' ORDER BY nome";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			ArrayList<Dependente> lista = new ArrayList<Dependente>();
			
			while(rs.next()) {
				Dependente dependente = new Dependente();
				dependente.setId(rs.getInt("id"));
				dependente.setNome(rs.getString("nome"));
				dependente.setGrau(Grau.valueOf(rs.getString("grau")));
				dependente.setTitular(clienteDao.busca(rs.getInt("titular")));
		
				lista.add(dependente);
			}
			ps.close();
			return lista;
			
		} catch(SQLException ex) {
			System.err.println("Erro ao Recuperar Dependente" + ex.getMessage());
		} catch(Exception ex) {
			System.err.println("Erro Geral DependenteDAO" + ex.getMessage());
		}
		return null;
	}
	
	public ArrayList<Dependente> readAll(){
		try {
			String SQL = "SELECT * FROM " + tabela + " ORDER BY nome";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			ArrayList<Dependente> lista = new ArrayList<Dependente>();
			
			while(rs.next()) {
				Dependente dependente = new Dependente();
				dependente.setId(rs.getInt("id"));
				dependente.setNome(rs.getString("nome"));
				dependente.setGrau(Grau.valueOf(rs.getString("grau")));
				dependente.setTitular(clienteDao.busca(rs.getInt("titular")));
		
				lista.add(dependente);
			}
			ps.close();
			return lista;
			
		} catch(SQLException ex) {
			System.err.println("Erro ao Recuperar Dependente" + ex.getMessage());
		} catch(Exception ex) {
			System.err.println("Erro Geral DependenteDAO" + ex.getMessage());
		}
		return null;
	}
	
	public void inserir(Dependente d) {
		try {
			
			String SQL = "INSERT INTO " + tabela + " (nome, grau, titular) VALUES ('" + d.getNome() + "', '" + d.getGrau() + "', '" + d.getTitular().getId() + "');";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);						// Usado para fazer qualquer alteração. Não tem nenhum retorno
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro Geral DependenteDAO: " + e.getMessage());
		}
	}
	
	public void editar(Dependente d) {
		try {
			
			String SQL = "UPDATE " + tabela + " SET nome = '" + d.getNome() + "', grau = '" + d.getGrau() + "', titular = '" + d.getTitular().getId() + "' WHERE id = " + d.getId() + ";" ;			// id é int, não colocar aspassimples
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro Geral DependenteDAO: " + e.getMessage());
		}
	}
	
	public void apagar(Integer id) {
		try {
			
			String SQL = "DELETE FROM " + tabela + " WHERE id = " + id + ";" ;			// id é int, não colocar aspassimples
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);												// Usado para fazer qualquer alteração. Não tem nenhum retorno
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro Geral DependenteDAO: " + e.getMessage());
		}
	}
}
