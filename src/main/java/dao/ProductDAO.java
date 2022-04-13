package dao;

import model.Category;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/productmanager";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

    private static final String FIND_ALL_PRODUCT = "select p.id,p.name,p.price,p.quantity,p.color,p.description,p.id_category,c.name from product p join category c on p.id_category = c.id;";
    private static final String FIND_PRODUCT_BY_ID = "select p.name,p.price,p.quantity,p.color,p.description,p.id_category,c.name from product p join category c on p.id_category = c.id where p.id = ?;";
//    private static final String FIND_ALL_PRODUCT = "select * from product;";
    private static final String UPDATE_PRODUCT = "update product set name=?,price=?,quantity=?,color=?,description=?,id_category=? where id = ?";
    private static final String ADD_PRODUCT = "insert into product(name,price,quantity,color,description,id_category) values(?,?,?,?,?,?)";
    private static final String DELETE_PRODUCT= "delete from product where id = ?";
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PRODUCT)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("p.name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String color = rs.getString("color");
                String description = rs.getString("description");
                int id_category = rs.getInt("id_category");
                String categoryName = rs.getString("c.name");
                Category category = new Category(id_category,categoryName);
                Product product = new Product(id,name,price,quantity,color,description,id_category,category);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public Product findById(int id) {
        Product product = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String color = rs.getString("color");
                String description = rs.getString("description");
                int id_category = rs.getInt("id_category");
                Category category = new Category(id,name);
                product = new Product(id,name,price,quantity,color,description,id_category,category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }


    public void createProduct(Product product) throws SQLException {
        System.out.println(ADD_PRODUCT);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2,product.getPrice());
            preparedStatement.setInt(3,product.getQuantity());
            preparedStatement.setString(4,product.getColor());
            preparedStatement.setString(5,product.getDescription());
            preparedStatement.setInt(6,product.getId_category());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }
    }

    public void update(Product product) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT)) {
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4,product.getQuantity());
            preparedStatement.setString(5,product.getColor());
            preparedStatement.setString(6,product.getDescription());
            preparedStatement.setInt(7,product.getCategory().getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean deleteProduct(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
}
