import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class Principal {

	public static void main(String[] args) {

		/*
		 * Esse programa deve ser executado com as tabelas do BD vazias
		 */


		/*
		 * As classes de domínio (Aluno, Disciplina e Matricula) possuem agora o construtor
		 * sem parâmetro, que invoca o construtor sem parâmetor do ObjetoPersistencia.
		 * 
		 * Foi implementado um construtor em ObjetoPersistencia sem parâmetro
		 * que obtem Oid direto do FrameWork de Persistência.
		 * 
		 * Assim, ao criar uma instancia de Cliente, Fita, Aluno, Disciplina ou Matricula utilizando o construtor
		 * sem parâmetro, automaticamente utiliza um Oid criado e gerenciado pelo
		 * FrameWork.
		 */

		
		
		JOptionPane.showMessageDialog(null, Persistencia.obterInstancia().quantidadeRegistro(Aluno.class));

		
		if(Persistencia.obterInstancia().quantidadeRegistro(Aluno.class) == 0){
			Armazenar arq_aluno = new Armazenar("MatriculaNome.csv");
			Armazenar arq_disciplina = new Armazenar("CodigoDisciplinaCH.csv");
			Armazenar arq_matricula = new Armazenar("MatriculaCodigoDisciplina.csv");

			Aluno aluno = null;
			Disciplina disciplina = null;
			Matricula matricula = null;
			Transacao transacao = Transacao.obterInstancia(); //acrescimo de linha 

			try {

				for(int i=0; i < arq_aluno.quantidadeRegistros(); i++){
					aluno = new Aluno();
					arq_aluno.get(i, aluno);
					transacao.adicionarInserir(aluno); //mudança de linha
				}

				for(int i=0; i < arq_disciplina.quantidadeRegistros(); i++){
					disciplina = new Disciplina();
					arq_disciplina.get(i, disciplina);
					transacao.adicionarInserir(disciplina); //mudança de linha
				}

				if(transacao.efetivar()){
					System.out.println("ALUNOS E DISCIPLINAS CADASTRADOS");
				} else {
					System.out.println("TRANSAÇÃO NÃO OK");
				}


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*
			 * Aqui há duas opções:
			 * 1) reaproveitar a instancia de Transacao criada no início desse código;
			 * 2) Criar uma nova instancia de Transacao a partir do comando transacao = Transacao.obterInstancia();
			 * 
			 * Optarei por reaproveitar a instancia.
			 * 
			 * A opção 2) é interessante quando há necessidade de realizar grupos diferentes de
			 * transacoes. A Transacao SINGLETON não permitira construir varias grupos de comandos
			 * (encapsulamento de comandos) em monentos diferente da programação.
			 *  
			 */
			try {

				for(int i=0; i < arq_matricula.quantidadeRegistros(); i++){
					matricula = new Matricula();
					arq_matricula.get(i, matricula);
					transacao.adicionarInserir(matricula);
				}

				if(transacao.efetivar()){
					System.out.println("MATRICULAS CADASTRADAS");
				} else {
					System.out.println("TRANSAÇÃO NÃO OK");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Iterator<Aluno> ita = (Iterator<Aluno>) Persistencia.obterInstancia().obterTodos(Aluno.class);

			System.out.println("\n===== ALUNOS =====");
			while(ita.hasNext()){
				aluno = ita.next();
				System.out.println("\n==================");
				System.out.println("Oid:" + aluno.getOid().getString());
				System.out.println("MATRICULA:" + aluno.getMatricula());
				System.out.println("NOME:" + aluno.getNome());
				System.out.println("ESTADO_TRANSICAO:" + aluno.getNomeEstadoTransicao());
				System.out.println("==================");
			}

			Iterator<Disciplina> itd = (Iterator<Disciplina>) Persistencia.obterInstancia().obterTodos(Disciplina.class);

			System.out.println("\n\n===== DISCIPLINAS =====");
			while(itd.hasNext()){
				disciplina = itd.next();
				System.out.println("\n==================");
				System.out.println("Oid:" + disciplina.getOid().getString());
				System.out.println("CODIGO:" + disciplina.getCodigo());
				System.out.println("DESCRIÇÃO:" + disciplina.getDescricao());
				System.out.println("ESTADO_TRANSICAO:" + disciplina.getNomeEstadoTransicao());
				System.out.println("==================");
			}

			Iterator<Matricula> itm = (Iterator<Matricula>) Persistencia.obterInstancia().obterTodos(Matricula.class);

			System.out.println("\n\n===== MATRÍCULAS =====");
			while(itm.hasNext()){
				matricula = itm.next();
				System.out.println("\n==================");
				System.out.println("Oid:" + matricula.getOid().getString());
				aluno = matricula.getAluno();
				System.out.println("->Oid:" + aluno.getOid().getString());
				System.out.println("->MATRICULA:" + aluno.getMatricula());
				System.out.println("->NOME:" + aluno.getNome());
				System.out.println("->ESTADO_TRANSICAO:" + aluno.getNomeEstadoTransicao()+"\n");
				itd = matricula.getDisciplinas();
				while(itd.hasNext()){
					disciplina = itd.next();
					System.out.println("-->Oid:" + disciplina.getOid().getString());
					System.out.println("-->CODIGO:" + disciplina.getCodigo());
					System.out.println("-->DESCRIÇÃO:" + disciplina.getDescricao());
					System.out.println("-->ESTADO_TRANSICAO:" + disciplina.getNomeEstadoTransicao());	
				}			
				System.out.println("==================");
			}		

		}

		JOptionPane.showMessageDialog(null, Persistencia.obterInstancia().quantidadeRegistro(Aluno.class));
		
		Aluno aluno = null;
		Iterator<Aluno> ita = (Iterator<Aluno>)Persistencia.obterInstancia().obterTodos(Aluno.class);
		while(ita.hasNext()){
			aluno = ita.next();
			if(aluno.getMatricula().equalsIgnoreCase("2767")){
				JOptionPane.showMessageDialog(null, aluno.getNome());
				break;
			}
			
		}		
		
		Iterator<Matricula> itm = (Iterator<Matricula>)Persistencia.obterInstancia().obterTodos(Matricula.class);
		Iterator<Disciplina> itd = null;
		while(itm.hasNext()){
			Matricula m = itm.next();
			if(m.getAluno().getOid().getString().equalsIgnoreCase(aluno.getOid().getString())){
				itd = m.getDisciplinas();
				break;
			}
			
		}		
		
		while(itd.hasNext()){
			Disciplina disciplina = itd.next();
			System.out.println("-->Oid:" + disciplina.getOid().getString());
			System.out.println("-->CODIGO:" + disciplina.getCodigo());
			System.out.println("-->DESCRIÇÃO:" + disciplina.getDescricao());
			System.out.println("-->ESTADO_TRANSICAO:" + disciplina.getNomeEstadoTransicao());	
		}			
		System.out.println("==================");
		

	}

}
