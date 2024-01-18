import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*TODO:
 * WAŻNE:
 * 	X0. Add FIleSave to everything
 *  X1. Server comms
 *  X2. Lamda exp. - pomysł -> w kliencie xD
 *  3. Separate database file(?) -> to tam ewentualnie jak czasu starczy
 *  4. Clean up of code
 *  X5. Add a function addBook
 *  6. Add GUI
 *  X7. Fix multibplyBook
 *  X8. Fix saving books to file
 *  X9. Make the client only close client, not the server
 *  X10. add a load before every command, so that 2 clients can "interact"
 *  11. Zmienić toString dla książki, bo wygląda tragicznie xD
 *  
 * MNIEJ WAŻNE:
 *  1. Add different functions
 *  2. readFileLinesToLibraries i readFileWordsToBooks - sprawdz czy da sie zrobić w 1 metode
 *  3. Zmerguj tak wszystkie metody co sie da
 *  4. Uporządkuj nieco kod
 *  5. Sprawdz, czy wszystko w klasach jest "czyste" (tj. clean code) i ew. wyczyść
 *  6. Dodaj usuwanie bibliotek
 *  7. Ewentualne komendy na serwerze
 *  
 *  
 * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
 * PO WYKONANIU KTÓREGOŚ ZADANIA Z LISTY PRZED NUMERKIEM OZNACZ ZNAKIEM "X"
 * !!!PRIORYTETEM JEST, ABY TO DZIAŁAŁO!!!
 */

public class LibraryServerCommuncationThread extends Thread{
	private Socket clientSocket;
	private Map<String, Library> libraries;
	private boolean on;
	private Scanner sc;
	
	public static final File listOfLibraries = new File("listOfGenres.txt");

	public LibraryServerCommuncationThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
		libraries = new HashMap<>();
		sc = new Scanner(System.in);

