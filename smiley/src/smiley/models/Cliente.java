package smiley.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nome", nullable=false)
	private String nome;
	
	@Column(name="data_nascimento", nullable=false)
	@Type(type="date")
	private Date dataNascimento;
	
	@Column(name="telefone")
	private String telefone;
	
	@Column(name="email")
	private String email;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Sexo sexo;
	
	@Column(nullable=false, columnDefinition="bit")
	private Boolean hasEncarregado = false;
	
	@ManyToOne(cascade = {
			CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(nullable=true, name= "encarregado_id")
	private Encarregado encarregado;
	
	@Column(nullable = false)
	private String naturalidade;
	
	@Column(name= "morada")
	private String morada;
	
	@Column(name="notasImportantes")
	private String notasImportantes;
	
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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Boolean getHasEncarregado() {
		return hasEncarregado;
	}

	public void setHasEncarregado(Boolean hasEncarregado) {
		this.hasEncarregado = hasEncarregado;
	}

	public Encarregado getEncarregado() {
		return encarregado;
	}

	public void setEncarregado(Encarregado encarregado) {
		this.encarregado = encarregado;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getMorada() {
		return morada;
	}

	public void setMorada(String morada) {
		this.morada = morada;
	}

	public String getNotasImportantes() {
		return notasImportantes;
	}

	public void setNotasImportantes(String notasImportantes) {
		this.notasImportantes = notasImportantes;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public String toString() {
		return this.nome;
	}

}
