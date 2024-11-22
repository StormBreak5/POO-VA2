import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import excecao.MapeadorNaoEncontradoException;
import excecao.ParametroNuloException;


//Singleton

public class FabricaDeMapeador {
	
	private Hashtable<String, IMapeador> colecaoMapeadores = null;

	static FabricaDeMapeador instancia = null;

	private FabricaDeMapeador(){
		colecaoMapeadores = new Hashtable<String, IMapeador>();
		montarColecaoMapeadores();
	}
	
	private void montarColecaoMapeadores(){
		File f= new File("configuracao/configuracao.xml");
		SAXBuilder builder = new SAXBuilder();
		Document doc=null;
		try {
			doc = builder.build(f);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Element root = (Element) doc.getRootElement();
		List<Element> mapeadores = root.getChildren();
		Iterator<Element> i = mapeadores.iterator();
		
		while(i.hasNext()){
			
			Element mapeador = i.next();
			
			/* --- informativo para perceber a leitura do arquivo xml
			
			System.out.println("Tipo:" + mapeador.getAttributeValue("tipo"));
			System.out.println("Chave:" + mapeador.getChildText("chave"));
			System.out.println("Mapa:" + mapeador.getChildText("mapa"));
			System.out.println("Tabela do Banco de Dados:" + mapeador.getChildText("tabela"));
			System.out.println();
			// --- ----------------------------------------------------
			*/
			try {
				
				
				if(mapeador.getAttributeValue("tipo").equalsIgnoreCase("BDR")){
					Class<MapeadorDeBDRAbstrato> cimp = (Class<MapeadorDeBDRAbstrato>) Class.forName(mapeador.getChildText("mapa"));
					Constructor<MapeadorDeBDRAbstrato> cons = cimp.getConstructor(String.class);					
					MapeadorDeBDRAbstrato mbdra = cons.newInstance(mapeador.getChildText("tabela"));
						colecaoMapeadores.put(mapeador.getChildText("chave"), mbdra );
				}
				
				
			
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e){
				
			}
			
		}
		
	}
	
	public static FabricaDeMapeador obterInstancia(){
		if(instancia == null){
			instancia = new FabricaDeMapeador();
		}
		return instancia;
	}
	
	public IMapeador obter(String classeDePersistencia){
		IMapeador imap = colecaoMapeadores.get(classeDePersistencia);
	    if(imap == null) throw new MapeadorNaoEncontradoException("FabricaDeMapeadores:classe "+classeDePersistencia+
	    		" não encontrada");
		return imap;
	}
}
