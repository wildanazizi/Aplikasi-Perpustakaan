package com.mycompany.librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

class Member {
    String name;
    String id;
    
    public Member(String name, String id) {
        this.name = name;
        this.id = id;
    }
}

class Book {
    String title;
    String author;
    String isbn;
    boolean isAvailable;
    Date dueDate;
    
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
        this.dueDate = null;
    }
}

public class LibrarySystem {
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();
    
    public LibrarySystem() {
        // Data awal anggota dan buku
        members.add(new Member("John Doe", "M001"));
        members.add(new Member("Jane Smith", "M002"));
        
        books.add(new Book("Book 1", "Author 1", "B001"));
        books.add(new Book("Book 2", "Author 2", "B002"));
    }
    
    public void addMember(String name, String id) {
        members.add(new Member(name, id));
    }
    
    public void removeMember(String id) {
        members.removeIf(member -> member.id.equals(id));
    }
    
    public void addBook(String title, String author, String isbn) {
        books.add(new Book(title, author, isbn));
    }
    
    public void removeBook(String isbn) {
        books.removeIf(book -> book.isbn.equals(isbn));
    }
    
    public void borrowBook(String memberId, String bookIsbn) {
        Member member = getMember(memberId);
        Book book = getBook(bookIsbn);
        
        if (member != null && book != null && book.isAvailable) {
            book.isAvailable = false;
            Date currentDate = new Date();
            long fiveDaysInMillis = 5 * 24 * 60 * 60 * 1000L;
            book.dueDate = new Date(currentDate.getTime() + fiveDaysInMillis);
        }
    }
    
    public void returnBook(String bookIsbn) {
        Book book = getBook(bookIsbn);
        
        if (book != null && !book.isAvailable) {
            Date currentDate = new Date();
            long overdueDays = (currentDate.getTime() - book.dueDate.getTime()) / (24 * 60 * 60 * 1000);
            if (overdueDays > 0) {
                int fine = (int) (overdueDays * 1000);
                JOptionPane.showMessageDialog(null, "Denda sebesar Rp " + fine + " harus dibayar.");
            }
            book.isAvailable = true;
            book.dueDate = null;
        }
    }
    
    public Member getMember(String id) {
        for (Member member : members) {
            if (member.id.equals(id)) {
                return member;
            }
        }
        return null;
    }
    
    public Book getBook(String isbn) {
        for (Book book : books) {
            if (book.isbn.equals(isbn)) {
                return book;
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
        LibrarySystem librarySystem = new LibrarySystem();
        
        // Buat antarmuka grafis
        JFrame frame = new JFrame("Library System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new FlowLayout());
        
        JButton addButton = new JButton("Tambah Anggota");
        JButton removeButton = new JButton("Hapus Anggota");
        JButton addBookButton = new JButton("Tambah Buku");
        JButton removeBookButton = new JButton("Hapus Buku");
        JButton borrowButton = new JButton("Pinjam Buku");
        JButton returnButton = new JButton("Kembalikan Buku");
        
        JTextField memberIdField = new JTextField(10);
        JTextField bookIsbnField = new JTextField(10);
        
        frame.add(new JLabel("ID Anggota: "));
        frame.add(memberIdField);
        frame.add(new JLabel("ISBN Buku: "));
        frame.add(bookIsbnField);
        frame.add(addButton);
        frame.add(removeButton);
        frame.add(addBookButton);
        frame.add(removeBookButton);
        frame.add(borrowButton);
        frame.add(returnButton);
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberId = memberIdField.getText();
                String name = JOptionPane.showInputDialog("Nama Anggota: ");
                librarySystem.addMember(name, memberId);
            }
        });
        
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberId = memberIdField.getText();
                librarySystem.removeMember(memberId);
            }
        });
        
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = bookIsbnField.getText();
                String title = JOptionPane.showInputDialog("Judul Buku: ");
                String author = JOptionPane.showInputDialog("Pengarang: ");
                librarySystem.addBook(title, author, isbn);
            }
        });
        
        removeBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = bookIsbnField.getText();
                librarySystem.removeBook(isbn);
            }
        });
        
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberId = memberIdField.getText();
                String bookIsbn = bookIsbnField.getText();
                librarySystem.borrowBook(memberId, bookIsbn);
            }
        });
        
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookIsbn = bookIsbnField.getText();
                librarySystem.returnBook(bookIsbn);
            }
        });
        
        frame.setVisible(true);
    }
}