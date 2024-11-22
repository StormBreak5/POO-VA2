
public class Disciplina extends ObjetoPersistente {
	private String codigo;
	private String descricao;
	private int ch;
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getCh() {
		return ch;
	}

	public void setCh(int ch) {
		this.ch = ch;
	}
	
	public Disciplina(Oid oid) {
		super(oid);
		// TODO Auto-generated constructor stub
	}

	public Disciplina() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void materializar(String texto) {
		setCodigo(valorCampo(texto,0));
		setDescricao(valorCampo(texto, 1));
		setCh(Integer.parseInt(valorCampo(texto, 2)));
	}

	@Override
	public String desmaterializar() {
		String objeto = getCodigo()+";"+getDescricao()+";"+getCh();
		return objeto;
	}

}
