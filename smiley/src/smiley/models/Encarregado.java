package smiley.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Encarregado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String telefone;

	@OneToMany(mappedBy = "encarregado")
	private List<Cliente> educandos = new ArrayList<>();

	@Column(nullable = false, columnDefinition = "bit")
	private Boolean active = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<Cliente> getEducandos() {
		return educandos;
	}

	public void setEducandos(List<Cliente> educandos) {
		this.educandos = educandos;
	}

	public void addEducando(Cliente cliente) {
		if (cliente != null)
			this.educandos.add(cliente);
	}

	public void removeEducando(Cliente cliente) {
		if (cliente != null)
			this.educandos.remove(cliente);
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return this.nome;
	}

}
