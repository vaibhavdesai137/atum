package com.intuit.atum.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.intuit.atum.db.models.BookStatus;

public enum DBAccess {

	INSTANCE;

	public void checkoutBook(int bookId, int memberId) throws Exception {

		// Is valid book?
		if (!bookExists(bookId)) {
			String msg = "Book does not exist, bookId: " + bookId;
			System.out.println(msg);
			throw new Exception(msg);
		}

		// Check if the book is available for checkout
		if (isBookCheckedOut(bookId)) {
			String msg = "Book is already checkedout, bookId: " + bookId;
			System.out.println(msg);
			throw new Exception(msg);
		}

		Connection conn = DataSourceManager.INSTANCE.getConnection();
		PreparedStatement ptmt1 = null;
		PreparedStatement ptmt2 = null;
		String sql;

		try {

			conn.setAutoCommit(false);

			sql = "UPDATE books SET status = ?, updated_time = NOW() WHERE id = ?";
			ptmt1 = conn.prepareStatement(sql);
			ptmt1.setString(1, BookStatus.CHECKEDOUT.toString());
			ptmt1.setInt(2, bookId);
			ptmt1.executeUpdate();

			sql = "INSERT INTO checkouts(book_id, member_id, checkout_date, expected_return_date, created_time, updated_time) "
					+ "VALUES (?, ?, NOW(), TIMESTAMPADD(DAY, 14, NOW()), NOW(), NOW())";
			ptmt2 = conn.prepareStatement(sql);
			ptmt2.setInt(1, bookId);
			ptmt2.setInt(2, memberId);
			ptmt2.executeUpdate();

			conn.commit();
			conn.setAutoCommit(true);

		} catch (Exception e) {
			conn.rollback();
			System.out.println("Exception in checkoutBook, bookId: " + bookId + ", memberId: " + memberId + ", ex: "
					+ e);
			throw e;
		} finally {
			DataSourceManager.INSTANCE.closeQuietly(ptmt1);
			DataSourceManager.INSTANCE.closeQuietly(ptmt2);
			DataSourceManager.INSTANCE.closeConnection(conn);
		}
	}

	public void returnBook(int bookId) throws Exception {

		// Is valid book?
		if (!bookExists(bookId)) {
			String msg = "Book does not exist, bookId: " + bookId;
			System.out.println(msg);
			throw new Exception(msg);
		}

		// Check if the book was checkedout
		if (!isBookCheckedOut(bookId)) {
			String msg = "Book was not checkedout yet to return, bookId: " + bookId;
			System.out.println(msg);
			throw new Exception(msg);
		}

		Connection conn = DataSourceManager.INSTANCE.getConnection();
		PreparedStatement ptmt1 = null;
		PreparedStatement ptmt2 = null;
		String sql;

		try {

			conn.setAutoCommit(false);

			sql = "UPDATE checkouts SET actual_return_date = NOW(), updated_time = NOW() "
					+ "WHERE id = (SELECT id FROM checkouts WHERE book_id = ? ORDER BY created_time LIMIT 1)";
			ptmt1 = conn.prepareStatement(sql);
			ptmt1.setInt(1, bookId);
			ptmt1.executeUpdate();

			sql = "UPDATE books SET status = ?, updated_time = NOW() WHERE id = ?";
			ptmt2 = conn.prepareStatement(sql);
			ptmt2.setString(1, BookStatus.AVAILABLE.toString());
			ptmt2.setInt(2, bookId);
			ptmt2.executeUpdate();

			conn.commit();
			conn.setAutoCommit(true);

		} catch (Exception e) {
			conn.rollback();
			System.out.println("Exception in returnBook, bookId: " + bookId + ", ex: " + e);
			throw e;
		} finally {
			DataSourceManager.INSTANCE.closeQuietly(ptmt1);
			DataSourceManager.INSTANCE.closeQuietly(ptmt2);
			DataSourceManager.INSTANCE.closeConnection(conn);
		}

	}

	public String getBooksBorrowedByMember(int memberId) {

		Connection conn = DataSourceManager.INSTANCE.getConnection();
		PreparedStatement ptmt = null;
		ResultSet rs = null;

		JsonArray booksArray = new JsonArray();
		JsonObject result = new JsonObject();
		result.add("booksBorrowed", booksArray);

		try {
			String sql = "SELECT b.id AS id, b.title AS title, c.return_due_date AS return_due_date FROM books b, checkouts c "
					+ "WHERE b.id = c.book_id AND b.status = ? AND c.memberId = ?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, BookStatus.CHECKEDOUT.toString());
			ptmt.setInt(2, memberId);
			rs = ptmt.executeQuery();

			while (rs.next()) {
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("bookId", rs.getInt("id"));
				jsonObj.addProperty("bookTitle", rs.getString("title"));
				jsonObj.addProperty("returnDueDate", rs.getString("return_due_date"));
				booksArray.add(jsonObj);
			}

		} catch (Exception e) {
			System.out.println("Exception in getBooksBorrowedByMember: " + e);
		} finally {
			DataSourceManager.INSTANCE.closeQuietly(ptmt);
			DataSourceManager.INSTANCE.closeQuietly(rs);
			DataSourceManager.INSTANCE.closeConnection(conn);
		}

		return result.toString();

	}

	public void getBookDetails(int bookId) throws Exception {

	}

	private boolean bookExists(int bookId) throws Exception {

		Connection conn = DataSourceManager.INSTANCE.getConnection();
		PreparedStatement ptmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT id FROM books WHERE id = ?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, bookId);
			rs = ptmt.executeQuery();

			while (rs.next()) {
				// Getting a result indicates the book exists
				return true;
			}

		} catch (Exception e) {
			System.out.println("Exception in bookExists, bookId: " + bookId + ", ex: " + e);
			throw e;
		} finally {
			DataSourceManager.INSTANCE.closeQuietly(ptmt);
			DataSourceManager.INSTANCE.closeQuietly(rs);
			DataSourceManager.INSTANCE.closeConnection(conn);
		}

		return false;
	}

	private boolean isBookCheckedOut(int bookId) throws Exception {

		Connection conn = DataSourceManager.INSTANCE.getConnection();
		PreparedStatement ptmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT id FROM books WHERE id = ? AND status = ?";
			ptmt = conn.prepareStatement(sql);
			ptmt.setInt(1, bookId);
			ptmt.setString(2, BookStatus.CHECKEDOUT.toString());
			rs = ptmt.executeQuery();

			while (rs.next()) {
				// Getting a result indicates the book is available
				return true;
			}

		} catch (Exception e) {
			System.out.println("Exception in isBookCheckedOut, bookId: " + bookId + ", ex: " + e);
			throw e;
		} finally {
			DataSourceManager.INSTANCE.closeQuietly(ptmt);
			DataSourceManager.INSTANCE.closeQuietly(rs);
			DataSourceManager.INSTANCE.closeConnection(conn);
		}

		return false;
	}

}