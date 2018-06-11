package smiley.models.dao;

import java.util.Date;
import java.util.List;

import smiley.models.Cliente;
import smiley.models.Sexo;

public interface ClienteDao {
	
	public void create(Cliente cliente);

	public List<Cliente> findBirthdayClients(int dia, int mes, Boolean active);

	public List<Cliente> findClients(Long id, String nome, String email, String telefone, Date startDate, Date endDate, Sexo sexo,
			Boolean active);

	public void update(Cliente selectedCliente);

}
