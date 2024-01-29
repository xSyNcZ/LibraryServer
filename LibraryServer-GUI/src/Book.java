
public class Book {
	private String author;
	private String title;
	private String publisher;
	private int numberOfPages;

	public Book(String author, String title, String publisher, int numberOfPages){
		this.author = author;
		this.title = title;
		this.publisher = publisher;
		this.numberOfPages = numberOfPages;
	}
	
	
	
	public String getAuthor() {
		return author;
	}



	public void setAuthor(String author) {
		this.author = author;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getPublisher() {
		return publisher;
	}



	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}



	public int getNumberOfPages() {
		return numberOfPages;
	}



	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	@Override
	public String toString() {
		return "Autor: " + author + ", Tytuł: " + title + ", Wydawca: " + publisher + ", Ilość stron: " + numberOfPages;
	}
}
