import java.util.Iterator;
import java.util.LinkedList;

import excecao.ParametroNuloException;

/*
 * MODIFICAÇÃO DA CLASSE Transacao entre a versão 10 e 11
 * 
 * A classe Transacao na versão 11 deixara de ser um SINGLETON para ser uma
 * fabrica de transacoes. Todas as vezes que o metodo obterInstancia()  for
 * chamado ele retornará uma instancia diferente de Transacao.
 * 
 * Essa decisão de projeto foi realizada para permitir que haja várias instancias
 * de Transacao indepentendes ao longo do programa. A Transacao como SINGLETON não
 * permitia a existênia de várias transacoes diferentes espalhadas por toda a extensão
 * do programa.
 * 
 */
public class Transacao {
	protected LinkedList<IComando> comandos=null;
	private Transacao() {
		comandos = new LinkedList<IComando>();
	}
	
	public static Transacao obterInstancia(){
		Transacao instancia = new Transacao();		
		return instancia;
	}
	
	public boolean efetivar(){
		boolean resultado_comando=true;
		int posicao_erro;
		int i=0;
		while(resultado_comando && i<comandos.size()){
			resultado_comando = comandos.get(i).executar();
			if(!resultado_comando){
				posicao_erro = i;
				for(i=0;i<posicao_erro; i++){
					comandos.get(i).desfazer();
				}
			}
			i++;
		}
		comandos.clear();		
		return resultado_comando;		
	}
	
	public void adicionarExcluir(ObjetoPersistente ob)  {
		if(ob == null) throw new ParametroNuloException("Transação:adicionarExcluir");
		comandos.addLast(new ComandoDeExcluirDoBD(ob));
	}
	
	public void adicionarInserir(ObjetoPersistente ob) {
		if(ob == null) throw new ParametroNuloException("Transação:adicionarInserir");
		comandos.addLast(new ComandoDeInsercaoNoBD(ob));
	}
	
	public void adicionarAtualizar(ObjetoPersistente ob) {
		if(ob == null) throw new ParametroNuloException("Transação:adicionarAtualizar");
		comandos.addLast(new ComandoDeAtualizacaoDoBD(ob));
	}

}
