import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;


public class Matricula extends ObjetoPersistente {
	private Aluno aluno;
	private LinkedList<Disciplina> disciplinas;
	
	
	
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Iterator<Disciplina> getDisciplinas() {
		return disciplinas.iterator();
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplinas.add(disciplina);
	}
	public void setDisciplinas(Iterator<Disciplina> itd ){
		disciplinas.clear();
		disciplinas.addAll((Collection<Disciplina>)itd);
	}

	public Matricula(Oid oid) {
		super(oid);
		disciplinas = new LinkedList<Disciplina>();
		// TODO Auto-generated constructor stub
	}

	public Matricula() {
		super();
		disciplinas = new LinkedList<Disciplina>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void materializar(String texto) {
		boolean interromper = false;
		Iterator<Aluno> ita = (Iterator<Aluno>) Persistencia.obterInstancia().obterTodos(Aluno.class);
		//carregar o atributo Aluno
		while(!interromper && ita.hasNext()){
			aluno = ita.next();
			if(aluno.getMatricula().equalsIgnoreCase(valorCampo(texto, 0))){
				interromper = true;
			}
		}
		
		//carregar lista de disciplinas
		
		int quantidade_campos = quantidadeCampos(texto); 
		for(int i=1; i< quantidade_campos; i++){
			interromper = false;
			Iterator<Disciplina> itd = (Iterator<Disciplina>) Persistencia.obterInstancia().obterTodos(Disciplina.class);
			while(!interromper && itd.hasNext()){
				Disciplina disciplina = itd.next();
				if(disciplina.getCodigo().equalsIgnoreCase(valorCampo(texto, i))){
					disciplinas.add(disciplina);
					interromper = true;
				}
			}		
		}
	}

	@Override
	public String desmaterializar() {
		String objeto;
		objeto = getAluno().getMatricula();
		for(int i=0; i < disciplinas.size(); i++){
			objeto += (";"+disciplinas.get(i).getCodigo());
		}
		
		return objeto;
		
	}

}
