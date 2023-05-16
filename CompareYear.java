import java.util.Comparator;

/*
Programming task: Library
Elina Karppinen
15.5.2023
*/

public class CompareYear implements Comparator<Book>
   {
    @Override
    public int compare(Book b1, Book b2)
    {
           return b1.year - b2.year;
    }
}
