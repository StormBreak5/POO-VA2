
public class ComandoDeInsercaoNoBD extends ComandoDeBDAdapter {

	public ComandoDeInsercaoNoBD(ObjetoPersistente ob) {
		super(ob);
	}
	
public boolean executar(){
		
		/*
		 * Retorna verdadeiro se é EstadoNovo e se o Oid não
		 * é repetido.
		 */
		return objeto.efetivar();
	}

}
