package smiley.managers;

import smiley.models.Consulta;
import smiley.models.Empresa;
import smiley.models.Procedimento;
import smiley.models.dao.ConsultaDao;
import smiley.models.dao.EmpresaDao;
import smiley.models.dao.ProcedimentoDao;
import smiley.models.dao.jpa.ConsultaJpaDao;
import smiley.models.dao.jpa.EmpresaJpaDao;
import smiley.models.dao.jpa.ProcedimentoJpaDao;
import smiley.utils.SessionHelper;

public class ProcessManagerImp implements ProcessManager {
	
	DataManager dataManager = new DataManagerImp();
	ConsultaDao consultaDao = new ConsultaJpaDao();
	ProcedimentoDao procedimentoDao = new ProcedimentoJpaDao();
	EmpresaDao empresaDao = new EmpresaJpaDao();

	@Override
	public void createConsulta(Consulta consulta) {
		if(consulta!=null) {
			consulta.setUser(dataManager.findCurrentUser());
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

}
