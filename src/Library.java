import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Library {
	private String library_name;
	private List<Book> booklist;

	public Library(String library_name) {
		this.library_name = library_name;
		booklist = new ArrayList<Book>();
	}

	public void addBook(Book book) {
		booklist.add(book);
	}

	public void showLibrary(PrintWriter out) {
		if (booklist.size() == 0) {
			out.println("Biblioteka " + library_name + " jest pusta!");
			out.flush();
		} else {
			for (int i = 0; i < booklist.size(); i++) {
				out.println((i + 1) + ". " + booklist.get(i).toString());
				out.flush();
			}
		}
	}
	
	public int numberOfBooks() {
		int booklistSize = booklist.size();
		return booklistSize;
	}

	public void deleteBook(int choosenLibraryIndex) {
		booklist.remove(choosenLibraryIndex);
	}
	
	public void multiplyBook(int choosenLibraryIndex) {
		Book toMultiply = booklist.get(choosenLibraryIndex);
		booklist.add(toMultiply);
	}
	
	public String toDBCode(int choosenLibraryIndex) {
		Book toDBCode = booklist.get(choosenLibraryIndex);
		String DBCode = toDBCode.getAuthor() + ";" + toDBCode.getTitle() + ";" + toDBCode.getPublisher() + ";" + toDBCode.getNumberOfPages(); 
		return DBCode;
	}
	
	public boolean doesExist(int choosenBookIndex) {
		boolean doesExist;
		if(booklist.get(choosenBookIndex)==null) {
			doesExist=false;
		}else {
			doesExist=true;
		}
		return doesExist;
	}
}
