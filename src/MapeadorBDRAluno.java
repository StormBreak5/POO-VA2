import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class MapeadorBDRAluno extends MapeadorDeBDRAbstrato {

	public MapeadorBDRAluno(String nometabela) {
		super(nometabela);
		// TODO Auto-generated constructor stub
	}


	@Override
	protected String inserirObjetoNoRegistro(ObjetoPersistente ob) {
		String sql = "insert into \""+getNomeTabela()+"\" ";
		
		Aluno aluno = (Aluno) ob;

		sql += "(oid, matricula, nome) values ('"+
				aluno.getOid().getString()+"', '"+
				aluno.getMatricula() +"', '"+
				aluno.getNome()+"')";

		return sql;
	}

	@Override
	protected String atualizarObjetoNoRegistro(ObjetoPersistente ob) {
		return inserirObjetoNoRegistro(ob);
	}

	@Override
	protected void recarregarObjetoDoObjeto(ObjetoPersistente a_ser_modificado,
			ObjetoPersistente obtido_do_BD) {
		Aluno destino = (Aluno) a_ser_modificado, origem = (Aluno) obtido_do_BD;

		destino.setMatricula(origem.getMatricula());
		destino.setNome(origem.getNome());
	}

	@Override
	public ObjetoPersistente mapearResultSetParaObjetoPersistencia(ResultSet rs) {
		Aluno aluno=null;
		
		try {
			rs.next();
			aluno = new Aluno(new Oid(rs.getString(1)));
			aluno.setMatricula(rs.getString(2));
			aluno.setNome(rs.getString(3));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return aluno;
	}

}
