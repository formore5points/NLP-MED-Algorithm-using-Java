import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class GUI {

	public static final int INSERT = 0;
	public static final int DELETE = 1;
	public static final int REPLACE = 2;

	public static final int COST_INSERT = 1;
	public static final int COST_DELETE = 1;
	public static int COST_REPLACE = 1;

	public static Tuple<Integer, Integer> minOfTuples(Tuple<Integer, Integer> t1, Tuple<Integer, Integer> t2,
			Tuple<Integer, Integer> t3) {
		Tuple<Integer, Integer> result = null;

		int c_t1 = t1.getFirst() + COST_DELETE;
		int c_t2 = t2.getFirst() + COST_INSERT;
		int c_t3 = t3.getFirst() + COST_REPLACE;

		if (c_t2 <= c_t1 && c_t2 <= c_t3)
			result = new Tuple<Integer, Integer>(c_t2, INSERT);
		else if (c_t1 <= c_t2 && c_t1 <= c_t3)
			result = new Tuple<Integer, Integer>(c_t1, DELETE);
		else if (c_t3 <= c_t2 && c_t3 <= c_t1)
			result = new Tuple<Integer, Integer>(c_t3, REPLACE);

		return result;
	}

	public static Tuple<Integer, Integer>[][] MED(String source, String destination) {

		String s1 = source;
		String s2 = destination;

		int m = s1.length();
		int n = s2.length();

		@SuppressWarnings("unchecked")
		Tuple<Integer, Integer>[][] MED_MATRIX = new Tuple[m + 1][n + 1];

		for (int i = 0; i < m + 1; i++) {
			for (int j = 0; j < n + 1; j++) {

				MED_MATRIX[i][j] = new Tuple<Integer, Integer>(0, 0);
			}
		}

		for (int i = 0; i < m + 1; i++) {
			MED_MATRIX[i][0] = new Tuple<Integer, Integer>(i, DELETE);
		}

		for (int i = 0; i < n + 1; i++) {
			MED_MATRIX[0][i] = new Tuple<Integer, Integer>(i, INSERT);
		}

		for (int i = 1; i < m + 1; i++) {
			for (int j = 1; j < n + 1; j++) {

				if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
					COST_REPLACE = 0;
				}

				MED_MATRIX[i][j] = minOfTuples(MED_MATRIX[i - 1][j], MED_MATRIX[i][j - 1], MED_MATRIX[i - 1][j - 1]);
				COST_REPLACE = 1;
			}
		}

		return MED_MATRIX;

	}

	public static ArrayList<String> MED_PATH(Tuple<Integer, Integer>[][] MED_MATRIX, String source,
			String destination) {

		ArrayList<Tuple<Character, Character>> path = new ArrayList<Tuple<Character, Character>>();
		ArrayList<String> ops = new ArrayList<String>();
		String s1 = source;
		String s2 = destination;
		int i = s1.length();
		int j = s2.length();

		while (i > 0 || j > 0) {

			if (i == 0 && j == 0)
				break;
			if ((int) MED_MATRIX[i][j].getSecond() == REPLACE) {
				i--;
				j--;
				path.add(new Tuple<Character, Character>(s1.charAt(i), s2.charAt(j)));
			} else if ((int) MED_MATRIX[i][j].getSecond() == INSERT) {
				j--;

				path.add(new Tuple<Character, Character>('*', s2.charAt(j)));
			} else if ((int) MED_MATRIX[i][j].getSecond() == DELETE) {
				i--;

				path.add(new Tuple<Character, Character>(s1.charAt(i), '*'));
			}

		}

		int step = 1;

		for (int k = path.size() - 1; k >= 0; k--) {

			if (path.get(k).getFirst() == path.get(k).getSecond()) {
				ops.add(step + ") Remains Same (" + path.get(k).getFirst() + ")");

			} else if (path.get(k).getFirst() == '*') {
				ops.add(step + ") Insert (" + path.get(k).getSecond() + ") +1");

			} else if (path.get(k).getSecond() == '*') {
				ops.add(step + ") Delete (" + path.get(k).getFirst() + ") +1");

			} else {
				ops.add(step + ") Replace (" + path.get(k).getFirst() + " -> " + path.get(k).getSecond() + ") +1");

			}
			step++;
		}

		return ops;
	}

	public static int getMED(String source, String destination) {

		return MED(source, destination)[source.length()][destination.length()].getFirst();

	}

	private JFrame frame;
	private JTextField textField1;
	static ArrayList<String> sözlük = new ArrayList<String>();
	private JTextField textField;
	private JTextField textField_1;

	public static void main(String[] args) throws FileNotFoundException {
		
		File sozluk = new File("sozluk.txt");
		Scanner myReader = new Scanner(sozluk, "UTF-8");

		while (myReader.hasNextLine()) {
			sözlük.add(myReader.nextLine());

		}
		myReader.close();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();

		frame.setBounds(450, 100, 798, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel Label1 = new JLabel("Word:");
		Label1.setBounds(10, 10, 42, 31);
		frame.getContentPane().add(Label1);

		textField1 = new JTextField();
		textField1.setBounds(44, 16, 96, 19);
		frame.getContentPane().add(textField1);
		textField1.setColumns(10);

		JLabel lblNewLabel0 = new JLabel("");
		lblNewLabel0.setBounds(44, 77, 191, 13);
		frame.getContentPane().add(lblNewLabel0);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(44, 100, 191, 13);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(44, 123, 191, 13);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(44, 146, 191, 13);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBounds(44, 169, 191, 13);
		frame.getContentPane().add(lblNewLabel_4);

		JButton btnNewButton = new JButton("Go");
		btnNewButton.setBounds(150, 15, 85, 21);
		frame.getContentPane().add(btnNewButton);

		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setBounds(44, 51, 191, 13);
		frame.getContentPane().add(lblNewLabel_5);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(44, 249, 254, 271);
		frame.getContentPane().add(textArea);

		JLabel lblNewLabel = new JLabel("Matrix");
		lblNewLabel.setBounds(153, 226, 45, 13);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_6 = new JLabel("Steps");
		lblNewLabel_6.setBounds(421, 226, 45, 13);
		frame.getContentPane().add(lblNewLabel_6);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(308, 249, 261, 271);
		frame.getContentPane().add(textArea_1);

		JLabel lblNewLabel_7 = new JLabel("Source Word:");
		lblNewLabel_7.setBounds(10, 536, 90, 13);
		frame.getContentPane().add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("Destination Word:");
		lblNewLabel_8.setBounds(202, 536, 106, 13);
		frame.getContentPane().add(lblNewLabel_8);

		textField = new JTextField();
		textField.setBounds(97, 533, 96, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(318, 533, 96, 19);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_10 = new JLabel("");
		lblNewLabel_10.setBounds(593, 536, 181, 13);
		frame.getContentPane().add(lblNewLabel_10);

		JButton btnNewButton_1 = new JButton("Show");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!textField.getText().isEmpty() && !textField_1.getText().isEmpty()) {

					long P2startTime = System.currentTimeMillis();

					ArrayList<String> steps = new ArrayList<String>();
					Tuple<Integer, Integer>[][] matrix = MED(textField.getText(), textField_1.getText());
					steps = MED_PATH(matrix, textField.getText(), textField_1.getText());

					String s = "";
					for (int i = 0; i < steps.size(); i++) {
						s = s + steps.get(i) + "\n";
					}
					textArea_1.setText(s);
					s = "";
					s = "     " + textField_1.getText().replace("", " ").trim() + "\n";
					String temp = " " + textField.getText();
					int temp_count = 0;
					for (int i = 0; i < matrix.length; i++) {

						s = s + temp.charAt(temp_count) + " ";

						temp_count++;

						for (int j = 0; j < matrix[0].length; j++) {

							s = s + matrix[i][j].getFirst() + " ";
						}
						s = s + "\n";
					}
					s = s + "\nMED : " + matrix[matrix.length - 1][matrix[0].length - 1].getFirst() + "\n";
					textArea.setText(s);
					long P2estimatedTime = System.currentTimeMillis() - P2startTime;

					lblNewLabel_10.setText("Part 2 running time is: " + P2estimatedTime + " ms.");
				}

			}
		});
		btnNewButton_1.setBounds(421, 532, 148, 21);
		frame.getContentPane().add(btnNewButton_1);

		JLabel lblNewLabel_9 = new JLabel("");
		lblNewLabel_9.setBounds(263, 19, 191, 13);
		frame.getContentPane().add(lblNewLabel_9);

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (textField1.getText().isEmpty())
					textField1.setText("Wrong Input");
				else {
					long P1startTime = System.currentTimeMillis();

					String word = textField1.getText();
					ArrayList<Tuple<String, Integer>> meds = new ArrayList<Tuple<String, Integer>>();

					for (int i = 0; i < sözlük.size(); i++) {

						meds.add(new Tuple<String, Integer>(sözlük.get(i), getMED(word, sözlük.get(i))));
					}

					Collections.sort(meds, new TupleComparator());

					lblNewLabel_5.setText("First 5 Closest words of " + textField1.getText());
					lblNewLabel0.setText("1) " + meds.get(0).getFirst() + " MED: " + meds.get(0).getSecond());
					lblNewLabel_1.setText("2) " + meds.get(1).getFirst() + " MED: " + meds.get(1).getSecond());
					lblNewLabel_2.setText("3) " + meds.get(2).getFirst() + " MED: " + meds.get(2).getSecond());
					lblNewLabel_3.setText("4) " + meds.get(3).getFirst() + " MED: " + meds.get(3).getSecond());
					lblNewLabel_4.setText("5) " + meds.get(4).getFirst() + " MED: " + meds.get(4).getSecond());

					long P1estimatedTime = System.currentTimeMillis() - P1startTime;

					lblNewLabel_9.setText("Part 1 running time is: " + P1estimatedTime + " ms.");
				}
			}
		});

	}
}
