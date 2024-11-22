
public class EstadoAntigoLimpo extends ObjetoEstadoAdapter {

	private static EstadoAntigoLimpo instancia=null;
	private EstadoAntigoLimpo() {
		tipo = TipoObjetoEstado.ANTIGO_LIMPO;
	}
	public static EstadoAntigoLimpo obterInstancia(){
		if(instancia==null){
			instancia = new EstadoAntigoLimpo();
		}
		return instancia;
	}
	
	public boolean excluir(ObjetoPersistente ob) {
		ob.setEstado(EstadoAntigoExcluido.obterInstancia());
		return true;
	}
	
	public boolean salvar(ObjetoPersistente ob) {
		ob.setEstado(EstadoAntigoSujo.obterInstancia());
		return true;
	}
	
	public boolean retroceder(ObjetoPersistente ob){
		ob.setEstado(EstadoAntigoExcluido.obterInstancia());
		ob.efetivar();
		ob.setEstado(EstadoNovo.obterInstancia());
		return true;
	}

}
