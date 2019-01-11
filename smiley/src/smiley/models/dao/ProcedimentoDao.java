package smiley.models.dao;

import java.util.List;

import smiley.models.Procedimento;

public interface ProcedimentoDao {

	void create(Procedimento procedimento);

	List<Procedimento> find(Long id, String nome, String codigo, Boolean active);

}
