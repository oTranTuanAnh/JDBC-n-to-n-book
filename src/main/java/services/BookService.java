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
    public static final String SELECT_FROM_BOOK_CATEGORY_WHERE_BOOK_ID = "select * from book_category where book_id=?";
    public static final String SELECT_FROM_CATEGORY_WHERE_ID = "select * from category where id=?;";
    public static final String UPDATE_BOOK_SET_NAME_AUTHOR_DESCRIPTION_WHERE_ID = "update book set name = ? , author= ? , description =? where id = ?;";
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
        int b_id = 0;
        String b_name = "";
        String b_author = "";
        String b_description = "";
        List<Category> b_categories = new ArrayList<>();

        try {
            PreparedStatement statement = connectionJDBC.prepareStatement(SELECT_FROM_BOOK_WHERE_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                b_id = resultSet.getInt("id");
                b_name = resultSet.getString("name");
                b_author = resultSet.getString("author");
                b_description = resultSet.getString("description");
                b_categories = getCategoryList(id);
                book = new Book(b_id, b_name, b_author, b_description, b_categories);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;

    }

    private List<Category> getCategoryList(int id) throws SQLException {
        List<Category> b_categories = new ArrayList<>();
        PreparedStatement statement1 = connectionJDBC.prepareStatement(SELECT_FROM_BOOK_CATEGORY_WHERE_BOOK_ID);

        statement1.setInt(1, id);
        ResultSet resultSet1 = statement1.executeQuery();

        while (resultSet1.next()){
            int c_id = resultSet1.getInt("category_id");
            PreparedStatement statement2 = connectionJDBC.prepareStatement(SELECT_FROM_CATEGORY_WHERE_ID);
            statement2.setInt(1, c_id);
            ResultSet resultSet2 = statement2.executeQuery();
            while (resultSet2.next()){
               String c_name = resultSet2.getString("name");
               String c_description = resultSet2.getString("description");
               Category category = new Category(c_id, c_name, c_description);
               b_categories.add(category);
            }

        }


        return b_categories;
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
    public void edit(int id, Book book, int[] categories){
        //xoa thong tin tai bang trung gian
        try {
            PreparedStatement statement = connectionJDBC.prepareStatement(DELETE_FROM_BOOK_CATEGORY_WHERE_BOOK_ID);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //update thong tin sach
        try {
            PreparedStatement statement1 = connectionJDBC.prepareStatement(UPDATE_BOOK_SET_NAME_AUTHOR_DESCRIPTION_WHERE_ID);
            statement1.setString(1, book.getName());
            statement1.setString(2, book.getAuthor());
            statement1.setString(3, book.getDescription());
            statement1.setInt(4, id);
            statement1.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // ghi tong tin vao bang trung gian
        PreparedStatement statement2 = null;
        try {
            statement2 = connectionJDBC.prepareStatement(INSERT_INTO_BOOK_CATEGORY_VALUES);
            for (int cateID : categories){
                statement2.setInt(1, id);
                statement2.setInt(2, cateID);
                statement2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void edit(int id, Book book) {

    }
}
