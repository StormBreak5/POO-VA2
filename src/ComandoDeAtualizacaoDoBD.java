
public class ComandoDeAtualizacaoDoBD extends ComandoDeBDAdapter {

	public ComandoDeAtualizacaoDoBD(ObjetoPersistente ob) {
		super(ob);
		objeto.salvar();
	}

	public boolean executar(){

		if(objeto.tipo() == TipoObjetoEstado.ANTIGO_SUJO){
			return objeto.efetivar();			
		} 
		
		return false;
	}
}



