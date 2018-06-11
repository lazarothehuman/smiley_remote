package smiley.managers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import smiley.models.Cliente;
import smiley.models.Medico;
import smiley.models.Profile;
import smiley.models.Sexo;
import smiley.models.User;

public interface DataManager {
	
	public void createUser(User user) throws UnsupportedEncodingException, GeneralSecurityException;
	public User findUser(String username);
	public List<User> findUsers();
	public boolean checkPassword(String password, String pass) throws GeneralSecurityException, IOException;
	public String encryptPassword(String password) throws UnsupportedEncodingException, GeneralSecurityException;
	public void createProfile(Profile profile);
	public Profile findProfile(Long id);
	public void createCliente(Cliente cliente);
	public List<Cliente> findClientesAniversarientes(Date date, Boolean active);
	public List<Cliente> findClientes(Long id, String nome, String email, String telefone, Date birthDate, Date selectedEndDate, Sexo sexo,
			Boolean active);
	public void setSelectedUser(User user);
	public User findCurrentUser();
	public List<Profile> findProfiles(Boolean active);
	public void updateCliente(Cliente selectedCliente);
	public void createMedico(Medico medico);
	public void updateUser(User user) throws UnsupportedEncodingException, GeneralSecurityException;
	public List<User> findUsers(String name, String username, Profile profile, Boolean active);
	public void updateMedico(Medico medico);
	public List<Medico> findMedicos(Long id, String nome, String email, String telefone, Date dataEscolhida,
			Date selectedEndDate, Sexo sexo, Boolean activee);
	public Profile findProfile(String string);
	public User findUser(long id);

}
