package smiley.models.dao;

import java.util.List;

import smiley.models.Cliente;
import smiley.models.Consulta;
import smiley.models.Medico;
import smiley.models.User;

public interface ConsultaDao {

	void create(Consulta consulta);

	void update(Consulta consulta);

	List<Consulta> find(Cliente cliente, Medico medico, User user, Long id, Boolean active);

}
