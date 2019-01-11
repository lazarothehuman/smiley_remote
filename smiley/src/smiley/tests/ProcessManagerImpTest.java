package smiley.tests;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import smiley.managers.ProcessManager;
import smiley.managers.ProcessManagerImp;
import smiley.models.Procedimento;

public class ProcessManagerImpTest {
	
	ProcessManager processManager = new ProcessManagerImp();
	
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
	public void findProcedimentosWithParametersTest() {
		List<Procedimento> procedimentos = processManager.findProcedimentos(null, "Consulta", null, null);
		Assert.assertNotNull(procedimentos);
		Assert.assertEquals(2, procedimentos.size());
		
	}

}
