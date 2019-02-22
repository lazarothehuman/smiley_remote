package smiley.managers;

import java.util.List;

import smiley.models.Consulta;
import smiley.models.Empresa;
import smiley.models.Procedimento;
import smiley.models.ProcedimentoConsulta;
import smiley.models.dao.ConsultaDao;
import smiley.models.dao.EmpresaDao;
import smiley.models.dao.ProcedimentoDao;
import smiley.models.dao.jpa.ConsultaJpaDao;
import smiley.models.dao.jpa.EmpresaJpaDao;
import smiley.models.dao.jpa.ProcedimentoJpaDao;

public class ProcessManagerImp implements ProcessManager {
	
	DataManager dataManager = new DataManagerImp();
	ConsultaDao consultaDao = new ConsultaJpaDao();
	ProcedimentoDao procedimentoDao = new ProcedimentoJpaDao();
	EmpresaDao empresaDao = new EmpresaJpaDao();

	@Override
	public void createConsulta(Consulta consulta) {
		Double custoTotal = 0d;
		if(consulta!=null) {
			consulta.setUser(dataManager.findCurrentUser());
			for (ProcedimentoConsulta element : consulta.getProcedimentosConsulta()) 
				custoTotal += element.getProcedimento().getValor()*element.getQuantidade();
			consulta.setCustoTotal(custoTotal);
			consultaDao.create(consulta);
		}

	}

	@Override
	public void createProcedimento(Procedimento procedimento) {
		if(procedimento!=null)
			procedimentoDao.create(procedimento);

	}

	@Override
	public void createEmpresa(Empresa empresa) {
		if(empresa!=null)
			empresaDao.create(empresa);

	}

	@Override
	public List<Procedimento> findProcedimentos(Long id,String nome, String codigo, Boolean active) {
		return procedimentoDao.find(id,nome,codigo,active);
	}

	@Override
	public void updateProcedimento(Procedimento procedimento) {
		if (procedimento != null) {
			procedimentoDao.update(procedimento);
		}
		
	}

}
