package smiley.tests;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import smiley.managers.DataManager;
import smiley.managers.DataManagerImp;
import smiley.managers.ProcessManager;
import smiley.managers.ProcessManagerImp;
import smiley.models.Cliente;
import smiley.models.Consulta;
import smiley.models.Medico;
import smiley.models.Procedimento;
import smiley.models.ProcedimentoConsulta;
import smiley.models.User;

public class ProcessManagerImpTest {
	
	ProcessManager processManager = new ProcessManagerImp();
	DataManager dataManager = new DataManagerImp();
	
	@Test
	public void testCreateProcedimento() {
		Procedimento procedimento = new Procedimento();
		procedimento.setNome("Consulta especial");
		procedimento.setValor(23200d);
		procedimento.setCodigo("special");
		
		processManager.createProcedimento(procedimento);
		Assert.assertNotNull(procedimento.getId());
		
	}
	
	@Test
	public void createConsultaTest() {
		Procedimento procedimento = processManager.findProcedimentos(4l, null, null, null).get(0);
		Medico medico = dataManager.findMedicos(1l, null, null, null, null, null, null, null).get(0);
		User user = dataManager.findUser(1l);
		Cliente cliente  = dataManager.findClientes(1l, null, null, null, null, null, null, null).get(0);
		
		ProcedimentoConsulta procedimentoConsulta = new ProcedimentoConsulta();
		procedimentoConsulta.setProcedimento(procedimento);
		procedimentoConsulta.setQuantidade(1);
		
		Consulta consulta = new Consulta();
		consulta.setCliente(cliente);
		consulta.setDataRealizacao(new Date());
		consulta.setMedico(medico);
		consulta.setUser(user);
		consulta.setCliente(cliente);
		
		procedimentoConsulta.setConsulta(consulta);
		consulta.addProcedimento(procedimentoConsulta);
		
		processManager.createConsulta(consulta);
		Assert.assertNotNull(consulta.getId());
		Assert.assertNotNull(procedimentoConsulta.getId());
		Assert.assertEquals(1, consulta.getProcedimentosConsulta().size());	

	}
	
	@Test
	public void createConsultaWithMultipeProcedimentos() {
		List<Procedimento> procedimentos = processManager.findProcedimentos(null, null, null, true);
		Medico medico = dataManager.findMedicos(1l, null, null, null, null, null, null, null).get(0);
		User user = dataManager.findUser(1l);
		Cliente cliente  = dataManager.findClientes(1l, null, null, null, null, null, null, null).get(0);
		List<ProcedimentoConsulta> proceConsultas = new ArrayList<ProcedimentoConsulta>();
		for (Procedimento procedimento : procedimentos) {
			ProcedimentoConsulta procedimentoConsulta = new ProcedimentoConsulta();
			procedimentoConsulta.setProcedimento(procedimento);
			procedimentoConsulta.setQuantidade(1);
			proceConsultas.add(procedimentoConsulta);
		}

		
		Consulta consulta = new Consulta();
		consulta.setCliente(cliente);
		consulta.setDataRealizacao(new Date());
		consulta.setMedico(medico);
		consulta.setUser(user);
		consulta.setCliente(cliente);
		
		for (ProcedimentoConsulta procedimentoConsulta : proceConsultas) {
			procedimentoConsulta.setConsulta(consulta);
			consulta.addProcedimento(procedimentoConsulta);
		}
		
		
		processManager.createConsulta(consulta);
		Assert.assertNotNull(consulta.getId());
		Assert.assertEquals(4, consulta.getProcedimentosConsulta().size());	
	}
	
	@Test
	public void findProcedimentosWithParametersTest() {
		List<Procedimento> procedimentos = processManager.findProcedimentos(null, "Consulta", null, null);
		Assert.assertNotNull(procedimentos);
		Assert.assertEquals(2, procedimentos.size());
		
	}

}
