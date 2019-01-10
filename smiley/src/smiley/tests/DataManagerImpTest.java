package smiley.tests;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.models.Cliente;
import smiley.models.Encarregado;
import smiley.models.Medico;
import smiley.models.Profile;
import smiley.models.Sexo;
import smiley.models.Transaccao;
import smiley.models.User;

public class DataManagerImpTest {
	
	DataManager dataManager = new DataManagerImp();
	

	@Test
	public void createTransactionTest() {
		Profile profile = dataManager.findProfile(1l);
		Transaccao transaccao = new Transaccao();
		transaccao.setActive(true);
		transaccao.setUrl("/application/forms/Modify-Profile.fxml");
		transaccao.setCode(204l);
		transaccao.addProfile(profile);
		profile.addTransaction(transaccao);
		dataManager.updateProfile(profile);
		Assert.assertNotNull(transaccao.getId());
		

	}
	
	@Test
	public void insertTransactionIntoProfileTest() {
		Profile profile = dataManager.findProfile(1l);
		Transaccao transaccao = dataManager.findTransaccao(102l);
		if(profile!=null && transaccao!=null) {
			profile.addTransaction(transaccao);
			transaccao.addProfile(profile);
			dataManager.updateProfile(profile);
		}
		//Assert.assertNotNull(profile);
		//Assert.assertNotNull(transaccao);
		Assert.assertEquals(2, transaccao.getProfiles().size());
	}
	
	@Test
	public void findAllTransaccoes() {
		List<Transaccao> transaccoes = dataManager.findAllTransaccoes();
		Assert.assertNotNull(transaccoes);
		Assert.assertFalse(transaccoes.isEmpty());
		Assert.assertEquals(9, transaccoes.size());
	}
	
	@Test
	public void insertAllTransactionIntoProfileTest() {
		Profile profile = dataManager.findProfile(2l);
		List<Transaccao> transaccoes = dataManager.findAllTransaccoes();
		for (Transaccao transaccao : transaccoes) {
			profile.addTransaction(transaccao);
			transaccao.addProfile(profile);
		}
		dataManager.updateProfile(profile);
		Assert.assertEquals(9, profile.getTransaccoes().size());
	}
	
	
	@Test
	public void createMedicoTest() {
		/*Medico medico = new Medico();
		medico.setName("Candinha");
		medico.setDataNascimento(new Date());
		medico.setSexo(Sexo.FEMININO);
		medico.setTelefone(null);
		medico.setEmail(null);
		medico.setActive(true);
		dataManager.createMedico(medico);
		Assert.assertNotNull(medico.getId());*/
		
	}
	
	@Test
	public void createClienteWithoutEncarregadoTest() {
		Cliente cliente = new Cliente();
		cliente.setNome("Teste-1");
		cliente.setDataNascimento(new Date());
		cliente.setHasEncarregado(false);
		cliente.setSexo(Sexo.MASCULINO);
		cliente.setNaturalidade("Moçambique");
		dataManager.createCliente(cliente);
		Assert.assertNotNull(cliente.getId());
		
	}
	
	@Test
	public void createClienteWithEncarregadoTest() {
		Cliente cliente = new Cliente();
		cliente.setNome("Teste-2");
		cliente.setDataNascimento(new Date());
		cliente.setHasEncarregado(true);
		cliente.setSexo(Sexo.MASCULINO);
		cliente.setNaturalidade("Moçambique");
		
		Encarregado encarregado = new Encarregado();
		encarregado.setNome("Teste-2");
		encarregado.setTelefone("825004957");
		encarregado.addEducando(cliente);
		
		cliente.setEncarregado(encarregado);
		dataManager.createCliente(cliente);
		
		Assert.assertNotNull(cliente.getId());
		Assert.assertNotNull(cliente.getEncarregado());
		Assert.assertNotNull(encarregado.getId());
		
		
	}
	
	@Test
	public void findClientesAniversarientes() {
		@SuppressWarnings("deprecation")
		Date date = new Date(1997, 6, 7);
		List<Cliente> clientes =dataManager.findClientesAniversarientes(date, true);
		Assert.assertEquals(2, clientes.size());
		
	}
	
	@Test
	public void findClientesTest() {
		List<Cliente> clientes = dataManager.findClientes(null, null, "lazaro", "82", null, null, Sexo.MASCULINO, true);
		Assert.assertEquals(1, clientes.size());
	}
	
	@Test
	public void findOneClientTest() {
		List<Cliente> cliente = dataManager.findClientes(1l, null, null, null, null, null, null, null);
		Assert.assertEquals(1, cliente.size());
		Assert.assertEquals("Lazaro Sebastiao Mathe Junior", cliente.get(0).getNome());
	}
	
	@Test
	public void findMedicoTest() {
		List<Medico> medicos = dataManager.findMedicos(null, null, null, null, null, null, null, true);
		Assert.assertEquals(2, medicos.size());
	}
	
	@Test
	public void findUsersTest() {
		List<User> users = dataManager.findUsers();
		Assert.assertEquals(6, users.size());	
	}
	
	@Test
	public void modifyClienteTest() {
		List<Cliente> clientes = dataManager.findClientes(1l, null, null, null, null, null, null, null);
		Cliente cliente = clientes.get(0);
		cliente.setNome("Albertto Alfredo Alberto");
		cliente.setActive(false);
		dataManager.updateCliente(cliente);
		Assert.assertNotNull(cliente.getId());
		Assert.assertNotEquals("Lazaro Sebastiao Mathe Junior", cliente.getNome());
		Assert.assertEquals(Sexo.MASCULINO, cliente.getSexo());		
	}
	
	@Test
	public void modifyUserTest() {
		User user = dataManager.findUser(1l);
		user.setName("Diana triste");
		Assert.assertNotNull(user.getId());
		Assert.assertNotEquals("diana feliz", user.getName());
		Assert.assertEquals(Long.valueOf(1), user.getId());
	}
	
	@Test
	public void modifyMedicoTest() {
		List<Medico> medicos = dataManager.findMedicos(1l, null, null, null, null, null, null, null);
		Medico medico = medicos.get(0);
		medico.setActive(true);
		Assert.assertNotNull(medico.getId());
		Assert.assertEquals(Boolean.TRUE, medico.getActive());
		
	}
	
	

	
}
