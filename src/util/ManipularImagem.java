package util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;


// Classe serve para manipular as imagens, redimensiona, transforma para BufferedImage e para array de Bytes para inserçao em BD.
public class ManipularImagem {

	// Funcao pega uma imagem, redimensina e retorna um Buffered Image
	// Informo o diretório da imagem, a altura a redefinir e a largura a redefinir.
	// Caso não queira redefinir o tamanho só retirar o IF/ELSE inteiro.
	public static BufferedImage setImagemDimensao(String caminhoImg, Integer imgLargura, Integer imgAltura) {
		Double novaImgLargura = null;
		Double novaImgAltura = null;
		Double imgProporcao = null;
		Graphics2D g2d = null;
		BufferedImage imagem = null, novaImagem = null;
		
		try {
			// Obtem Imagem a ser redimensionada
			imagem = ImageIO.read(new File(caminhoImg));
		} catch(IOException ex) {
			System.out.println(ex.getMessage());
			Logger.getLogger(ManipularImagem.class.getName()).log(Level.SEVERE, null, ex);
		}
			
		// Obtem a Largura da Imagem
		novaImgLargura = (double) imagem.getWidth();
		
		// Obtem a altura da imagem
		novaImgAltura = (double) imagem.getHeight();
		
		
		// Verifica se a altura ou largura recebida sao maiores ou menores do que a
		if (novaImgLargura >= imgLargura) {
			imgProporcao = (novaImgAltura / novaImgLargura);  	// Calcula a proporcao
			novaImgLargura = (double) imgLargura;
			
			// Altura deve <= ao parametro imgAltura e proporcional a largura
			novaImgAltura = (novaImgLargura * imgProporcao);
			
			// se altura for maior que o parametro imgAltura, diminui-se
			// forma que a altura seja igual ao parametro imgAltura e proporcional a imgLargura
			while (novaImgAltura > imgAltura) {
				novaImgLargura = (double) (--imgLargura);
				novaImgAltura = (novaImgLargura * imgProporcao);
			}
			
		} else if (novaImgAltura >= imgAltura) {
			imgProporcao = (novaImgLargura / novaImgAltura);
			novaImgAltura = (double) imgAltura;
			
			// Se a largura for maior que o parametro imgLargura, diminui-se de
			// forma que a largura seja igual ao parametro imgLargura
			while (novaImgLargura > imgLargura) {
				novaImgAltura = (double) (--imgAltura);
				novaImgLargura = (novaImgAltura * imgProporcao);
			}	
		}
		
		novaImagem = new BufferedImage(novaImgLargura.intValue(), novaImgAltura.intValue(), BufferedImage.TYPE_INT_RGB);
		g2d = novaImagem.createGraphics();
		g2d.drawImage(imagem, 0, 0, novaImgLargura.intValue(), novaImgAltura.intValue(), null);
	
		return novaImagem;
	}
	
	
	// Pega uma imagem de um path do PC e retorna um BufferedImage.
	public static BufferedImage getBufferedImage(String caminhoImg) {
		Double novaImgLargura = null;
		Double novaImgAltura = null;
		Graphics2D g2d = null;
		BufferedImage imagem = null, novaImagem = null;
		
		try {
			// Obtem Imagem a ser copiada
			imagem = ImageIO.read(new File(caminhoImg));
		} catch(IOException ex) {
			System.out.println(ex.getMessage());
			Logger.getLogger(ManipularImagem.class.getName()).log(Level.SEVERE, null, ex);
		}
			
		// Obtem a Largura da Imagem
		novaImgLargura = (double) imagem.getWidth();
		
		// Obtem a altura da imagem
		novaImgAltura = (double) imagem.getHeight();
		
		
		novaImagem = new BufferedImage(novaImgLargura.intValue(), novaImgAltura.intValue(), BufferedImage.TYPE_INT_RGB);
		g2d = novaImagem.createGraphics();
		g2d.drawImage(imagem, 0, 0, novaImgLargura.intValue(), novaImgAltura.intValue(), null);
	
		return novaImagem;
	}
	
	
	
	// função retorna bufferedImage de Path de File 	(Estou usando a getBufferedImage)	
	public static BufferedImage fileToBuffered (String pathImg) {
		
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File(pathImg));	
		} catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
		return img;
	}
	
	
	
	
	
	// Pega um BufferedImage e converte para um array de Bytes
	public static byte[] bufferedToBytes(BufferedImage image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "JPEG", baos);
			//baos.flush();
		} catch (IOException ex) {
			
		}
		
		InputStream is = new ByteArrayInputStream(baos.toByteArray());  // não está sendo usado pra nada
	
		return baos.toByteArray();
	}
		
		
		
	
	// Recebe a imagem como array de bytes converte para BufferedImage
	public static BufferedImage byteToBuffered(byte[] byteImg) {
		BufferedImage buffImg = null;
		
		// verifica se tem a imagem, caso tenha converte para BufferedImage através do InputStream que é o formato reconhecido
		if (byteImg != null) {
			InputStream input = new ByteArrayInputStream(byteImg);
			try {
				buffImg = ImageIO.read(input);
				
			} catch (IOException ex) {}
		} 
		
		return buffImg;
	}
		
	
	
	
	
	
	
	// Conversor de Image para Bytes
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
