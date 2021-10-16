package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.util.RedirectView;

@ManagedBean
@ViewScoped
public class AutorBean {

	private Autor autor = new Autor();

	public Autor getAutor() {
		return autor;
	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public RedirectView gravar() {
		/*
		 * Verifica se o autor da IHC já existe.
		 */
		if (this.autor.getId() == null) {
			System.out.println("Gravando novo autor " + this.autor.getNome());
			new DAO<Autor>(Autor.class).adiciona(this.autor);
		} else {
			System.out.println("Atualizando autor " + this.autor.getNome());
			new DAO<Autor>(Autor.class).atualiza(this.autor);
		}

		this.autor = new Autor();

		return new RedirectView("livro");
	}

	public void alterarAutor(Autor autor) {
		System.out.println("Alterando o autor " + autor.getNome());
		this.autor = autor;

	}
	
	public void desativarAutor(Autor autor) {
		System.out.println("Desativando o autor " + autor.getNome());
		new DAO<Autor>(Autor.class).remove(autor);
	}
}
