
public class Fita extends ObjetoPersistente {
    private String genero;
	public Fita(Oid oid) {
		super(oid);
	}
	
	public Fita() {
		super();
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
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
