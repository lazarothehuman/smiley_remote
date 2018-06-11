package smiley.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Cliente;
import smiley.models.Sexo;

public class TestSearch {
	
	/*
	 * Atencao ao  calendar e date. Quando vou buscar o month esta 0 e' jan e 11 e' December
	 */

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		int answer = -1;

		BufferedReader bufer = new BufferedReader(new InputStreamReader(System.in));
		PrintStream out = System.out;
		DataManager dataManager = new DataManagerImp();

		do {

			try {
				out.println("Smiley na consola");
				out.println("1- Procurar todos clientes");
				out.println("2- Procurar clientes que completam anos hoje ou numa data escolhida");
				out.println("3- Inserir cliente ");
				answer = Integer.parseInt(bufer.readLine());
				switch (answer) {
				case 1:
					List<Cliente> clientes = dataManager.findClientes(null, null, null, null, null, null, Sexo.MASCULINO, true);
					if (clientes != null) {
						for (Cliente cliente : clientes) {
							System.out.println("ID: " + cliente.getId());
							System.out.println("Name: " + cliente.getNome());
							System.out.println("E-mail: " + cliente.getEmail());
							System.out.println("Telefone: " + cliente.getTelefone());
							out.println("Dia: "+cliente.getDataNascimento().getDate());
							Calendar cal = Calendar.getInstance();
							cal.setTime(cliente.getDataNascimento());
							out.println("Mes: "+cal.get(Calendar.MONTH));
							out.println("Ano: "+cliente.getDataNascimento().getYear());
							System.out.println("Sexo: " + cliente.getSexo().toString());
						}
					}
					break;
				case 2:
					out.println("Insira o dia de nascimento");
					int diaA = Integer.parseInt(bufer.readLine());
					out.println("Insira o mes de nascimento");
					int mesA = Integer.parseInt(bufer.readLine());
					out.println("Insira o ano de nascimento");
					int anoA = Integer.parseInt(bufer.readLine());
					Date date = new Date(anoA, mesA, diaA);
					List<Cliente> clientesAniversarios = dataManager.findClientesAniversarientes(date, true);
					if (clientesAniversarios != null) {
						for (Cliente cliente : clientesAniversarios) {
							System.out.println("ID: " + cliente.getId());
							System.out.println("Name: " + cliente.getNome());
							System.out.println("E-mail: " + cliente.getEmail());
							System.out.println("Telefone: " + cliente.getTelefone());
							System.out.println("Data de nascimento: " + cliente.getDataNascimento().toString());
							System.out.println("Sexo: " + cliente.getSexo().toString());
						}
					}
					break;
				case 3:
					Sexo sexoE;
					out.println("Insira o nome");
					String name = bufer.readLine();
					out.println("Insira o sexo (Masculino ou Feminino)");
					String sexo = bufer.readLine();
					if (sexo.charAt(0) == 'M' || sexo.charAt(0) == 'm')
						sexoE = Sexo.MASCULINO;
					else
						sexoE = Sexo.FEMININO;
					out.println("Insira o dia de nascimento");
					int dia = Integer.parseInt(bufer.readLine());
					out.println("Insira o mes de nascimento");
					int mes = Integer.parseInt(bufer.readLine());
					out.println("Insira o ano de nascimento");
					int ano = Integer.parseInt(bufer.readLine());
					Date birthDate = new Date(ano, mes, dia);
					Cliente cliente = new Cliente();
					cliente.setNome(name);
					cliente.setSexo(sexoE);
					cliente.setDataNascimento(birthDate);
					dataManager.createCliente(cliente);
					break;
				case 4:
					System.exit(0);
					break;
				default:
					out.println("Escolha dentro dos parametros");

				}
			} catch (IOException a) {
				a.printStackTrace();
			}
		} while (answer < 4);
	}

}
