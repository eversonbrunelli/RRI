/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.sql.*;
import dal.Conexao;
import java.awt.Color;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author eversonbrunelli-fit
 */
public class TelaAprovacao extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    public String entrada = null;

    /**
     * Creates new form TelaAprovacao
     */
    public TelaAprovacao() {
        initComponents();
        conexao = Conexao.conector();
        pesquisa_usuario();
        pesquisa_analista();
        envia_email_aprovado();
        envia_email_reprovado();
        envia_email_aprovado_concessao();
        getContentPane().setBackground(Color.GRAY);
    }

    //Metodo pesquisa os analistas no banco de dados e populariza a caixa de combinação
    private void pesquisa_analista() {

        String sql = "Select * from usuario where perfil='analista'";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.first();
            do {
                jComboBoxAnalista.addItem(rs.getString("usuario"));
            } while (rs.next());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Método pesquisa os usuarios no banco e populariza a caixa de combinação
    private void pesquisa_usuario() {

        String sql = "Select * from usuario where perfil= 'user'";
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

    //Método para envio de email de confirmação de aprovação
    private void envia_email_aprovado() {

        String meuEmail = "rri@fit.flora.com.br";
        String minhaSenha = "tKu2D6xDF78d";

        SimpleEmail email = new SimpleEmail();
        email.setHostName("correio.flora.com.br");
        email.setSslSmtpPort("465");
        email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));
        email.setSSLOnConnect(true);
        email.setStartTLSRequired(true);
        email.setStartTLSEnabled(true);
        email.setSSLOnConnect(true);

        try {

            email.setFrom(meuEmail);
            email.setSubject("Entrada Aprovada");
            email.setMsg(entrada);
            email.addTo("itajai.rri@fit.flora.com.br");
            email.send();
            JOptionPane.showMessageDialog(null, "Email de confirmação enviado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Método para envio de email de confirmação de aprovação
    private void envia_email_reprovado() {

        String meuEmail = "rri@fit.flora.com.br";
        String minhaSenha = "tKu2D6xDF78d";

        SimpleEmail email = new SimpleEmail();
        email.setHostName("correio.flora.com.br");
        email.setSslSmtpPort("465");
        email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));
        email.setSSLOnConnect(true);
        email.setStartTLSRequired(true);
        email.setStartTLSEnabled(true);
        email.setSSLOnConnect(true);

        try {

            email.setFrom(meuEmail);
            email.setSubject("Entrada Reprovada");
            email.setMsg(entrada);
            email.addTo("itajai.rri@fit.flora.com.br");
            email.send();
            JOptionPane.showMessageDialog(null, "Email de confirmação enviado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Método para envio de email de confirmação de aprovação
    private void envia_email_aprovado_concessao() {

        String meuEmail = "rri@fit.flora.com.br";
        String minhaSenha = "tKu2D6xDF78d";

        SimpleEmail email = new SimpleEmail();
        email.setHostName("correio.flora.com.br");
        email.setSslSmtpPort("465");
        email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));
        email.setSSLOnConnect(true);
        email.setStartTLSRequired(true);
        email.setStartTLSEnabled(true);
        email.setSSLOnConnect(true);

        try {

            email.setFrom(meuEmail);
            email.setSubject("Entrada Aprovada por Concessão");
            email.setMsg(entrada);
            email.addTo("itajai.rri@fit.flora.com.br");
            email.send();
            JOptionPane.showMessageDialog(null, "Email de confirmação enviado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Método para pesquisa de entrada
    private void pesquisa_entrada() {

        //Cria caixa de entrada jOptionPane
        String num_ent = JOptionPane.showInputDialog("Número da Entrada");
        String sql = "select * from entrada where identrada = " + num_ent;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {

                jTextFieldNrEntrada.setText(rs.getString(1));
                //jTextFieldDataRec.setText(rs.getString(2));
                //conversao de string to date para mysql 1983-03-18 para 18/03/1983
                String dia_rec = rs.getString(2).substring(8, 10);
                String mes_rec = rs.getString(2).substring(5, 7);
                String ano_rec = rs.getString(2).substring(0, 4);
                String datarec = dia_rec + "/" + mes_rec + "/" + ano_rec;
                jTextFieldDataRec.setText(datarec);
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
                //jTextFieldDtAprovacao.setText(rs.getString(18));
                String dia_apr = rs.getString(17).substring(8, 10);
                String mes_apr = rs.getString(17).substring(5, 7);
                String ano_apr = rs.getString(17).substring(0, 4);
                String hor_apr = rs.getString(17).substring(11, 13);
                String min_apr = rs.getString(17).substring(14, 16);
                String seg_apr = rs.getString(17).substring(17, 19);
                String dataapr = dia_apr + "/" + mes_apr + "/" + ano_apr + " " + hor_apr + ":" + min_apr + ":" + seg_apr;
                jTextFieldDtAprovacao.setText(dataapr);
                jComboBoxAnalista.setSelectedItem(rs.getString(18));
                jTextAreaObs.setText(rs.getString(19));

            } else {
                JOptionPane.showMessageDialog(null, "Entrada não encontrada!");
                //jTextFieldLoteFlora.setText(null);
                jTextFieldLoteFor.setText(null);
                jTextFieldNfe.setText(null);
                jTextFieldFabricacao.setText(null);
                jTextFieldValidade.setText(null);
                jTextFieldNrAmostra.setText(null);
                jTextFieldQuantidade.setText(null);
                jTextFieldDtAmostragem.setText(null);
                jTextFieldDataRec.setText(null);
                jTextFieldClassificacao.setText(null);
                jTextFieldProduto.setText(null);
                jTextFieldFornecedor.setText(null);
                jTextFieldCodErp.setText(null);
                jTextFieldNrEntrada.setText(null);
                jTextAreaObs.setText(null);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Pesquisa concluída!");
        }
    }

    //Altera entrada
    private void altera_entrada() {

        String sql = "update entrada set situacao = ?,dtaprovacao = ?, aprovador = ?, obs = ? where identrada = ?";
        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, jComboBoxSituacao.getSelectedItem().toString());
            //pst.setString(2, jTextFieldDtAprovacao.getText());
            //Insere a data e hora atual ao campo
            java.util.Date data = new java.util.Date();
            SimpleDateFormat formatar = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String dataFormatada = formatar.format(data);
            pst.setString(2, (dataFormatada));
            pst.setString(3, jComboBoxAnalista.getSelectedItem().toString());
            pst.setString(4, jTextAreaObs.getText());
            pst.setString(5, jTextFieldNrEntrada.getText());
            //conversao de string to date para mysql 18/03/1983 1983-03-18
            //String dia_fab = jTextFieldFabricacao.getText().substring(0, 2);
            //String mes_fab = jTextFieldFabricacao.getText().substring(3, 5);
            //String ano_fab = jTextFieldFabricacao.getText().substring(6);
            //String datafabricacao = ano_fab + "-" + mes_fab + "-" + dia_fab;

            if (jTextFieldNrEntrada.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos da tela!");
            } else {

                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");

                    entrada = jTextFieldProduto.getText();

                    if (jComboBoxSituacao.getSelectedItem().toString().equals("Aprovado")) {

                        envia_email_aprovado();

                    }
                    if (jComboBoxSituacao.getSelectedItem().toString().equals("Aprovado/Concessao")) {

                        envia_email_aprovado_concessao();

                    }
                    if (jComboBoxSituacao.getSelectedItem().toString().equals("Reprovado")) {

                        envia_email_reprovado();

                    }

                    //jTextFieldLoteFlora.setText(null);
                    jTextFieldLoteFor.setText(null);
                    jTextFieldNfe.setText(null);
                    jTextFieldFabricacao.setText(null);
                    jTextFieldValidade.setText(null);
                    jTextFieldNrAmostra.setText(null);
                    jTextFieldQuantidade.setText(null);
                    jTextFieldDtAmostragem.setText(null);
                    jTextFieldDataRec.setText(null);
                    jTextFieldClassificacao.setText(null);
                    jTextFieldProduto.setText(null);
                    jTextFieldFornecedor.setText(null);
                    jTextFieldCodErp.setText(null);
                    jTextFieldDtAprovacao.setText(null);
                    jTextAreaObs.setText(null);

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldNrEntrada = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxSituacao = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldDataRec = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldLoteFor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldNfe = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldNrAmostra = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldFabricacao = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldQuantidade = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldDtAmostragem = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jComboBoxRequisitante = new javax.swing.JComboBox<Object>();
        jLabel17 = new javax.swing.JLabel();
        jTextFieldValidade = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldClassificacao = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldProduto = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldFornecedor = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldCodErp = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jComboBoxAnalista = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldDtAprovacao = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaObs = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tela de Aprovação");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel1.setText("N° Entrada");

        jTextFieldNrEntrada.setEditable(false);
        jTextFieldNrEntrada.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldNrEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNrEntradaActionPerformed(evt);
            }
        });

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

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewIcones/pesquisar_new.png"))); // NOI18N
        jButton2.setPreferredSize(new java.awt.Dimension(80, 80));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel3.setText("Situação");

        jComboBoxSituacao.setBackground(new java.awt.Color(204, 204, 204));
        jComboBoxSituacao.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jComboBoxSituacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Em Analise", "Aprovado", "Reprovado", "Aprovado/Concessao" }));

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel2.setText("Dt Recebimento");

        jTextFieldDataRec.setEditable(false);
        jTextFieldDataRec.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldDataRec.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel4.setText("Lote");

        jTextFieldLoteFor.setEditable(false);
        jTextFieldLoteFor.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldLoteFor.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel5.setText("Nfe");

        jTextFieldNfe.setEditable(false);
        jTextFieldNfe.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldNfe.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel6.setText("N° Amostras");

        jTextFieldNrAmostra.setEditable(false);
        jTextFieldNrAmostra.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldNrAmostra.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel7.setText("Fabricação");

        jTextFieldFabricacao.setEditable(false);
        jTextFieldFabricacao.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldFabricacao.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel8.setText("Quantidade");

        jTextFieldQuantidade.setEditable(false);
        jTextFieldQuantidade.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldQuantidade.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel9.setText("Dt Amostragem");

        jTextFieldDtAmostragem.setEditable(false);
        jTextFieldDtAmostragem.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldDtAmostragem.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel13.setText("Requisitante");

        jComboBoxRequisitante.setBackground(new java.awt.Color(204, 204, 204));
        jComboBoxRequisitante.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jComboBoxRequisitante.setEnabled(false);

        jLabel17.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel17.setText("Validade");

        jTextFieldValidade.setEditable(false);
        jTextFieldValidade.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldValidade.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel12.setText("Classificação");

        jTextFieldClassificacao.setEditable(false);
        jTextFieldClassificacao.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldClassificacao.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel19.setText("Descrição");

        jTextFieldProduto.setEditable(false);
        jTextFieldProduto.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldProduto.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel11.setText("Fornecedor");

        jTextFieldFornecedor.setEditable(false);
        jTextFieldFornecedor.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldFornecedor.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewIcones/alterar_new.png"))); // NOI18N
        jButton3.setPreferredSize(new java.awt.Dimension(80, 80));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel14.setText("CodErp");

        jTextFieldCodErp.setEditable(false);
        jTextFieldCodErp.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldCodErp.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel15.setText("Alterar");

        jComboBoxAnalista.setBackground(new java.awt.Color(204, 204, 204));
        jComboBoxAnalista.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel16.setText("Dt/Hr/Aprova");

        jTextFieldDtAprovacao.setEditable(false);
        jTextFieldDtAprovacao.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldDtAprovacao.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel18.setText("Observação");

        jTextAreaObs.setBackground(new java.awt.Color(204, 204, 204));
        jTextAreaObs.setColumns(20);
        jTextAreaObs.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jTextAreaObs.setRows(5);
        jTextAreaObs.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setViewportView(jTextAreaObs);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewImagens/neutrox-01-produto.png"))); // NOI18N
        jLabel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel20.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel20.setText("Analista/Aprov");

        jLabel21.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel21.setText("Pesquisar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldQuantidade, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                            .addComponent(jTextFieldFabricacao)
                            .addComponent(jTextFieldNrAmostra)
                            .addComponent(jTextFieldNfe)
                            .addComponent(jTextFieldLoteFor)
                            .addComponent(jTextFieldDataRec)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldDtAmostragem)
                            .addComponent(jComboBoxAnalista, 0, 142, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel21)))
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel15)))))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18)
                            .addComponent(jLabel11)
                            .addComponent(jLabel14))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldFornecedor)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldCodErp, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldDtAprovacao, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextFieldProduto))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(jComboBoxSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel17)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(119, 119, 119)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldValidade, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxRequisitante, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldClassificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(44, 44, 44))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jLabel20)
                    .addContainerGap(725, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jComboBoxSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(42, 42, 42))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldDataRec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jTextFieldLoteFor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jComboBoxRequisitante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(jTextFieldValidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jTextFieldNfe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(jTextFieldClassificacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jTextFieldProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextFieldNrAmostra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextFieldFabricacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextFieldQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldDtAmostragem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxAnalista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jTextFieldFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jTextFieldCodErp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jTextFieldDtAprovacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel15))
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(428, Short.MAX_VALUE)
                    .addComponent(jLabel20)
                    .addGap(161, 161, 161)))
        );

        setSize(new java.awt.Dimension(853, 649));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldNrEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNrEntradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNrEntradaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        pesquisa_entrada();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        altera_entrada();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaAprovacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAprovacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAprovacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAprovacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaAprovacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBoxAnalista;
    private javax.swing.JComboBox<Object> jComboBoxRequisitante;
    private javax.swing.JComboBox jComboBoxSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaObs;
    private javax.swing.JTextField jTextFieldClassificacao;
    private javax.swing.JTextField jTextFieldCodErp;
    private javax.swing.JTextField jTextFieldDataRec;
    private javax.swing.JTextField jTextFieldDtAmostragem;
    private javax.swing.JTextField jTextFieldDtAprovacao;
    private javax.swing.JTextField jTextFieldFabricacao;
    private javax.swing.JTextField jTextFieldFornecedor;
    private javax.swing.JTextField jTextFieldLoteFor;
    private javax.swing.JTextField jTextFieldNfe;
    private javax.swing.JTextField jTextFieldNrAmostra;
    private javax.swing.JTextField jTextFieldNrEntrada;
    private javax.swing.JTextField jTextFieldProduto;
    private javax.swing.JTextField jTextFieldQuantidade;
    private javax.swing.JTextField jTextFieldValidade;
    // End of variables declaration//GEN-END:variables

}
