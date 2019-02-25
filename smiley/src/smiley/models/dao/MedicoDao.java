package smiley.models.dao;

import java.util.Date;
import java.util.List;

import smiley.models.Medico;
import smiley.models.Sexo;

public interface MedicoDao {
	
	public void create(Medico medico);

	public void update(Medico medico);

	public List<Medico> find(Long id, String nome, String email, String telefone, Date startDate, Date endDate, Sexo sexo,
			Boolean activee);

	public Medico find(Long id, String name);

}
