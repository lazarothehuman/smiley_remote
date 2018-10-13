package smiley.tests;

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
		procedimento.setNome("Caterizacao");
		procedimento.setValor(2200d);
		procedimento.setCodigo("CT");
		
		processManager.createProcedimento(procedimento);
		Assert.assertNotNull(procedimento.getId());
		
	}

}
