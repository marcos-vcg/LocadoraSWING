package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Dependente;
import model.Grau;
import model.Locacao;

public class LocacaoDAO {
	private DataSource datasource;
	private ClienteDAO clienteDao; 
	private FilmeDAO filmeDao;
	private String tabela;
	
	public LocacaoDAO(DataSource datasource){
		this.datasource = datasource;
		this.clienteDao = new ClienteDAO(datasource);
		this.filmeDao = new FilmeDAO(datasource);
		this.tabela = "locacao";
	}
	
	public Locacao busca(int id) {
		try {
			String SQL = "SELECT * FROM " + tabela + " WHERE id = '" + id + "'";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			Locacao locacao = new Locacao();
			
			while(rs.next()) {
				
				locacao.setId(rs.getInt("id"));
				locacao.setFilme(filmeDao.busca(rs.getInt("filme")));
				locacao.setLocacao(rs.getDate("aluguel"));
				locacao.setDevolucao(rs.getDate("devolucao"));
		
			}
			
			ps.close();
			return locacao;
			
		} catch(SQLException ex) {
			System.err.println("Erro ao Recuperar locacao: " + ex.getMessage());
		} catch(Exception ex) {
			System.err.println("Erro Geral LocacaoDAO: " + ex.getMessage());
		}
		return null;
	}
	
	public ArrayList<Locacao> readAllOf(int id){
		try {
			String SQL = "SELECT * FROM " + tabela + " WHERE titular = '" + id + "' ORDER BY aluguel";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			ArrayList<Locacao> lista = new ArrayList<Locacao>();
			
			while(rs.next()) {
				Locacao locacao = new Locacao();
				locacao.setId(rs.getInt("id"));
				locacao.setFilme(filmeDao.busca(rs.getInt("filme")));
				locacao.setLocacao(rs.getDate("aluguel"));
				locacao.setDevolucao(rs.getDate("devolucao"));
		
				lista.add(locacao);
			}
			ps.close();
			return lista;
			
		} catch(SQLException ex) {
			System.err.println("Erro ao Recuperar Locacao: " + ex.getMessage());
		} catch(Exception ex) {
			System.err.println("Erro Geral LocacaoDAO" + ex.getMessage());
		}
		return null;
	}
	
	public ArrayList<Locacao> readAll(){
		try {
			String SQL = "SELECT * FROM " + tabela + " ORDER BY aluguel";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			ArrayList<Locacao> lista = new ArrayList<Locacao>();
			
			while(rs.next()) {
				Locacao locacao = new Locacao();
				locacao.setId(rs.getInt("id"));
				locacao.setFilme(filmeDao.busca(rs.getInt("filme")));
				locacao.setLocacao(rs.getDate("aluguel"));
				locacao.setDevolucao(rs.getDate("devolucao"));
		
				lista.add(locacao);
			}
			ps.close();
			return lista;
			
		} catch(SQLException ex) {
			System.err.println("Erro ao Recuperar Locacao: " + ex.getMessage());
		} catch(Exception ex) {
			System.err.println("Erro Geral LocacaoDAO" + ex.getMessage());
		}
		return null;
	}
	
	public void inserir(Locacao l) {
		try {
			
			String SQL = "INSERT INTO " + tabela + " (filme, locacao) VALUES ('" + l.getFilme().getId() + "', '" + l.getLocacao() + "');";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);						// Usado para fazer qualquer altera��o. N�o tem nenhum retorno
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro Geral LocacaoDAO: " + e.getMessage());
		}
	}
	
	public void editar(Locacao l) {
		try {
			
			String SQL = "UPDATE " + tabela + " SET devolucao = '" + l.getDevolucao() + "' WHERE id = " + l.getId() + ";" ;			// id � int, n�o colocar aspassimples
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro Geral LocacaoDAO: " + e.getMessage());
		}
	}
	
	public void apagar(Integer id) {
		try {
			
			String SQL = "DELETE FROM " + tabela + " WHERE id = " + id + ";" ;			// id � int, n�o colocar aspassimples
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);												// Usado para fazer qualquer altera��o. N�o tem nenhum retorno
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro Geral LocacaoDAO: " + e.getMessage());
		}
	}
}