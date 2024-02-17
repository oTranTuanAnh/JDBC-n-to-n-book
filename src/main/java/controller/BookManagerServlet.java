package controller;

import model.Book;
import services.BookService;
import services.CategoryService;
import services.IBookService;
import services.ICategoryService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/books")
public class BookManagerServlet extends HttpServlet {
    IBookService bookService = new BookService();
    ICategoryService categoryService = new CategoryService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String act = req.getParameter("action");
        if (act == null){
            act = "";
        }
        switch (act){
            case "create":
                showFormCreate(req, resp);
                break;
            case "delete":
                showFormDelete(req, resp);
                break;
            case "edit":
                showFormEdit(req, resp);
                break;
            default:
                showListBook(req, resp);
                break;

        }

    }

    private void showFormEdit(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("edit.jsp");
        req.setAttribute("books", bookService.findById(id));
        req.setAttribute("categories", categoryService.findAll());

        try {
            requestDispatcher.forward(req,resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showFormDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Book book = bookService.findById(id);
        req.setAttribute("books", book);

        RequestDispatcher dispatcher = req.getRequestDispatcher("delete.jsp");
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showFormCreate(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher dispatcher = req.getRequestDispatcher("create.jsp");
        req.setAttribute("categories", categoryService.findAll());
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showListBook(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher dispatcher = req.getRequestDispatcher("list.jsp");
        List<Book> bookList = bookService.findAll();
        req.setAttribute("books", bookList);

        try {
            dispatcher.forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String act = req.getParameter("action");
        if (act == null){
            act = "";
        }
        switch (act){
            case "create":
                createNewBook(req, resp);
                showListBook(req, resp);
                break;
            case "delete":
                deleteBook(req, resp);
                showListBook(req, resp);
                break;
            case "edit":
                editBook(req, resp);
                showListBook(req, resp);
                break;
            default:
                break;

        }

    }

    private void editBook(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String author = req.getParameter("author");
        String description = req.getParameter("description");
        String[] categoriesStr = req.getParameterValues("categories");
        int[] categories = new int[categoriesStr.length];
        for (int i = 0; i < categoriesStr.length; i++) {
            categories[i] = Integer.parseInt(categoriesStr[i]);
        }
        Book book = new Book(name, author, description);
        bookService.edit(id, book, categories);
    }

    private void deleteBook(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        bookService.delete(id);
    }

    private void createNewBook(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        String author = req.getParameter("author");
        String description = req.getParameter("description");
        String[] categoriesStr = req.getParameterValues("categories");
        int[] categories = new int[categoriesStr.length];
        for (int i = 0; i < categoriesStr.length; i++) {
            categories[i] = Integer.parseInt(categoriesStr[i]);
        }
        Book book = new Book(name, author, description);
        bookService.save(book, categories);

    }
}
