package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.fabric.xmlrpc.base.Data;


public class ManipularDatas {

	public static Date enviarDataBD(Date data) {
		SimpleDateFormat form = new SimpleDateFormat("aaaa-mm-dd");
		Date d = form.format(data);
		return new Date (form.format(data));
	}
	
	public static String convDataBanco(String dataSistema) {
	    java.util.Date dataFormatada;
	    String dataBanco = "";//variavel que vai receber a data para o banco
	    try {//Convers�o da data do sistema para formato da data do Banco
	        dataFormatada = new SimpleDateFormat("dd/MM/yyyy").parse(dataSistema);
	        dataBanco = new SimpleDateFormat("yyyy-MM-dd").format(dataFormatada);
	    } catch (ParseException ex) {
	        Logger.getLogger(ManipularDatas.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    return dataBanco;
	}
}
