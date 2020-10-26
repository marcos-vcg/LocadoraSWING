package telas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dao.CategoriaDAO;
import dao.DataSource;
import model.Categoria;

@SuppressWarnings("serial")
public class CategoriaCadastro extends JInternalFrame {
	static final int xPosition = 140, yPosition = 90;
	static boolean edit = false;
	
	DataSource dataSource;
	CategoriaDAO categoriaDao;
	ArrayList<Categoria> cadCategoria;
	Integer idSelected;
	
	JTabbedPane abas;
	JPanel pnl_consulta, pnl_cadastro;
	JTextField txf_categoria_pesquisa, txf_nova_categoria, txf_novo_preco;
	JButton btn_pesquisar, btn_editar, btn_excluir, btn_novo, btn_cadastro;
	DefaultTableModel tbl_modelo;
	JTable tbl_categorias;
	JScrollPane scp_categorias;	
	
	

	public CategoriaCadastro(ArrayList<Categoria> cadCategoria) {
		super("Cadastro de Categorias", true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		setSize(350, 300);
		setLocation(xPosition, yPosition);
		setLayout(null);
		
		
		setarElementos ();
		adicionarListeners();
		limparComponentes();
		preencherTabela();	
	}
	
	
	private void setarElementos() {
		
		// Controlador do Acesso ao Banco de Dados
		dataSource = new DataSource();
		categoriaDao = new CategoriaDAO(dataSource);
		cadCategoria = categoriaDao.readAll();
		
		
		// Criar Abas
		abas = new JTabbedPane(JTabbedPane.TOP);
		add(abas).setBounds(10, 11, 320, 240);
		
			
		// Painéis das Abas
		pnl_consulta = new JPanel();
		pnl_consulta.setLayout(null);
		abas.addTab("Pesquisa", null, pnl_consulta, "Pesquisar Categorias");
		pnl_cadastro = new JPanel();
		pnl_cadastro.setLayout(null);
		abas.addTab("Cadastro", null, pnl_cadastro, "Cadastrar Categoria");
		
		
		// Aba Consulta
		pnl_consulta.add(new JLabel("Categoria:")).setBounds(20, 18, 98, 14);
		txf_categoria_pesquisa = new JTextField(10);
		pnl_consulta.add(txf_categoria_pesquisa).setBounds(90, 16, 98, 22);
		btn_pesquisar = new JButton("Pesquisar");
		pnl_consulta.add(btn_pesquisar).setBounds(200, 16, 100, 20);
		
		// Criar Tabela de Dados
		tbl_modelo = new DefaultTableModel();
		tbl_categorias = new JTable(tbl_modelo);
		scp_categorias = new JScrollPane(tbl_categorias);
		pnl_consulta.add(scp_categorias).setBounds(20, 90, 170, 100);		
		
		// Inserir Colunas da Tabela de Dados
		tbl_modelo.addColumn("ID");
		tbl_modelo.addColumn("Categoria");
		tbl_modelo.addColumn("Preço");
		tbl_categorias.getColumnModel().getColumn(0).setPreferredWidth(20);
		tbl_categorias.getColumnModel().getColumn(1).setPreferredWidth(100);	
		tbl_categorias.getColumnModel().getColumn(2).setPreferredWidth(45);	

				
		
		// Botões de Ação
		btn_editar = new JButton("Editar");
		btn_editar.setEnabled(false);
		pnl_consulta.add(btn_editar).setBounds(210, 90, 80, 25);		
	
		btn_excluir = new JButton("Excluir");
		btn_excluir.setEnabled(false);
		pnl_consulta.add(btn_excluir).setBounds(210, 130, 80, 25);
		
		btn_novo = new JButton("Novo");
		btn_novo.setVisible(true);
		pnl_consulta.add(btn_novo).setBounds(210, 170, 80, 25);		
		
		
				
		// Aba Cadastro
		pnl_cadastro.add(new JLabel("Categoria:")).setBounds(55, 18, 98, 14);
		txf_nova_categoria = new JTextField(10);
		pnl_cadastro.add(txf_nova_categoria).setBounds(150, 14, 98, 22);
		
		pnl_cadastro.add(new JLabel("Preço:")).setBounds(55, 38, 98, 14);;
		txf_novo_preco = new JTextField(10);
		pnl_cadastro.add(txf_novo_preco).setBounds(150, 44, 98, 22);
		
		btn_cadastro = new JButton("Cadastrar");
		pnl_cadastro.add(btn_cadastro).setBounds(120, 80, 98, 22);	
		
	}
	
	private void adicionarListeners() {
		
		// Percebe Ação de Clicar na tabela
		tbl_categorias.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                //altera os botoes para ativados somente se houver linha selecionada
                btn_editar.setEnabled(!lsm.isSelectionEmpty());
                btn_excluir.setEnabled(!lsm.isSelectionEmpty());
                
                if(lsm.isSelectionEmpty()) {
                	idSelected = -1;
                }else {
                	idSelected = (Integer) tbl_modelo.getValueAt(tbl_categorias.getSelectedRow(), 0);
                }
            }
        });
				
				
		
		// Ações dos Botões
		btn_pesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				if(txf_categoria_pesquisa.getText().contentEquals("")) {
					preencherTabela();
				} else {
					// Filtra a Tabela
					tbl_modelo.setNumRows(0);
					for (Categoria categoria : cadCategoria) {
						if(categoria.getNome().toLowerCase().contains(txf_categoria_pesquisa.getText().toLowerCase())) {
							tbl_modelo.addRow(new Object[]{categoria.getId(), categoria.getNome(), categoria.getPreco()});	} 
					}
				}
				limparComponentes();
			}
		});	
		
		
		
		btn_editar.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txf_nova_categoria.setText(tbl_categorias.getValueAt(tbl_categorias.getSelectedRow(), 1).toString());
				txf_novo_preco.setText(tbl_categorias.getValueAt(tbl_categorias.getSelectedRow(), 2).toString());
				abas.setSelectedIndex(1);
				btn_cadastro.setText("Editar");
				edit = true;
			}	
		});		
		
		
		btn_excluir.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {				

				categoriaDao.apagarCategoria(idSelected);
				JOptionPane.showMessageDialog(null, "Exclusão efetuada com sucesso!", "Exclusão Efetuada!", JOptionPane.WARNING_MESSAGE);
				limparComponentes();
				preencherTabela();
			}
		});		    

		
		btn_novo.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparComponentes();
				abas.setSelectedIndex(1);				
			}
		});
		
		
		
		// Criar e Editar
		btn_cadastro.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				
				String palavra = txf_nova_categoria.getText();
				String preco = txf_novo_preco.getText();
				
				if(palavra.contentEquals("") || preco.contentEquals("")) {
					
					JOptionPane.showMessageDialog(null, "Campos Obrigatórios Vazios!", "Ação Inválida!", JOptionPane.WARNING_MESSAGE);
					
				} else if (edit) {
					
					palavra = palavra.substring(0,1).toUpperCase().concat(palavra.substring(1).toLowerCase());
					categoriaDao.editarCategoria(idSelected, palavra, preco);
					JOptionPane.showMessageDialog(null, "Edição efetuada com sucesso!", "Edição Efetuada!", JOptionPane.WARNING_MESSAGE);
					
				} else {
					
					palavra = palavra.substring(0,1).toUpperCase().concat(palavra.substring(1).toLowerCase());
					categoriaDao.inserirCategoria(palavra, preco);
					JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!", "Cadastro Efetuado!", JOptionPane.WARNING_MESSAGE);
	
				}
				
				limparComponentes();
				preencherTabela();	
			}
		});	
			
		
	}
	
	
	private void preencherTabela() {

		cadCategoria = categoriaDao.readAll();
		tbl_modelo.setNumRows(0);
		cadCategoria.sort(Comparator.comparing(Categoria::getNome));
		for (Categoria categoria : cadCategoria) {tbl_modelo.addRow(new Object[]{categoria.getId(), categoria.getNome(), categoria.getPreco()});}

	}
	
	
	private void limparComponentes() {
		abas.setSelectedIndex(0);
		txf_nova_categoria.setText("");
		txf_novo_preco.setText("");
		btn_cadastro.setText("Cadastrar");
		edit = false;
	}
	
}