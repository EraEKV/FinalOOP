package Research;

import java.util.*;

public class ResearchProject {
	private String title;
	private Date dateOfWritting;
	private Date publishDate;
	private Vector<Researcher> authors;
	private Vector<ResearchPaper> papers;
	private Researcher superVisor;

	public ResearchProject() {
	}

	public ResearchProject(String title, Date dateOfWritting, Date publishDate, Vector<Researcher> authors, Vector<ResearchPaper> papers, Researcher superVisor) {
		this.title = title;
		this.dateOfWritting = dateOfWritting;
		this.publishDate = publishDate;
		this.authors = authors;
		this.papers = papers;
		this.superVisor = superVisor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDateOfWritting() {
		return dateOfWritting;
	}

	public void setDateOfWritting(Date dateOfWritting) {
		this.dateOfWritting = dateOfWritting;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Vector<Researcher> getAuthors() {
		return authors;
	}

	public void setAuthors(Vector<Researcher> authors) {
		this.authors = authors;
	}

	public Vector<ResearchPaper> getPapers() {
		return papers;
	}

	public void setPapers(Vector<ResearchPaper> papers) {
		this.papers = papers;
	}

	public Researcher getSuperVisor() {
		return superVisor;
	}

	public void setSuperVisor(Researcher superVisor) {
		this.superVisor = superVisor;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		ResearchProject that = (ResearchProject) o;
		return Objects.equals(title, that.title) && Objects.equals(dateOfWritting, that.dateOfWritting) && Objects.equals(publishDate, that.publishDate) && Objects.equals(authors, that.authors) && Objects.equals(papers, that.papers) && Objects.equals(superVisor, that.superVisor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, dateOfWritting, publishDate, authors, papers, superVisor);
	}

	@Override
	public String toString() {
		return "ResearchProject{" +
				"title='" + title + '\'' +
				", dateOfWritting=" + dateOfWritting +
				", publishDate=" + publishDate +
				", authors=" + authors +
				", papers=" + papers +
				", superVisor=" + superVisor +
				'}';
	}
}
