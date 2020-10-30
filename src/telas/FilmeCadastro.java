package telas;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dao.CategoriaDAO;
import dao.DataSource;
import dao.FilmeDAO;
import dao.GeneroDAO;
import model.Categoria;
import model.Filme;
import model.Genero;
import util.ManipularImagem;


@SuppressWarnings("serial")
public class FilmeCadastro extends JInternalFrame {

	static final int xPosition = 30, yPosition = 30;
	private final Action cancelar = new SwingAction_cancelar();
	private boolean edit = false;
	
	DataSource dataSource;
	FilmeDAO filmeDao;
	GeneroDAO generoDao;
	CategoriaDAO categoriaDao;
	ArrayList<Filme> cadFilme;	
	ArrayList<Genero> cadGenero;
	ArrayList<Categoria> cadCategoria;
	
	JTabbedPane abas;
	JPanel pnl_consulta, pnl_cadastro;
	JTextField txf_titulo_pesquisa, txf_genero_pesquisa, txf_lancamento_pesquisa, txf_titulo, txf_copias, txf_duracao, txf_lancamento;
	JButton btn_pesquisar, btn_editar, btn_excluir, btn_novo,  btn_cancelar, btn_cadastro, btn_upload;
	DefaultTableModel tbl_modelo;
	JTable tbl_filmes;
	JScrollPane scp_filmes, scrl_sinopse;
	JComboBox<String> cbx_genero, cbx_categoria;
	JTextArea txa_sinopse;
	
