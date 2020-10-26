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


import dao.DataSource;
import dao.GeneroDAO;
import model.Genero;

@SuppressWarnings("serial")
public class GeneroCadastro extends JInternalFrame {
	static final int xPosition = 140, yPosition = 90;
	static boolean edit = false;
	
	
	DataSource dataSource;
	GeneroDAO generoDao;
	ArrayList<Genero> cadGenero;
	
	JTabbedPane abas;
	JPanel pnl_consulta, pnl_cadastro;
	JTextField txf_genero_pesquisa, txf_novo_genero;
	JButton btn_pesquisar;
	DefaultTableModel tbl_modelo;
	JTable tbl_generos;
	JScrollPane scp_generos;
	JButton btn_editar, btn_excluir, btn_novo, btn_cadastro;

	Integer idSelected;

	
	public GeneroCadastro() {
		super("Cadastro de Gêneros", true, // resizable
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
		generoDao = new GeneroDAO(dataSource);
		cadGenero = generoDao.readAll();
		
		
		// Criar Abas
		abas = new JTabbedPane(JTabbedPane.TOP);
		add(abas).setBounds(10, 11, 320, 240);
		
		// Painéis das Abas
		pnl_consulta = new JPanel();
		pnl_consulta.setLayout(null);
		abas.addTab("Pesquisa", null, pnl_consulta, "Pesquisar Gêneros");
		
		pnl_cadastro = new JPanel();
		abas.addTab("Cadastro", null, pnl_cadastro, "Cadastrar Gêneros");
		
		
		// Aba Consulta
		pnl_consulta.add(new JLabel("Consulta:")).setBounds(20, 18, 98, 14);
		txf_genero_pesquisa = new JTextField(10);
		pnl_consulta.add(txf_genero_pesquisa).setBounds(90, 16, 98, 22);
		
		btn_pesquisar = new JButton("Pesquisar");
		pnl_consulta.add(btn_pesquisar).setBounds(200, 16, 100, 20);
		
		// Criar Tabela de Dados
		tbl_modelo = new DefaultTableModel();
		tbl_generos = new JTable(tbl_modelo);
	    scp_generos = new JScrollPane(tbl_generos);
		pnl_consulta.add(scp_generos).setBounds(40, 90, 120, 100);		
		
		// Inserir Colunas da Tabela de Dados
		tbl_modelo.addColumn("ID");
		tbl_modelo.addColumn("Gênero");
		tbl_generos.getColumnModel().getColumn(0).setPreferredWidth(20);
		tbl_generos.getColumnModel().getColumn(1).setPreferredWidth(100);
		
		// Monta Tabela
		tbl_modelo.setNumRows(0);
		cadGenero.sort(Comparator.comparing(Genero::getNome));
		for (Genero genero : cadGenero) {tbl_modelo.addRow(new Object[]{genero.getId(), genero.getNome()});}
		
		
		// Botões de Ação
		btn_editar = new JButton("Editar");
		btn_editar.setEnabled(false);
		pnl_consulta.add(btn_editar).setBounds(210, 90, 80, 25);		
		
		btn_excluir = new JButton("Excluir");
		btn_excluir.setEnabled(false);
		pnl_consulta.add(btn_excluir).setBounds(210, 130, 80, 25);
		
		btn_novo = new JButton("Novo");
		pnl_consulta.add(btn_novo).setBounds(210, 170, 80, 25);		
		
			
		// Aba Cadastro
		pnl_cadastro.add(new JLabel("Gênero:"));
		txf_novo_genero = new JTextField(10);
		pnl_cadastro.add(txf_novo_genero);
		
		btn_cadastro = new JButton("Cadastrar");
		pnl_cadastro.add(btn_cadastro);				
	}
	
	
	private void adicionarListeners() {
		
		// Percebe Ação de Clicar na tabela
		tbl_generos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                //altera os botoes para ativados somente se houver linha selecionada
                btn_editar.setEnabled(!lsm.isSelectionEmpty());
                btn_excluir.setEnabled(!lsm.isSelectionEmpty());
                
                if(lsm.isSelectionEmpty()) {
                	idSelected = -1;
                }else {
                	idSelected = (Integer) tbl_modelo.getValueAt(tbl_generos.getSelectedRow(), 0);
                }
            }
        });
		
		
		// Ações dos Botões
		btn_pesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Remonta Tabela
				tbl_modelo.setNumRows(0);
				if(txf_genero_pesquisa.getText().contentEquals("")) {
					for (Genero genero : cadGenero) {
						tbl_modelo.addRow(new Object[]{genero.getId(), genero.getNome()});
					}
				} else {
					// Filtra a Tabela
					for (Genero genero : cadGenero) {
						if(genero.getNome().toLowerCase().contains(txf_genero_pesquisa.getText().toLowerCase())) {
							tbl_modelo.addRow(new Object[]{genero.getId(), genero.getNome()});	} 
					}	
				}
				limparComponentes();
			}
		});	
		
		
		
		btn_editar.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txf_novo_genero.setText(tbl_generos.getValueAt(tbl_generos.getSelectedRow(), tbl_generos.getSelectedColumn()).toString());
				abas.setSelectedIndex(1);
				btn_cadastro.setText("Editar");
				edit = true;
			}	
		});
		
		
		btn_excluir.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				
				generoDao.apagarGenero(idSelected);
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
				
				if(txf_novo_genero.getText().contentEquals("") ) {
					JOptionPane.showMessageDialog(null, "Campos Obrigatórios Vazios!", "Ação Cancelada!", JOptionPane.WARNING_MESSAGE);
					limparComponentes();
				} else if (edit) {
					String palavra = txf_novo_genero.getText();
					palavra = palavra.substring(0,1).toUpperCase().concat(palavra.substring(1).toLowerCase());
					generoDao.editarGenero(idSelected, palavra);
					//for(int i = 0; i < cadGenero.size(); i++) { if (cadGenero.get(i).getId() == idSelected) {cadGenero.get(i).setNome(palavra);}  }
					JOptionPane.showMessageDialog(null, "Edição efetuada com sucesso!", "Edição Efetuada!", JOptionPane.WARNING_MESSAGE);
						
				} else {
					String palavra = txf_novo_genero.getText();
					palavra = palavra.substring(0,1).toUpperCase().concat(palavra.substring(1).toLowerCase());
					generoDao.inserirGenero(palavra);
					//cadGenero.add(new Genero(palavra));
					
					JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!", "Cadastro Efetuado!", JOptionPane.WARNING_MESSAGE);
				}
				limparComponentes();
				preencherTabela();
			}
		});	
		
	}
	
	
	private void limparComponentes() {
		abas.setSelectedIndex(0);
		txf_novo_genero.setText("");
		btn_cadastro.setText("Cadastrar");
		edit = false;
	}
	
	
	private void preencherTabela() {
		cadGenero = generoDao.readAll();
		tbl_modelo.setNumRows(0);
		cadGenero.sort(Comparator.comparing(Genero::getNome));
		for (Genero genero : cadGenero) {tbl_modelo.addRow(new Object[]{genero.getId(), genero.getNome()});}
	}
	
}