package smiley.managers;

import smiley.models.Consulta;
import smiley.models.Empresa;
import smiley.models.Procedimento;

public interface ProcessManager {
	
	public void createConsulta(Consulta consulta);
	public void createProcedimento(Procedimento procedimento);
	public void createEmpresa(Empresa empresa);

}
