package com.wszib.gui;

import com.wszib.core.Authenticator;
import com.wszib.database.BookDAO;
import com.wszib.database.UserDAO;
import com.wszib.model.Book;
import com.wszib.model.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Scanner;

public class GUI {
    public static BookDAO bookDB = BookDAO.getInstance();
    static final UserDAO userDB = UserDAO.getInstance();
    private static final GUI instance = new GUI();
    final static Authenticator authenticator = Authenticator.getInstance();
    private static final Scanner scanner = new Scanner(System.in);
    private GUI() {
    }
    public static String showLogMenu(){
        System.out.println("1.Registration");
        System.out.println("2.Login");
        System.out.println("3. Exit");
        return scanner.nextLine();
    }

    public static String showMenu(){
        System.out.println("1. Search ");
        System.out.println("2. Borrow book");
        System.out.println("3. Logout");

        if (authenticator.loggedUser != null &&
                authenticator.loggedUser.getRole().equals(User.Role.ADMIN)) {
            System.out.println("4. Add book");
            System.out.println("5. List all book");
            System.out.println("6. List borrowed books");
            System.out.println("7. List books after the deadline");
            System.out.println("8. List users");
        }
        return scanner.nextLine();
    }

    public static User readLoginAndPassword(){
        User user = new User();
        System.out.println("Login:");
        user.setLogin(scanner.nextLine());
        System.out.println("Password:");
        user.setPassword(DigestUtils.md5Hex(scanner.nextLine() + authenticator.seed));
        return user;
    }
    public static User readLoginAndPasswordFirstTime() {

        User user = new User();

        boolean registered = false;

            System.out.println("Login:");
            user.setLogin(scanner.nextLine());
            System.out.println("Password:");
            user.setPassword(DigestUtils.md5Hex(scanner.nextLine() + authenticator.seed));
           // user.setPassword(scanner.nextLine());
            System.out.println("First name:");
            user.setFirstName(scanner.nextLine());
            System.out.println("Last name:");
            user.setLastName(scanner.nextLine());

            if (!userDB.ifUserExist(user.getLogin()))
                registered = true;
            GUI.showEffectRegistration(registered);
            return user;
    }

    public static void searchBook(){
        System.out.println("Search book");
        bookDB.listAvailableBooks(scanner.nextLine());
    }


    public static void showEffectRegistration(boolean effect){
        if(effect)
            System.out.println("Registered successful");
        else
            System.out.println("login is taken, please try again");
    }
    public static void showBooksList() {
        System.out.println("Title\t\t\t\t\t\t\t\tAuthor\t\t\t\t\t\t\t\tISBN\t\t  Borrowed");
        bookDB.getBooks().stream().forEach(System.out::println);
        System.out.println("\n");
    }


//    public static void showBorrowedBooksList() {
//        System.out.println("Title\t\t\t\t\t\t\t\tAuthor\t\t\t\t\t\t\t\tISBN\t\t  Borrowed");
//        bookDB.getBooks().stream().filter(b->b.getStatus().equals(Book.Status.BORROWED))
//                .forEach(System.out::println);
//    }
    public static void showBorrowedBooksList2() {
        bookDB.listBorrowedBooks();
    }
//    public static void showBorrowedBooksAfterTheDeadlineList() {
//        System.out.println("Title\t\t\t\t\t\t\t\tAuthor\t\t\t\t\t\t\t\tISBN\t\t  Borrowed");
//        bookDB.getBooks().stream().filter(b->b.getStatus().equals(Book.Status.BORROWED))
//                .forEach(System.out::println);
//    }
    public static void showBorrowedBooksAfterTheDeadlineList2() {
        bookDB.listBorrowedBooksAfterTheDeadline();
    }


    public static void showUsersList(){
        UserDAO userDB = UserDAO.getInstance();
        System.out.println("First name\t\t\t\t Last name\t\t\t Login\t\t\tPassword\t\t\t\t\t\t   Role");
       // userDB.getUsers().stream().forEach(System.out::println);
        userDB.getUsers();
    }
    public static void showBorrowEffect(boolean effect) {
        if (effect) {
            System.out.println("The Book has been borrowed successfully\n");
        } else {
            System.out.println("The Book doesn't exist or it is currently on loan \n");
        }
    }


    public static String readTitle(){
        System.out.println("Podaj tytu??: ");
        return scanner.nextLine();
    }

    public static Book readNewBookData() {
        System.out.println("Title:");
        String title = scanner.nextLine();
        System.out.println("Author:");
        String author = scanner.nextLine();
        System.out.println("ISBN: ");
        int ISBN = Integer.parseInt(scanner.nextLine());
        return new Book(ISBN,author,title, Book.Status.AVAILABLE);
    }

    public static GUI getInstance() {
        return instance;
    }

}
