import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;


public abstract class MapeadorDePersistenciaAbstrato implements IMapeador {
	
	private Hashtable<String, ObjetoPersistente> cache = new Hashtable<String, ObjetoPersistente>();
	
	protected boolean estaNaCache(Oid oid){
		return cache.containsKey(oid.getString());
	}
	
	protected ObjetoPersistente obterCache(Oid oid){
		return cache.get(oid.getString());
	}
	
	protected void inserirCache(ObjetoPersistente objeto){
		cache.put(objeto.getOid().getString(), objeto);
	}

	public ObjetoPersistente obter(Oid oid){ 
		ObjetoPersistente objeto;
		  
		if (!cache.containsKey(oid.getString())){
			objeto = obterObjetoDoArmazem(oid);
			if(objeto != null){
				objeto.setEstado(EstadoAntigoLimpo.obterInstancia());
				cache.put(oid.getString(), objeto);
			}
		} else {
			objeto = cache.get(oid.getString());
		}
		
		
		return objeto; 	
	}
	
	protected abstract ObjetoPersistente obterObjetoDoArmazem(Oid oid);

	public Oid obterProximoOid(){
		return obterProximoOidDoArmazem();
	}
	
	protected abstract Oid obterProximoOidDoArmazem();
	
	
	public boolean inserir(ObjetoPersistente ob) {
		boolean realizou = false;
		if(obter(ob.getOid())==null){
			realizou = inserirObjetoNoArmazem(ob);
			if(realizou){
				cache.put(ob.getOid().getString(), ob);
			}
			return realizou;
		}
		
		return realizou;
	}
	
	protected abstract boolean inserirObjetoNoArmazem(ObjetoPersistente ob);

	
	public boolean atualizar(ObjetoPersistente ob) {
		ObjetoPersistente aux=null;
		boolean realizou = false;
		if(obter(ob.getOid())!=null){
			realizou = atualizarObjetoNoArmazem(ob);
			if(realizou){
				cache.remove(ob.getOid().getString());
				cache.put(ob.getOid().getString(), ob);
			}
			return realizou;
		}
		return realizou;
	}
	
	protected abstract boolean atualizarObjetoNoArmazem(ObjetoPersistente ob);

	
	public boolean excluir(ObjetoPersistente ob) {
		boolean realizou = false;
		if(obter(ob.getOid())!=null){
			realizou = excluirObjetoNoArmazem(ob);
			if(realizou){
				cache.remove(ob.getOid().getString());
			}
			return realizou;
		}
		return realizou;
	}
	
	protected abstract boolean excluirObjetoNoArmazem(ObjetoPersistente ob);
	
	public boolean recarregar(ObjetoPersistente ob){ 
		ObjetoPersistente objeto;
		  
		if (cache.containsKey(ob.getOid().getString())){
			recarregarObjetoDoArmazem(ob);
			cache.remove(ob.getOid().getString());
			cache.put(ob.getOid().getString(), ob);
		} else {
			return false;
		}
		
		
		return true; 	
	}
	
	protected abstract void recarregarObjetoDoArmazem(ObjetoPersistente ob);
	
	public Iterator<ObjetoPersistente> obterTodos(){
		Iterator<ObjetoPersistente> it = obterTodosDoArmazem();
		LinkedList<ObjetoPersistente> llop = new LinkedList<ObjetoPersistente>();
		while(it.hasNext()){
			ObjetoPersistente op = it.next();
			
			if(!cache.containsKey(op.getOid().getString())){
				llop.addLast(op);
				op.setEstado(EstadoAntigoLimpo.obterInstancia());
				cache.put(op.getOid().getString(), op);				
			} else {
				llop.addLast(cache.get(op.getOid().getString()));
			}
		}
		
		return llop.iterator();
	}
	
	public abstract Iterator<ObjetoPersistente> obterTodosDoArmazem();
	
	public long quantidadeRegistros(){
		return obterQuantidadeRegistrosDoArmazem();
	}
	
	public abstract long obterQuantidadeRegistrosDoArmazem();

}
