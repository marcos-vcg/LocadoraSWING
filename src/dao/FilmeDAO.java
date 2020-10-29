package dao;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Categoria;
import model.Filme;
import model.Genero;

import javax.imageio.ImageIO;
import javax.swing.Icon;

public class FilmeDAO {
	private DataSource dataSource;
	private String tabela;
	private GeneroDAO generoDao;
	private CategoriaDAO categoriaDao;
	
	public FilmeDAO(DataSource datasource){
		this.dataSource = datasource;
		this.tabela = "filme";
		generoDao = new GeneroDAO(dataSource);
		categoriaDao = new CategoriaDAO(dataSource);
	}
	
	
	public Filme busca(Integer id){
		try {
			String SQL = "SELECT * FROM " + tabela + " WHERE id = '" + id + "';";
			java.sql.PreparedStatement ps = dataSource.getConnection().prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			Filme filme = new Filme();
			
			while(rs.next()) {
				
				filme.setId(rs.getInt("id"));
				filme.setTitulo(rs.getString("titulo"));
				filme.setGenero(generoDao.busca(rs.getInt("genero")));
				filme.setCopias(rs.getInt("copias"));
				filme.setSinopse(rs.getString("sinopse"));
				filme.setDuracao(rs.getString("duracao"));
				filme.setLancamento(rs.getString("lancamento"));
				filme.setImagem(rs.getBytes("imagem"));
				filme.setCategoria(categoriaDao.busca(rs.getInt("categoria")));
				System.out.println("Filme lido");
				
			}
			ps.close();
			return filme;
			
		} catch(SQLException ex) {
			System.err.println("Erro ao Recuperar filme " + ex.getMessage());
		} catch(Exception ex) {
			System.err.println("Erro Geral " + ex.getMessage());
		}
		return null;
	}
	
	
	public ArrayList<Filme> readAll(){
		try {
			String SQL = "SELECT * FROM " + tabela + "  ORDER BY titulo;";
			java.sql.PreparedStatement ps = dataSource.getConnection().prepareStatement(SQL);
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
				filme.setImagem(rs.getBytes("imagem"));
				//filme.setImagem(rs.getBlob("imagem"));
				//filme.setImagem(rs.getBlob("imagem").getBinaryStream());
				//filme.setImagem((Icon)rs.getBinaryStream("imagem"));
				//filme.setImagem((Icon)rs.getObject("imagem"));
				filme.setCategoria(categoriaDao.busca(rs.getInt("categoria")));
				System.out.println("Filme lido");
				
				if(filme.getId() != null ) {
					lista.add(filme);
				}
				
			}
			ps.close();
			return lista;
			
		} catch(SQLException ex) {
			System.err.println("Erro ao Recuperar filme " + ex.getMessage());
		} catch(Exception ex) {
			System.err.println("Erro Geral " + ex.getMessage());
		}
		return null;
	}
	
	
	
	public void inserir(Filme f) {
		try {
			
			String SQL = "INSERT INTO " + tabela + " VALUES (DEFAULT,  '" + f.getTitulo() + "', '" + f.getGenero().getId() + "', '" + f.getCopias() + "', '" + f.getSinopse() + "', '" + f.getDuracao() + "', '" + f.getLancamento() + "', '" + f.getImagem() + "', '" + f.getCategoria().getId() + "');";
			java.sql.PreparedStatement ps = dataSource.getConnection().prepareStatement(SQL);

			ps.executeUpdate(SQL);						// Usado para fazer qualquer alteração. Não tem nenhum retorno
			ps.close();
			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	/*
	public Boolean inserir(Filme f) {
		
		Boolean retorno = false;
		
		try {
			
			String SQL = "INSERT INTO " + tabela + " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";
			java.sql.PreparedStatement ps = dataSource.getConnection().prepareStatement(SQL);
			
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
	
	
	public void editar(Filme f) {
		try {
			
			String SQL = "UPDATE " + tabela + " SET titulo = '" + f.getTitulo() + "', genero = '" + f.getGenero() + "', copias = '" + f.getCopias() + "', sinopse = '" + f.getSinopse() + "', duracao = '" + f.getDuracao() + "', lancamento = '" + f.getLancamento() + "', imagem = '" + f.getImagem() + "', categoria = '" + f.getCategoria() + "' WHERE id = " + f.getId() + ";" ;			// id é int, não colocar aspassimples
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
	
	
	// Conversor de Imagem para Bytes para mandar para o Banco de Dados
	public byte[] imageToByte(Image image) {	
		
		BufferedImage bi = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(image, 0, 0, null);
		bg.dispose();
		
		ByteArrayOutputStream buff = new ByteArrayOutputStream();		
	    try {  
	    	ImageIO.write(bi, "JPG", buff);  
	    } catch (IOException e) {  
	    	e.printStackTrace();  
	    }  
	    return buff.toByteArray();		
	}
	
	
	
	public static Image byteToImage(byte[] bytes) {
		if(bytes == null) {
			return null;
		}else {
			return Toolkit.getDefaultToolkit().createImage(bytes);
		}
	}
	
}
