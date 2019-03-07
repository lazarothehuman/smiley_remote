package smiley.models.dao;

import java.util.List;

import smiley.models.Transaccao;

public interface TransaccaoDao {

	Transaccao find(Long code);

	void create(Transaccao transaction);

	void update(Transaccao transaccao);

	List<Transaccao> find();

	List<Transaccao> find(Long id, String nome, String codigo, Boolean active);

}
