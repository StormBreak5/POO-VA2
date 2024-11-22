
public class EstadoAntigoSujo extends ObjetoEstadoAdapter {
	private static EstadoAntigoSujo instancia=null;
	private EstadoAntigoSujo() {
		tipo = TipoObjetoEstado.ANTIGO_SUJO;
	}
	public static EstadoAntigoSujo obterInstancia(){
		if(instancia==null){
			instancia = new EstadoAntigoSujo();
		}
		return instancia;
	}
	
	public boolean efetivar(ObjetoPersistente ob) {	
		boolean resultado = Persistencia.obterInstancia().atualizar(ob);
		if(resultado){
			ob.setEstado(EstadoAntigoLimpo.obterInstancia());
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
	
	public boolean excluir(ObjetoPersistente ob) {
		ob.setEstado(EstadoAntigoExcluido.obterInstancia());
		return true;
	}

}
