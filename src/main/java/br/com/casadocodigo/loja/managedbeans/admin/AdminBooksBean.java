package br.com.casadocodigo.loja.managedbeans.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
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
	private Part cover;
	
	@Inject
	private FileSaver fileSave;
	
	
	
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
		
		
		product.setSummaryPath(fileSave.writer("summaries",summary));
		product.setCoverPath(fileSave.writer("covers",cover));
			
		bookDAO.save(product);
		
		
		messagesHelper.addFlash(new FacesMessage("livro gravado com sucesso"));
		
		
		return "/livros/lista?faces-redirect=true";
	}
	
	

	@PostConstruct
	@Transactional
	private void loadObjects(){
		this.authors =authorDAO.list();
		
		System.out.println("Quantidade : "+ authors.size());
		
	}

	public List<Author> getAuthors() {
		return authors;
	}


	

	

	public Part getSummary() {
		return summary;
	}

	public void setSummary(Part summary) {
		this.summary = summary;
	}



	public Part getCover() {
		return cover;
	}



	public void setCover(Part cover) {
		this.cover = cover;
	}



	

	public Book getProduct() {
		return product;
	}


	




	




}
