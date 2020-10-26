package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Categoria;
import model.Filme;
import model.Genero;

import javax.swing.Icon;

public class FilmeDAO {
	private DataSource datasource;
	private String tabela;
	private GeneroDAO generoDao;
	private CategoriaDAO categoriaDao;
	
	public FilmeDAO(DataSource datasource){
		this.datasource = datasource;
		this.tabela = "filme";
	}
	
	public ArrayList<Filme> readAll(){
		try {
			String SQL = "SELECT * FROM " + tabela + "  ORDER BY titulo";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			ArrayList<Filme> lista = new ArrayList<Filme>();
			
			while(rs.next()) {
				Filme filme = new Filme();
				filme.setId(rs.getInt("id"));
				filme.setTitulo(rs.getString("titulo"));
				filme.setGenero(generoDao.busca(rs.getInt("genero")));
				filme.setCopias(rs.getInt("copias"));
				filme.setSinopse(rs.getString("sinopse"));
				filme.setDuracao(rs.getString("duracao"));
				filme.setLancamento(rs.getString("lancamento"));
				filme.setImagem((Icon)rs.getBinaryStream("imagem"));
				//filme.setImagem((Icon)rs.getObject("imagem"));
				filme.setCategoria(categoriaDao.busca(rs.getInt("categoria")));
				
		
				lista.add(filme);
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
	
	
	
	public void inserir(Filme f) {
		
		try {
			
			String SQL = "INSERT INTO " + tabela + " VALUES (DEFAULT,  '" + f.getTitulo() + "', '" + f.getGenero().getId() + "', '" + f.getCopias() + "', '" + f.getSinopse() + "', '" + f.getDuracao() + "', '" + f.getLancamento() + "', '" + f.getImagem() + "', '" + f.getCategoria().getId() + "');";
			java.sql.PreparedStatement ps = datasource.getConnection().prepareStatement(SQL);
			ps.executeUpdate(SQL);						// Usado para fazer qualquer alteração. Não tem nenhum retorno
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	public void editar(Integer id, String titulo, Genero genero, Integer copias, String sinopse, String duracao, String lancamento, Icon imagem, Categoria categoria) {
		try {
			
			String SQL = "UPDATE " + tabela + " SET titulo = '" + titulo + "', genero = '" + genero + "', copias = '" + copias + "', sinopse = '" + sinopse + "', duracao = '" + duracao + "', lancamento = '" + lancamento + "', imagem = '" + imagem + "', categoria = '" + categoria + "' WHERE id = " + id + ";" ;			// id é int, não colocar aspassimples
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
