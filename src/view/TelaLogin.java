/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.sql.*;
import dal.Conexao;
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author eversonbrunelli-fit
 */
public class TelaLogin extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void logar() {

        String sql = "select * from usuario where usuario = ? and senha = ?";
        try {
            //as linhas abaixo preparam a consulta ao banco com referencia
            //ao que foi digitado nas caixas de texto
            pst = conexao.prepareStatement(sql);
            pst.setString(1, jTextFieldUsuario.getText());
            pst.setString(2, jPasswordFieldSenha.getText());
            //a linha abaixo executa a consulta
            rs = pst.executeQuery();
            //Estrutura de decisao (se existe usuario correspondente)
            if (rs.next()) {
                //a linha abaixo exibe o conteudo do campo perfil usuario
                String perfil = rs.getString(4);
                //System.err.println(perfil);
                //A estrutura abaixo faz o tratamento do perfil de usuario
                if (perfil.equals("admin")) {
                    //A linha abaixo exibe o conteudo do campo da tabela
                    TelaPrincipal view = new TelaPrincipal();
                    view.setVisible(true);
                    TelaPrincipal.jMenuItemCadUsu.setEnabled(true);
                    TelaPrincipal.jMenuItemMovApr.setEnabled(true);
                    TelaPrincipal.jMenuItemCadFor.setEnabled(true);
                    TelaPrincipal.jMenuItemCadPro.setEnabled(true);
                    TelaPrincipal.jMenuItemMovEnt.setEnabled(true);
                    TelaPrincipal.jLabelUsuarioPrincipal.setText(rs.getString(2));
                    TelaPrincipal.jLabelUsuarioPrincipal.setForeground(Color.red);
                    this.dispose();
                    conexao.close();
                }
                else { if(perfil.equals("analista")){
                    TelaPrincipal view = new TelaPrincipal();
                    view.setVisible(true);
                    TelaPrincipal.jMenuItemMovApr.setEnabled(true);
                    TelaPrincipal.jButtonEntrada.setEnabled(false);
                    TelaPrincipal.jLabelUsuarioPrincipal.setText(rs.getString(2));
                    TelaPrincipal.jLabelUsuarioPrincipal.setForeground(Color.MAGENTA);
                    TelaPrincipal.jButtonCadPro.setEnabled(false);
                    TelaPrincipal.jButtonCadFor.setEnabled(false);
                    TelaPrincipal.jButtonCadUsu.setEnabled(false);
                    this.dispose();
                    conexao.close();
                    
                }else{
                    TelaPrincipal view = new TelaPrincipal();
                    view.setVisible(true);
                    TelaPrincipal.jMenuItemCadFor.setEnabled(true);
                    TelaPrincipal.jMenuItemCadPro.setEnabled(true);
                    TelaPrincipal.jMenuItemMovEnt.setEnabled(true);
                    TelaPrincipal.jButtonAprovacao.setEnabled(false);
                    TelaPrincipal.jButtonCadUsu.setEnabled(false);
                    TelaPrincipal.jLabelUsuarioPrincipal.setText(rs.getString(2));
                    TelaPrincipal.jLabelUsuarioPrincipal.setForeground(Color.blue);
                    this.dispose();
                    conexao.close();
                }

                    
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválidos!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * Creates new form TelaLogin
     */
    public TelaLogin() {
        initComponents();
        conexao = Conexao.conector();
        getRootPane().setDefaultButton(jButtonEntrar);
        getContentPane().setBackground(Color.GRAY);
        //System.out.println(conexao);
        if (conexao != null) {
            jLabelStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewIcones/ico_conectado.png")));
        } else {
            jLabelStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewIcones/ico_desconectado.png")));

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
        jButtonEntrar = new javax.swing.JButton();
        jTextFieldUsuario = new javax.swing.JTextField();
        jPasswordFieldSenha = new javax.swing.JPasswordField();
        jLabelStatus = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tela Login RRI");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/viewImagens/flora_linha.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 0, 620, 315));

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel2.setText("Usuario:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, -1, -1));

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel3.setText("Senha:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 350, -1, -1));

        jButtonEntrar.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jButtonEntrar.setText("Entrar");
        jButtonEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEntrarActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonEntrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 380, 220, 40));

        jTextFieldUsuario.setBackground(new java.awt.Color(204, 204, 204));
        getContentPane().add(jTextFieldUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 320, 160, -1));

        jPasswordFieldSenha.setBackground(new java.awt.Color(204, 204, 204));
        getContentPane().add(jPasswordFieldSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, 160, -1));

        jLabelStatus.setFont(new java.awt.Font("Comic Sans MS", 0, 10)); // NOI18N
        getContentPane().add(jLabelStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 50, 50));

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 10)); // NOI18N
        jLabel4.setText("Versão 1.2");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 410, -1, -1));

        setSize(new java.awt.Dimension(589, 466));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEntrarActionPerformed
        //Chamando o metodo logar
        logar();
//        getRootPane().setDefaultButton(jButtonEntrar);

    }//GEN-LAST:event_jButtonEntrarActionPerformed

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
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonEntrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JPasswordField jPasswordFieldSenha;
    private javax.swing.JTextField jTextFieldUsuario;
    // End of variables declaration//GEN-END:variables
}