package services;

import model.Book;
import model.Category;

public interface IBookService extends IService<Book>{

    void save(Book p, int[] categories);
    void edit(int id, Book book, int[] categories);
}
