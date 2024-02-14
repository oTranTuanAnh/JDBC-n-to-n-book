package services;

import controller.ConnectionJDBC;
import model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService implements ICategoryService{
    public static final String SELECT_ALL_CATEGORY = "select * from category;";
    public static final String JOIN_CATEGORY_ON_BOOK_ID_CATEGORY_ID = "select * from category c join book_category bc on c.id = bc.category_id where book_id = ?;";
    Connection connection = ConnectionJDBC.getConnection();
    @Override
    public List<Category> findAll() {
        List<Category> categoryList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CATEGORY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Category category = new Category(id, name, description);
                categoryList.add(category);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return categoryList;
    }

    @Override
    public Category findById(int id) {
        return null;
    }

    @Override
    public void save(Category p) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void edit(int id, Category category) {

    }

    @Override
    public List<Category> findAllByBookID(int b_id) {
        List<Category> categoryList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(JOIN_CATEGORY_ON_BOOK_ID_CATEGORY_ID);
            statement.setInt(1, b_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Category category = new Category(id, name, description);
                categoryList.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categoryList;
    }
}
