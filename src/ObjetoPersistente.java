
public abstract class ObjetoPersistente extends ItemParaArmazenar {
	private Oid oid;
	protected IObjetoEstado estado=null;
	
	public ObjetoPersistente (Oid oid){
		this.oid = oid;
		estado = EstadoNovo.obterInstancia();
	}
	
	public ObjetoPersistente(){
		this.oid = Persistencia.obterInstancia().obterProximoOid(this.getClass());
		estado = EstadoNovo.obterInstancia();
	}
	
	public String getNomeEstadoTransicao(){
		return estado.getClass().getSimpleName();
	}
	
	public TipoObjetoEstado tipo(){
		return estado.tipo();
	}
	
	public void setEstado(IObjetoEstado proximo_estado){
		estado = proximo_estado;
	}
	
	public boolean efetivar() {
		return estado.efetivar(this);
	}

	
	public boolean excluir() {
		return estado.excluir(this);
	}

	
	public boolean retroceder() {
		return estado.retroceder(this);
	}

	
	public boolean salvar() {
		return estado.salvar(this);
	}
	
	public String getClasse(){
		return this.getClass().getSimpleName();		
	}
	
	public Oid getOid() {
		return oid;
	}


}
