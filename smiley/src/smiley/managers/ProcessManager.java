package smiley.managers;

import java.util.List;

import smiley.models.Cliente;
import smiley.models.Consulta;
import smiley.models.Empresa;
import smiley.models.Medico;
import smiley.models.Procedimento;
import smiley.models.User;

public interface ProcessManager {
	
	public void createConsulta(Consulta consulta);
	public void createProcedimento(Procedimento procedimento);
	public void createEmpresa(Empresa empresa);
	public List<Procedimento> findProcedimentos(Long id, String nome, String codigo, Boolean active);
	public void updateProcedimento(Procedimento procedimento);
	public void updateConsulta(Consulta consulta);
	public List<Consulta> findConsultas(Cliente cliente, Medico medico, User user, Long id, Boolean active);

}
