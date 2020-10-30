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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dao.ClienteDAO;
import dao.DataSource;
import dao.DependenteDAO;
import model.Cliente;
import model.Dependente;
import model.Grau;
import model.Locacao;
import util.ManipularImagem;

@SuppressWarnings("serial")
public class ClienteCadastro extends JInternalFrame {

	static final int xPosition = 30, yPosition = 30;
	private final Action cancelar = new SwingAction_1();
	private boolean edit = false;
	private boolean editdependente = false;
	
	DataSource dataSource;
	ClienteDAO clienteDao;
	DependenteDAO dependenteDao;

	ArrayList<Cliente> cadCliente;
	ArrayList<Dependente> cadDependente;
	ArrayList<Locacao> cadLocacao;	
	

	JTabbedPane abas;
	JPanel pnl_consulta, pnl_cadastro;
	JTextField txf_nome_pesquisa, txf_cpf_pesquisa;
	JButton btn_pesquisar;
	
	DefaultTableModel tbl_modelo;
	JTable tbl_clientes;
	JScrollPane scp_tbl_clientes;
	JButton btn_editar, btn_excluir, btn_novo;
	
	JTextField txf_nome, txf_cpf, txf_telefone, txf_email, txf_nascimento, txf_endereco, txf_dependente ;
	JLabel lbl_mostrar_imagem;
	JButton btn_upload;
	JComboBox<Grau> cbx_grau;
	
	DefaultTableModel tbl_modelo_dep;
	JTable tbl_dependentes;
	JScrollPane scp_tbl_dependentes;
	
	JButton btnVoltar, btn_cadastro;
	JButton btn_tbl_cadastro, btn_tbl_editar, btn_tbl_excluir;
	
