import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.sql.rowset.CachedRowSet;


public abstract class MapeadorDeBDRAbstrato extends MapeadorDePersistenciaAbstrato {

	private String nometabela = null;
	private long contador_oid;
	
	protected String getNomeTabela() { return nometabela;}
	
	public MapeadorDeBDRAbstrato(String nometabela) {
		this.nometabela = nometabela;
		
		inicializarContadorOid();
	}
	
	public Iterator<ObjetoPersistente> obterTodosDoArmazem(){
		String sql = "select distinct oid from \""+getNomeTabela()+"\"";
		ResultSet rs = FabricaConexaoBD.obterInstancia().consulta(sql);
		LinkedList<ObjetoPersistente> llop = new LinkedList<ObjetoPersistente>();
		ObjetoPersistente op = null;
		try {
			while (rs.next()){
				Oid oid = new Oid(rs.getString("oid"));
				if(!estaNaCache(oid)){
					op = obterObjetoDoArmazem(oid);
					op.setEstado(EstadoAntigoLimpo.obterInstancia());
					inserirCache(op);
				} else {
					op = obterCache(oid);
				}
				
				llop.addLast(op);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return llop.iterator();
	}
	
	public abstract ObjetoPersistente mapearResultSetParaObjetoPersistencia(ResultSet rs);
	
	private boolean existeTabela(String nome_tabela){
		String sql = "select count(relname) from pg_class where relname = '"+nome_tabela+"' and relkind='r'";
		ResultSet rs = FabricaConexaoBD.obterInstancia().consulta(sql);
		try {
			if(rs.next()){
				return rs.getInt(1) == 1 ? true : false; 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private void inicializarContadorOid(){
		String sql = null;		
		if(!existeTabela(this.getClass().getSimpleName()+"Oid")){
			sql = "CREATE TABLE \""+this.getClass().getSimpleName()+"Oid\" ("+
					"oid integer NOT NULL DEFAULT 0)";
			FabricaConexaoBD.obterInstancia().inserir(sql);
			sql = "insert into \""+this.getClass().getSimpleName()+"Oid\" (oid) values (1)";
			FabricaConexaoBD.obterInstancia().inserir(sql);
			contador_oid = 1;
			
		} else {
			
			sql = "select * from \""+this.getClass().getSimpleName()+"Oid\"";
			ResultSet rs = FabricaConexaoBD.obterInstancia().consulta(sql);
			try {
				rs.next();
				contador_oid = rs.getLong(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	protected ObjetoPersistente obterObjetoDoArmazem(Oid oid) {
		
		ResultSet registro_BD = obterRegistroBD(oid);
		ObjetoPersistente objeto = null;
		try {
			registro_BD.last();
			if(registro_BD.getRow() != 0){
				registro_BD.beforeFirst();
				objeto =  mapearResultSetParaObjetoPersistencia(registro_BD);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return objeto;
	}
	
	//protected abstract ObjetoPersistente obterObjetoDoRegistro(Oid oid, ResultSet registro_BD);
	
	//Metodo implementado para trabalhar com banco de dados relacional
	private ResultSet obterRegistroBD(Oid oid){
		
		String sql = "select * from \"" + getNomeTabela() + "\" where oid = '"+oid.getString()+"'";
		ResultSet rs =  FabricaConexaoBD.obterInstancia().consulta(sql);
		return rs;
	}
	
	protected Oid obterProximoOidDoArmazem(){
		return obterProximoOidDoBD();
	}
	
	private Oid obterProximoOidDoBD(){
		String sql=null;
		long proximo = contador_oid;
		sql = "update \""+this.getClass().getSimpleName()+"Oid\" set oid="+(++contador_oid);
		FabricaConexaoBD.obterInstancia().atualizar(sql);	
		return new Oid(this.getClass().getSimpleName()+"-"+String.valueOf(proximo));
	}
	
	protected boolean inserirObjetoNoArmazem(ObjetoPersistente ob){
		
		
		return inserirRegistroNoBD(inserirObjetoNoRegistro(ob));
		
	}
	
	protected  boolean inserirRegistroNoBD(String sql){
		return FabricaConexaoBD.obterInstancia().inserir(sql);
	}
	
	protected abstract String inserirObjetoNoRegistro(ObjetoPersistente ob);
	
	protected boolean atualizarObjetoNoArmazem(ObjetoPersistente ob){
		return atualizarRegitroNoBD(ob.getOid(),atualizarObjetoNoRegistro(ob));
	}
	
	private boolean atualizarRegitroNoBD(Oid oid, String sql_valores){		
		String sql = "BEGIN; ";
		sql += "delete from \""+getNomeTabela()+"\" where oid='"+oid.getString()+"';";
		sql += sql_valores + "; ";
		sql += "COMMIT;";
		return FabricaConexaoBD.obterInstancia().atualizar(sql);
	}
	
	protected abstract String atualizarObjetoNoRegistro(ObjetoPersistente ob);
	
	
	protected boolean excluirObjetoNoArmazem(ObjetoPersistente ob){
		return excluirRegitroNoBD(ob.getOid());
	}
	
	private boolean excluirRegitroNoBD(Oid oid){
		String sql = "BEGIN; delete from \""+getNomeTabela()+"\" where oid='"+oid.getString()+"'; COMMIT;";
		return FabricaConexaoBD.obterInstancia().excluir(sql);
	}
	
	protected void recarregarObjetoDoArmazem(ObjetoPersistente ob){
		ObjetoPersistente objeto_recarregado=null;
		objeto_recarregado = obterObjetoDoArmazem(ob.getOid());
		recarregarObjetoDoObjeto(ob, objeto_recarregado);		
	}
	
	protected abstract void recarregarObjetoDoObjeto(ObjetoPersistente a_ser_modificado, 
			ObjetoPersistente obtido_do_BD);
	
	public long obterQuantidadeRegistrosDoArmazem(){
		return FabricaConexaoBD.obterInstancia().qdeRegistros(getNomeTabela());
	}
	
	

}
