/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.sql.*;
import dal.Conexao;
import java.awt.Color;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author eversonbrunelli-fit
 */
public class TelaEntrada extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private HashMap params;

    /**
     * Creates new form TelaEntrada
     */
    public TelaEntrada() {
        initComponents();
        conexao = Conexao.conector();
        //pesquisa_fornecedor();
        pesquisa_usuario();
        getContentPane().setBackground(Color.GRAY);

    }

//Pesquisa os produtos no campo através do nome e apresenta na tabela
    private void pesquisa_produto() {

        String sql = "select idproduto as Id, produto as Produto, coderp as CodErp, classificacao as Classificacao from produto where produto like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jTextFieldPesquisaProd.getText() + "%");
            rs = pst.executeQuery();
            jTableProdutos.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
//Seta o produto selecionado da tabela no campo ID

    private void seta_produto() {

        int setapro = jTableProdutos.getSelectedRow();
        jTextFieldIdProduto.setText(jTableProdutos.getModel().getValueAt(setapro, 0).toString());
        jTextFieldProduto.setText(jTableProdutos.getModel().getValueAt(setapro, 1).toString());
        jTextFieldClassificacao.setText(jTableProdutos.getModel().getValueAt(setapro, 3).toString());
        jTextFieldCodErp.setText(jTableProdutos.getModel().getValueAt(setapro, 2).toString());

    }

//Pesquisa os fornecedores e apresenta na tabela
    private void pesquisa_fornecedor() {

        String sql = "select idfornecedor as Id, fornecedor as Fornecedor from fornecedor where fornecedor like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jTextFieldPesquisaFor.getText() + "%");
            rs = pst.executeQuery();
            jTableFornecedores.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

//Seta o fornecedor selecionado da tabela no campo ID
    private void seta_fornecedor() {

        int setafor = jTableFornecedores.getSelectedRow();
        jTextFieldFornecedor.setText(jTableFornecedores.getModel().getValueAt(setafor, 1).toString());

    }

//    //Método pesquisa os usuarios no banco e populariza a caixa de combinação
    private void pesquisa_usuario() {

        String sql = "Select * from usuario where perfil = 'user'";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.first();
            do {
                jComboBoxRequisitante.addItem(rs.getString("usuario"));
            } while (rs.next());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Método pesquisa lote no banco
    private void valida_lote_aprovado() {

        String sql = "Select * from entrada where lotefor = ? and situacao = 'Aprovado'";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jTextFieldLoteFor.getText());
            rs = pst.executeQuery();

            if (rs.next()) {

                String lotefor = rs.getString("lotefor");
                if (lotefor.equals(jTextFieldLoteFor.getText())) {
                    JOptionPane.showMessageDialog(null, "Lote ja analisado com a situação Aprovado!");

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    //Método para adicionar uma entrada
    private void entrada_insumo() {

        String sql = "insert into entrada(lotefor,nfe,fabricacao,validade,idproduto,nramostra,quantidade,dtamostragem,situacao,datarec,classificacao,produto,fornecedor,usuario,coderp) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {

            pst = conexao.prepareStatement(sql);
            //pst.setString(1, jTextFieldLoteFlora.getText());
            pst.setString(1, jTextFieldLoteFor.getText());
            pst.setString(2, jTextFieldNfe.getText());
            //conversao de string to date para mysql 18/03/1983 1983-03-18
            String dia_fab = jTextFieldFabricacao.getText().substring(0, 2);
            String mes_fab = jTextFieldFabricacao.getText().substring(3, 5);
            String ano_fab = jTextFieldFabricacao.getText().substring(6);
            String datafabricacao = ano_fab + "-" + mes_fab + "-" + dia_fab;
            pst.setString(3, datafabricacao);
            //conversao de string to date para mysql 18/03/1983 1983-03-18
            String dia = jTextFieldValidade.getText().substring(0, 2);
            String mes = jTextFieldValidade.getText().substring(3, 5);
            String ano = jTextFieldValidade.getText().substring(6);
            String datavalidade = ano + "-" + mes + "-" + dia;
            pst.setString(4, datavalidade);
            pst.setString(5, jTextFieldIdProduto.getText());
            pst.setString(6, jTextFieldNrAmostra.getText());
            pst.setString(7, jTextFieldQuantidade.getText());
            //conversao de string to date para mysql 18/03/1983 1983-03-18
            //String dia_amo = jTextFieldDtAmostragem.getText().substring(0, 2);
            //String mes_amo = jTextFieldDtAmostragem.getText().substring(3, 5);
            //String ano_amo = jTextFieldDtAmostragem.getText().substring(6,10);
            //String hora_amo = jTextFieldDtAmostragem.getText().substring(11, 13);
            //String min_amo = jTextFieldDtAmostragem.getText().substring(13);
            //String dataamostragem = ano_amo + "-" + mes_amo + "-" + dia_amo + " " + hora_amo + min_amo;
            //pst.setString(9, dataamostragem);
            Date data = new Date();
            SimpleDateFormat formatar = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String dataFormatada = formatar.format(data);
            pst.setString(8, (dataFormatada));
            pst.setString(9, jComboBoxSituacao.getSelectedItem().toString());
            //conversao de string to date para mysql 18/03/1983 1983-03-18
            String dia_rec = jTextFieldDataRec.getText().substring(0, 2);
            String mes_rec = jTextFieldDataRec.getText().substring(3, 5);
            String ano_rec = jTextFieldDataRec.getText().substring(6);
            String datarecebimento = ano_rec + "-" + mes_rec + "-" + dia_rec;
            pst.setString(10, datarecebimento);

            //Conversao de data calendario, passando valor pelo pst string
            //DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            //java.sql.Date d = null;
            //try {
            //    d = new java.sql.Date(df.parse(df.format(jDateChooser1.getDate())).getTime());
            //} catch (ParseException ex) {
            //    JOptionPane.showMessageDialog(rootPane, "Introduza a data correcta", "ERRO", JOptionPane.ERROR_MESSAGE);
            //}
            //pst.setString(10, d.toString());
            pst.setString(11, jTextFieldClassificacao.getText());
            pst.setString(12, jTextFieldProduto.getText());
            pst.setString(13, jTextFieldFornecedor.getText());
            pst.setString(14, jComboBoxRequisitante.getSelectedItem().toString());
            pst.setString(15, jTextFieldCodErp.getText());

            //Verifica campos obrigatorios
            if ((((((((((((((jTextFieldLoteFor.getText().isEmpty())) || (jTextFieldNfe.getText().isEmpty())) || (jTextFieldFabricacao.getText().isEmpty())) || (jTextFieldValidade.getText().isEmpty())) || (jTextFieldIdProduto.getText().isEmpty())) || (jTextFieldQuantidade.getText().isEmpty())) || (jTextFieldClassificacao.getText().isEmpty())) || (jTextFieldProduto.getText().isEmpty())) || (jTextFieldFornecedor.getText().isEmpty())))))) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos da tela!");

            } else {

                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Entrada realizada com sucesso!");

                    String sql2 = "select identrada FROM entrada order by identrada desc limit 1";
                    pst = conexao.prepareStatement(sql2);
                    rs = pst.executeQuery();
                    if (rs.first()) {

                        String identrada = rs.getString("identrada");

                        int confirma = JOptionPane.showConfirmDialog(null, "Deseja imprimir a entrada?", "Atenção!", JOptionPane.YES_NO_OPTION);
                        if (confirma == JOptionPane.YES_OPTION) {
                            //Imprimir relatório com o framework JasperReports        
                            try {
                                //usando a classe HashMap para criar um filtro
                                HashMap filtro = new HashMap();
                                filtro.put("identrada",
                                        Integer.parseInt(identrada));

                                //usando a classe Jasperprint para preparar a impressão    
                                JasperPrint imprime = JasperFillManager.fillReport("\\\\fitnt\\Compartilhado\\RRI\\RRI - NEW - 2020\\Projeto_RRI\\src\\relatorios\\Imprime_Entrada.jasper", filtro, conexao);
                                //A linha abaixo exibe o relatório através da classe JasperVieWer
                                JasperViewer.viewReport(imprime, false);

                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, e);
                            }
                        }
                    }

                    jTextFieldNrEntrada.setText(null);
                    jTextFieldLoteFor.setText(null);
                    jTextFieldNfe.setText(null);
                    jTextFieldFabricacao.setText(null);
                    jTextFieldValidade.setText(null);
                    jTextFieldIdProduto.setText(null);
                    jTextFieldNrAmostra.setText(null);
                    jTextFieldQuantidade.setText(null);
                    jTextFieldDtAmostragem.setText(null);
                    jTextFieldDataRec.setText(null);
                    jTextFieldClassificacao.setText(null);
                    jTextFieldProduto.setText(null);
                    jTextFieldFornecedor.setText(null);
                    jTextFieldCodErp.setText(null);

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Pesquisa Entradas
    private void pesquisa_entrada() {

        //Cria caixa de entrada jOptionPane
        String num_ent = JOptionPane.showInputDialog("Número da Entrada");
        String sql = "select * from entrada where identrada = " + num_ent;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {

                jTextFieldNrEntrada.setText(rs.getString(1));
                jTextFieldDataRec.setText(rs.getString(2));
                String dia_rec = rs.getString(2).substring(8, 10);
                String mes_rec = rs.getString(2).substring(5, 7);
                String ano_rec = rs.getString(2).substring(0, 4);
                String datarec = dia_rec + "/" + mes_rec + "/" + ano_rec;
                jTextFieldDataRec.setText(datarec);
                //Teste setar valor de data no campo datechooser
                //String dia_rec = rs.getString(2).substring(8, 10);
                //String mes_rec = rs.getString(2).substring(5, 7);
                //String ano_rec = rs.getString(2).substring(0, 4);
                //String datarec = dia_rec + "/" + mes_rec + "/" + ano_rec;
                //jDateChooser1.setDateFormatString(datarec);

                jTextFieldLoteFor.setText(rs.getString(3));
                //jTextFieldLoteFlora.setText(rs.getString(4));
                jTextFieldNfe.setText(rs.getString(4));
                //jTextFieldFabricacao.setText(rs.getString(6));
                String dia_fab = rs.getString(5).substring(8, 10);
                String mes_fab = rs.getString(5).substring(5, 7);
                String ano_fab = rs.getString(5).substring(0, 4);
                String datafab = dia_fab + "/" + mes_fab + "/" + ano_fab;
                jTextFieldFabricacao.setText(datafab);
                //jTextFieldValidade.setText(rs.getString(7));
                String dia_val = rs.getString(6).substring(8, 10);
                String mes_val = rs.getString(6).substring(5, 7);
                String ano_val = rs.getString(6).substring(0, 4);
                String dataval = dia_val + "/" + mes_val + "/" + ano_val;
                jTextFieldValidade.setText(dataval);
                jTextFieldIdProduto.setText(rs.getString(7));
                jTextFieldClassificacao.setText(rs.getString(8));
                jTextFieldProduto.setText(rs.getString(9));
                jTextFieldNrAmostra.setText(rs.getString(10));
                jTextFieldQuantidade.setText(rs.getString(11));
                //jTextFieldDtAmostragem.setText(rs.getString(13));
                String dia_amo = rs.getString(12).substring(8, 10);
                String mes_amo = rs.getString(12).substring(5, 7);
                String ano_amo = rs.getString(12).substring(0, 4);
                String hor_amo = rs.getString(12).substring(11, 13);
                String min_amo = rs.getString(12).substring(14, 16);
                String seg_amo = rs.getString(12).substring(17, 19);
                String dataamo = dia_amo + "/" + mes_amo + "/" + ano_amo + " " + hor_amo + ":" + min_amo + ":" + seg_amo;
                jTextFieldDtAmostragem.setText(dataamo);
                jComboBoxSituacao.setSelectedItem(rs.getString(13));
                jTextFieldFornecedor.setText(rs.getString(14));
                jComboBoxRequisitante.setSelectedItem(rs.getString(15));
                jTextFieldCodErp.setText(rs.getString(16));
                jButtonAdiciona.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(null, "Entrada não encontrada!");
                //jTextFieldLoteFlora.setText(null);
                jTextFieldLoteFor.setText(null);
                jTextFieldNfe.setText(null);
                jTextFieldFabricacao.setText(null);
                jTextFieldValidade.setText(null);
                jTextFieldIdProduto.setText(null);
                jTextFieldNrAmostra.setText(null);
                jTextFieldQuantidade.setText(null);
                jTextFieldDtAmostragem.setText(null);
                jTextFieldDataRec.setText(null);
                jTextFieldClassificacao.setText(null);
                jTextFieldProduto.setText(null);
                jTextFieldFornecedor.setText(null);
                jTextFieldNrEntrada.setText(null);
                jTextFieldCodErp.setText(null);
                jButtonAdiciona.setEnabled(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Altera entrada
    private void altera_entrada() {

        String sql = "update entrada set lotefor = ?,nfe = ?,fabricacao = ?,validade = ?,idproduto = ?,nramostra = ?,quantidade = ?,dtamostragem = ?,situacao = ?,datarec = ?,classificacao = ?,produto = ?,fornecedor = ?,usuario = ?,coderp = ? where identrada = ?";
        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, jTextFieldLoteFor.getText());
            pst.setString(2, jTextFieldNfe.getText());
            //conversao de string to date para mysql 18/03/1983 1983-03-18
            String dia_fab = jTextFieldFabricacao.getText().substring(0, 2);
            String mes_fab = jTextFieldFabricacao.getText().substring(3, 5);
            String ano_fab = jTextFieldFabricacao.getText().substring(6);
            String datafabricacao = ano_fab + "-" + mes_fab + "-" + dia_fab;
            pst.setString(3, datafabricacao);
            //conversao de string to date para mysql 18/03/1983 1983-03-18
            String dia = jTextFieldValidade.getText().substring(0, 2);
            String mes = jTextFieldValidade.getText().substring(3, 5);
            String ano = jTextFieldValidade.getText().substring(6);
            String datavalidade = ano + "-" + mes + "-" + dia;
            pst.setString(4, datavalidade);
            pst.setString(5, jTextFieldIdProduto.getText());
            pst.setString(6, jTextFieldNrAmostra.getText());
            pst.setString(7, jTextFieldQuantidade.getText());
            Date data = new Date();
            SimpleDateFormat formatar = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String dataFormatada = formatar.format(data);
            pst.setString(8, (dataFormatada));
            pst.setString(9, jComboBoxSituacao.getSelectedItem().toString());
            //conversao de string to date para mysql 18/03/1983 1983-03-18
            String dia_rec = jTextFieldDataRec.getText().substring(0, 2);
            String mes_rec = jTextFieldDataRec.getText().substring(3, 5);
            String ano_rec = jTextFieldDataRec.getText().substring(6);
            String datarecebimento = ano_rec + "-" + mes_rec + "-" + dia_rec;
            pst.setString(10, datarecebimento);
            pst.setString(11, jTextFieldClassificacao.getText());
            pst.setString(12, jTextFieldProduto.getText());
            pst.setString(13, jTextFieldFornecedor.getText());
            pst.setString(14, jComboBoxRequisitante.getSelectedItem().toString());
            pst.setString(15, jTextFieldCodErp.getText());
            pst.setString(16, jTextFieldNrEntrada.getText());

            if ((((((((((((jTextFieldLoteFor.getText().isEmpty())) || (jTextFieldNfe.getText().isEmpty())) || (jTextFieldFabricacao.getText().isEmpty())) || (jTextFieldValidade.getText().isEmpty())) || (jTextFieldIdProduto.getText().isEmpty())) || (jTextFieldQuantidade.getText().isEmpty())) || (jTextFieldClassificacao.getText().isEmpty())) || (jTextFieldProduto.getText().isEmpty())) || (jTextFieldFornecedor.getText().isEmpty())))) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos da tela!");
            } else {

                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
                    jTextFieldLoteFor.setText(null);
                    jTextFieldNfe.setText(null);
                    jTextFieldFabricacao.setText(null);
                    jTextFieldValidade.setText(null);
                    jTextFieldIdProduto.setText(null);
                    jTextFieldNrAmostra.setText(null);
                    jTextFieldQuantidade.setText(null);
                    jTextFieldDtAmostragem.setText(null);
                    jTextFieldDataRec.setText(null);
                    jTextFieldClassificacao.setText(null);
                    jTextFieldProduto.setText(null);
                    jTextFieldFornecedor.setText(null);
                    jTextFieldCodErp.setText(null);
                    jButtonAdiciona.setEnabled(true);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Metodo para excluir entrada
    private void deleta_entrada() {

        int confirma = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir a entrada?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from entrada where identrada = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, jTextFieldNrEntrada.getText());
                int removido = pst.executeUpdate();
                if (removido > 0) {
                    JOptionPane.showMessageDialog(null, "Entrada excluída com sucesso!");
                    jTextFieldLoteFor.setText(null);
                    jTextFieldNfe.setText(null);
                    jTextFieldFabricacao.setText(null);
                    jTextFieldValidade.setText(null);
                    jTextFieldIdProduto.setText(null);
                    jTextFieldNrAmostra.setText(null);
                    jTextFieldQuantidade.setText(null);
                    jTextFieldDtAmostragem.setText(null);
                    jTextFieldDataRec.setText(null);
                    jTextFieldClassificacao.setText(null);
                    jTextFieldProduto.setText(null);
                    jTextFieldFornecedor.setText(null);
                    jTextFieldCodErp.setText(null);
                    jButtonAdiciona.setEnabled(true);

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    //Metodo para imprimir entrada gerada
    private void imprime_entrada() {

        int confirma = JOptionPane.showConfirmDialog(null, "Deseja imprimir a entrada?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            //Imprimir relatório com o framework JasperReports        
            try {
                //usando a classe HashMap para criar um filtro
                HashMap filtro = new HashMap();
                filtro.put("identrada",
                        Integer.parseInt(jTextFieldNrEntrada.getText()));

                //usando a classe Jasperprint para preparar a impressão    
                JasperPrint imprime = JasperFillManager.fillReport("\\\\fitnt\\Compartilhado\\RRI\\RRI - NEW - 2020\\Projeto_RRI\\src\\relatorios\\Imprime_Entrada.jasper", filtro, conexao);
                //A linha abaixo exibe o relatório através da classe JasperVieWer
                JasperViewer.viewReport(imprime, false);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel14 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldNrEntrada = new javax.swing.JTextField();
        jComboBoxSituacao = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jButtonAdiciona = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldLoteFor = new javax.swing.JTextField();
        jTextFieldNfe = new javax.swing.JTextField();
        jTextFieldNrAmostra = new javax.swing.JTextField();
        jTextFieldFabricacao = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldQuantidade = new javax.swing.JTextField();
        jTextFieldDtAmostragem = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldPesquisaProd = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldIdProduto = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextFieldValidade = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldClassificacao = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldProduto = new javax.swing.JTextField();
        jTextFieldFornecedor = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableProdutos = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableFornecedores = new javax.swing.JTable();
        jTextFieldPesquisaFor = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldCodErp = new javax.swing.JTextField();
        jComboBoxRequisitante = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jTextFieldDataRec = new javax.swing.JTextField();

        jLabel14.setText("jLabel14");

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tela de Entrada de Insumos");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel1.setText("N° Entrada");

        jTextFieldNrEntrada.setEditable(false);
        jTextFieldNrEntrada.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldNrEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldNrEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jComboBoxSituacao.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jComboBoxSituacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Em Analise", "Aprovado", "Reprovado", "Aprovado/Concessao" }));
        jComboBoxSituacao.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel3.setText("Situação");

        jButtonAdiciona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewIcones/adicionar_new.png"))); // NOI18N
        jButtonAdiciona.setPreferredSize(new java.awt.Dimension(80, 80));
        jButtonAdiciona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdicionaActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewIcones/pesquisar_new.png"))); // NOI18N
        jButton2.setPreferredSize(new java.awt.Dimension(80, 80));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewIcones/alterar_new.png"))); // NOI18N
        jButton3.setPreferredSize(new java.awt.Dimension(80, 80));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewIcones/deletar_new_2.png"))); // NOI18N
        jButton4.setPreferredSize(new java.awt.Dimension(80, 80));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewIcones/imprimir_new.png"))); // NOI18N
        jButton5.setPreferredSize(new java.awt.Dimension(80, 80));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel4.setText("Lote");

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel5.setText("Nfe");

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel6.setText("N° Amostras");

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel7.setText("Fabricação");

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel8.setText("Quantidade");

        jTextFieldLoteFor.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldLoteFor.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jTextFieldLoteFor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextFieldLoteForMouseExited(evt);
            }
        });

        jTextFieldNfe.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldNfe.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jTextFieldNrAmostra.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldNrAmostra.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jTextFieldFabricacao.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldFabricacao.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel9.setText("Dt Amost");

        jTextFieldQuantidade.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldQuantidade.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jTextFieldDtAmostragem.setEditable(false);
        jTextFieldDtAmostragem.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldDtAmostragem.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel11.setText("* Fornecedor");

        jLabel13.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel13.setText("Requisitante");

        jTextFieldPesquisaProd.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldPesquisaProd.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jTextFieldPesquisaProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisaProdKeyReleased(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewIcones/search_new_3.png"))); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(80, 80));

        jLabel16.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel16.setText("* Id");

        jTextFieldIdProduto.setEditable(false);
        jTextFieldIdProduto.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldIdProduto.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel17.setText("Validade");

        jTextFieldValidade.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldValidade.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Dt Recebimento");

        jTextFieldClassificacao.setEditable(false);
        jTextFieldClassificacao.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldClassificacao.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel12.setText("* Classificação");

        jLabel19.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel19.setText("*Descrição");

        jTextFieldProduto.setEditable(false);
        jTextFieldProduto.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldProduto.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jTextFieldFornecedor.setEditable(false);
        jTextFieldFornecedor.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldFornecedor.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jTableProdutos.setBackground(new java.awt.Color(204, 204, 204));
        jTableProdutos.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jTableProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Produto", "CodErp", "Classificacao"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableProdutosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableProdutos);
        if (jTableProdutos.getColumnModel().getColumnCount() > 0) {
            jTableProdutos.getColumnModel().getColumn(0).setResizable(false);
            jTableProdutos.getColumnModel().getColumn(1).setResizable(false);
            jTableProdutos.getColumnModel().getColumn(2).setResizable(false);
            jTableProdutos.getColumnModel().getColumn(3).setResizable(false);
        }

        jTableFornecedores.setBackground(new java.awt.Color(204, 204, 204));
        jTableFornecedores.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jTableFornecedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Id", "Fornecedor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableFornecedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableFornecedoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableFornecedores);
        if (jTableFornecedores.getColumnModel().getColumnCount() > 0) {
            jTableFornecedores.getColumnModel().getColumn(0).setResizable(false);
            jTableFornecedores.getColumnModel().getColumn(1).setResizable(false);
        }

        jTextFieldPesquisaFor.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldPesquisaFor.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jTextFieldPesquisaFor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisaForKeyReleased(evt);
            }
        });

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewIcones/search_new_3.png"))); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(80, 80));

        jLabel18.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel18.setText("*Coderp");

        jTextFieldCodErp.setEditable(false);
        jTextFieldCodErp.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldCodErp.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jTextFieldCodErp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCodErpActionPerformed(evt);
            }
        });

        jComboBoxRequisitante.setBackground(new java.awt.Color(204, 204, 204));
        jComboBoxRequisitante.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel20.setText("Imprimir");

        jLabel22.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel22.setText("Adicionar");

        jLabel23.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel23.setText("Pesquisar");

        jLabel24.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel24.setText("Alterar");

        jLabel25.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel25.setText("Deletar");

        jTextFieldDataRec.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addGap(64, 64, 64)
                                        .addComponent(jTextFieldValidade, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(18, 18, 18)
                                                .addComponent(jComboBoxSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(37, 37, 37)
                                            .addComponent(jComboBoxRequisitante, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 9, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel22))
                                    .addComponent(jButtonAdiciona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(82, 82, 82))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldDtAmostragem, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel18))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldProduto)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextFieldClassificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jTextFieldCodErp, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jTextFieldPesquisaProd, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jTextFieldIdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel23)
                                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(70, 70, 70)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel24)
                                                .addGap(103, 103, 103)
                                                .addComponent(jLabel25)
                                                .addGap(87, 87, 87))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(70, 70, 70)
                                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(75, 75, 75)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel20)))
                                        .addGap(93, 93, 93))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jTextFieldPesquisaFor, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(35, 35, 35))
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)))
                                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(31, 31, 31)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldDataRec, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldFabricacao, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldNfe, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldNrAmostra, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldLoteFor, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton2, jButton3, jButton4, jButton5, jButtonAdiciona});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jComboBoxSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldDataRec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldLoteFor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldNfe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextFieldNrAmostra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextFieldFabricacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jTextFieldIdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldClassificacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jTextFieldProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(jTextFieldCodErp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldPesquisaProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jTextFieldFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextFieldPesquisaFor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextFieldQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldDtAmostragem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxRequisitante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jTextFieldValidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonAdiciona, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel25)
                            .addComponent(jLabel24)
                            .addComponent(jLabel23))))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton2, jButton3, jButton4, jButton5, jButtonAdiciona});

        setSize(new java.awt.Dimension(908, 686));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldPesquisaProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisaProdKeyReleased
        // Pesquisa produtos pelo nome
        pesquisa_produto();
    }//GEN-LAST:event_jTextFieldPesquisaProdKeyReleased

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // data de recebimento

    }//GEN-LAST:event_formWindowActivated

    private void jButtonAdicionaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdicionaActionPerformed
        entrada_insumo();
    }//GEN-LAST:event_jButtonAdicionaActionPerformed

    private void jTableProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProdutosMouseClicked
        seta_produto();
    }//GEN-LAST:event_jTableProdutosMouseClicked

    private void jTextFieldPesquisaForKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisaForKeyReleased
        pesquisa_fornecedor();
    }//GEN-LAST:event_jTextFieldPesquisaForKeyReleased

    private void jTableFornecedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableFornecedoresMouseClicked
        seta_fornecedor();
    }//GEN-LAST:event_jTableFornecedoresMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        pesquisa_entrada();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        altera_entrada();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        deleta_entrada();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        imprime_entrada();

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextFieldLoteForMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldLoteForMouseExited
        valida_lote_aprovado();
    }//GEN-LAST:event_jTextFieldLoteForMouseExited

    private void jTextFieldCodErpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCodErpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCodErpActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaEntrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaEntrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaEntrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaEntrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaEntrada().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonAdiciona;
    private javax.swing.JComboBox jComboBoxRequisitante;
    private javax.swing.JComboBox jComboBoxSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableFornecedores;
    private javax.swing.JTable jTableProdutos;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextFieldClassificacao;
    private javax.swing.JTextField jTextFieldCodErp;
    private javax.swing.JTextField jTextFieldDataRec;
    private javax.swing.JTextField jTextFieldDtAmostragem;
    private javax.swing.JTextField jTextFieldFabricacao;
    private javax.swing.JTextField jTextFieldFornecedor;
    private javax.swing.JTextField jTextFieldIdProduto;
    private javax.swing.JTextField jTextFieldLoteFor;
    private javax.swing.JTextField jTextFieldNfe;
    private javax.swing.JTextField jTextFieldNrAmostra;
    private javax.swing.JTextField jTextFieldNrEntrada;
    private javax.swing.JTextField jTextFieldPesquisaFor;
    private javax.swing.JTextField jTextFieldPesquisaProd;
    private javax.swing.JTextField jTextFieldProduto;
    private javax.swing.JTextField jTextFieldQuantidade;
    private javax.swing.JTextField jTextFieldValidade;
    // End of variables declaration//GEN-END:variables
}
