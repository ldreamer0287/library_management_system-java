package Frame;

import Data.FileHandler;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class MainFrame extends JFrame {

    ArrayList<String> books;
    ArrayList<String> members;
    ArrayList<String> issued;

    JTextArea area;
    JTextField bookField, memberField, searchField;

    public MainFrame() {

        setTitle("Library System");
        setSize(700, 500);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 240, 240));

        // LOAD DATA
        books = FileHandler.load("books.txt");
        members = FileHandler.load("members.txt");
        issued = FileHandler.load("issuedBooks.txt");

        // TITLE
        JLabel title = new JLabel("Library System");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(220, 20, 300, 40);
        add(title);

        // LOGO
        JLabel logo = new JLabel("Logo", SwingConstants.CENTER);
        logo.setBounds(30, 20, 60, 60);
        logo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(logo);

        // BOOK FIELD
        bookField = new JTextField();
        bookField.setBounds(120, 100, 150, 30);
        add(bookField);

        JButton addBookBtn = new JButton("Add Book");
        addBookBtn.setBounds(300, 100, 120, 30);
        addBookBtn.setBackground(new Color(46, 204, 113));
        addBookBtn.setForeground(Color.WHITE);
        addBookBtn.setFocusPainted(false);
        add(addBookBtn);

        JButton showBtn = new JButton("Show All");
        showBtn.setBounds(450, 100, 120, 30);
        showBtn.setBackground(new Color(52, 73, 94));
        showBtn.setForeground(Color.WHITE);
        showBtn.setFocusPainted(false);
        add(showBtn);

        // MEMBER FIELD
        memberField = new JTextField();
        memberField.setBounds(120, 150, 150, 30);
        add(memberField);

        JButton addMemberBtn = new JButton("Add Member");
        addMemberBtn.setBounds(300, 150, 120, 30);
        addMemberBtn.setBackground(new Color(52, 152, 219));
        addMemberBtn.setForeground(Color.WHITE);
        addMemberBtn.setFocusPainted(false);
        add(addMemberBtn);

        // SEARCH FIELD
        searchField = new JTextField();
        searchField.setBounds(120, 200, 150, 30);
        add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(300, 200, 120, 30);
        searchBtn.setBackground(new Color(26, 188, 156));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        add(searchBtn);

        // ISSUE & RETURN BUTTONS
        JButton issueBtn = new JButton("Issue Book");
        issueBtn.setBounds(450, 150, 120, 30);
        issueBtn.setBackground(new Color(155, 89, 182));
        issueBtn.setForeground(Color.WHITE);
        issueBtn.setFocusPainted(false);
        add(issueBtn);

        JButton returnBtn = new JButton("Return Book");
        returnBtn.setBounds(450, 200, 120, 30);
        returnBtn.setBackground(new Color(241, 196, 15));
        returnBtn.setForeground(Color.BLACK);
        returnBtn.setFocusPainted(false);
        add(returnBtn);

        // TEXT AREA
        area = new JTextArea();
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(100, 260, 500, 170);
        add(scroll);

        //  ADD BOOK
        addBookBtn.addActionListener(e -> {
            String book = bookField.getText();
            if (!book.equals("")) {
                books.add(book);
                FileHandler.save("books.txt", books);
                bookField.setText("");
                JOptionPane.showMessageDialog(this, "Book Added");
            }
        });

        // ADD MEMBER
        addMemberBtn.addActionListener(e -> {
            String member = memberField.getText();
            if (!member.equals("")) {
                members.add(member);
                FileHandler.save("members.txt", members);
                memberField.setText("");
                JOptionPane.showMessageDialog(this, "Member Added");
            }
        });

        // ISSUE BOOK
        issueBtn.addActionListener(e -> {
            String book = bookField.getText();
            String member = memberField.getText();

            if (!book.equals("") && !member.equals("")) {

                for (String i : issued) {
                    if (i.startsWith(book + ",")) {
                        JOptionPane.showMessageDialog(this, "Book already issued!");
                        return;
                    }
                }

                issued.add(book + "," + member);
                FileHandler.save("issuedBooks.txt", issued);

                JOptionPane.showMessageDialog(this, "Book Issued");

                // ADDED FEATURE
                area.setText("Recently Issued:\nBook: " + book + " → Member: " + member);
            }
        });

        // RETURN BOOK
        returnBtn.addActionListener(e -> {
            String book = bookField.getText();

            for (int i = 0; i < issued.size(); i++) {
                if (issued.get(i).startsWith(book + ",")) {
                    issued.remove(i);
                    FileHandler.save("issuedBooks.txt", issued);

                    JOptionPane.showMessageDialog(this, "Book Returned");
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Book not found in issued list");
        });

        // SEARCH
        searchBtn.addActionListener(e -> {
            String text = searchField.getText().toLowerCase();
            area.setText("Search Result:\n\n");

            for (String b : books) {
                if (b.toLowerCase().contains(text)) {

                    boolean found = false;

                    for (String i : issued) {
                        String[] parts = i.split(",");

                        if (parts[0].equalsIgnoreCase(b)) {
                            area.append("Book: " + b + " (Issued to " + parts[1] + ")\n");
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        area.append("Book: " + b + " (Available)\n");
                    }
                }
            }

            for (String m : members) {
                if (m.toLowerCase().contains(text)) {
                    area.append("Member: " + m + "\n");
                }
            }
        });

        //SHOW ALL
        showBtn.addActionListener(e -> {
            area.setText("Books:\n");

            for (String b : books) {
                boolean found = false;

                for (String i : issued) {
                    String[] parts = i.split(",");

                    if (parts[0].equalsIgnoreCase(b)) {
                        area.append(b + " (Issued to " + parts[1] + ")\n");
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    area.append(b + " (Available)\n");
                }
            }

            area.append("\nMembers:\n");
            for (String m : members) {
                area.append(m + "\n");
            }
        });

        setVisible(true);
    }
}