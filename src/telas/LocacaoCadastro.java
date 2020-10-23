package telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

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

import classes.Categoria;
import classes.Cliente;
import classes.Filme;
import classes.Genero;
import classes.Locacao;


@SuppressWarnings("serial")
public class LocacaoCadastro extends JInternalFrame {

	static final int xPosition = 30, yPosition = 30;
		
	ArrayList<Cliente> cadCliente;
	ArrayList<Filme> cadFilme;

	JTabbedPane abas;
	JPanel pnl_clientes, pnl_locacoes, teste;
	JTextField txf_nome_pesquisa, txf_cpf_pesquisa;
	JButton btn_pesquisar, btn_locacoes;
	
	DefaultTableModel tbl_modelo_clientes, tbl_modelo_locacoes, tbl_modelo_filmes;
	JTable tbl_clientes, tbl_locacoes, tbl_filmes;
	JScrollPane scp_tbl_clientes, scp_tbl_locacoes, scp_tbl_filmes;

	JLabel lbl_nome_selecionado, lbl_cpf_selecionado;
	JButton btn_tbl_locacao, btn_tbl_devolucao;
	
	JInternalFrame escolherFilme;
	
	DateFormat dataFormatada;
	
	private int idClienteSelect, indexClienteSelect, idLocacaoSelect, indexLocacaoSelect, idFilmeSelect, indexFilmeSelect; 
	long prazoDevolucao;
	