		loadBooksAndLibraries();
	}
	
	public void turnOn(BufferedReader in, PrintWriter out) throws IOException {
		on = true;
		while (on) {
			displayMenu(out);
			int option = getOption(in);
			chooseOption(option,in,out);
		}
	}
	
	public void displayMenu(PrintWriter out) {
		out.println("Witam na serwerze. Wybierz opcję:");
		out.println("	(0) Wyświetl bibliotekę");
		out.flush();
		out.println("	(1) Dodaj bibliotekę");
		out.flush();
		out.println("	(2) Zduplikuj książkę w bibliotece");
		out.flush();
		out.println("	(3) Usuń książkę w bibliotece");
		out.flush();
		out.println("	(4) Dodaj książkę do biblioteki");
		out.flush();
		out.println("	(5) Zakończ działanie programu");
		out.flush();
	}
	
	public int getOption(BufferedReader in) {
		int option = -1;
		try {
			option = Integer.parseInt(in.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return option;
	}

	public void chooseOption(int option, BufferedReader in, PrintWriter out) throws IOException {
		switch (option) {
		case 0:
			viewLibrary(in,out);
			break;

		case 1:
			addLibrary(in,out);
			break;

		case 2:
			multiplyBook(in,out);
			break;
		case 3:
			deleteBook(in,out);
			break;
		case 4:
			addBook(in,out);
			break;
		case 5:
			//System.exit(1);
			on=false;
			break;
		default:
			System.err.println("Nie znaleziono takiej opcji");
			out.println("Nie znaleziono takiej opcji");
			out.flush();
		}
	}
	public void viewLibrary(BufferedReader in, PrintWriter out) throws IOException {
		loadBooksAndLibraries();
		out.println("Dostępne biblioteki: ");
		out.flush();
		displayLibraries(out);
		out.println("Którą bibliotekę chcesz wyświetlić: ");
		out.flush();
		String name = in.readLine();
		if (libraries.containsKey(name.toUpperCase()) != true) {
			out.println("Nie ma takiej biblioteki");
			out.flush();
		} else {
			Library library = libraries.get(name.toUpperCase());
			library.showLibrary(out);
		}
	}
	
	public void addLibrary(BufferedReader in, PrintWriter out) throws IOException {
		loadBooksAndLibraries();
		out.println("Podaj nazwę biblioteki, którą chcesz dodać: ");
		out.flush();
		String name = in.readLine();
		Library library = new Library(name.toUpperCase());
		boolean nameExists = libraries.containsKey(name.toUpperCase());
		if (nameExists == true) {
			out.println("Taka biblioteka już istnieje");
			out.flush();
		} else {
			libraries.put(name.toUpperCase(), library);
			try {
				saveListOfLibrariesToFile(listOfLibraries.getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			createNewLibraryBookListFile(name);
			out.println("Dodano bibliotekę");
			out.flush();
		}
	}

	public void multiplyBook(BufferedReader in, PrintWriter out) throws IOException {
		loadBooksAndLibraries();
		out.println("Dostępne biblioteki: ");
		out.flush();
		displayLibraries(out);
		out.println("Z której biblioteki chcesz powielić książkę: ");
		out.flush();
		String name = in.readLine();
		if (libraries.containsKey(name.toUpperCase()) != true) {
			out.println("Nie ma takiej biblioteki");
			out.flush();
		} else {
			Library library = libraries.get(name.toUpperCase());
			library.showLibrary(out);
			out.println("Którą książkę chcesz powielić? Podaj index: ");
			out.flush();
			int choosenIndex = Integer.parseInt(in.readLine());
			if (libraries.get(name.toUpperCase()).doesExist(choosenIndex-1) == false) {
				out.println("Nie ma książki o takim indeksie w danej biliotece");
				out.flush();
			} else {
				libraries.get(name.toUpperCase()).multiplyBook(choosenIndex-1);
				saveListOfBooksToFile(name.toUpperCase());
				out.println("Powielono podaną książkę");
				out.flush();
			}
		}
	}

	public void deleteBook(BufferedReader in, PrintWriter out) throws IOException {
		loadBooksAndLibraries();
		out.println("Dostępne biblioteki: ");
		out.flush();
		displayLibraries(out);
		out.println("Z której biblioteki chcesz usunąć książkę: ");
		out.flush();
		String name = in.readLine();
		if (libraries.containsKey(name.toUpperCase()) != true) {
			out.println("Nie ma takiej biblioteki");
			out.flush();
		} else {
			Library library = libraries.get(name.toUpperCase());
			library.showLibrary(out);
			out.println("Którą książkę chcesz usunąć? Podaj index: ");
			out.flush();
			int choosenIndex = Integer.parseInt(in.readLine());
			if (libraries.get(name.toUpperCase()).doesExist(choosenIndex-1)==false) {
				out.println("Nie ma książki o takim indeksie w danej biliotece");
				out.flush();
			} else {
				libraries.get(name.toUpperCase()).deleteBook(choosenIndex-1);
				saveListOfBooksToFile(name.toUpperCase());
				out.println("Usunięto podaną książkę");
				out.flush();
			}
		}
	}
	
	public void addBook(BufferedReader in, PrintWriter out) throws IOException {
		loadBooksAndLibraries();
		out.println("Dostępne biblioteki: ");
		out.flush();
		displayLibraries(out);
		out.println("Do której biblioteki chcesz dodać książkę: ");
		out.flush();
		String name = in.readLine();
		if (libraries.containsKey(name.toUpperCase()) != true) {
			out.println("Nie ma takiej biblioteki");
			out.flush();
		} else {
			out.println("Podaj autora książki: ");
			out.flush();
			String Author = in.readLine();
			out.println("Podaj tytuł książki: ");
			out.flush();
			String Title = in.readLine();
			out.println("Podaj wydawcę książki: ");
			out.flush();
			String Publisher = in.readLine();
			out.println("Podaj ilość stron książki: ");
			out.flush();
			int numOfPages = Integer.parseInt(in.readLine());
			libraries.get(name.toUpperCase()).addBook(new Book(Author, Title, Publisher, numOfPages));
			saveListOfBooksToFile(name.toUpperCase());
			out.println("Dodano wybraną książkę");
			out.flush();
		}
	}
	public void displayLibraries(PrintWriter out) {
		for (String name : libraries.keySet()) {
			out.println(name);
			out.flush();
		}
	}
	
	public void readFileLinesToLibraries(String inputFilePathWay) {
		String filePath = inputFilePathWay;
		try {
			BufferedReader input = new BufferedReader(new FileReader(filePath));
			String libraryName;
			while((libraryName = input.readLine())!=null) {
				libraries.put(libraryName.toUpperCase(), new Library(libraryName.toUpperCase()));
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void readFileWordsToBooks(String inputFileName) {
		String filePath = inputFileName + ".txt"; // Replace with your file path

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(";"); // Split the line into words by spaces, replace with ";"
                String Author = words[0];
                String Title = words[1];
                String Publisher = words[2];
                int numOfPages = Integer.parseInt(words[3]);
                libraries.get(inputFileName).addBook(new Book(Author, Title, Publisher, numOfPages));
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
	
	public void loadBooksAndLibraries() {
		try {
			readFileLinesToLibraries(listOfLibraries.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (String libraryName : libraries.keySet()) {
			readFileWordsToBooks(libraryName);
		}
	}
	
	public void saveListOfLibrariesToFile(String filePathWay) {
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(filePathWay, false));
			for (String libraryName : libraries.keySet()) {
				output.write(libraryName.toUpperCase() + "\n");
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void saveListOfBooksToFile(String libraryName) {
		String filePathWay = libraryName.toUpperCase() + ".txt";
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(filePathWay));
			int numberOfBooks = libraries.get(libraryName).numberOfBooks();
			for(int i=0; i<numberOfBooks;i++) {
				output.write(libraries.get(libraryName).toDBCode(i) + "\n");
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void createNewLibraryBookListFile(String fileName) {
		String filePathWay = fileName.toUpperCase() + ".txt";
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(filePathWay));
			
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			System.out.println("SERVER: IN and OUT streams opened. Starting receiving data...");
			turnOn(in,out);
			System.out.println("SERVER: Ending sequence received. Closing streams and sockets.");
			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
