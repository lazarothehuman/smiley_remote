package smiley.managers;

import java.util.List;

import smiley.models.Consulta;
import smiley.models.Empresa;
import smiley.models.Procedimento;

public interface ProcessManager {
	
	public void createConsulta(Consulta consulta);
	public void createProcedimento(Procedimento procedimento);
	public void createEmpresa(Empresa empresa);
	public List<Procedimento> findProcedimentos(Long id, String nome, String codigo, Boolean active);
	public void updateProcedimento(Procedimento procedimento);

}
