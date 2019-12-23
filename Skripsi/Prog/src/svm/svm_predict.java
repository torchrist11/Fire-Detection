package svm;

import libsvm.*;

import java.awt.FlowLayout;
import java.io.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import Program.Control;

public class svm_predict {
	private static BufferedReader reader;
	private static BufferedReader readerExtract;
	private static BufferedWriter writer;
	private static File hasilAkhir;
	private static int count = 0;
	private static JTable table = new JTable();
	private static DefaultTableModel dm = new DefaultTableModel();;
	private static JScrollPane sp;
	private static String[] header = new String[] { "Waktu", "Hasil Prediksi" };
	private static JFrame f = new JFrame("Hasil Prediksi");
//	private static int banyakBaris = 0;

//	public boolean isCellEditable(int rowIndex, int colIndex) {
//		return false;
//	}

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
		List<String> extractCounterAwal = new ArrayList<String>();
		String hasil = "";
		String tempReader = "";
		String tempReaderExtract = "";
		String compute = "";
		String ubah = "";
		String compute2 = "";
		int count2 = 0;
		String temphasil = "";
		String temp2 = "";

		if (f.isActive() == false) {
			dm.setColumnIdentifiers(header);
			for (int ct = 0; ct < 25; ct++) {
				dm.addRow(new Object[] { "", "" });
			}
			table.setModel(dm);
			table.getColumnModel().getColumn(0).setPreferredWidth(100);
			table.getColumnModel().getColumn(1).setPreferredWidth(725);
			table.setPreferredScrollableViewportSize(table.getPreferredSize());
			table.setFillsViewportHeight(true);
			table.setEnabled(false);
			sp = new JScrollPane(table);
			f.add(sp);
			f.setVisible(true);
			f.setAlwaysOnTop(true);
			f.setSize(1000, 500);
			f.setLocationRelativeTo(null);
			f.setLayout(new FlowLayout());
			f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		} else {

		}
		while ((tempReader = reader.readLine()) != null) {
			extractCounterAwal.add(tempReader);
		}
		while ((tempReaderExtract = readerExtract.readLine()) != null) {
			String[] split1 = tempReaderExtract.split(";");
			if (split1[11].equals("[0]")) {
				extractCounter.add(tempReaderExtract);
			} else {

			}
		}
		
		for(int i = 0; i< extractCounterAwal.size();i++) {
			compute2 = extractCounterAwal.get(count2);
			compute = extractCounter.get(count2);
			count2++;
			String[] splitter = compute.split(";");

			StringTokenizer st = new StringTokenizer(compute2, " \t\n\r\f:");
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
			for (int j = 0; j < splitter.length - 1; j++) {
				temphasil += splitter[j] + ";";
				if (j == 1) {

				} else {
					temp2 += splitter[j] + ";";
				}
			}

			hasil = temphasil + hasilPredict;
			writer.write(hasil + "\n");
			System.out.println(hasil);
			if (hasil.equals("") || hasil == null) {

			} else {
//					int banyakBaris =dm.getRowCount();
//					dm.insertRow(banyakBaris, new Object[] { splitter[1], temp2 });
				dm.insertRow(0, new Object[] { splitter[1], temp2 + " Hasil Prediksi: " + hasilPredict });
				dm.fireTableDataChanged();
//					new svm_predict();
			}
			temphasil="";
			hasil = "";
			temp2 ="";
		}
		writer.close();

		FileReader fr = new FileReader(Control.fileExtract);
		BufferedReader br = new BufferedReader(fr);

		while ((ubah = br.readLine()) != null) {
			if (ubah.contains("[0]")) {
				ubah = ubah.replace("[0]", "[1]");
			}
			temp.add(ubah);
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
