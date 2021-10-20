package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.LivroDataModel;
import br.com.caelum.livraria.util.RedirectView;

@ManagedBean
@ViewScoped
public class LivroBean {

	private Livro livro = new Livro();
	private Integer autorId;
	private List<Livro> livros;
//	private LivroDataModel livroDataModel = new LivroDataModel();

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Livro> getLivros() {
		DAO<Livro> dao = new DAO<Livro>(Livro.class);

		if (this.livros == null) {
			this.livros = dao.listaTodos();
		}
		return this.livros;
	}

	public void associarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
		livro.adicionaAutor(autor);
	}

	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}

	public List<Autor> getAutores() {

		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return livro.getAutores();
	}

	public void gravar() {
		DAO<Livro> dao = new DAO<Livro>(Livro.class);

		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));

			return;
		}

		/*
		 * Verifica se o livro carregado na IHC existe
		 */

		if (this.livro.getId() == null) {
			dao.adiciona(this.livro);
			this.livros = dao.listaTodos();
		} else {
			dao.atualiza(this.livro);
		}
		this.livro = new Livro();
	}

	public void desativar(Livro livro) {
		DAO<Livro> dao = new DAO<Livro>(Livro.class);

		System.out.println("Removendo o livro " + livro.getTitulo());

		dao.remove(livro);
		this.livros = dao.listaTodos();

	}

	public void isbnDeveIniciarComUm(FacesContext fc, UIComponent component, Object valor) throws ValidatorException {
		String valorInserido = (String) valor;
		if (!valorInserido.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("O ISBN deve iniciar com 1."));
		}
	}

	public RedirectView formAutor() {
		System.out.println("[INFO] Chamando o formulário de Autores.");
		return new RedirectView("autor");
	}
	
	public RedirectView formLivro() {
		System.out.println("[INFO] Chamando o formulário de Livros.");
		
		return new RedirectView("livro");
	}

//	public LivroDataModel getLivroDataModel() {
//		return livroDataModel;
//	}

}
