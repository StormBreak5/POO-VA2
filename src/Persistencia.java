import java.util.Iterator;


public class Persistencia {

	private static Persistencia persistencia = null;

	private Persistencia(){

	}

	public static Persistencia obterInstancia(){
		if(persistencia == null){
			persistencia = new Persistencia();
		}

		return persistencia;
	}

	public ObjetoPersistente obter(Oid oid, Class<?> classeDePersistencia){
		return FabricaDeMapeador.obterInstancia().obter(classeDePersistencia.getSimpleName()).obter(oid);
	}

	public Oid obterProximoOid(Class<?> classeDePersistencia){ 
		return FabricaDeMapeador.obterInstancia().obter(classeDePersistencia.getSimpleName()).obterProximoOid();
	}

	public boolean inserir(ObjetoPersistente ob){

		return FabricaDeMapeador.obterInstancia().obter(ob.getClasse()).inserir(ob);
	}

	public boolean atualizar(ObjetoPersistente ob){

		return FabricaDeMapeador.obterInstancia().obter(ob.getClasse()).atualizar(ob);
	}

	public boolean excluir(ObjetoPersistente ob){

		return FabricaDeMapeador.obterInstancia().obter(ob.getClasse()).excluir(ob);
	}

	public boolean recarregar(ObjetoPersistente ob){
		return FabricaDeMapeador.obterInstancia().obter(ob.getClasse()).recarregar(ob);
	}

	public Iterator<?> obterTodos(Class<?> classeDePersistencia){
		return FabricaDeMapeador.obterInstancia().obter(classeDePersistencia.getSimpleName()).obterTodos();
	}
	
	public long quantidadeRegistro(Class<?> classeDePersistencia){
		return FabricaDeMapeador.obterInstancia().obter(classeDePersistencia.getSimpleName()).quantidadeRegistros();
	}
}