	//int idClienteSelect, indexClienteSelect, idDependenteSelecionado, indexDependenteSelect; 
	ArrayList<Dependente> temp = new ArrayList<>();
	//clienteSelecionado;
	//Dependente dependenteSelecionado;
	byte[] imagemByte;
	
	
	public ClienteCadastro() {
		super("Cadastro de Clientes", true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable
		setSize(570, 355);
		setLocation(xPosition, yPosition);
		setLayout(null);

		
		setarElementos();
		adicionarListeners();
		setarTabelaClientes();
		limparComponentes();

	}


	
	
	private void setarElementos () {
		
		// Controlador do Acesso ao Banco de Dados
		dataSource = new DataSource();
		
		clienteDao = new ClienteDAO(dataSource);
		dependenteDao = new DependenteDAO(dataSource);
		
		this.cadCliente = clienteDao.readAll();
		this.cadDependente = dependenteDao.readAll();

		
		
		
		// Criar Abas
		abas = new JTabbedPane(JTabbedPane.TOP);
		add(abas).setBounds(10, 11, 540, 300);
		
		
		// Painéis das Abas
		pnl_consulta = new JPanel();
		pnl_consulta.setLayout(null);
		abas.addTab("Pesquisa", null, pnl_consulta, "Pesquisar Clientes");
		
		pnl_cadastro = new JPanel();
		pnl_cadastro.setLayout(null);
		abas.addTab("Cadastro", null, pnl_cadastro, "Cadastrar Clientes");
		
		// Aba Consulta
		pnl_consulta.add(new JLabel("Nome:")).setBounds(20, 11, 81, 14);
		txf_nome_pesquisa = new JTextField(10);
		pnl_consulta.add(txf_nome_pesquisa).setBounds(20, 29, 220, 20);

		pnl_consulta.add(new JLabel("CPF:")).setBounds(270, 11, 120, 14);
		txf_cpf_pesquisa = new JTextField(10);
		pnl_consulta.add(txf_cpf_pesquisa).setBounds(270, 29, 120, 20);
		
		btn_pesquisar = new JButton("Pesquisar");
		pnl_consulta.add(btn_pesquisar).setBounds(420, 29, 100, 20);
		
		
		// Criar Tabela de Dados
		tbl_modelo = new DefaultTableModel();
		tbl_clientes = new JTable(tbl_modelo);
		scp_tbl_clientes = new JScrollPane(tbl_clientes);
		pnl_consulta.add(scp_tbl_clientes).setBounds(20, 60, 500, 100);
		
		// Preencher Tabela de Dados
		tbl_modelo.addColumn("ID");
		tbl_modelo.addColumn("Nome");
		tbl_modelo.addColumn("CPF");
		tbl_modelo.addColumn("Email");
		tbl_clientes.getColumnModel().getColumn(0).setPreferredWidth(20);
		tbl_clientes.getColumnModel().getColumn(1).setPreferredWidth(220);
		tbl_clientes.getColumnModel().getColumn(2).setPreferredWidth(80);
		tbl_clientes.getColumnModel().getColumn(3).setPreferredWidth(80);		
		
		
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
		pnl_cadastro.add(new JLabel("*Nome:")).setBounds(20, 11, 81, 14);
		txf_nome = new JTextField(10);
		pnl_cadastro.add(txf_nome).setBounds(20, 29, 230, 20);

		pnl_cadastro.add(new JLabel("*CPF:")).setBounds(270, 11, 98, 14);
		txf_cpf = new JTextField(10);
		pnl_cadastro.add(txf_cpf).setBounds(270, 29, 110, 20);
		
		pnl_cadastro.add(new JLabel("*Telefone:")).setBounds(400, 11, 100, 14);
		txf_telefone = new JTextField(10);
		pnl_cadastro.add(txf_telefone).setBounds(400, 29, 115, 20);
		

		pnl_cadastro.add(new JLabel("Email:")).setBounds(20, 65, 55, 14);
		txf_email = new JTextField(10);
		pnl_cadastro.add(txf_email).setBounds(20, 90, 120, 20);

		pnl_cadastro.add(new JLabel("Nascimento:")).setBounds(160, 65, 90, 14);
		txf_nascimento = new JTextField(10);
		pnl_cadastro.add(txf_nascimento).setBounds(160, 90, 90, 20);
	
		
		pnl_cadastro.add(new JLabel("Endereço:")).setBounds(270, 65, 90, 14);
		txf_endereco = new JTextField(10);
		pnl_cadastro.add(txf_endereco).setBounds(270, 90, 110, 20);
		
		pnl_cadastro.add(new JLabel("Foto:")).setBounds(400, 65, 75, 14);
		lbl_mostrar_imagem = new JLabel("");
		pnl_cadastro.add(lbl_mostrar_imagem).setBounds(415, 120, 90, 110);
		btn_upload = new JButton("Upload");
		pnl_cadastro.add(btn_upload).setBounds(440, 65, 75, 18);
		
		
		// Dependentes
		pnl_cadastro.add(new JLabel("Dependentes:")).setBounds(20, 120, 98, 14);
		
		pnl_cadastro.add(new JLabel("Nome:")).setBounds(20, 140, 70, 14);
		txf_dependente = new JTextField(10);
		pnl_cadastro.add(txf_dependente).setBounds(60, 140, 120, 20);
		
		pnl_cadastro.add(new JLabel("Grau:")).setBounds(210, 140, 98, 14);
		cbx_grau = new JComboBox<>(Grau.values());
		pnl_cadastro.add(cbx_grau).setBounds(250, 138, 90, 22);
		
		
		// Criar Tabela de Dados
		tbl_modelo_dep = new DefaultTableModel();
		tbl_dependentes = new JTable(tbl_modelo_dep);
		scp_tbl_dependentes = new JScrollPane(tbl_dependentes);
		pnl_cadastro.add(scp_tbl_dependentes).setBounds(20, 190, 200, 70);
		
		// Colunas da Tabela
		tbl_modelo_dep.addColumn("ID");
		tbl_modelo_dep.addColumn("Nome");
		tbl_modelo_dep.addColumn("Grau");
		tbl_dependentes.getColumnModel().getColumn(0).setPreferredWidth(10);
		tbl_dependentes.getColumnModel().getColumn(1).setPreferredWidth(80);
		tbl_dependentes.getColumnModel().getColumn(2).setPreferredWidth(40);
		
		
		// Botões de Ação
		btnVoltar = new JButton("Cancelar");
		//pnl_cadastro.add(btnVoltar).setBounds(325, 238, 85, 23);
		btnVoltar.setAction(cancelar);

		btn_cadastro = new JButton("Cadastrar");
		pnl_cadastro.add(btn_cadastro).setBounds(424, 238, 100, 23);
		
		
		// Botões Tabela Detalhe
		btn_tbl_cadastro = new JButton("Cadastrar");
		//btn_tbl_cadastro.setEnabled(false);
		pnl_cadastro.add(btn_tbl_cadastro).setBounds(240, 180, 100, 18);
		
		btn_tbl_editar = new JButton("Editar");
		btn_tbl_editar.setEnabled(false);
		pnl_cadastro.add(btn_tbl_editar).setBounds(240, 210, 100, 18);
		
		btn_tbl_excluir = new JButton("Excluir");
		btn_tbl_excluir.setEnabled(false);
		pnl_cadastro.add(btn_tbl_excluir).setBounds(240, 240, 100, 18);	
	}
	
	
	
	private void adicionarListeners() {
		
		// Percebe Ação de Clicar na tabela
		tbl_clientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                //altera os botoes para ativados somente se houver linha selecionada
                btn_editar.setEnabled(!lsm.isSelectionEmpty());
                btn_excluir.setEnabled(!lsm.isSelectionEmpty());
                //btn_tbl_cadastro.setEnabled(!lsm.isSelectionEmpty());
				
               /* 
                if(lsm.isSelectionEmpty()) {
                	idClienteSelect = -1;
                	indexClienteSelect = -1;
                }else {
                	idClienteSelect = (int) tbl_modelo.getValueAt(tbl_clientes.getSelectedRow(), 0);
                    for(int i = 0; i < cadCliente.size(); i++) { 
						if (cadCliente.get(i).getId() == idClienteSelect) {	indexClienteSelect = i;	} 
                    }
                }*/
                
            }
        });
		
		
		tbl_dependentes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                //altera os botoes para ativados somente se houver linha selecionada
                btn_tbl_editar.setEnabled(!lsm.isSelectionEmpty());
                btn_tbl_excluir.setEnabled(!lsm.isSelectionEmpty());
   
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
				
				// Limpa Tabela
				tbl_modelo.setNumRows(0);
				String nome = txf_nome_pesquisa.getText().toLowerCase();
				String cpf = txf_cpf_pesquisa.getText().toLowerCase();
				if(nome.isEmpty() && cpf.isEmpty()) {
					setarTabelaClientes();
				} else {
					// Filtra a Tabela
					for (Cliente c : cadCliente) {
                        if(!nome.isEmpty()) {
                        	if(!c.getNome().toLowerCase().contains(nome)) {
                        		continue;
                        	} 	
                        }
                        if(!cpf.isEmpty()) {
                        	if(!c.getCpf().toLowerCase().contains(cpf)) {
                        		continue;
                        	} 
                        }

                        tbl_modelo.addRow(new Object[]{c.getId(), c.getNome(), c.getCpf(), c.getEmail()});
					}
				}	
			}
		});	
		
		
		
		btn_editar.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Integer idSelected = (Integer) tbl_modelo.getValueAt(tbl_clientes.getSelectedRow(), 0);
				Cliente cliente = clienteDao.busca(idSelected);
				cadDependente = dependenteDao.readAll(idSelected);
				
				txf_nome.setText(cliente.getNome());
				txf_cpf.setText(cliente.getCpf());
				txf_telefone.setText(cliente.getTelefone());
				txf_email.setText(cliente.getEmail());
				txf_nascimento.setText(cliente.getNascimento());
				txf_endereco.setText(cliente.getNascimento());			
				
				setarDependentes(idSelected);
				
				
				/*
				for(int i = 0; i < cadCliente.size(); i++) { 
					if (cadCliente.get(i).getId() == idSelected) {
						txf_nome.setText(cadCliente.get(i).getNome());
						txf_cpf.setText(cadCliente.get(i).getCpf());
						txf_telefone.setText(cadCliente.get(i).getTelefone());
						txf_email.setText(cadCliente.get(i).getEmail());
						txf_nascimento.setText(cadCliente.get(i).getNascimento());
						txf_endereco.setText(cadCliente.get(i).getNascimento());
						//lbl_mostrar_imagem.setIcon(cadCliente.get(i).getImagem());
						
						tbl_modelo_dep.setNumRows(0);
						cadCliente.get(i).getDependentes().sort(Comparator.comparing(Dependente::getNome));
						for (Dependente d : cadCliente.get(i).getDependentes()) { tbl_modelo_dep.addRow(new Object[]{d.getId(), d.getNome(), d.getGrau() });	}
					}  
				}*/
				
				abas.setSelectedIndex(1);
				btn_cadastro.setText("Editar");
				edit = true;
			}	
		});		
		
		btn_excluir.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {				

				Integer idClienteSelected = (Integer) tbl_modelo.getValueAt(tbl_clientes.getSelectedRow(), 0);
				clienteDao.apagar(idClienteSelected);
				JOptionPane.showMessageDialog(null, "Exclusão efetuada com sucesso!", "Exclusão Efetuada!", JOptionPane.WARNING_MESSAGE);
				
				setarTabelaClientes();
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
				
				if (txf_nome.getText().contentEquals("") || txf_cpf.getText().contentEquals("") || txf_telefone.getText().contentEquals("") ) {

					JOptionPane.showMessageDialog(null, "Campos Obrigatórios Vazios!", "Operação Inválida!", JOptionPane.WARNING_MESSAGE);
					if(edit) {
						limparComponentes();
						abas.setSelectedIndex(0);
						btn_cadastro.setText("Cadastrar");
						edit = false;
					}
					
				} else if (edit) {
					
					Integer idClienteSelect = (Integer) tbl_modelo.getValueAt(tbl_clientes.getSelectedRow(), 0);
										
					
					String palavra = txf_nome.getText();
					palavra = palavra.substring(0,1).toUpperCase().concat(palavra.substring(1).toLowerCase());
					
					if (idClienteSelect != null) {
						Cliente cliente = clienteDao.busca(idClienteSelect);
						cliente.setNome(palavra); 
						cliente.setCpf(txf_cpf.getText());
						cliente.setTelefone(txf_telefone.getText());
						cliente.setEmail(txf_email.getText());
						cliente.setNascimento(txf_nascimento.getText());
						cliente.setEndereco(txf_endereco.getText());
						//cliente.setImagem(lbl_mostrar_imagem.getIcon());		
						
						clienteDao.editar(cliente);
						JOptionPane.showMessageDialog(null, "Edição efetuada com sucesso!", "Edição Efetuada!", JOptionPane.WARNING_MESSAGE);
						limparComponentes ();
		
					}  else {
						JOptionPane.showMessageDialog(null, "Selecione novamente o Cliente!", "Edição não Efetuada!", JOptionPane.WARNING_MESSAGE);
						abas.setSelectedIndex(0);
					}
	
				} else if(existeCpf()){
					JOptionPane.showMessageDialog(null, "CPF já Cadastrado!", "Cadastro Inválido!", JOptionPane.WARNING_MESSAGE);
				}else{
					
					Cliente novoCliente = new Cliente();
					
					String palavra = txf_nome.getText();
					palavra = palavra.substring(0,1).toUpperCase().concat(palavra.substring(1).toLowerCase());
					novoCliente.setNome(palavra);
					novoCliente.setCpf(txf_cpf.getText());
					novoCliente.setTelefone(txf_telefone.getText());
					novoCliente.setEmail(txf_email.getText());
					novoCliente.setNascimento(txf_nascimento.getText());
					novoCliente.setEndereco(txf_endereco.getText());
					//novoCliente.setImagem(lbl_mostrar_imagem.getIcon());
					
					clienteDao.inserir(novoCliente);
					
					for(Dependente d: temp) {
						novoCliente.getDependentes().add(new Dependente(d.getNome(), d.getGrau()));
						
					}
					
					//novoCliente.setDependentes(temp);
					cadCliente.add(novoCliente);
					JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!", "Cadastro Efetuado!", JOptionPane.WARNING_MESSAGE);
					limparComponentes ();	
				}
				
				tbl_modelo.setNumRows(0);
				cadCliente.sort(Comparator.comparing(Cliente::getNome));
				for (Cliente c : cadCliente) { tbl_modelo.addRow(new Object[]{c.getId(), c.getNome(), c.getCpf(), c.getEmail()});	}
				
			}
		});
		
		
		btn_tbl_cadastro.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				
				Integer idClienteSelect = (Integer) tbl_modelo.getValueAt(tbl_clientes.getSelectedRow(), 0);
				//Integer idDependenteSelecionado = (Integer) tbl_modelo_dep.getValueAt(tbl_dependentes.getSelectedRow(), 0);
				
				if (txf_dependente.getText().contentEquals("") || Grau.valueOf(cbx_grau.getSelectedItem().toString()).getDescricao().contentEquals("") ){
					JOptionPane.showMessageDialog(null, "Campos Obrigatórios Vazios!", "Cadastro Inválido!", JOptionPane.WARNING_MESSAGE);
				} else if(editdependente){
					if(tbl_clientes.getSelectedRow() != -1) {
						
						
						if (tbl_dependentes.getSelectedRow() != -1) {
							Integer idDependenteSelecionado = (Integer) tbl_modelo_dep.getValueAt(tbl_dependentes.getSelectedRow(), 0);
							Dependente dependente = dependenteDao.busca(idDependenteSelecionado);
							
							dependente.setNome(txf_dependente.getText().toString());
							dependente.setGrau(Grau.valueOf(cbx_grau.getSelectedItem().toString()));
							
							dependenteDao.editar(dependente);
							JOptionPane.showMessageDialog(null, "Edição efetuada com sucesso!", "Edição Efetuada!", JOptionPane.WARNING_MESSAGE);
						}
						setarTabelaDependentes(idClienteSelect);	
					} else {
						
						for (Dependente d: temp) {
							if(d.getId() == tbl_modelo_dep.getValueAt(tbl_dependentes.getSelectedRow(), 0)) {
								d.setNome(txf_dependente.getText().toString());
								d.setGrau(Grau.valueOf(cbx_grau.getSelectedItem().toString()));
							}
						}
						tbl_modelo_dep.setNumRows(0);
						for (Dependente d: temp) { tbl_modelo_dep.addRow(new Object[]{d.getId(), d.getNome(), d.getGrau()}); }
					}
					
					btn_tbl_cadastro.setText("Cadastrar");
					editdependente = false;
					
					txf_dependente.setText("");	
					cbx_grau.setSelectedIndex(0);
								
					
				} else {	
					
					if(tbl_clientes.getSelectedRow() != -1) {
						//Integer idClienteSelect = (Integer) tbl_modelo.getValueAt(tbl_clientes.getSelectedRow(), 0);
						cadDependente = dependenteDao.readAll(idClienteSelect);
						if(cadDependente.size()<3) {
							
							Dependente dependente = new Dependente(txf_dependente.getText().toString() , Grau.valueOf(cbx_grau.getSelectedItem().toString()));
							
							dependenteDao.inserir(dependente);
							JOptionPane.showMessageDialog(null, "O Dependente Foi Cadastrado!", "Cadastro Efetuado!", JOptionPane.WARNING_MESSAGE);
							
							txf_dependente.setText("");	
							cbx_grau.setSelectedIndex(0);
							setarTabelaDependentes(idClienteSelect);
						}else {
							JOptionPane.showMessageDialog(null, "O máximo de Dependentes é 3!", "Cadastro não Efetuado!", JOptionPane.WARNING_MESSAGE);
						}
							
						
						 
					} else {
							
						if(temp.size()<3) {
							temp.add(new Dependente(txf_dependente.getText().toString() , Grau.valueOf(cbx_grau.getSelectedItem().toString())));
							JOptionPane.showMessageDialog(null, "O Dependente Foi Cadastrado!", "Cadastro Efetuado!", JOptionPane.WARNING_MESSAGE);
							
							txf_dependente.setText("");	
							cbx_grau.setSelectedIndex(0);
							
							tbl_modelo_dep.setNumRows(0);
							for (Dependente d: temp) { tbl_modelo_dep.addRow(new Object[]{d.getId(), d.getNome(), d.getGrau()}); }
							
						}else {
							JOptionPane.showMessageDialog(null, "O máximo de Dependentes é 3!", "Cadastro não Efetuado!", JOptionPane.WARNING_MESSAGE);
						}
							
					}
					
				}  		
			}
		});	
		
		btn_tbl_editar.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {				

				txf_dependente.setText(tbl_modelo_dep.getValueAt(tbl_dependentes.getSelectedRow(), 1).toString() );
				cbx_grau.setSelectedItem(tbl_modelo_dep.getValueAt(tbl_dependentes.getSelectedRow(), 2));
				
				btn_tbl_cadastro.setText("Salvar");
				editdependente = true;
				
			}
		});	
		
		btn_tbl_excluir.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {				

				Integer idClienteSelect = (Integer) tbl_modelo.getValueAt(tbl_clientes.getSelectedRow(), 0);
				int idDependenteSelecionado = (int) tbl_modelo_dep.getValueAt(tbl_dependentes.getSelectedRow(), 0);
				cadDependente.remove(idDependenteSelecionado); 
				
				txf_dependente.setText("");	
				cbx_grau.setSelectedIndex(0);
				setarTabelaDependentes (idClienteSelect);	
			}
		});	
		
		
	}
	
	
	private void setarTabelaClientes() {
		// Monta Tabela
		cadCliente = clienteDao.readAll();
		tbl_modelo.setNumRows(0);
		//cadCliente.sort(Comparator.comparing(Cliente::getNome));
		for (Cliente c : cadCliente) { tbl_modelo.addRow(new Object[]{c.getId(), c.getNome(), c.getCpf(), c.getEmail()});	}
	}
	
	
	private void setarTabelaDependentes (Integer id) {
		
		cadDependente = dependenteDao.readAll(id);
		tbl_modelo_dep.setNumRows(0);
		//cliente.getDependentes().sort(Comparator.comparing(Dependente::getNome));
		for (Dependente d : cadDependente) { tbl_modelo_dep.addRow(new Object[]{d.getId(), d.getNome(), d.getGrau() });	}
	
	}
	
	
	private void limparComponentes() {
		txf_nome.setText("");
		txf_cpf.setText("");
		txf_telefone.setText("");
		txf_email.setText("");
		txf_nascimento.setText("");
		txf_endereco.setText("");
		imagemByte = null;
		lbl_mostrar_imagem.setIcon(null);
		tbl_modelo_dep.setNumRows(0);
		txf_dependente.setText("");	
		cbx_grau.setSelectedIndex(0);
		temp = null;
		temp = new ArrayList<>();
		abas.setSelectedIndex(0);
		btn_cadastro.setText("Cadastrar");
		edit = false;
	}
	
	public boolean existeCpf() {
		boolean retorno = false;
		for(Cliente c: cadCliente) {
			if(c.getCpf().trim().equals(txf_cpf.getText())) {
				retorno = true;
			} 
		} 
		return retorno;
	}

	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "Voltar");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	
	
	public void setarDependentes(Integer id) {
		cadDependente = dependenteDao.readAll(id);
		tbl_modelo_dep.setNumRows(0);
		//cliente.getDependentes().sort(Comparator.comparing(Dependente::getNome));
		for (Dependente d : cadDependente) { tbl_modelo_dep.addRow(new Object[]{d.getId(), d.getNome(), d.getGrau() });	}
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
	
	
	
}
