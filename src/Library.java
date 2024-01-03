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

	public void showLibrary() {
		if (booklist.size() == 0) {
			System.out.println("Biblioteka " + library_name + " jest pusta!");
		} else {
			for (int i = 0; i < booklist.size(); i++) {
				System.out.println((i + 1) + ". " + booklist.get(i).toString());
			}
		}
	}

	public void deleteBook(int choosenLibraryIndex) {
		booklist.remove(choosenLibraryIndex);
	}
	
	public void multiplyBook(int choosenLibraryIndex) {
		Book toMultiply = booklist.get(choosenLibraryIndex);
		booklist.add(toMultiply);
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
