
public class EstadoNovo extends ObjetoEstadoAdapter {

	private static EstadoNovo instancia=null;
	private EstadoNovo() {
		tipo = TipoObjetoEstado.NOVO;
	}
	public static EstadoNovo obterInstancia(){
		if(instancia==null){
			instancia = new EstadoNovo();
		}
		return instancia;
	}
	
	public boolean efetivar(ObjetoPersistente ob) {	
		boolean resultado = Persistencia.obterInstancia().inserir(ob);
		if(resultado){
			ob.setEstado(EstadoAntigoLimpo.obterInstancia());
		}
		return resultado;		
	}

}
