package services;

import controller.ConnectionJDBC;
import model.Book;
import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookService implements IBookService{
    public static final String INSERT_NEW_BOOK = "insert into book(name, author, description) values (?,?,?);";
    public static final String INSERT_INTO_BOOK_CATEGORY_VALUES = "insert into book_category values (?,?);";
    public static final String SELECT_ALL_BOOK = "select * from book;";
    Connection connectionJDBC = ConnectionJDBC.getConnection();
    ICategoryService categoryService = new CategoryService();
    @Override
    public void save(Book p, int[] categories) {
        int book_id = 0;
        try {
            connectionJDBC.setAutoCommit(false);
            PreparedStatement statement = connectionJDBC.prepareStatement(INSERT_NEW_BOOK, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, p.getName());
            statement.setString(2, p.getAuthor());
            statement.setString(3, p.getDescription());

            int num = statement.executeUpdate();
            //lay id cua book
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()){
                book_id = resultSet.getInt(1);
            }

            //ghi thong tin vao bang trung gian
            PreparedStatement statement1 = connectionJDBC.prepareStatement(INSERT_INTO_BOOK_CATEGORY_VALUES);
            for (int cateID : categories){
                statement1.setInt(1, book_id);
                statement1.setInt(2, cateID);
                statement1.executeUpdate();
            }

            connectionJDBC.commit();

        } catch (SQLException e) {
            try {
                connectionJDBC.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try {
            PreparedStatement statement = connectionJDBC.prepareStatement(SELECT_ALL_BOOK);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String author = resultSet.getString("author");
                String description = resultSet.getString("description");
                List<Category> categoryList = categoryService.findAllByBookID(id);
                Book book = new Book(id, name, author, description, categoryList);
                bookList.add(book);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return bookList;
    }

    @Override
    public Book findById(int id) {
        return null;
    }

    @Override
    public void save(Book p) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void edit(int id, Book book) {

    }
}
