/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author eversonbrunelli-fit
 */
public class DataFormatada {
    
//Metodo teste data e hora sistema
   
    public static void main(String[] args){
    
        Date data = new Date();
        SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dataFormatada = formatar.format(data);
        System.out.println(dataFormatada);
    }
    
}
