import java.sql.ResultSet;
import java.sql.SQLException;


public class MapeadorBDRCliente extends MapeadorDeBDRAbstrato {

	public MapeadorBDRCliente(String nometabela) {
		super(nometabela);
	}


	@Override
	protected String inserirObjetoNoRegistro(ObjetoPersistente ob) {
		String sql = "insert into \""+getNomeTabela()+"\" ";
		Cliente cliente = (Cliente) ob;
		
		sql += "(oid, nome) values ('"+cliente.getOid().getString()+"', '"+
					cliente.getNome()+"')";
		
		
		return sql;
	}

	
	protected String atualizarObjetoNoRegistro(ObjetoPersistente ob) {
		Cliente cliente = (Cliente) ob;
		String sql = "nome='"+cliente.getNome()+"'";
		return sql;
	}

	
	protected void recarregarObjetoDoObjeto(ObjetoPersistente a_ser_modificado,
			ObjetoPersistente obtido_do_BD) {
		Cliente destino = (Cliente) a_ser_modificado, origem = (Cliente) obtido_do_BD;
		
		destino.setNome(origem.getNome());
		
	}

	@Override
	public ObjetoPersistente mapearResultSetParaObjetoPersistencia(ResultSet rs) {
		Cliente cliente = null;
		try {
			rs.next();
			cliente = new Cliente(new Oid(rs.getString(1)));
			cliente.setNome(rs.getString(2));	

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return cliente;
	}

}
