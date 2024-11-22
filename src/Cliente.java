
public class Cliente extends ObjetoPersistente {
	private String nome;
	
	public Cliente(Oid oid) {
		super(oid);
	}
	
	public Cliente() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public void materializar(String texto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String desmaterializar() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
