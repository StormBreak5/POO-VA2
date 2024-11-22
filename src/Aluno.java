
public class Aluno extends ObjetoPersistente {
	private String matricula;
	private String nome;
	
	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Aluno(Oid oid) {
		super(oid);
		// TODO Auto-generated constructor stub
	}

	public Aluno() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void materializar(String texto) {
		setMatricula(valorCampo(texto, 0));
		setNome(valorCampo(texto,1));
	}

	@Override
	public String desmaterializar() {
		return getMatricula()+";"+getNome();
	}

}
