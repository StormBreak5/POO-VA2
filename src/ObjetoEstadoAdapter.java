

public class ObjetoEstadoAdapter implements IObjetoEstado {
	protected TipoObjetoEstado tipo;
	
	public String getNomeEstadoTransicao(){
		return this.getClass().getSimpleName();
	}
	
	public TipoObjetoEstado tipo(){
		return tipo;
	}
	
	public ObjetoEstadoAdapter() {}

	
	public boolean efetivar(ObjetoPersistente ob) {return false;}

	
	public boolean excluir(ObjetoPersistente ob) { return false;}

	
	public boolean retroceder(ObjetoPersistente ob) {return false;}

	
	public boolean salvar(ObjetoPersistente ob) {return false;}

}
