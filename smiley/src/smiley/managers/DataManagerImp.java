package smiley.managers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import smiley.models.Cliente;
import smiley.models.Consulta;
import smiley.models.Medico;
import smiley.models.Procedimento;
import smiley.models.Profile;
import smiley.models.Sexo;
import smiley.models.Transaccao;
import smiley.models.User;
import smiley.models.dao.ClienteDao;
import smiley.models.dao.ConsultaDao;
import smiley.models.dao.MedicoDao;
import smiley.models.dao.ProcedimentoDao;
import smiley.models.dao.ProfileDao;
import smiley.models.dao.SessionHelperDao;
import smiley.models.dao.TransaccaoDao;
import smiley.models.dao.UserDao;
import smiley.models.dao.jpa.ClienteJpaDao;
import smiley.models.dao.jpa.ConsultaJpaDao;
import smiley.models.dao.jpa.MedicoJpaDao;
import smiley.models.dao.jpa.ProcedimentoJpaDao;
import smiley.models.dao.jpa.ProfileJpaDao;
import smiley.models.dao.jpa.SessionHelperJpaDao;
import smiley.models.dao.jpa.TransaccaoJpaDao;
import smiley.models.dao.jpa.UserJpaDao;
import smiley.utils.ProtectedConfigFile;
import smiley.utils.SessionHelper;

public class DataManagerImp implements DataManager {

	UserDao userDao = new UserJpaDao();
	ProfileDao profileDao = new ProfileJpaDao();
	ClienteDao clienteDao = new ClienteJpaDao();
	SessionHelperDao sessionHelperDao = new SessionHelperJpaDao();
	MedicoDao medicoDao = new MedicoJpaDao();
	ConsultaDao consultadao = new ConsultaJpaDao();
	ProcedimentoDao procedimentoDao = new ProcedimentoJpaDao();
	TransaccaoDao transaccaoDao = new TransaccaoJpaDao();

	@Override
	public void createUser(User user) throws UnsupportedEncodingException, GeneralSecurityException {
		if (user != null) {
			user.setPassword(encryptPassword(user.getPassword()));
			userDao.create(user);
		}
	}

	@Override
	public User findUser(String username) {
		User user = userDao.find(username);
		if (user != null)
			return user;
		else
			return null;
	}

	@Override
	public List<User> findUsers() {
		return userDao.findAllUsers();
	}

	@Override
	public boolean checkPassword(String password, String pass) throws GeneralSecurityException, IOException {
		String p = ProtectedConfigFile.decrypt(password);
		if (p.equals(pass))
			return true;
		else
			return false;
	}

	@Override
	public String encryptPassword(String password) throws UnsupportedEncodingException, GeneralSecurityException {
		return ProtectedConfigFile.encrypt(password);
	}

	@Override
	public void createProfile(Profile profile) {
		if (profile != null)
			profileDao.create(profile);
	}

	@Override
	public Profile findProfile(Long id) {
		return profileDao.findbyID(id);
	}

	@Override
	public void createCliente(Cliente cliente) {

		if (cliente != null)
			clienteDao.create(cliente);
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Cliente> findClientesAniversarientes(Date date, Boolean active) {
		return clienteDao.findBirthdayClients(date.getDate(), date.getMonth(), active);
	}

	@Override
	public List<Cliente> findClientes(Long id, String nome, String email, String telefone, Date startDate, Date endDate,
			Sexo sexo, Boolean active) {
		if (endDate != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			endDate = calendar.getTime();
		}
		List<Cliente> list = clienteDao.findClients(id, nome, email, telefone, startDate, endDate, sexo, active);
		return list;
	}

	@Override
	public void setSelectedUser(User user) {
		if (user != null)
			sessionHelperDao.set(user);

	}

	@Override
	public User findCurrentUser() {
		return sessionHelperDao.get();
	}

	@Override
	public List<Profile> findProfiles(Boolean active) {
		return profileDao.findProfiles();
	}

	@Override
	public void updateCliente(Cliente cliente) {
		if (cliente != null)
			clienteDao.update(cliente);
	}

	@Override
	public void createMedico(Medico medico) {
		if (medico != null)
			medicoDao.create(medico);
	}

	@Override
	public void updateUser(User user) throws UnsupportedEncodingException, GeneralSecurityException {
		if (user != null) {
			user.setPassword(encryptPassword(user.getPassword()));
			userDao.update(user);
		}

	}

	@Override
	public List<User> findUsers(String name, String username, Profile profile, Boolean active) {
		return userDao.find(name, username, profile, active);
	}

	@Override
	public void updateMedico(Medico medico) {
		if (medico != null)
			medicoDao.update(medico);

	}

	@Override
	public List<Medico> findMedicos(Long id, String nome, String email, String telefone, Date startDate, Date endDate,
			Sexo sexo, Boolean activee) {
		if (endDate != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			endDate = calendar.getTime();
		}
		return medicoDao.find(id, nome, email, telefone, startDate, endDate, sexo, activee);
	}

	@Override
	public Profile findProfile(String string) {
		return profileDao.findByName(string);
	}

	@Override
	public User findUser(long id) {
		return userDao.find(id);
	}

	@Override
	public void createConsulta(Consulta consulta) {
		if (consulta != null)
			consultadao.create(consulta);

	}

	@Override
	public void createProcedimento(Procedimento procedimento) {
		if (procedimento != null)
			procedimentoDao.create(procedimento);

	}

	@Override
	public Transaccao findTransaccao(Long code) {
		return transaccaoDao.find(code);
	}

	@Override
	public List<Transaccao> findAllTransaccoes() {
		return transaccaoDao.find();
	}

	@Override
	public void updateProfile(Profile profile) {
		if (profile != null)
			profileDao.update(profile);

	}

	@Override
	public SessionHelper getSessionHelper() {
		SessionHelper session = sessionHelperDao.findSession();
		if (session != null)
			return session;
		return null;
	}

	@Override
	public Cliente findCliente(Long id, String name) {
		Cliente cliente = clienteDao.find(id, name);
		if (cliente != null)
			return cliente;
		return null;
	}

	@Override
	public User findUser(Long id, String name) {
		User user = null;
		if (id != null)
			user = userDao.find(id);
		else
			user = userDao.find(name);
		return user;
	}

	@Override
	public Medico findMedico(Long id, String name) {
		Medico medico = medicoDao.find(id, name);
		if (medico != null)
			return medico;
		return null;
	}

	@Override
	public void updateTransaccao(Transaccao transaccao) {
		if (transaccao != null) 
			transaccaoDao.update(transaccao);
		
	}

	@Override
	public List<Transaccao> findTransaccaos(Long id, String nome, String codigo, Boolean active) {
		return transaccaoDao.find(id,nome,codigo,active);
	}

	@Override
	public List<Profile> findProfiles(String nome, boolean active) {
		return profileDao.findProfiles(nome,active);
	}

	@Override
	public void createTransaccao(Transaccao transaccao) {
		if (transaccao != null) {
			transaccaoDao.create(transaccao);
		}
		
	}

}
