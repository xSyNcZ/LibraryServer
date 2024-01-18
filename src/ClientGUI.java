import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientGUI {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ClientGUI() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame("LibraryClient");
		frame.setBounds(100, 100, 343, 357);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton showLibraryButton = new JButton("Show Library");
		showLibraryButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		showLibraryButton.setBounds(10, 10, 93, 21);
		frame.getContentPane().add(showLibraryButton);
		
		JButton addLibraryButton = new JButton("Add Library");
		addLibraryButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		addLibraryButton.setBounds(106, 10, 93, 21);
		frame.getContentPane().add(addLibraryButton);
		
		JButton duplicateBookButton = new JButton("Duplicate book in library");
		duplicateBookButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		duplicateBookButton.setBounds(10, 41, 150, 21);
		frame.getContentPane().add(duplicateBookButton);
		
		JButton deleteBookButton = new JButton("Delete book from a library");
		deleteBookButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		deleteBookButton.setBounds(163, 41, 159, 21);
		frame.getContentPane().add(deleteBookButton);
		
		JButton addBookButton = new JButton("Exit the programme");
		addBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		addBookButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
		addBookButton.setBounds(199, 10, 123, 21);
		frame.getContentPane().add(addBookButton);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 71, 312, 219);
		frame.getContentPane().add(textArea);
	}
}
