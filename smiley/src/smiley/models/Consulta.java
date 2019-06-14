package smiley.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Consulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Medico medico;

	@OneToMany(mappedBy = "consulta", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<ProcedimentoConsulta> procedimentosConsulta = new ArrayList<>();

	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;

	@Column(nullable = false)
	private Date dataRealizacao;
	
	@Column
	private Date dataTermino;

	@Column(nullable = false)
	private Double custoTotal;
	
	@Column(nullable = false, columnDefinition = "bit")
	private boolean closed = false;
	

	@Column(nullable = false, columnDefinition = "bit")
	private Boolean active = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCustoTotal() {
		return custoTotal;
	}
	
	public void setCustoTotal(Double custoTotal) {
		this.custoTotal = custoTotal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(Date dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public List<ProcedimentoConsulta> getProcedimentosConsulta() {
		return procedimentosConsulta;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void addProcedimento(ProcedimentoConsulta procedimentoConsulta) {
		if (procedimentoConsulta != null)
			this.procedimentosConsulta.add(procedimentoConsulta);

	}

}