	JLabel lbl_mostrar_imagem;
	BufferedImage imagem;
	byte[] imagemByte;
	
		
	public FilmeCadastro() {
		super("Cadastro de Filmes", true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		setSize(570, 355);
		setLocation(xPosition, yPosition);
		setLayout(null);

		
		setarElementos ();
		adicionarListeners();
		preencherTabela();
		limparComponentes();	
		
	}
		
	
	
	
	private void setarElementos () {
		
		// Controlador do Acesso ao Banco de Dados
		dataSource = new DataSource();
		
		filmeDao = new FilmeDAO(dataSource);
		generoDao = new GeneroDAO(dataSource);
		categoriaDao = new CategoriaDAO(dataSource);
		
		
		this.cadFilme = filmeDao.readAll();
		this.cadGenero = generoDao.readAll();
		this.cadCategoria = categoriaDao.readAll();

		
		
		// Criar Abas
		abas = new JTabbedPane(JTabbedPane.TOP);
		add(abas).setBounds(10, 11, 540, 300);
		
		
		// Painéis das Abas
		pnl_consulta = new JPanel();
		pnl_consulta.setLayout(null);
		abas.addTab("Pesquisa", null, pnl_consulta, "Pesquisar Filmes");
		
		pnl_cadastro = new JPanel();
		pnl_cadastro.setLayout(null);
		abas.addTab("Cadastro", null, pnl_cadastro, "Cadastrar Filme");
		
		
		// Aba Consulta
		pnl_consulta.add(new JLabel("Titulo:")).setBounds(20, 11, 81, 14);
		txf_titulo_pesquisa = new JTextField(10);
		pnl_consulta.add(txf_titulo_pesquisa).setBounds(20, 29, 161, 20);
		
		pnl_consulta.add(new JLabel("Genero:")).setBounds(200, 11, 98, 14);
		txf_genero_pesquisa = new JTextField(10);
		pnl_consulta.add(txf_genero_pesquisa).setBounds(200, 28, 98, 22);

		pnl_consulta.add(new JLabel("Lançamento:")).setBounds(320, 11, 75, 14);
		txf_lancamento_pesquisa = new JTextField(10);
		pnl_consulta.add(txf_lancamento_pesquisa).setBounds(320, 29, 75, 20);
		
		btn_pesquisar = new JButton("Pesquisar");
		pnl_consulta.add(btn_pesquisar).setBounds(420, 29, 100, 20);
		
		
		// Criar Tabela de Dados
		tbl_modelo = new DefaultTableModel();
		tbl_filmes = new JTable(tbl_modelo);
		scp_filmes = new JScrollPane(tbl_filmes);
		pnl_consulta.add(scp_filmes).setBounds(20, 60, 500, 100);
		
		// Setar Colunas
		tbl_modelo.addColumn("ID");
		tbl_modelo.addColumn("Título");
		tbl_modelo.addColumn("Gênero");
		tbl_modelo.addColumn("Duração");
		tbl_modelo.addColumn("Lançamento");
		tbl_filmes.getColumnModel().getColumn(0).setPreferredWidth(20);
		tbl_filmes.getColumnModel().getColumn(1).setPreferredWidth(220);
		tbl_filmes.getColumnModel().getColumn(2).setPreferredWidth(60);
		tbl_filmes.getColumnModel().getColumn(3).setPreferredWidth(50);
		tbl_filmes.getColumnModel().getColumn(4).setPreferredWidth(70);
		
		
		// Botões de Ação
		btn_editar = new JButton("Editar");
		btn_editar.setEnabled(false);
		pnl_consulta.add(btn_editar).setBounds(230, 225, 80, 25);		
		
		btn_excluir = new JButton("Excluir");
		btn_excluir.setEnabled(false);
		pnl_consulta.add(btn_excluir).setBounds(330, 225, 80, 25);
		
		btn_novo = new JButton("Novo");
		btn_novo.setVisible(true);
		pnl_consulta.add(btn_novo).setBounds(430, 225, 80, 25);	
		
		
		// Aba Cadastro
		pnl_cadastro.add(new JLabel("*Titulo:")).setBounds(20, 11, 81, 14);
		txf_titulo = new JTextField(10);
		pnl_cadastro.add(txf_titulo).setBounds(20, 29, 200, 20);
		
		pnl_cadastro.add(new JLabel("*Genero:")).setBounds(250, 11, 98, 14);
		cbx_genero = new JComboBox<>();
		for(int i = 0; i < cadGenero.size(); i++) {
			cbx_genero.addItem(cadGenero.get(i).getNome());   }
		pnl_cadastro.add(cbx_genero).setBounds(250, 28, 100, 22);
			
		pnl_cadastro.add(new JLabel("*Cópias:")).setBounds(380, 11, 55, 14);
		txf_copias = new JTextField(10);
		pnl_cadastro.add(txf_copias).setBounds(380, 29, 55, 20);
		
		pnl_cadastro.add(new JLabel("Duração:")).setBounds(20, 65, 98, 14);
		txf_duracao = new JTextField(10);
		pnl_cadastro.add(txf_duracao).setBounds(20, 90, 90, 20);
		
		pnl_cadastro.add(new JLabel("Lançamento:")).setBounds(130, 65, 75, 14);
		txf_lancamento = new JTextField(10);
		pnl_cadastro.add(txf_lancamento).setBounds(130, 90, 90, 20);
		
		pnl_cadastro.add(new JLabel("Categoria:")).setBounds(250, 65, 98, 14);
		cbx_categoria = new JComboBox<>();
		for(int i = 0; i < cadCategoria.size(); i++) {
			cbx_categoria.addItem(cadCategoria.get(i).getNome());   }
		pnl_cadastro.add(cbx_categoria).setBounds(250, 90, 98, 22);

		pnl_cadastro.add(new JLabel("Imagem:")).setBounds(380, 65, 75, 14);
		lbl_mostrar_imagem = new JLabel("");
		pnl_cadastro.add(lbl_mostrar_imagem).setBounds(405, 120, 90, 110);
		btn_upload = new JButton("Upload");
		pnl_cadastro.add(btn_upload).setBounds(440, 65, 75, 18);
		
		pnl_cadastro.add(new JLabel("Sinopse:")).setBounds(20, 135, 98, 14);
		txa_sinopse = new JTextArea(10, 30);
		scrl_sinopse = new JScrollPane(txa_sinopse);
		pnl_cadastro.add(scrl_sinopse).setBounds(20, 160, 290, 100);
		
		
		// Botões de Ação
		btn_cancelar = new JButton("Cancelar");
		pnl_cadastro.add(btn_cancelar).setBounds(325, 238, 85, 23);
		btn_cancelar.setAction(cancelar);
		
		btn_cadastro = new JButton("Cadastrar");
		pnl_cadastro.add(btn_cadastro).setBounds(424, 238, 100, 23);
		
		
				
	}

	private void adicionarListeners () {

		// Percebe Ação de Clicar na tabela
		tbl_filmes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                //altera os botoes para ativados somente se houver linha selecionada
                btn_editar.setEnabled(!lsm.isSelectionEmpty());
                btn_excluir.setEnabled(!lsm.isSelectionEmpty());
            }
        });
		
		
		btn_upload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				UploadFoto();
			}		
		});  
		
		
		// Ações dos Botões
		btn_pesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Reseta Tabela
				tbl_modelo.setNumRows(0);
				String titulo = txf_titulo_pesquisa.getText().toLowerCase();
				String genero = txf_genero_pesquisa.getText().toLowerCase();
				String lancamento = txf_lancamento_pesquisa.getText().toLowerCase();
				
				// Condição se houver exclusivamente todos os campos pesquisados
				if(titulo.isEmpty() && genero.isEmpty() && lancamento.isEmpty()) {
					preencherTabela();
				} else {
					for (Filme f : cadFilme) {
	
						if(!titulo.isEmpty() && !f.getTitulo().toLowerCase().contains(titulo)) continue; 	
						if(!genero.isEmpty() && !f.getGenero().getNome().toLowerCase().contains(genero)) continue; 
                        if(!lancamento.isEmpty() && !f.getLancamento().toLowerCase().contains(lancamento)) continue; 
						
						tbl_modelo.addRow(new Object[]{f.getId(), f.getTitulo(), f.getGenero().getNome(), f.getDuracao(), f.getLancamento()});	
					}
				}								
			}
		});	
		
		
		
		btn_editar.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Integer idSelected = (Integer) tbl_modelo.getValueAt(tbl_filmes.getSelectedRow(), 0);
				
				Filme filme = filmeDao.busca(idSelected);
				txf_titulo.setText(filme.getTitulo());
				cbx_genero.setSelectedItem(filme.getGenero().getNome());
				txf_copias.setText(filme.getCopias().toString());
				txf_duracao.setText(filme.getDuracao());
				txf_lancamento.setText(filme.getLancamento());
				cbx_categoria.setSelectedItem(filme.getCategoria().getNome());
				txa_sinopse.setText(filme.getSinopse());
				imagem = ManipularImagem.byteToBuffered(filme.getImagem());
				//lbl_mostrar_imagem.setIcon(GerarIcone(imagem));
				GerarIcone(filme.getImagem());

				abas.setSelectedIndex(1);
				btn_cadastro.setText("Editar");
				edit = true;
			}	
		});		
		
		
		btn_excluir.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {				

				Integer idSelected = (Integer) tbl_modelo.getValueAt(tbl_filmes.getSelectedRow(), 0);				
				filmeDao.apagar(idSelected);
				JOptionPane.showMessageDialog(null, "Exclusão efetuada com sucesso!", "Exclusão Efetuada!", JOptionPane.WARNING_MESSAGE);
				
				preencherTabela();				
				limparComponentes ();
			}
		});		  

		
		btn_novo.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				limparComponentes ();
				abas.setSelectedIndex(1);
			}
		});
		
		
		
		// Criar e Editar
		btn_cadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (txf_titulo.getText().contentEquals("")
						|| cbx_genero.getSelectedItem().toString().contentEquals("")
						|| txf_copias.getText().contentEquals("")) {

					JOptionPane.showMessageDialog(null, "Campos Obrigatórios Vazios!", "Cadastro Inválido!", JOptionPane.WARNING_MESSAGE);
					abas.setSelectedIndex(0);
					btn_cadastro.setText("Cadastrar");
					edit = false;
				} else if (edit) {
					
					String palavra = txf_titulo.getText();
					palavra = palavra.substring(0,1).toUpperCase().concat(palavra.substring(1).toLowerCase());
					
					Integer idSelected = (Integer) tbl_modelo.getValueAt(tbl_filmes.getSelectedRow(), 0);
					
					Filme editar = filmeDao.busca(idSelected);
					editar.setTitulo(palavra); 
					editar.setGenero(cadGenero.get(cbx_genero.getSelectedIndex()));
					editar.setCopias(Integer.parseInt(txf_copias.getText()));
					editar.setDuracao(txf_duracao.getText());
					editar.setLancamento(txf_lancamento.getText());
					editar.setCategoria(cadCategoria.get(cbx_categoria.getSelectedIndex()));
					editar.setImagem(ManipularImagem.bufferedToBytes(imagem));
					editar.setImagem(imagemByte);
					editar.setSinopse(txa_sinopse.getText());
					
					filmeDao.editar(editar);
					JOptionPane.showMessageDialog(null, "Edição efetuada com sucesso!", "Edição Efetuada!", JOptionPane.WARNING_MESSAGE);
				
				} else {
					
					String palavra = txf_titulo.getText();
					palavra = palavra.substring(0,1).toUpperCase().concat(palavra.substring(1).toLowerCase());
					
					
					Filme novo = new Filme();
					novo.setTitulo(palavra);
					novo.setGenero(cadGenero.get(cbx_genero.getSelectedIndex()));
					novo.setCopias(Integer.parseInt(txf_copias.getText()));
					novo.setDuracao(txf_duracao.getText());
					novo.setLancamento(txf_lancamento.getText());
					novo.setCategoria(cadCategoria.get(cbx_categoria.getSelectedIndex()));
					//novo.setImagem(ManipularImagem.bufferedToBytes(imagem));
					novo.setImagem(imagemByte);
					novo.setSinopse(txa_sinopse.getText());
					
					Boolean cadastro = filmeDao.inserir(novo);
					if(cadastro) {
						JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!", "Cadastro Efetuado!", JOptionPane.WARNING_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "Erro ao inserir no servidor!", "Cadastro não Efetuado!", JOptionPane.WARNING_MESSAGE);
					}
				
				}
				limparComponentes();
				preencherTabela();				
			}
		});	
		
	}
	
	
	
	// Funções
	
	private void limparComponentes () {
		
		txf_titulo.setText("");
		cbx_genero.setSelectedIndex(0);
		txf_copias.setText("");
		txf_duracao.setText("");
		txf_lancamento.setText("");
		cbx_categoria.setSelectedIndex(0);
		//lbl_mostrar_imagem.setIcon(null);
		//setarImagemPadrao();
		imagemByte = null;
		txa_sinopse.setText("");
		abas.setSelectedIndex(0);
		btn_cadastro.setText("Cadastrar");
		edit = false;
	}
	
	
	private void preencherTabela () {
		
		cadFilme = filmeDao.readAll();
		tbl_modelo.setNumRows(0);
		cadFilme.sort(Comparator.comparing(Filme::getLancamento));
		for (Filme f : cadFilme) { tbl_modelo.addRow(new Object[]{f.getId(), f.getTitulo(), f.getGenero().getNome(), f.getDuracao(), f.getLancamento()});	}
	}
	
	
	private class SwingAction_cancelar extends AbstractAction {
		public SwingAction_cancelar() {
			putValue(NAME, "Cancelar");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			//dispose();
			limparComponentes();
		}
	}
	
	
	// Funcao para escolher uma Foto e copiar para um BufferedImage
	public void UploadFoto() {
		JFileChooser fc_upload = new JFileChooser();
		int resposta = fc_upload.showOpenDialog(null);
		
		if (resposta == JFileChooser.APPROVE_OPTION) {
			File arquivo = fc_upload.getSelectedFile();
			try {
				
				//imagem = ManipularImagem.getBufferedImage(arquivo.getAbsolutePath());
				//lbl_mostrar_imagem.setIcon(GerarIcone(imagem));
				GerarIcone(arquivo);
				
				BufferedImage img = ManipularImagem.getBufferedImage(arquivo.getAbsolutePath());
				imagemByte = ManipularImagem.bufferedToBytes(img);
			}catch(Exception ex) {
				System.out.println(ex.getStackTrace().toString());
			}
		}   // else {System.out.println("Voce não selecionou nenhum arquivo!");}
	}
	
	
	/*
	// Metodo sendo substituido por UploadFoto();
	public Image CarregarImagem() {
		
		JFileChooser fc_upload = new JFileChooser();
		fc_upload.setDialogTitle("Procurar aquivo");
		fc_upload.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
		fc_upload.setFileFilter(filter);
		fc_upload.showOpenDialog(getParent());  // pode ser null ou getParent()
		
		File arquivo = fc_upload.getSelectedFile();
		
		// Import ImageIcon   
		if(arquivo != null) {
			
			InputStream stream;
			Image image;
			ImageIcon icon;
			 
			try {
			
				stream = new FileInputStream(arquivo.getPath());
				image = ImageIO.read(stream);
				icon = GerarIcone(imagem);
				lbl_mostrar_imagem.setIcon(icon);
				
				return image;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	
			
		} 
		
		return null;
	}
	*/
	
	
	// Gera o icone redimensionado ao label que será exibido
	public ImageIcon erarIcone(BufferedImage image) {
		
		ImageIcon icone = null;
		try {
			//icone = new ImageIcon(image);   		// Imagem cortada
			icone = new ImageIcon(image.getScaledInstance(lbl_mostrar_imagem.getWidth(),lbl_mostrar_imagem.getHeight(), Image.SCALE_DEFAULT));
		} catch (Exception ex) {
			System.out.println("Erro ao carregar ícone da imagem");
			//setarImagemPadrao();
		}
		return icone;
	}
	
	// Gera o icone redimensionado ao label que será exibido
	public void GerarIcone(byte[] img) {
		
		if (img instanceof byte[]) {
			ImageIcon icone = new ImageIcon(img);
			icone.setImage(icone.getImage().getScaledInstance(lbl_mostrar_imagem.getWidth(),lbl_mostrar_imagem.getHeight(), Image.SCALE_DEFAULT));
			lbl_mostrar_imagem.setIcon(icone);
		}
	}
	// Gera o icone redimensionado ao label que será exibido
	public void GerarIcone(File img) {
		
		if(img instanceof File) {
			ImageIcon icone = new ImageIcon(img.getAbsolutePath());
			icone.setImage(icone.getImage().getScaledInstance(lbl_mostrar_imagem.getWidth(),lbl_mostrar_imagem.getHeight(), Image.SCALE_DEFAULT));
			lbl_mostrar_imagem.setIcon(icone);
		}
	}
	
	
	
	
	
	/*
	
	// Seta a Imagem padrão atraves do metodo GerarIcone() sempre que limpar a tela para um novo cadastro
	public void setarImagemPadrao() {
		try {
			
			InputStream stream = new FileInputStream("../LocadoraSWING/src/util/teste_netflix.jpg");
			imagem = ImageIO.read(stream);
			lbl_mostrar_imagem.setIcon(GerarIcone(imagem));
			
		} catch (IOException e) {
			System.out.println("Erro ao selecionar imagem padrao");
			e.printStackTrace();
		}
	}
	*/
	
	
	
	/*
	
	private File selecionarImagem() {
		
		JFileChooser fc_upload = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagens em JPEG & png", "jpg", "png");
		fc_upload.addChoosableFileFilter(filter);
		fc_upload.setAcceptAllFileFilterUsed(false);
		fc_upload.setDialogType(JFileChooser.OPEN_DIALOG);
		fc_upload.setCurrentDirectory(new File("/"));
		fc_upload.showOpenDialog(this);
		
		return fc_upload.getSelectedFile();
		
	}*/
	
	
	
	
	/*
	private byte[] getImagem() {
		boolean isPng = false;
		
		if(imagem != null) {
			isPng = imagem.getName().endsWith("png");
			
			try {
				
				BufferedImage image = ImageIO.read(imagem);
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				int type = BufferedImage.TYPE_INT_RGB;
			
				if(isPng) {
					type = BufferedImage.BITMASK;
				}
				
				BufferedImage novaImagem = new BufferedImage(lbl_mostrar_imagem.getWidth() - 5, lbl_mostrar_imagem.getHeight() - 10 , type);
				Graphics2D g = novaImagem.createGraphics();
				g.setComposite(AlphaComposite.Src);
				g.drawImage(image, 0, 0, lbl_mostrar_imagem.getWidth() -5, lbl_mostrar_imagem.getHeight() -10, null);
				
				if(isPng) {
					ImageIO.write(novaImagem, "png", out);
				} else {
					ImageIO.write(novaImagem, "jpg", out);
				}
				
				out.flush();
				byte[] byteArray = out.toByteArray();		//Converteu o out para o vetor de bytes
				
				return byteArray;
				
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			
			
		}
		return null;
	}
	
	
	
	
	public Image pegarImagem () {
		if(imagem != null) {
			ImageIcon iconLogo = new ImageIcon(imagem.getPath());
			Image img = iconLogo.getImage().getScaledInstance(lbl_mostrar_imagem.getWidth(),lbl_mostrar_imagem.getHeight(), Image.SCALE_DEFAULT);
		
			//byteArray = getImagem();
			return img;
		}  
		return null;
	}
	*/	
	

	
	
}
