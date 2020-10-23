package telas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import classes.Categoria;
import classes.Cliente;
import classes.Dependente;
import classes.Filme;
import classes.Genero;
import classes.Locacao;


class Main {

	JMenu menuCadastros, submenuCadastros, menuLocacao;
	JMenuItem i1, i2, i3, i4, i5, i6, i7;
	ArrayList<Genero> generos;
	ArrayList<Categoria> categorias;
	ArrayList<Filme> filmes;
	ArrayList<Cliente> clientes;

	
	Main() {

		//generos = new ArrayList<>(Arrays.asList (new String[]{"", "Ação", "Aventura", "Comédia", "Drama", "Romance", "Suspense", "Terror", "Musical", "Retrô", "Infantil"}));
		generos = new ArrayList<Genero>();
		
		generos.add(new Genero(""));
		generos.add(new Genero("Ação"));
		generos.add(new Genero("Aventura"));
		generos.add(new Genero("Comédia"));
		generos.add(new Genero("Drama"));
		generos.add(new Genero("Romance"));
		generos.add(new Genero("Suspense"));
		generos.add(new Genero("Terror"));
		generos.add(new Genero("Musical"));
		generos.add(new Genero("Retrô"));
		generos.add(new Genero("Infantil"));
		
		
		//categorias = new ArrayList<String>(Arrays.asList (new String[]{"", "Lançamento", "Padrão", "Antigo"}));
		categorias = new ArrayList<Categoria>();
		categorias.add(new Categoria("", ""));
		categorias.add(new Categoria("Lançamento", "10,00"));
		categorias.add(new Categoria("Atual", "7,50"));
		categorias.add(new Categoria("Intermediário", "5,00"));
		categorias.add(new Categoria("Antigo", "2,50"));
		
		
		filmes = new ArrayList<Filme>();
		clientes = new ArrayList<Cliente>();
		
		
		// Deserializa => Carrega dos arquivos 
		try {
			
			generos = (ArrayList<Genero>) deserializar("C:\\Users\\RAVDev\\Documents\\GitHub\\rav_tec_marcos\\Locadora\\src\\arquivos\\generos");
			int contGenero = 0;
			for(Genero g: generos) { if(g.getId() > contGenero) contGenero = g.getId();	}
			Genero.setContador(contGenero+1);
			
			categorias = (ArrayList<Categoria>) deserializar("C:\\Users\\RAVDev\\Documents\\GitHub\\rav_tec_marcos\\Locadora\\src\\arquivos\\categorias");
			int contCategoria = 0;
			for(Categoria c: categorias) { if(c.getId() > contCategoria) contCategoria = c.getId();	}
			Categoria.setContador(contCategoria+1);
			
			filmes = (ArrayList<Filme>) deserializar("C:\\Users\\RAVDev\\Documents\\GitHub\\rav_tec_marcos\\Locadora\\src\\arquivos\\filmes");
			int contFilme = 0;
			for(Filme f: filmes) { if(f.getId() > contFilme) contFilme = f.getId();	}
			Filme.setContador(contFilme+1);
			
			clientes = (ArrayList<Cliente>) deserializar("C:\\Users\\RAVDev\\Documents\\GitHub\\rav_tec_marcos\\Locadora\\src\\arquivos\\clientes");
			int contCliente = 0;
			for(Cliente c: clientes) { if(c.getId() > contCliente) contCliente = c.getId();	}
			Cliente.setContador(contCliente+1);
			
			int contLocacao = 0;
			for(Cliente c: clientes) { for(Locacao l: c.getLocacoes()) { if(l.getId() > contLocacao) contLocacao = l.getId(); }	}
			Locacao.setContador(contLocacao+1);
			
			int contDependente = 0;
			for(Cliente c: clientes) { for(Dependente d: c.getDependentes()) { if(d.getId() > contDependente) contDependente = d.getId(); }	}
			Dependente.setContador(contDependente+1);
			
			
		} catch (java.io.FileNotFoundException ex){	
		} catch (Exception ex) {
            System.err.println("Falha ao deserializar! - " + ex.toString());
        }
		
		
		JFrame f = new JFrame("Locadora");
		JMenuBar mb = new JMenuBar();
		menuCadastros = new JMenu("Cadastros");
		menuCadastros.setMnemonic('C');
		menuLocacao = new JMenu("Locação");
		menuLocacao.setMnemonic('L');
		//submenuCadastros = new JMenu("Sub Menu");
		
		
		i1 = new JMenuItem("Gênero");
		JDesktopPane jdpGenero = new JDesktopPane();
		i1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneroCadastro generoFrame = new GeneroCadastro(generos);
				generoFrame.setVisible(true);
				jdpGenero.add(generoFrame);
				f.setContentPane(jdpGenero);
			}
		});

		
		i2 = new JMenuItem("Categoria");
		final JDesktopPane jdpCategoria = new JDesktopPane();
		i2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CategoriaCadastro categoriaFrame = new CategoriaCadastro(categorias);
				jdpCategoria.add(categoriaFrame);				
				categoriaFrame.setVisible(true);
				f.setContentPane(jdpCategoria);
			}
		});
		
		
		i3 = new JMenuItem("Filme");
		final JDesktopPane jdpFilme = new JDesktopPane();
		i3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilmeCadastro filmeFrame = new FilmeCadastro(filmes, generos, categorias);
				jdpFilme.add(filmeFrame);
				filmeFrame.setVisible(true);
				f.setContentPane(jdpFilme);
			}
		});
		
		
		i4 = new JMenuItem("Cliente");
		final JDesktopPane jdpCliente = new JDesktopPane();
		i4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClienteCadastro clienteFrame = new ClienteCadastro(clientes);
				
				jdpCliente.add(clienteFrame);
				clienteFrame.setVisible(true);
				f.setContentPane(jdpCliente);
			}
		});
		
		
		i5 = new JMenuItem("Item 5");
		i6 = new JMenuItem("Item 6");
		
		
		menuCadastros.add(i1);
		menuCadastros.add(i2);
		menuCadastros.add(i3);
		menuCadastros.add(i4);
		//menuCadastros.add(submenuCadastros);
		//submenuCadastros.add(i5);
		//submenuCadastros.add(i6);
		mb.add(menuCadastros);
		
		
		i7 = new JMenuItem("Nova/Consulta");
		final JDesktopPane jdpNova = new JDesktopPane();
		i7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocacaoCadastro novaFrame = new LocacaoCadastro(filmes, generos, categorias, clientes);
				
				jdpNova.add(novaFrame);
				novaFrame.setVisible(true);
				f.setContentPane(jdpNova);
			}
		});
		
		
		menuLocacao.add(i7);
		mb.add(menuLocacao);
		
		
		f.setJMenuBar(mb);
		f.setSize(650, 480);
		f.setLayout(null);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		f.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				//serializa
				try {
					serializar("C:\\Users\\RAVDev\\Documents\\GitHub\\rav_tec_marcos\\Locadora\\src\\arquivos\\generos", generos);
					serializar("C:\\Users\\RAVDev\\Documents\\GitHub\\rav_tec_marcos\\Locadora\\src\\arquivos\\categorias", categorias);
					serializar("C:\\Users\\RAVDev\\Documents\\GitHub\\rav_tec_marcos\\Locadora\\src\\arquivos\\filmes", filmes);
					serializar("C:\\Users\\RAVDev\\Documents\\GitHub\\rav_tec_marcos\\Locadora\\src\\arquivos\\clientes", clientes); 
		        } catch (java.io.FileNotFoundException ex){
				} catch (Exception ex) {
		            System.err.println("Falha ao serializar! - " + ex.toString());
		        } finally {
		        	System.exit(0);
		        }

			}
		});
	}

	
	
	public static void main(String args[]) {
		new Main();
	}
	
	
	
    public void serializar(String path, Object obj) throws Exception {
        FileOutputStream outFile = new FileOutputStream(path);
		ObjectOutputStream s = new ObjectOutputStream(outFile);
		s.writeObject(obj);
		s.close();
	}


    public Object deserializar(String path) throws Exception {
        FileInputStream inFile = new FileInputStream(path);
        ObjectInputStream d = new ObjectInputStream(inFile);
        Object o = d.readObject();
        d.close();
        return o;
    }

	
}