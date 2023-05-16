/*
Programming task: Library
Elina Karppinen
15.5.2023
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class LibraryBooks {
    public static void main(String args[]) throws Exception {
    	boolean continueProgram = true;
    	
    	if (args.length == 0) {
    		throw new Exception("Exception: No library file. "
    				+ "Please include library file name as an argument in form name.txt.");
    	}
    	File fileName = new File(args[0]);
    	if(!fileName.exists() && !fileName.isDirectory()) {
    		throw new Exception("Exception: Incorrect library file. "
    				+ "Please include library file name as an argument in form name.txt.");
   		}
    
    	Scanner scanner = new Scanner(System.in);
    	while (continueProgram) {

        	System.out.println("\nDo you want to \n1 Add new book"
        			+ "\n2 Print current database content in ascending order by publishing year"
        			+ "\n3 Exit the program?"
        			+ "\nType 1, 2 or 3 and press enter.");
    		String userInput = scanner.nextLine();

    		switch(userInput) {
    		case "1":
    			addNewBook(fileName, scanner);    	   
    			break;    	
    		case "2":
    			printDatabaseContent(fileName);
    			break;
    		case "3":
    			continueProgram = false;
    			scanner.close();
    			break;
    		default:
    			System.out.println("Incorrect input. Type 1, 2 or 3 and press enter.");
    		}
    	}
    }
        
    static void addNewBook(File fileName, Scanner scanner) throws IOException {
    	boolean correctInput = false;
    	//Removes "/" from input data if user used those, since it is used as separator in the library file. 
    	
    	System.out.println("\nTo add new book write\n book's name: ");
		String booksName = scanner.nextLine();
		if (booksName.contains("/")) {
			booksName = booksName.replace("/", "");
			System.out.format("'/' removed from book's name: %s\n.", booksName);
		}
    	
    	System.out.println(" writer's name: ");
		String writersName = scanner.nextLine();
		if (writersName.contains("/")) {
			writersName = writersName.replace("/", "");
			System.out.format("'/' removed from writer's name: %s\n.", writersName);
		}
    	
		System.out.println(" book's ISBN: ");
		String isbn = scanner.nextLine();
		if (isbn.contains("/")) {
			isbn = isbn.replace("/", "");
			System.out.format("'/' removed from ISBN: %s\n.", isbn);
		}
		
		String year = null;
		System.out.println(" book's publishing year: ");
    	while (!correctInput) {
    		year = scanner.nextLine();
    		try {
    			Integer.parseInt(year);
    			correctInput = true;
    		} catch(Exception e) {
    			System.out.println("book's publishing year needs to be number: ");
    		}
    	}
    	
    	correctInput = false;
    	while (!correctInput) {
    		System.out.format("\nDo you want to add %s / %s / %s / %s to database? Y/N: ", 
    				booksName, writersName, isbn, year);
    		String update = scanner.nextLine();
    		if (update.equalsIgnoreCase("y") || update.equalsIgnoreCase("yes")) {
    			correctInput = true;
    			updateLibraryFile(fileName, booksName, writersName, isbn, year);
    			
    		} else if (update.equalsIgnoreCase("n") || update.equalsIgnoreCase("no")) {
    			correctInput = true;
    			//exit to main menu
    		} else {
        		System.out.println("Y/N: ");
    		}
    	}       
    }
    
    static void updateLibraryFile(
    		File fileName, String booksName, String bookWriter, String booksIsbn, String publiched) 
    		throws IOException {
    	BufferedReader reader = new BufferedReader(new FileReader(fileName));
    	ArrayList<Book> library = new ArrayList<Book>();

    	//Read books from file and add to list.
        String currentLine = reader.readLine();
        while (currentLine != null)
        {
            String[] bookData = currentLine.split("/");
            String name = bookData[0];
            String writer = bookData[1];
            String isbn = bookData[2];
            int year = Integer.parseInt(bookData[3]);
            
            library.add(new Book(name, writer, isbn, year));
            currentLine = reader.readLine();
        }
        reader.close();
        //Add the new book to list.
        library.add(new Book(booksName, bookWriter, booksIsbn, Integer.parseInt(publiched)));
        
        //Short books based on publishing year
        Collections.sort(library, new CompareYear());
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (Book book : library)
        {
            writer.write(book.booksName);
            writer.write("/"+book.writersName);
            writer.write("/"+book.isbn);
            writer.write("/"+book.year);
            writer.newLine();
        }
        writer.close();
    }
    
    static void printDatabaseContent(File fileName) throws FileNotFoundException {

        Scanner reader = new Scanner(fileName);
        if (!reader.hasNextLine()) {
    		System.out.println("File is empty.");
        }
        while (reader.hasNextLine()) {
          String book = reader.nextLine();
          System.out.println(book);
        }
        reader.close();
    }  
}