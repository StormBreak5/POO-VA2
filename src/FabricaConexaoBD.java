import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class FabricaConexaoBD {
	private static FabricaConexaoBD instancia = null;
	protected Connection conexao = null;
	private FabricaConexaoBD() {
		try {
			Class.forName("org.postgresql.Driver");
		}
		catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
		
		try {
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/FrameWorkPersistencia","postgres","123456");

		} catch (Exception e){
			System.out.println("Exceção Geral !!");
		}
		
	}
	
	public ResultSet consulta(String sql){

		try{
			
			PreparedStatement  stm = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stm.executeQuery();
			//stm.close();
			return rs;

		} catch (SQLException e){
			System.out.println("Problema Dao SQL - consultar.");
			System.out.println(e.toString());
		}

		return null;
	}

	public boolean inserir(String sql){

		try{
			PreparedStatement  stm = conexao.prepareStatement(sql);
			stm.executeUpdate();
			stm.close();
			

		} catch (SQLException e){
			System.out.println("Problema Dao SQL - inserir.");
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null, sql);
			return false;
		}
		

		return true;
	}

	public boolean atualizar(String sql){
		int x;
		try{
			PreparedStatement  stm = conexao.prepareStatement(sql);
			x=stm.executeUpdate();
			stm.close();

		} catch (SQLException e){
			System.out.println("Problema Dao SQL - atualizar.");
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null, sql);
			return false;
		}
		return true;
	}
	
	public boolean excluir(String sql){
		return atualizar(sql);
	}
	
	public long qdeRegistros(String tabela){
		
		String sql = "select count(*) from \"" + tabela + "\";" ;
		try{
			PreparedStatement  stm = conexao.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			//stm.close();
			if (rs.next()){
				return rs.getInt(1);	
			}
			

		} catch (SQLException e){
			//System.out.println("Problema Dao SQL - qde registros.");
			//System.out.println(e.toString());
			return -1; //tabela não existe
		}
		
		
		return 0; 
	}

	protected void finalize () {
		try{
			conexao.close();
		} catch (SQLException e){
			System.out.println("Problema fechar conexão - finalize");
		}
	}
	
	public static FabricaConexaoBD obterInstancia(){
		if (instancia == null){
			instancia = new FabricaConexaoBD();
		}
		return instancia;
	}

}
