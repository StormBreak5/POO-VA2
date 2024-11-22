import java.util.Iterator;


public interface IMapeador {
	public ObjetoPersistente obter(Oid oid); 
	public Oid obterProximoOid();
	public boolean inserir(ObjetoPersistente ob);
	public boolean atualizar(ObjetoPersistente ob);
	public boolean excluir(ObjetoPersistente ob);
	public boolean recarregar(ObjetoPersistente ob);
	public Iterator<ObjetoPersistente> obterTodos();
	public long quantidadeRegistros();
}
