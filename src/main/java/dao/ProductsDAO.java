package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Product;

public class ProductsDAO {
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;

	private void connect() throws NamingException, SQLException {
		Context context = new InitialContext();
		DataSource ds = (DataSource) context.lookup("java:comp/env/mariadb");
		this.con = ds.getConnection();
	}

	private void disconnect() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Product> findAll() {

		List<Product> productList = new ArrayList<>();
		try {
			this.connect();
			stmt = con.prepareStatement("SELECT * FROM products ORDER BY id DESC");
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String updated = sdf.format(rs.getTimestamp("updated"));
				Product product = new Product(id, name, price, updated);
				productList.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return productList;
	}

	public boolean insertOne(Product product) {
		try {
			this.connect();
			stmt = con.prepareStatement("INSERT INTO products(name,price,updated) VALUES(?,?,?)");
			stmt.setString(1, product.getName());
			stmt.setInt(2, product.getPrice());
			stmt.setString(3, product.getUpdated());
			int result = stmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return true;
	}

	public Product findOne(int id) {
		Product product = null;
		try {
			this.connect();
			stmt = con.prepareStatement("SELECT * FROM products WHERE id=?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				String name = rs.getString("name");
				int price = rs.getInt("price");
				String updated = rs.getString("updated");
				product = new Product(id, name, price, updated);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return product;
	}

	public boolean updateOne(Product product) {
		try {
			this.connect();
			stmt = con.prepareStatement("UPDATE products SET name=?,price=?,updated=? WHERE id=?");
			stmt.setString(1, product.getName());
			stmt.setInt(2, product.getPrice());
			stmt.setString(3, product.getUpdated());
			stmt.setInt(4, product.getId());
			int result = stmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return true;
	}

	public boolean deleteOne(int id) {

		try {
			this.connect();
			stmt = con.prepareStatement("DELETE FROM products WHERE id=?");
			stmt.setInt(1, id);
			int result = stmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return true;
	}

	public void checkConnect() {
		try {
			this.connect();
			System.out.println("データベースとの接続に成功！！");
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
	}
}
