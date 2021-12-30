/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author eversonbrunelli-fit
 */
import java.sql.*;
import dal.Conexao;
import java.awt.Color;
import javax.swing.JOptionPane;
//Importa recursos da bibilioteca rs2
import net.proteanit.sql.DbUtils;

public class TelaCadProduto extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCadProduto
     */
    public TelaCadProduto() {
        initComponents();
        conexao = Conexao.conector();
        getContentPane().setBackground(Color.GRAY);
    }
    
    //Método pesquisa lote no banco
    private void valida_cod_repetido() {

        String sql = "Select * from produto where coderp = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jTextFieldCodigoerpTelaCadPro.getText());
            rs = pst.executeQuery();

            if (rs.next()) {

                String coderp = rs.getString("coderp");
                if (coderp.equals(jTextFieldCodigoerpTelaCadPro.getText())) {
                    JOptionPane.showMessageDialog(null, "Código ja cadastrado em outro produto!!");

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    //Metodo Adicionar
    private void adicionar() {

        String sql = "insert into produto(produto, coderp, classificacao) values (?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jTextFieldProdutoTelaCadPro.getText());
            pst.setString(2, jTextFieldCodigoerpTelaCadPro.getText());
            pst.setString(3, jTextFieldClassificacaoTelaCadPro.getText());

            //Validação dos campos obrigatórios
            if (((jTextFieldProdutoTelaCadPro.getText().isEmpty()) || (jTextFieldCodigoerpTelaCadPro.getText().isEmpty())) || (jTextFieldClassificacaoTelaCadPro.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {

                //A linha abaixo insere produto no banco com os dados fornecidos
                //A estrutura abaixo é testar e confirma a insercao
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                    jTextFieldProdutoTelaCadPro.setText(null);
                    jTextFieldCodigoerpTelaCadPro.setText(null);
                    jTextFieldClassificacaoTelaCadPro.setText(null);

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }

    }
    //Metodo pesquisa avancada
    private void pesquisa_produto(){
    
        String sql = "select * from produto where produto like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteúdo da caixa de pesquisa para o ?
            //atencao ao % que é a continuação da string sql
            pst.setString(1, jTextFieldPesqTelaCadPro.getText() + "%");
            rs = pst.executeQuery();
            // A linha abaixo usa a bibilioteca rs2.jar para preencher a tabela
            jTableProdutosTelaCadPro.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //Metodo para setar os campos do formulario com o campo da tabela selecionado
    public void setar_campos(){
    
        int setar = jTableProdutosTelaCadPro.getSelectedRow();
        jTextFieldIdprodutoTelaCadPro.setText(jTableProdutosTelaCadPro.getModel().getValueAt(setar, 0).toString());
        jTextFieldProdutoTelaCadPro.setText(jTableProdutosTelaCadPro.getModel().getValueAt(setar, 1).toString());
        jTextFieldCodigoerpTelaCadPro.setText(jTableProdutosTelaCadPro.getModel().getValueAt(setar, 2).toString());
        jTextFieldClassificacaoTelaCadPro.setText(jTableProdutosTelaCadPro.getModel().getValueAt(setar, 3).toString());
        // A linha abaixo desabilita o botão adicionar
        jButtonCreateTelaCadPro.setEnabled(false);
    }
    //Metodo Alterar
    private void alterar(){
        
        String sql = "update produto set produto = ?, coderp = ?, classificacao = ? where idproduto = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jTextFieldProdutoTelaCadPro.getText());
            pst.setString(2, jTextFieldCodigoerpTelaCadPro.getText());
            pst.setString(3, jTextFieldClassificacaoTelaCadPro.getText());
            pst.setString(4, jTextFieldIdprodutoTelaCadPro.getText());
             if (((jTextFieldProdutoTelaCadPro.getText().isEmpty()) || (jTextFieldCodigoerpTelaCadPro.getText().isEmpty())) || (jTextFieldClassificacaoTelaCadPro.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {

                //A linha abaixo altera usuario no banco com os dados fornecidos
                //A estrutura abaixo é testar e confirma a alteracao
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
                    jTextFieldProdutoTelaCadPro.setText(null);
                    jTextFieldCodigoerpTelaCadPro.setText(null);
                    jTextFieldClassificacaoTelaCadPro.setText(null);
                    jTextFieldProdutoTelaCadPro.setText(null);
                    jButtonCreateTelaCadPro.setEnabled(true);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //Metodo Deletar

    public void deletar() {
        //Estrutura confirma remoção
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o produto?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from produto where idproduto = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, jTextFieldIdprodutoTelaCadPro.getText());
                int removido = pst.executeUpdate();
                if (removido > 0) {
                    JOptionPane.showMessageDialog(null, "Produto excluído com sucesso!");
                    jTextFieldIdprodutoTelaCadPro.setText(null);
                    jTextFieldCodigoerpTelaCadPro.setText(null);
                    jTextFieldProdutoTelaCadPro.setText(null);
                    jTextFieldClassificacaoTelaCadPro.setText(null);
                    jButtonCreateTelaCadPro.setEnabled(true);
                }
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldCodigoerpTelaCadPro = new javax.swing.JTextField();
        jTextFieldProdutoTelaCadPro = new javax.swing.JTextField();
        jTextFieldClassificacaoTelaCadPro = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButtonCreateTelaCadPro = new javax.swing.JButton();
        jButtonUpdateTelaCadPro = new javax.swing.JButton();
        jButtonDeleteTelaCadPro = new javax.swing.JButton();
        jTextFieldPesqTelaCadPro = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProdutosTelaCadPro = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldIdprodutoTelaCadPro = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Produto");
        setResizable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewIcones/produto_new_2.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel2.setText("* CodigoERP");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel3.setText("* Produto");

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel4.setText("* Classificação");

        jTextFieldCodigoerpTelaCadPro.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldCodigoerpTelaCadPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextFieldCodigoerpTelaCadProMouseExited(evt);
            }
        });

        jTextFieldProdutoTelaCadPro.setBackground(new java.awt.Color(204, 204, 204));

        jTextFieldClassificacaoTelaCadPro.setBackground(new java.awt.Color(204, 204, 204));

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 0, 10)); // NOI18N
        jLabel5.setText("* Campos Obrigatórios");

        jButtonCreateTelaCadPro.setForeground(new java.awt.Color(204, 204, 204));
        jButtonCreateTelaCadPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewIcones/adicionar_new.png"))); // NOI18N
        jButtonCreateTelaCadPro.setPreferredSize(new java.awt.Dimension(80, 80));
        jButtonCreateTelaCadPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateTelaCadProActionPerformed(evt);
            }
        });

        jButtonUpdateTelaCadPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewIcones/alterar_new.png"))); // NOI18N
        jButtonUpdateTelaCadPro.setPreferredSize(new java.awt.Dimension(80, 80));
        jButtonUpdateTelaCadPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateTelaCadProActionPerformed(evt);
            }
        });

        jButtonDeleteTelaCadPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewIcones/deletar_new_2.png"))); // NOI18N
        jButtonDeleteTelaCadPro.setPreferredSize(new java.awt.Dimension(80, 80));
        jButtonDeleteTelaCadPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteTelaCadProActionPerformed(evt);
            }
        });

        jTextFieldPesqTelaCadPro.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldPesqTelaCadPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesqTelaCadProKeyReleased(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewIcones/search_new_3.png"))); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 80));

        jTableProdutosTelaCadPro.setBackground(new java.awt.Color(204, 204, 204));
        jTableProdutosTelaCadPro.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTableProdutosTelaCadPro.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jTableProdutosTelaCadPro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTableProdutosTelaCadPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableProdutosTelaCadProMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableProdutosTelaCadPro);

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel7.setText("* CódigoRRI");

        jTextFieldIdprodutoTelaCadPro.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldIdprodutoTelaCadPro.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel8.setText("Adicionar");

        jLabel9.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel9.setText("Deletar");

        jLabel10.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel10.setText("Alterar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldProdutoTelaCadPro, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldClassificacaoTelaCadPro, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextFieldIdprodutoTelaCadPro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                        .addComponent(jTextFieldCodigoerpTelaCadPro, javax.swing.GroupLayout.Alignment.LEADING))))
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane1)
                            .addComponent(jTextFieldPesqTelaCadPro))
                        .addComponent(jLabel7))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jLabel8)
                            .addGap(92, 92, 92)
                            .addComponent(jLabel10)
                            .addGap(98, 98, 98)
                            .addComponent(jLabel9))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButtonCreateTelaCadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(62, 62, 62)
                            .addComponent(jButtonUpdateTelaCadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(70, 70, 70)
                            .addComponent(jButtonDeleteTelaCadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(19, 19, 19))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextFieldPesqTelaCadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextFieldIdprodutoTelaCadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldCodigoerpTelaCadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldProdutoTelaCadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldClassificacaoTelaCadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonCreateTelaCadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonUpdateTelaCadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonDeleteTelaCadPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(11, 11, 11))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        setSize(new java.awt.Dimension(602, 495));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCreateTelaCadProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateTelaCadProActionPerformed
        adicionar();
    }//GEN-LAST:event_jButtonCreateTelaCadProActionPerformed

    private void jButtonUpdateTelaCadProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateTelaCadProActionPerformed
        alterar();
    }//GEN-LAST:event_jButtonUpdateTelaCadProActionPerformed
