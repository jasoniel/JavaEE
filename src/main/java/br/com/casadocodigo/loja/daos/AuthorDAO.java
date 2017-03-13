package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.casadocodigo.loja.models.Author;

public class AuthorDAO {

	
	@PersistenceContext
	EntityManager manager;
	
	
	public List<Author> list() {
		// TODO Auto-generated method stub
		
		return manager.createQuery("select a from Author a",Author.class).getResultList();
	}

}
