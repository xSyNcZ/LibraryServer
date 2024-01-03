import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;

/*TODO:
 * WAŻNE:
 * 	X0. Add FIleSave to everything
 *  1. Server comms
 *  2. Lamda exp.
 *  3. Separate database file
 *  4. Clean up of code
 *  5. Add a function addBook
 *  6. Add GUI
 *  
 * MNIEJ WAŻNE:
 *  1. Add different functions
 *  2. readFileLinesToLibraries i readFileWordsToBooks - sprawdz czy da sie zrobić w 1 metode
 *  3. Zmerguj tak wszystkie metody
 *  4. Uporządkuj nieco kod
 *  5. Sprawdz, czy wszystko w klasach jest "czyste" (tj. clean code) i ew. wyczyść
 *  
 * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
 * PO WYKONANIU KTÓREGOŚ ZADANIA Z LISTY PRZED NUMERKIEM OZNACZ ZNAKIEM "X"
 * !!!PRIORYTETEM JEST, ABY TO DZIAŁAŁO!!!
 */

public class LibraryRunner {
	private Map<String, Library> libraries;
	private boolean on;
	private Scanner sc;
	
	public static final File listOfLibraries = new File("listOfGenres.txt");

	public LibraryRunner() {
		libraries = new HashMap<>();
		sc = new Scanner(System.in);

		try {
			readFileLinesToLibraries(listOfLibraries.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (String libraryName : libraries.keySet()) {
			readFileWordsToBooks(libraryName);
		}
	}

	public void turnOn() {
		on = true;
		while (on) {
			displayMenu();
			int opcja = getOption();
			chooseOption(opcja);
		}
	}

	public void displayMenu() {
		System.out.println("Wybierz opcję:");
		System.out.println("	(0) Wyświetl bibliotekę");
		System.out.println("	(1) Dodaj bibliotekę");
		System.out.println("	(2) Zduplikuj książkę w bibliotece");
		System.out.println("	(3) Usuń książkę w bibliotece");
		System.out.println("	(4) Zakończ działanie programu");
	}

	public int getOption() {
		int option = sc.nextInt();
		return option;
	}

	public void chooseOption(int option) {
		switch (option) {
		case 0:
			viewLibrary();
			break;

		case 1:
			addLibrary();
			break;

		case 2:
			multiplyBook();
			break;
		case 3:
			deleteBook();
			break;
		case 4:
			System.exit(0);
			break;

		default:
			System.err.println("Nie znaleziono takiej opcji");
		}
	}

	public void viewLibrary() {
		System.out.println("Dostępne biblioteki: ");
		displayLibraries();
		System.out.println("Którą bibliotekę chcesz wyświetlić: ");
		sc.nextLine();
		String name = sc.nextLine();
		if (libraries.containsKey(name.toUpperCase()) != true) {
			System.out.println("Nie ma takiej biblioteki");
		} else {
			Library library = libraries.get(name.toUpperCase());
			library.showLibrary();
		}
	}

	public void addLibrary() {
		System.out.println("Podaj nazwę biblioteki, którą chcesz dodać: ");
		sc.nextLine();
		String name = sc.nextLine();
		Library library = new Library(name.toUpperCase());
		boolean nameExists = libraries.containsKey(name.toUpperCase());
		if (nameExists == true) {
			System.out.println("Taka biblioteka już istnieje");
		} else {
			libraries.put(name.toUpperCase(), library);
			try {
				saveListOfLibrariesToFile(listOfLibraries.getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			createNewLibraryBookListFile(name);
			System.out.println("Dodano bibliotekę");
		}
	}

	public void multiplyBook() {
		System.out.println("Dostępne biblioteki: ");
		displayLibraries();
		System.out.println("Z której biblioteki chcesz powielić książkę: ");
		sc.nextLine();
		String name = sc.nextLine();
		if (libraries.containsKey(name.toUpperCase()) != true) {
			System.out.println("Nie ma takiej biblioteki");
		} else {
			Library library = libraries.get(name.toUpperCase());
			library.showLibrary();
			System.out.println("Którą książkę chcesz powielić? Podaj index: ");
			int choosenIndex = sc.nextInt();
			if (libraries.get(name.toUpperCase()).doesExist(choosenIndex) == false) {
				System.out.println("Nie ma książki o takim indeksie w danej biliotece");
			} else {
				libraries.get(name.toUpperCase()).multiplyBook(choosenIndex-1);
				saveListOfBooksToFile(name.toUpperCase());
				System.out.println("Powielono podaną książkę");
			}
		}
	}

	public void deleteBook() {
		System.out.println("Dostępne biblioteki: ");
		displayLibraries();
		System.out.println("Z której biblioteki chcesz usunąć książkę: ");
		sc.nextLine();
		String name = sc.nextLine();
		if (libraries.containsKey(name.toUpperCase()) != true) {
			System.out.println("Nie ma takiej biblioteki");
		} else {
			Library library = libraries.get(name.toUpperCase());
			library.showLibrary();
			System.out.println("Którą książkę chcesz usunąć? Podaj index: ");
			int choosenIndex = sc.nextInt();
			if (libraries.get(name.toUpperCase()).doesExist(choosenIndex-1)==false) {
				System.out.println("Nie ma książki o takim indeksie w danej biliotece");
			} else {
				libraries.get(name.toUpperCase()).deleteBook(choosenIndex-1);
				saveListOfBooksToFile(name.toUpperCase());
				System.out.println("Usunięto podaną książkę");
			}
		}
	}

	public void displayLibraries() {
		for (String name : libraries.keySet()) {
			System.out.println(name);
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
	
	public static void main(String[] args) {
		LibraryRunner librun = new LibraryRunner();
		librun.turnOn();
		System.out.println("----- KONIEC -----");
	}
}
