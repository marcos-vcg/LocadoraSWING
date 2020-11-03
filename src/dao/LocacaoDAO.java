package dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Locacao;
import util.ManipularDatas;

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
				locacao.setCliente(clienteDao.busca(rs.getInt("cliente")));
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
	
	public ArrayList<Locacao> filmesDoCLiente(int id){
		try {
			String SQL = "SELECT * FROM " + tabela + " WHERE cliente = '" + id + "' ORDER BY aluguel";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			ArrayList<Locacao> lista = new ArrayList<Locacao>();
			
			while(rs.next()) {
				Locacao locacao = new Locacao();
				locacao.setId(rs.getInt("id"));
				locacao.setCliente(clienteDao.busca(rs.getInt("cliente")));
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
				locacao.setCliente(clienteDao.busca(rs.getInt("cliente")));
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
			
			String SQL = "INSERT INTO " + tabela + " (cliente, filme, aluguel) VALUES (" + l.getCliente().getId() + "," + l.getFilme().getId() + ", " + new Date(l.getLocacao().getTime()) + ");";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);						// Usado para fazer qualquer alteração. Não tem nenhum retorno
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro Geral LocacaoDAO: " + e.getMessage());
		}
	}
	
	public void nserir(Locacao l) {
		try {
			
			String SQL = "INSERT INTO " + tabela + " (cliente, filme, aluguel) VALUES (?,?,?)";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			
			ps.setInt(1, l.getCliente().getId());
			ps.setInt(2, l.getFilme().getId());
			ps.setDate(3, new Date(l.getLocacao().getTime()));
			
			ps.executeUpdate(SQL);						// Usado para fazer qualquer alteração. Não tem nenhum retorno
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro Geral LocacaoDAO: " + e.getMessage());
		}
	}
	
	public int quantosAlugados(int id){
		try {
			
			String SQL = "SELECT COUNT(*) FROM " + tabela + " WHERE filme = '" + id + "' AND devolucao = null ;";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			int quantidade;
			
			while(rs.next()) {
				quantidade = rs.getInt(1);
				return quantidade;
			}
			ps.close();
			
		} catch(SQLException ex) {
			System.err.println("Erro ao Recuperar Locacao: " + ex.getMessage());
		} catch(Exception ex) {
			System.err.println("Erro Geral LocacaoDAO" + ex.getMessage());
		}
		return 0;
	}
	
	
	public void editar(Locacao l) {
		try {
			
			String SQL = "UPDATE " + tabela + " SET devolucao = '" + l.getDevolucao() + "' WHERE id = " + l.getId() + ";" ;			// id é int, não colocar aspassimples
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro Geral LocacaoDAO: " + e.getMessage());
		}
	}
	
	public void apagar(Integer id) {
		try {
			
			String SQL = "DELETE FROM " + tabela + " WHERE id = " + id + ";" ;			// id é int, não colocar aspassimples
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);												// Usado para fazer qualquer alteração. Não tem nenhum retorno
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro Geral LocacaoDAO: " + e.getMessage());
		}
	}
}