//O evento abaixo é enquanto for digitando faça
    private void jTextFieldPesqTelaCadProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesqTelaCadProKeyReleased
        pesquisa_produto();
    }//GEN-LAST:event_jTextFieldPesqTelaCadProKeyReleased
//Quando clicar na tabela e selecionar uma linha sera carregado nos campos do formulario
    private void jTableProdutosTelaCadProMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProdutosTelaCadProMouseClicked
        //Chama metodo setar campos
        setar_campos();
    }//GEN-LAST:event_jTableProdutosTelaCadProMouseClicked

    private void jButtonDeleteTelaCadProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteTelaCadProActionPerformed
        deletar();
    }//GEN-LAST:event_jButtonDeleteTelaCadProActionPerformed

    private void jTextFieldCodigoerpTelaCadProMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldCodigoerpTelaCadProMouseExited
        valida_cod_repetido();
    }//GEN-LAST:event_jTextFieldCodigoerpTelaCadProMouseExited

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
            java.util.logging.Logger.getLogger(TelaCadProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadProduto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCreateTelaCadPro;
    private javax.swing.JButton jButtonDeleteTelaCadPro;
    private javax.swing.JButton jButtonUpdateTelaCadPro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableProdutosTelaCadPro;
    private javax.swing.JTextField jTextFieldClassificacaoTelaCadPro;
    private javax.swing.JTextField jTextFieldCodigoerpTelaCadPro;
    private javax.swing.JTextField jTextFieldIdprodutoTelaCadPro;
    private javax.swing.JTextField jTextFieldPesqTelaCadPro;
    private javax.swing.JTextField jTextFieldProdutoTelaCadPro;
    // End of variables declaration//GEN-END:variables
}
