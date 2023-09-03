package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductsDAO;
import model.Product;

@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String id = request.getParameter("id");

		ProductsDAO dao = new ProductsDAO();
		if(action != null) {
			if(action.equals("delete")) {
				dao.deleteOne(Integer.parseInt(id));
			}
		}
		List<Product> list = dao.findAll();

		ServletContext application = this.getServletContext();
		application.setAttribute("list", list);

		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/main.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext application = this.getServletContext();

		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String price = request.getParameter("price");

		if (name.isEmpty() || price.isEmpty()) {
			request.setAttribute("err", "未記入の項目があります");
		} else {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String updated = sdf.format(now);

			Product product = new Product(name, Integer.parseInt(price), updated);

			ProductsDAO dao = new ProductsDAO();
			dao.insertOne(product);

			request.setAttribute("msg", "1件登録しました。");
		}
		doGet(request, response);
	}
}
