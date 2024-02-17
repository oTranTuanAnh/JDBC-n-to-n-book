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
    public static final String SELECT_FROM_BOOK_WHERE_ID = "select * from book where id = ?;";
    public static final String DELETE_FROM_BOOK_CATEGORY_WHERE_BOOK_ID = "delete from book_category where book_id =?;";
    public static final String DELETE_FROM_BOOK_WHERE_BOOK_ID = "delete from book where id =?;";
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

            statement.executeUpdate();
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
        Book book = null;
        try {
            PreparedStatement statement = connectionJDBC.prepareStatement(SELECT_FROM_BOOK_WHERE_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int b_id = resultSet.getInt("id");
                String b_name = resultSet.getString("name");
                String b_author = resultSet.getString("author");
                String b_description = resultSet.getString("description");
                book = new Book(b_id, b_name, b_author, b_description);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;

    }

    @Override
    public void save(Book p) {

    }

    @Override
    public void delete(int id) {
        try {
            //xoa tai bang book_category
            PreparedStatement statement = connectionJDBC.prepareStatement(DELETE_FROM_BOOK_CATEGORY_WHERE_BOOK_ID);
            statement.setInt(1, id);
            statement.executeUpdate();

            //xoa tai bang book
            PreparedStatement statement1 = connectionJDBC.prepareStatement(DELETE_FROM_BOOK_WHERE_BOOK_ID);
            statement1.setInt(1, id);
            statement1.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void edit(int id, Book book) {

    }
}
