import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientGUI extends JFrame{

	private JTextArea clientTextArea;
	private JButton showLibraryButton;
	private JButton addLibraryButton;
	private JButton duplicateBookButton;
	private JButton deleteBookButton;
	private JButton addBookButton;
	private JButton exitButton;
	private Client client;

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI().setVisible(true);
            }
        });
    }

	public ClientGUI() {
		setTitle("Server GUI");
        setSize(847, 411);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();
        //
        //
        
        showLibraryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        
        addLibraryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        
        duplicateBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        
        deleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
		
	}

	private void initializeComponents() {
		getContentPane().setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        showLibraryButton = new JButton("Show library");
        addLibraryButton = new JButton("Add new library");
        duplicateBookButton = new JButton("Duplicate exiting book");
        deleteBookButton = new JButton("Delete existing book");
        addBookButton = new JButton("Add new book");
        exitButton = new JButton("Exit the programme");

        buttonPanel.add(showLibraryButton);
        buttonPanel.add(addLibraryButton);
        buttonPanel.add(duplicateBookButton);
        buttonPanel.add(deleteBookButton);
        buttonPanel.add(addBookButton);
        buttonPanel.add(exitButton);

        clientTextArea = new JTextArea();
        clientTextArea.setEditable(false);

        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(clientTextArea), BorderLayout.CENTER);
	}
	
	private void logMessage(String message) {
		clientTextArea.append(message + "\n");
    }
}
