import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;


public class MapeadorBDRMatricula extends MapeadorDeBDRAbstrato {

	public MapeadorBDRMatricula(String nometabela) {
		super(nometabela);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String inserirObjetoNoRegistro(ObjetoPersistente ob) {
		String sql = "insert into \""+getNomeTabela()+"\" ";
		Matricula matricula = (Matricula) ob;
		String oid = matricula.getOid().getString();
		String oid_aluno = matricula.getAluno().getOid().getString();
		
		 sql += "(oid, oid_aluno, oid_disciplina) values ";
		Iterator<Disciplina> itd = matricula.getDisciplinas();
		String valores="";
		
		while(itd.hasNext()){
			Disciplina disciplina = itd.next();
			valores += "('"+oid+"', '"+oid_aluno+"', '"+disciplina.getOid().getString()+"')";
			if(itd.hasNext()){
				valores +=", ";
			}
		}
		
		sql = sql + valores;

		return sql;
	}

	
	@Override
	protected String atualizarObjetoNoRegistro(ObjetoPersistente ob) {
		// TODO Auto-generated method stub
		return inserirObjetoNoRegistro(ob);
	}

	@Override
	protected void recarregarObjetoDoObjeto(ObjetoPersistente a_ser_modificado,
			ObjetoPersistente obtido_do_BD) {
		// TODO Auto-generated method stub
		Matricula destino = (Matricula) a_ser_modificado, origem = (Matricula) obtido_do_BD;

		destino.setAluno(origem.getAluno());
		destino.setDisciplinas(origem.getDisciplinas());
	}

	@Override
	public ObjetoPersistente mapearResultSetParaObjetoPersistencia(ResultSet rs) {
		Matricula matricula=null;
		Aluno aluno = null;
		
		
		try{
			rs.next();
			matricula = new Matricula(new Oid(rs.getString(1)));
			String oid_aluno = rs.getString(2);
			boolean interromper = false;
			Iterator<Aluno> ita = (Iterator<Aluno>) Persistencia.obterInstancia().obterTodos(Aluno.class);
			while(!interromper && ita.hasNext()){
				aluno = ita.next();
				if(aluno.getOid().getString().equalsIgnoreCase(oid_aluno)){
					matricula.setAluno(aluno);
					interromper = true;
				}
			}			
						 
			do {
				Iterator<Disciplina> itd = (Iterator<Disciplina>) Persistencia.obterInstancia().obterTodos(Disciplina.class);
				interromper = false;
				String oid_disciplina = rs.getString(3);
				while(!interromper && itd.hasNext()){
					Disciplina disciplina = itd.next();
					if(disciplina.getOid().getString().equalsIgnoreCase(oid_disciplina)){
						matricula.setDisciplina(disciplina);
						interromper = true;
					}
				}
			} while(rs.next());
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return matricula;
	}

}
