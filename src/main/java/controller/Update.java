package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProductsDAO;
import model.Product;

@WebServlet("/Update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータ取得
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		
		// データベースからidを利用して１件データ取得
		ProductsDAO dao = new ProductsDAO();
		Product product = dao.findOne(id);
		
		// セッションスコープにインスタンスを保存
		HttpSession session = request.getSession();
		session.setAttribute("product", product);
		
		// update.jspへフォワード
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/update.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータ取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		int price = Integer.parseInt(request.getParameter("price"));

		// セッションスコープからインスタンス取得
		HttpSession session = request.getSession();
		Product product = (Product)session.getAttribute("product");

		// インスタンスの内容を変更
		product.setName(name);
		product.setPrice(price);
		
		// データベース更新処理、その後セッションスコープからインスタンス削除
		ProductsDAO dao = new ProductsDAO();
		dao.updateOne(product);
		session.removeAttribute("product");
		
		// セッションスコープに更新メッセージ保存
		session.setAttribute("updateMsg", "１件更新しました");

		// Mainへリダイレクト
		response.sendRedirect("Main");
	}

}
