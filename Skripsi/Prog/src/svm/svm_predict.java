package svm;

import libsvm.*;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;
import javax.swing.table.DefaultTableModel;
import Program.Control;

public class svm_predict {
	private static BufferedReader reader;
	private static BufferedReader readerExtract;
	private static BufferedWriter writer;
	private static File hasilAkhir;
	private static int count = 0;
	static JTable table = new JTable();;
	static DefaultTableModel dm = new DefaultTableModel();;
	static JScrollPane sp;
	static String[] header = new String[] { "Waktu", "Hasil Prediksi" };
	static String[][] cols;
	static JFrame f = new JFrame("Hasil Prediksi");

	public svm_predict() {
//		table.setSize(800, 500);
//		table.setPreferredSize(table.getSize());

	}

	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}

	private static svm_print_interface svm_print_stdout = new svm_print_interface() {
		public void print(String s) {
			System.out.print(s);
		}
	};

	private static svm_print_interface svm_print_string = svm_print_stdout;

	public static void info(String s) {
		svm_print_string.print(s);
	}

	private static double atof(String s) {
		return Double.valueOf(s).doubleValue();
	}

	private static int atoi(String s) {
		return Integer.parseInt(s);
	}

	public static void predict(File data, svm_model model) throws IOException {
		/* predict_probability = 0 */
		if (count == 0) {
			int nomorFileAkhir = 1;
			hasilAkhir = new File(
					"C:\\Users\\torch\\eclipse-workspace\\Prog\\Hasil Akhir\\Akhir_" + nomorFileAkhir + ".txt");
			boolean exist = hasilAkhir.exists();
			try {
				if (exist == true) {
					while (exist == true) {
						nomorFileAkhir++;
						hasilAkhir = new File("C:\\Users\\torch\\eclipse-workspace\\Prog\\Hasil Akhir\\Akhir_"
								+ nomorFileAkhir + ".txt");
						exist = hasilAkhir.exists();
					}
				}
				count++;
			} catch (Exception e) {

			}
		}

		reader = new BufferedReader(new FileReader(data));
		readerExtract = new BufferedReader(new FileReader(Control.fileExtract));
		FileWriter fw = new FileWriter(hasilAkhir, true);
		writer = new BufferedWriter(fw);
		List<String> temp = new ArrayList<String>();
		List<String> extractCounter = new ArrayList<String>();
		String hasil = "";
		String line = "";
		String line2 = "";
		String line3 = "";
		String line4 = "";
		int count2 = 0;
		String temphasil = "";
		String temp2 = "";
		int banyakBaris = 0;

		if (f.isActive() == false) {
			dm.setColumnIdentifiers(header);
			for (int ct = 0; ct < 25; ct++) {
				dm.addRow(new Object[] {"",""});
			}
			table.setModel(dm);
			table.getColumnModel().getColumn(0).setPreferredWidth(100);
			table.getColumnModel().getColumn(1).setPreferredWidth(500);
			table.setPreferredScrollableViewportSize(table.getPreferredSize());
			table.setFillsViewportHeight(true);
			table.setEnabled(false);
			sp = new JScrollPane(table);
			f.add(sp);
			f.setVisible(true);
			f.setAlwaysOnTop(true);
			f.setSize(700, 500);
			f.setLocationRelativeTo(null);
			f.setLayout(new FlowLayout());
		} else {
			banyakBaris++;
		}
		while ((line = reader.readLine()) != null) {
			if (count2 == 0) {
				while ((line2 = readerExtract.readLine()) != null) {
					String[] split1 = line2.split(";");
					if (split1[11].equals("[0]")) {
						extractCounter.add(line2);
					} else {

					}
				}
			}
			line3 = extractCounter.get(count2);
			count2++;
			String[] splitter = line3.split(";");

			StringTokenizer st = new StringTokenizer(line, " \t\n\r\f:");
			int m = st.countTokens() / 2;
			svm_node[] x = new svm_node[m];
			for (int j = 0; j < m; j++) {
				x[j] = new svm_node();
				x[j].index = atoi(st.nextToken());
				x[j].value = atof(st.nextToken());
			}

			double predict_label;

			predict_label = svm.svm_predict(model, x);
			int hasilPredict = (int) predict_label;
			if (hasilPredict == -1) {
				hasilPredict = 0;
			}
			for (int i = 0; i < splitter.length - 1; i++) {
				temphasil += splitter[i] + ";";
			}
			for (int i = 0; i < splitter.length - 1; i++) {
				if (i == 1) {

				} else {
					temp2 += splitter[i] + ";";
				}
			}
			hasil = temphasil + hasilPredict;
			writer.write(hasil + "\n");
			System.out.println(hasil);
			if (hasil.equals("") || hasil == null) {

			} else {
//				int banyakBaris =dm.getRowCount();
				dm.insertRow(banyakBaris, new Object[] { splitter[1], temp2 });
//				dm.addRow(new Object[] { splitter[1], /* splitter[0] + ", Hasil Prediksi: " + hasilPredict */ temp2 });
				dm.fireTableDataChanged();
//				new svm_predict();
			}
		}
		writer.close();

		FileReader fr = new FileReader(Control.fileExtract);
		BufferedReader br = new BufferedReader(fr);

		while ((line4 = br.readLine()) != null) {
			if (line4.contains("[0]")) {
				line4 = line4.replace("[0]", "[1]");
			}
			temp.add(line4);
		}

		fr.close();
		br.close();

		FileWriter fw2 = new FileWriter(Control.fileExtract);
		BufferedWriter out = new BufferedWriter(fw2);
		for (String s : temp) {
			out.write(s);
			out.newLine();
		}
		out.flush();
		out.close();

		extractCounter.clear();
	}

	public static void main(String argv[]) throws IOException {
//		int i, predict_probability = 0;
//		svm_print_string = svm_print_stdout;
//
//		// parse options
//		for (i = 0; i < argv.length; i++) {
//			if (argv[i].charAt(0) != '-')
//				break;
//			++i;
//			switch (argv[i - 1].charAt(1)) {
//			case 'b':
//				predict_probability = atoi(argv[i]);
//				break;
//			case 'q':
//				svm_print_string = svm_print_null;
//				i--;
//				break;
//			default:
//				System.err.print("Unknown option: " + argv[i - 1] + "\n");
//				exit_with_help();
//			}
//		}
//		if (i >= argv.length - 2)
//			exit_with_help();
//		try {
//			BufferedReader input = new BufferedReader(new FileReader(argv[i]));
//			DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(argv[i + 2])));
//			svm_model model = svm.svm_load_model(argv[i + 1]);
//			if (model == null) {
//				System.err.print("can't open model file " + argv[i + 1] + "\n");
//				System.exit(1);
//			}
//			if (predict_probability == 1) {
//				if (svm.svm_check_probability_model(model) == 0) {
//					System.err.print("Model does not support probabiliy estimates\n");
//					System.exit(1);
//				}
//			} else {
//				if (svm.svm_check_probability_model(model) != 0) {
//					svm_predict.info("Model supports probability estimates, but disabled in prediction.\n");
//				}
//			}
//			predict(input, output, model, predict_probability);
//			input.close();
//			output.close();
//		} catch (FileNotFoundException e) {
//			exit_with_help();
//		} catch (ArrayIndexOutOfBoundsException e) {
//			exit_with_help();
//		}
	}
}
