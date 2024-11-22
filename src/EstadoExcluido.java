

public class EstadoExcluido extends ObjetoEstadoAdapter {

	private static EstadoExcluido instancia=null;
	private EstadoExcluido() {
		tipo = TipoObjetoEstado.EXCLUIDO;
	}
	public static EstadoExcluido obterInstancia(){
		if(instancia==null){
			instancia = new EstadoExcluido();
		}
		return instancia;
	}
	
	public boolean retroceder(ObjetoPersistente ob) {
		ob.setEstado(EstadoNovo.obterInstancia());
		return ob.efetivar();
	}

}
