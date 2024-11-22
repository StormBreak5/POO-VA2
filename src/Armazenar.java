

import java.util.*;
import java.io.*;

class Armazenar {
	String arquivo;
	int qde_registros;

	
	public Armazenar (String arq){
		arquivo = arq;
		qde_registros = calcularQuantidadeRegistros();
		try{
			this.criarArquivo(arquivo);
		}
		catch (FileNotFoundException e) {}
		catch (IOException e) {}
	}
	
	protected void criarArquivo(String arq) throws IOException{
		File f = new File(arq);
		if(!f.exists()){
			f.createNewFile();
		}
	}
		
	
	
	public void inserir(ItemParaArmazenar ipa)  throws IOException {
		try {			
			/*
			 * Voc��s devem prestar aten����o e raciocinar sobre o relacionamento e
			 * forma de v��nculo entre servi��os de objetos que a proxima linha
			 * estabelece. Pesquise sobre a responsabilidade de cada objeto envolvido
			 * para uma compreens��o plena do c��digo 
			 */
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo, true)));
			out.write(ipa.desmaterializar()+"\n");
			out.flush();
			out.close();
			qde_registros++;
			
		}
		catch (FileNotFoundException e) {
			
		}
		catch (IOException e) {
			
		}
	}
	
	
	public void get(int pos, ItemParaArmazenar ipa) throws Exception {
		
		String texto="";
		BufferedReader in = null;
		
		if (pos < 0 || pos >= qde_registros){
			throw new Exception("<ARMAZENAR-GET> POSIÇÃOO INVÁLIDA>");
		}

		
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo)));
			for (int i = -1; i<pos ; i++){
				texto = in.readLine();
			}
			ipa.materializar(texto); 
			in.close();
		}
		catch (IOException e) {
			System.out.println("Erro");
		}
		
	}
	
	protected int calcularQuantidadeRegistros(){
		
		BufferedReader in = null;
		int i=0;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo)));
			
			for (i = 0; in.readLine() != null ; i++);

			in.close();
		}
		catch (FileNotFoundException e) {}
		catch (IOException e) {}
		
		return i;
	}
	public int quantidadeRegistros(){
		return qde_registros;
	}
	
	public void limparArquivo(){
		File f = new File(arquivo);
		f.delete();
		f = null;
	}
}


