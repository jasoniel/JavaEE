package br.com.casadocodigo.loja.managedbeans.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.jboss.security.authorization.modules.AuthorizationModuleDelegate;

import br.com.casadocodigo.loja.daos.AuthorDAO;
import br.com.casadocodigo.loja.daos.BookDAO;
import br.com.casadocodigo.loja.infra.MessagesHelper;
import br.com.casadocodigo.loja.models.Author;
import br.com.casadocodigo.loja.models.Book;

@Model
public class AdminBooksBean {
	

	@Inject
	private FacesContext facesContext ;
	
	@Inject
	private MessagesHelper messagesHelper;
	
	private Book product = new Book();
	
	@Inject
	private BookDAO bookDAO;
	
	@Inject
	private AuthorDAO authorDAO;
	
	private List<Integer> selectedAuthorsIds = new ArrayList<Integer>();
	
	private List<Author> authors = new ArrayList<>();
	
	@Transactional
	public String save(){
		
		populateBookAuthor();
		
		bookDAO.save(product);
		clearObjects();
		
		
		
		messagesHelper.addFlash(new FacesMessage("livro gravado com sucesso"));
		
		
		return "/livros/lista?faces-redirect=true";
	}
	
	@PostConstruct
	@Transactional
	private void loadObjects(){
		this.authors = authorDAO.list();
	}

	private void clearObjects(){
		this.product = new Book();
		this.selectedAuthorsIds.clear();
	}
	private void populateBookAuthor() {
		// TODO Auto-generated method stub
		
		selectedAuthorsIds.stream().map((id) -> {
			
			return new Author(id);
		}).forEach(product::add);
		
	}

	public Book getProduct() {
		return product;
	}

	public List<Integer> getSelectedAuthorsIds() {
		return selectedAuthorsIds;
	}

	public void setSelectedAuthorsIds(List<Integer> selectedAuthorsIds) {
		this.selectedAuthorsIds = selectedAuthorsIds;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

}
