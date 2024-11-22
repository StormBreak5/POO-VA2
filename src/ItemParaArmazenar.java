

import java.util.*;
import java.io.*;


abstract class ItemParaArmazenar implements Serializable {
	
	protected String valorCampo(String texto, int pos){
		String [] campos = texto.split(";");
		return campos[pos];
	}
	
	protected int quantidadeCampos(String texto){
		String [] campos = texto.split(";");
		return campos.length;
	}
	
	public abstract void materializar(String texto);
	public abstract String desmaterializar();
	
}

