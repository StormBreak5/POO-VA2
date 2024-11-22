
public class ComandoDeBDAdapter implements IComando {
    protected ObjetoPersistente objeto=null;
    
	public ComandoDeBDAdapter(ObjetoPersistente ob) {
		objeto = ob;
	}

	
	public boolean executar(){
		return false;
	}

	
	public boolean desfazer() {
		
		return objeto.retroceder();
	}

}
