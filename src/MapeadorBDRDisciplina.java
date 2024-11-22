import java.sql.ResultSet;
import java.sql.SQLException;


public class MapeadorBDRDisciplina extends MapeadorDeBDRAbstrato {

	public MapeadorBDRDisciplina(String nometabela) {
		super(nometabela);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String inserirObjetoNoRegistro(ObjetoPersistente ob) {
		
		String sql = "insert into \""+getNomeTabela()+"\" ";
		Disciplina disciplina = (Disciplina) ob;

		sql += "(oid, codigo, descricao, ch) values ('"+
					disciplina.getOid().getString()+"', '"+
						disciplina.getCodigo()+"', '"+
					    disciplina.getDescricao()+"', "+
						disciplina.getCh()+")";


		return sql;
	}

	@Override
	protected String atualizarObjetoNoRegistro(ObjetoPersistente ob) {
		return inserirObjetoNoRegistro(ob);
	}

	@Override
	protected void recarregarObjetoDoObjeto(ObjetoPersistente a_ser_modificado,
			ObjetoPersistente obtido_do_BD) {
		Disciplina destino = (Disciplina) a_ser_modificado, origem = (Disciplina) obtido_do_BD;

		destino.setCodigo(origem.getCodigo());
		destino.setDescricao(origem.getDescricao());
		destino.setCh(origem.getCh());

	}

	@Override
	public ObjetoPersistente mapearResultSetParaObjetoPersistencia(ResultSet rs) {
		Disciplina disciplina = null;
		try {
			rs.next();
			disciplina = new Disciplina(new Oid(rs.getString(1)));
			disciplina.setCodigo(rs.getString(2));
			disciplina.setDescricao(rs.getString(3));
			disciplina.setCh(rs.getInt(4));				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return disciplina;
	}

}