	public LocacaoCadastro(ArrayList<Filme> cadFilme, ArrayList<Genero> cadGenero, ArrayList<Categoria> cadCategoria, ArrayList<Cliente> cadCliente) {
		super("Locação de Filmes", true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		setSize(570, 355);
		setLocation(xPosition, yPosition);
		setLayout(null);

		this.cadCliente = cadCliente;
		this.cadFilme = cadFilme;
		
		setarElementos();
		adicionarListeners();
		setarTabelaClientes();
	}


	
	private void setarElementos () {
		
		idClienteSelect = -1;
		indexClienteSelect = -1;
		idLocacaoSelect = -1;
		indexLocacaoSelect = -1;
		idFilmeSelect = -1;
		indexFilmeSelect = -1;
		
		dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		prazoDevolucao = 15;
		
		
		teste = new JPanel();
		add(teste).setBounds(10, 11, 200, 200);
		teste.setVisible(false);
		teste.setLayout(null);
		
		// Criar Abas
		abas = new JTabbedPane(JTabbedPane.TOP);
		add(abas).setBounds(10, 11, 540, 300);
		
		
		// Painéis das Abas
		pnl_clientes = new JPanel();
		pnl_clientes.setLayout(null);
		abas.addTab("Clientes", null, pnl_clientes, "Pesquisar Clientes");
		
		pnl_locacoes = new JPanel();
		pnl_locacoes.setLayout(null);
		abas.addTab("Locações", null, pnl_locacoes, "Cadastrar Clientes");
		
		// Aba Consulta Clientes
		pnl_clientes.add(new JLabel("Nome:")).setBounds(20, 11, 81, 14);
		txf_nome_pesquisa = new JTextField(10);
		pnl_clientes.add(txf_nome_pesquisa).setBounds(20, 29, 220, 20);

		pnl_clientes.add(new JLabel("CPF:")).setBounds(270, 11, 120, 14);
		txf_cpf_pesquisa = new JTextField(10);
		pnl_clientes.add(txf_cpf_pesquisa).setBounds(270, 29, 120, 20);
		
		
		// Botões de Ação
		btn_pesquisar = new JButton("Pesquisar");
		pnl_clientes.add(btn_pesquisar).setBounds(420, 29, 100, 20);
		
		btn_locacoes = new JButton("Locações");
		btn_locacoes.setEnabled(false);
		pnl_clientes.add(btn_locacoes).setBounds(420, 69, 100, 20);
		
		
		
		// Criar Tabela de Dados
		tbl_modelo_clientes = new DefaultTableModel();
		tbl_clientes = new JTable(tbl_modelo_clientes);
		scp_tbl_clientes = new JScrollPane(tbl_clientes);
		pnl_clientes.add(scp_tbl_clientes).setBounds(20, 60, 370, 200);
		
		// Preencher Tabela de Dados
		tbl_modelo_clientes.addColumn("ID");
		tbl_modelo_clientes.addColumn("Nome");
		tbl_modelo_clientes.addColumn("CPF");
		tbl_clientes.getColumnModel().getColumn(0).setPreferredWidth(20);
		tbl_clientes.getColumnModel().getColumn(1).setPreferredWidth(150);
		tbl_clientes.getColumnModel().getColumn(2).setPreferredWidth(60);
		
		
	
		
		// Aba Consulta Locacoes
		pnl_locacoes.add(new JLabel("Nome:")).setBounds(20, 11, 81, 14);
		lbl_nome_selecionado = new JLabel("");
		pnl_locacoes.add(lbl_nome_selecionado).setBounds(20, 29, 230, 20);

		pnl_locacoes.add(new JLabel("CPF:")).setBounds(270, 11, 98, 14);
		lbl_cpf_selecionado = new JLabel("");
		pnl_locacoes.add(lbl_cpf_selecionado).setBounds(270, 29, 110, 20);

		// Locações
		pnl_locacoes.add(new JLabel("Locações:")).setBounds(20, 60, 98, 14);		
		
		// Criar Tabela de Dados
		tbl_modelo_locacoes = new DefaultTableModel();
		tbl_locacoes = new JTable(tbl_modelo_locacoes);
		scp_tbl_locacoes = new JScrollPane(tbl_locacoes);
		pnl_locacoes.add(scp_tbl_locacoes).setBounds(20, 80, 500, 150);
		
		// Colunas da Tabela
		tbl_modelo_locacoes.addColumn("ID");
		tbl_modelo_locacoes.addColumn("Filme");
		tbl_modelo_locacoes.addColumn("Genero");
		tbl_modelo_locacoes.addColumn("Locação");
		tbl_modelo_locacoes.addColumn("Devolução");
		tbl_modelo_locacoes.addColumn("Status");
		tbl_locacoes.getColumnModel().getColumn(0).setPreferredWidth(10);
		tbl_locacoes.getColumnModel().getColumn(1).setPreferredWidth(100);
		tbl_locacoes.getColumnModel().getColumn(2).setPreferredWidth(50);
		tbl_locacoes.getColumnModel().getColumn(3).setPreferredWidth(50);
		tbl_locacoes.getColumnModel().getColumn(4).setPreferredWidth(50);
		tbl_locacoes.getColumnModel().getColumn(5).setPreferredWidth(50);
		
		
		// Botões Tabela Detalhe
		btn_tbl_locacao = new JButton("Nova Locação");
		btn_tbl_locacao.setEnabled(false);
		pnl_locacoes.add(btn_tbl_locacao).setBounds(400, 238, 120, 23);
		
		btn_tbl_devolucao = new JButton("Devolução");
		btn_tbl_devolucao.setEnabled(false);
		pnl_locacoes.add(btn_tbl_devolucao).setBounds(20, 238, 120, 23);
	
	}
	
	
	
	private void adicionarListeners() {
		
		// Percebe Ação de Clicar na tabela
		tbl_clientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                //altera os botoes para ativados somente se houver linha selecionada
                if(lsm.isSelectionEmpty()) {
                	lbl_nome_selecionado.setText("");
                	lbl_cpf_selecionado.setText("");
                	idClienteSelect = -1;
                	indexClienteSelect = -1;
                }else {
                	idClienteSelect = (int) tbl_modelo_clientes.getValueAt(tbl_clientes.getSelectedRow(), 0);
                	lbl_nome_selecionado.setText(tbl_modelo_clientes.getValueAt(tbl_clientes.getSelectedRow(), 1).toString() );
                    lbl_cpf_selecionado.setText(tbl_modelo_clientes.getValueAt(tbl_clientes.getSelectedRow(), 2).toString() );
                    for(int i = 0; i < cadCliente.size(); i++) { 
						if (cadCliente.get(i).getId() == idClienteSelect) {	indexClienteSelect = i;	} 
                    }
                }
                
                btn_locacoes.setEnabled(!lsm.isSelectionEmpty());
                btn_tbl_locacao.setEnabled(!lsm.isSelectionEmpty());
                setarTabelaLocacoes();
            }
        });
		
		
		tbl_locacoes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                //altera os botoes para ativados somente se houver linha selecionada
                if(lsm.isSelectionEmpty()) {
                	idLocacaoSelect = -1;
                	indexLocacaoSelect = -1;
                }else {
                	idLocacaoSelect = (int) tbl_modelo_locacoes.getValueAt(tbl_locacoes.getSelectedRow(), 0);  
	                for(int i = 0; i < cadCliente.get(indexClienteSelect).getLocacoes().size(); i++) { 
						if (cadCliente.get(indexClienteSelect).getLocacoes().get(i).getId() == idLocacaoSelect) { indexLocacaoSelect = i; } 
					}
                }
                
                btn_tbl_devolucao.setEnabled(!lsm.isSelectionEmpty());
            }
        });
				
		
		
		// Ações dos Botões
		btn_pesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Limpa Tabela
				tbl_modelo_clientes.setNumRows(0);
				String nome = txf_nome_pesquisa.getText().toLowerCase();
				String cpf = txf_cpf_pesquisa.getText().toLowerCase();
				if(nome.isEmpty() && cpf.isEmpty()) {
					setarTabelaClientes();
				} else {
					for (Cliente c : cadCliente) {
                        if(!nome.isEmpty() && !c.getNome().toLowerCase().contains(nome)) continue;
                        if(!cpf.isEmpty() && !c.getCpf().toLowerCase().contains(cpf)) continue;
                        
                        tbl_modelo_clientes.addRow(new Object[]{c.getId(), c.getNome(), c.getCpf()});
					}
				}	
			}
		});	
		
		
		
		btn_locacoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				abas.setSelectedIndex(1);
			}
		});
		

		
		btn_tbl_locacao.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
			
				// Tela exibir Filmes
				escolherFilme = new JInternalFrame("Locação de Filmes", true, true, true, true);
				escolherFilme.setLayout(null);
				escolherFilme.setLocation(120, 10);
				escolherFilme.setSize(300, 250);
				pnl_locacoes.add(escolherFilme, 0);
				escolherFilme.setVisible(true);
				
				// Criar Tabela de Dados
				tbl_modelo_filmes = new DefaultTableModel();
				tbl_filmes = new JTable(tbl_modelo_filmes);
				scp_tbl_filmes = new JScrollPane(tbl_filmes);
				escolherFilme.add(scp_tbl_filmes).setBounds(20, 20, 250, 180);
				
				// Colunas da Tabela
				tbl_modelo_filmes.addColumn("ID");
				tbl_modelo_filmes.addColumn("Filme");
				tbl_modelo_filmes.addColumn("Genero");
				tbl_modelo_filmes.addColumn("Copias");

				tbl_filmes.getColumnModel().getColumn(0).setPreferredWidth(10);
				tbl_filmes.getColumnModel().getColumn(1).setPreferredWidth(100);
				tbl_filmes.getColumnModel().getColumn(2).setPreferredWidth(50);
				tbl_filmes.getColumnModel().getColumn(3).setPreferredWidth(50);
				
				tbl_modelo_filmes.setNumRows(0);
				cadFilme.sort(Comparator.comparing(Filme::getLancamento));
				for (Filme f : cadFilme) { tbl_modelo_filmes.addRow(new Object[]{f.getId(), f.getTitulo(), f.getGenero().getNome(), f.getCopias()});	}

				
				
				// Selecionar filme da tabela
				tbl_filmes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		            @Override
		            public void valueChanged(ListSelectionEvent e) {
		                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
		                //altera os botoes para ativados somente se houver linha selecionada
		                if(lsm.isSelectionEmpty()) {
		                	idFilmeSelect = -1;
		                	indexFilmeSelect = -1;
		                }else if(indexClienteSelect != -1) {
		                	idFilmeSelect = (int) tbl_modelo_filmes.getValueAt(tbl_filmes.getSelectedRow(), 0);  
		                	for(int i = 0; i < cadFilme.size(); i++) { 
		                		if (cadFilme.get(i).getId() == idFilmeSelect) {	indexFilmeSelect = i; } 
		                	} 
		                	
		                	int alugados = 0;
			                for(int i = 0; i< cadCliente.size(); i++) {
			                	for (Locacao l : cadCliente.get(i).getLocacoes()) { 
			                		if ((l.getFilme().getId() == idFilmeSelect) && (l.getDevolucao() == null) ) { alugados++; }
			                	}
			                }
		                	
			                if(alugados < cadFilme.get(indexFilmeSelect).getCopias()) {
			                	cadCliente.get(indexClienteSelect).getLocacoes().add(new Locacao(cadFilme.get(indexFilmeSelect), new Date()));
			                	JOptionPane.showMessageDialog(null, "Locação efetuada com sucesso!", "Locação Efetuada!", JOptionPane.WARNING_MESSAGE);
			                	setarTabelaLocacoes();	
			                	escolherFilme.doDefaultCloseAction();
			                } else {
			                	JOptionPane.showMessageDialog(null, "Todas as cópias alugadas!", "Locação não Efetuada!", JOptionPane.WARNING_MESSAGE);
			                }		                	
		                } else {
		                	JOptionPane.showMessageDialog(null, "Favor selecionar cliente!", "Locação não Efetuada!", JOptionPane.WARNING_MESSAGE);
		                	escolherFilme.doDefaultCloseAction();
		                	abas.setSelectedIndex(0);
		                }
		            }
		        });	
				
				
				long diasPassados;
				for (Locacao l : cadCliente.get(indexClienteSelect).getLocacoes()) { 
					diasPassados = ((new Date().getTime() - l.getLocacao().getTime()) / 86400000L);
					
					if((l.getDevolucao() == null) && (diasPassados > prazoDevolucao)) {
						JOptionPane.showMessageDialog(null, "Possui filme em atraso!", "Locação não Permitida!", JOptionPane.WARNING_MESSAGE);
						escolherFilme.doDefaultCloseAction();
						break;
					} 	
				}
				
					
			}
		});	
		
		
		btn_tbl_devolucao.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				// Setar como devolvido
				if(indexClienteSelect != -1) {
					cadCliente.get(indexClienteSelect).getLocacoes().get(indexLocacaoSelect).setDevolucao(new Date());
					JOptionPane.showMessageDialog(null, "Filme Devolvido com Sucesso!", "Devolução Efetuada!", JOptionPane.WARNING_MESSAGE);
					setarTabelaLocacoes();
				} else {
					JOptionPane.showMessageDialog(null, "Favor selecionar cliente!", "Devolução não Efetuada!", JOptionPane.WARNING_MESSAGE);
				}
			}
		});	
		
		
	}
	
	
	private void setarTabelaClientes() {
		tbl_modelo_clientes.setNumRows(0);
		cadCliente.sort(Comparator.comparing(Cliente::getNome));
		for (Cliente c : cadCliente) { tbl_modelo_clientes.addRow(new Object[]{c.getId(), c.getNome(), c.getCpf()});	}
	}
	
	private void setarTabelaLocacoes () {
		
		if(indexClienteSelect != -1) {
			tbl_modelo_locacoes.setNumRows(0);
			cadCliente.get(indexClienteSelect).getLocacoes().sort(Comparator.comparing(Locacao::getLocacao));
			String situacao = "";
			String devolucao = null;
			long diasPassados;
			
			for (Locacao l : cadCliente.get(indexClienteSelect).getLocacoes()) { 
				diasPassados = ((new Date().getTime() - l.getLocacao().getTime())/ 86400000L);
				if(l.getDevolucao() != null) {
					situacao = "Devolvido";
				}else if(diasPassados > prazoDevolucao) {
					situacao = "Atrasado";
				}else {
					situacao = "No Prazo";
				}
				
				
				if(l.getDevolucao() != null ) {
					devolucao = dataFormatada.format(l.getDevolucao().getTime());
				}else {
					devolucao = null;
				}
				
				tbl_modelo_locacoes.addRow(new Object[]{l.getId(), l.getFilme().getTitulo(), l.getFilme().getGenero().getNome(), dataFormatada.format(l.getLocacao().getTime()), devolucao, situacao});	
			}
		} else { 
			tbl_modelo_locacoes.setNumRows(0); 
		}

	}

	
}
