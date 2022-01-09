/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author eversonbrunelli-fit
 */
public class Conexao {
    
    //metodo responsavel pela conexao com o banco
    public static Connection conector(){
        java.sql.Connection conexao = null;
        //a linha abaixo "chama" o driver
        String driver = "com.mysql.jdbc.Driver";
        //Armazenando informações referentes ao banco
        String url = "jdbc:mysql://Servidor/seubanco";
        String user = "seuusuario";
        String password = "senha";
        //Estabelecendo a cenexão com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            return null;
        }
        
    }
    
}
