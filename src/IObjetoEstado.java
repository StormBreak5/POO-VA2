
public interface IObjetoEstado {
	public boolean efetivar(ObjetoPersistente ob);
	public boolean excluir(ObjetoPersistente ob);
	public boolean retroceder(ObjetoPersistente ob);
	public boolean salvar(ObjetoPersistente ob);
	public TipoObjetoEstado tipo();
}
