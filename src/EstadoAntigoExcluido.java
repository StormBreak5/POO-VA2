
public class EstadoAntigoExcluido extends ObjetoEstadoAdapter {
	private static EstadoAntigoExcluido instancia=null;
	private EstadoAntigoExcluido() {
		tipo = TipoObjetoEstado.ANTIGO_EXCLUIDO;
	}
	public static EstadoAntigoExcluido obterInstancia(){
		if(instancia==null){
			instancia = new EstadoAntigoExcluido();
		}
		return instancia;
	}
	
	public boolean efetivar(ObjetoPersistente ob) {	
		boolean resultado = Persistencia.obterInstancia().excluir(ob);
		if(resultado){
			ob.setEstado(EstadoExcluido.obterInstancia());
		}
		return resultado;		
	}
	
	public boolean retroceder(ObjetoPersistente ob) {
		boolean resultado = Persistencia.obterInstancia().recarregar(ob);
		if(resultado){
			ob.setEstado(EstadoAntigoLimpo.obterInstancia());
		}
		return resultado;
	}

}
