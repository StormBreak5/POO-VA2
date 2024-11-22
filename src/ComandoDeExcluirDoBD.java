
public class ComandoDeExcluirDoBD extends ComandoDeBDAdapter {

	public ComandoDeExcluirDoBD(ObjetoPersistente ob) {
		super(ob);
		objeto.excluir();
	}

	public boolean executar(){

		if(objeto.tipo() == TipoObjetoEstado.ANTIGO_EXCLUIDO){
			return objeto.efetivar();			
		} 
		
		return false;
	}

}

