import java.sql.ResultSet;
import java.sql.SQLException;


public class MapeadorBDRFita extends MapeadorDeBDRAbstrato {

	public MapeadorBDRFita(String nometabela) {
		super(nometabela);
	}

	@Override
	protected String inserirObjetoNoRegistro(ObjetoPersistente ob) {
		String sql = "insert into \""+getNomeTabela()+"\" ";
		
		Fita fita = (Fita) ob;

		sql += "(oid, genero) values ('"+fita.getOid().getString()+"', '"+
				fita.getGenero()+"')";

		return sql;
	}
	
	protected String atualizarObjetoNoRegistro(ObjetoPersistente ob) {
		Fita fita = (Fita) ob;
		String sql = "genero='"+fita.getGenero()+"'";
		return sql;
	}

	
	protected void recarregarObjetoDoObjeto(ObjetoPersistente a_ser_modificado,
			ObjetoPersistente obtido_do_BD) {		
		
			Fita destino = (Fita) a_ser_modificado, origem = (Fita) obtido_do_BD;
			
			destino.setGenero(origem.getGenero());
	
	}

	@Override
	public ObjetoPersistente mapearResultSetParaObjetoPersistencia(ResultSet rs) {
		Fita fita = null;
		try {
			rs.next();
			fita = new Fita(new Oid(rs.getString(1)));
			fita.setGenero(rs.getString(2));	

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return fita;
	}

}
