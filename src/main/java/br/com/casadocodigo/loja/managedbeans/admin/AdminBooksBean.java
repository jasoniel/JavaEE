package br.com.casadocodigo.loja.managedbeans.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import br.com.casadocodigo.loja.daos.AuthorDAO;
import br.com.casadocodigo.loja.daos.BookDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.infra.MessagesHelper;
import br.com.casadocodigo.loja.models.Author;
import br.com.casadocodigo.loja.models.Book;

@Model
public class AdminBooksBean {
	
	private Part summary;
	
	@Inject
	private FileSaver fileSave;
	
	@Inject
	private FacesContext facesContext ;
	
	@Inject
	private MessagesHelper messagesHelper;
	
	private Book product = new Book();
	
	@Inject
	private BookDAO bookDAO;
	
	@Inject
	private AuthorDAO authorDAO;
	
	
	private List<Author> authors = new ArrayList<>();
	
	@Transactional
	public String save(){
		
		String summaryPath = fileSave.writer("summaries",summary);
			
		bookDAO.save(product);
		
		
		messagesHelper.addFlash(new FacesMessage("livro gravado com sucesso"));
		
		
		return "/livros/lista?faces-redirect=true";
	}
	
	

	@PostConstruct
	@Transactional
	private void loadObjects(){
		this.authors = authorDAO.list();
	}

	

	public Book getProduct() {
		return product;
	}

	

	public Part getSummary() {
		return summary;
	}

	public void setSummary(Part summary) {
		this.summary = summary;
	}

}
