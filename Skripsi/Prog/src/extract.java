import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class extract {
//	static BufferedReader read;
//	static BufferedWriter writer;
	private BufferedWriter writer;
	private static String[] alamat;
	private ArrayList<Double> node1T = new ArrayList<Double>();
	private ArrayList<Double> node1H = new ArrayList<Double>();
	private ArrayList<Double> node1P = new ArrayList<Double>();
	private ArrayList<Double> node2T = new ArrayList<Double>();
	private ArrayList<Double> node2H = new ArrayList<Double>();
	private ArrayList<Double> node2P = new ArrayList<Double>();
	private ArrayList<Double> node3T = new ArrayList<Double>();
	private ArrayList<Double> node3H = new ArrayList<Double>();
	private ArrayList<Double> node3P = new ArrayList<Double>();
	private ArrayList<Double> node4T = new ArrayList<Double>();
	private ArrayList<Double> node4H = new ArrayList<Double>();
	private ArrayList<Double> node4P = new ArrayList<Double>();
	private ArrayList<Double> node5T = new ArrayList<Double>();
	private ArrayList<Double> node5H = new ArrayList<Double>();
	private ArrayList<Double> node5P = new ArrayList<Double>();
	String[] temp2;
	String[] waktu;
	int hitungextract1 = 0;
	int hitungextract2 = 0;
	String idx = "";

//	public static void main(String[] args) {
//		
//		try {
//			fiturextract();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//	
//	static void fiturextract() throws Exception {
//		String tempawal;
//		int akhir = 1;
//		File fileAkhir = new File("C:\\Users\\torch\\eclipse-workspace\\Prog\\Hasil Akhir\\Akhir" + akhir + ".txt");
//		boolean exist = fileAkhir.exists();
//		
//		try {
//			if (exist == true) {
//				while (exist == true) {
//					akhir++;
//					fileAkhir = new File("C:\\Users\\torch\\eclipse-workspace\\Prog\\Hasil Akhir\\Akhir" + akhir + ".txt");
//					exist = fileAkhir.exists();
//				}
//			}
//			
//			read = new BufferedReader(new FileReader("C:\\Users\\torch\\eclipse-workspace\\Prog\\Hasil Sense\\Sense_14_47_13_26_9_2019.txt"));
//			writer = new BufferedWriter(new FileWriter(fileAkhir));
//			
//			while ((tempawal = read.readLine()) != null) {
//				temp.add(tempawal);
//			}
//			read.close();
//			inside();
//			writer.close();
//		} catch (Exception e) {
//
//		}
//	}

	public void inside() {
		int awal = 0;
		Control ctrl = new Control();
		try {
			writer = new BufferedWriter(new FileWriter(Control.fileExtract));
			if (awal == 0) {
				writer.write(
						"Node Sensor;max Temperature; min Temperature; rata-rata Temperature; max Humidity; min Humidity; rata-rata Humidity; max Pressure; min Pressure; rata-rata Pressure");
				awal = 1;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		alamat = new String[] { "(AAAF)", "(AADC)", "(CACF)", "(BCAF)", "(DACE)" };
		int counterwaktu = 0;
		try {
			for (int d = 0; d < ctrl.temp.size(); d++) {
				temp2 = ctrl.temp.get(d).split(";");
				if (temp2[6].equals("[0]")) {
					idx = temp2[0];
					waktu = temp2[2].split(":");
					counterwaktu = Integer.parseInt(waktu[2]);

					if (counterwaktu < 30) {
						inputarrayextract();
						hitungextract1 = 1;
					}
					if (counterwaktu <= 60 && counterwaktu >= 30) {
						if (hitungextract1 == 1) {
							writeextract();
							hitungextract1 = 0;
						} else {
							inputarrayextract();
							hitungextract2 = 1;
						}
					}
					if (hitungextract2 == 1 && hitungextract1 == 1) {
						writeextract();
						hitungextract2 = 0;
					}
				}
			}
			writer.close();
		} catch (Exception e) {

		}
	}

	private void inputarrayextract() {
		if (idx.equalsIgnoreCase(alamat[0])) {
			// Masukin tiap nilai sensing ke array
			node1T.add(Double.parseDouble(temp2[3]));
			node1H.add(Double.parseDouble(temp2[4]));
			node1P.add(Double.parseDouble(temp2[5]));
		} else if (idx.equalsIgnoreCase(alamat[1])) {
			// Masukin tiap nilai sensing ke array
			node2T.add(Double.parseDouble(temp2[3]));
			node2H.add(Double.parseDouble(temp2[4]));
			node2P.add(Double.parseDouble(temp2[5]));
		} else if (idx.equalsIgnoreCase(alamat[2])) {
			// Masukin tiap nilai sensing ke array
			node3T.add(Double.parseDouble(temp2[3]));
			node3H.add(Double.parseDouble(temp2[4]));
			node3P.add(Double.parseDouble(temp2[5]));
		} else if (idx.equalsIgnoreCase(alamat[3])) {
			// Masukin tiap nilai sensing ke array
			node4T.add(Double.parseDouble(temp2[3]));
			node4H.add(Double.parseDouble(temp2[4]));
			node4P.add(Double.parseDouble(temp2[5]));
		} else if (idx.equalsIgnoreCase(alamat[4])) {
			// Masukin tiap nilai sensing ke array
			node5T.add(Double.parseDouble(temp2[3]));
			node5H.add(Double.parseDouble(temp2[4]));
			node5P.add(Double.parseDouble(temp2[5]));
		}
	}

	private void writeextract() throws IOException {
		if (node1T.size() == 0) {

		} else {
			double temphasil = 0;
			String hasilextract = "";
			String x = "";
			String y = "";
			String z = "";
			double maxT = node1T.get(0);
			double minT = node1T.get(0);
			double maxH = node1H.get(0);
			double minH = node1H.get(0);
			double maxP = node1P.get(0);
			double minP = node1P.get(0);

			// Rata-rata Temperature
			for (int i = 0; i < node1T.size(); i++) {
				temphasil += node1T.get(i);
			}
			temphasil = temphasil / node1T.size();

			// Maksimum dan minimum Temperature
			for (int i = 0; i < node1T.size(); i++) {
				if (maxT < node1T.get(i)) {
					maxT = node1T.get(i);
				} else {
					if (minT > node1T.get(i)) {
						minT = node1T.get(i);
					}
				}
			}
			// hasil Temperature
			x = String.format("%.4f", maxT);
			y = String.format("%.4f", minT);
			z = String.format("%.4f", temphasil);
			hasilextract += x + ";" + y + ";" + z + ";";
			// hasilextract1 += maxT + " " + minT + " " + temphasil + " ";
			temphasil = 0;

			// Rata-rata Humidity
			for (int i = 0; i < node1H.size(); i++) {
				temphasil += node1H.get(i);
			}
			temphasil = temphasil / node1H.size();

			// Maksimum dan minimum Humidity
			for (int i = 0; i < node1H.size(); i++) {
				if (maxH < node1H.get(i)) {
					maxH = node1H.get(i);
				} else {
					if (minH > node1H.get(i)) {
						minH = node1H.get(i);
					}
				}
			}
			// hasil Humidity
			x = String.format("%.4f", maxH);
			y = String.format("%.4f", minH);
			z = String.format("%.4f", temphasil);
			hasilextract += x + ";" + y + ";" + z + ";";
			// hasilextract1 += maxH + " " + minH + " " + temphasil + " ";
			temphasil = 0;

			// Rata-rata Pressure
			for (int i = 0; i < node1P.size(); i++) {
				temphasil += node1P.get(i);
			}
			temphasil = temphasil / node1P.size();

			// Maksimum dan minimum Pressure
			for (int i = 0; i < node1P.size(); i++) {
				if (maxP < node1P.get(i)) {
					maxP = node1P.get(i);
				} else {
					if (minP > node1P.get(i)) {
						minP = node1P.get(i);
					}
				}
			}
			// hasil Pressure
			x = String.format("%.4f", maxP);
			y = String.format("%.4f", minP);
			z = String.format("%.4f", temphasil);
			hasilextract += x + " " + y + ";" + z + ";";
			// hasilextract1 += maxP + " " + minP + " " + temphasil + " ";
			writer.write(alamat[0] + ";");
			if (hitungextract1 == 1 && hitungextract2 == 0) {
				writer.write(waktu[0] + ":" + waktu[1] + ":" + "30;");
			} else {
				writer.write(waktu[0] + ":" + waktu[1] + ":" + "00;");
			}
			writer.write(hasilextract);
			writer.newLine();
			hasilextract = "";
			node1T.clear();
			node1H.clear();
			node1P.clear();
		}
		if (node2T.size() == 0) {

		} else {
			double temphasil = 0;
			String hasilextract = "";
			String x = "";
			String y = "";
			String z = "";
			double maxT = node2T.get(0);
			double minT = node2T.get(0);
			double maxH = node2H.get(0);
			double minH = node2H.get(0);
			double maxP = node2P.get(0);
			double minP = node2P.get(0);

			// Rata-rata Temperature
			for (int i = 0; i < node2T.size(); i++) {
				temphasil += node2T.get(i);
			}
			temphasil = temphasil / node2T.size();

			// Maksimum dan minimum Temperature
			for (int i = 0; i < node2T.size(); i++) {
				if (maxT < node2T.get(i)) {
					maxT = node2T.get(i);
				} else {
					if (minT > node2T.get(i)) {
						minT = node2T.get(i);
					}
				}
			}
			// hasil Temperature
			x = String.format("%.4f", maxT);
			y = String.format("%.4f", minT);
			z = String.format("%.4f", temphasil);
			hasilextract += x + ";" + y + ";" + z + ";";
			// hasilextract2 += maxT + " " + minT + " " + temphasil + " ";
			temphasil = 0;

			// Rata-rata Humidity
			for (int i = 0; i < node2H.size(); i++) {
				temphasil += node2H.get(i);
			}
			temphasil = temphasil / node2H.size();

			// Maksimum dan minimum Humidity
			for (int i = 0; i < node2H.size(); i++) {
				if (maxH < node2H.get(i)) {
					maxH = node2H.get(i);
				} else {
					if (minH > node2H.get(i)) {
						minH = node2H.get(i);
					}
				}
			}
			// hasil Humidity
			x = String.format("%.4f", maxH);
			y = String.format("%.4f", minH);
			z = String.format("%.4f", temphasil);
			hasilextract += x + ";" + y + ";" + z + ";";
			// hasilextract2 += maxH + ";" + minH + ";" + temphasil + ";";
			temphasil = 0;

			// Rata-rata Pressure
			for (int i = 0; i < node2P.size(); i++) {
				temphasil += node2P.get(i);
			}
			temphasil = temphasil / node2P.size();

			// Maksimum dan minimum Pressure
			for (int i = 0; i < node2P.size(); i++) {
				if (maxP < node2P.get(i)) {
					maxP = node2P.get(i);
				} else {
					if (minP > node2P.get(i)) {
						minP = node2P.get(i);
					}
				}
			}
			// hasil Pressure
			x = String.format("%.4f", maxP);
			y = String.format("%.4f", minP);
			z = String.format("%.4f", temphasil);
			hasilextract += x + ";" + y + ";" + z + ";";
			// hasilextract2 += maxP + " " + minP + " " + temphasil + " ";
			writer.write(alamat[1] + ";");
			if (hitungextract1 == 1 && hitungextract2 == 0) {
				writer.write(waktu[0] + ":" + waktu[1] + ":" + "30;");
			} else {
				writer.write(waktu[0] + ":" + waktu[1] + ":" + "00;");
			}
			writer.write(hasilextract);
			writer.newLine();
			hasilextract = "";
			node2T.clear();
			node2H.clear();
			node2P.clear();
		}
		if (node3T.size() == 0) {

		} else {
			double temphasil = 0;
			String hasilextract = "";
			String x = "";
			String y = "";
			String z = "";
			double maxT = node3T.get(0);
			double minT = node3T.get(0);
			double maxH = node3H.get(0);
			double minH = node3H.get(0);
			double maxP = node3P.get(0);
			double minP = node3P.get(0);

			// Rata-rata Temperature
			for (int i = 0; i < node3T.size(); i++) {
				temphasil += node3T.get(i);
			}
			temphasil = temphasil / node3T.size();

			// Maksimum dan minimum Temperature
			for (int i = 0; i < node3T.size(); i++) {
				if (maxT < node3T.get(i)) {
					maxT = node3T.get(i);
				} else {
					if (minT > node3T.get(i)) {
						minT = node3T.get(i);
					}
				}
			}
			// hasil Temperature
			x = String.format("%.4f", maxT);
			y = String.format("%.4f", minT);
			z = String.format("%.4f", temphasil);
			hasilextract += x + ";" + y + ";" + z + ";";
			// hasilextract3 += maxT + " " + minT + " " + temphasil + " ";
			temphasil = 0;

			// Rata-rata Humidity
			for (int i = 0; i < node3H.size(); i++) {
				temphasil += node3H.get(i);
			}
			temphasil = temphasil / node3H.size();

			// Maksimum dan minimum Humidity
			for (int i = 0; i < node3H.size(); i++) {
				if (maxH < node3H.get(i)) {
					maxH = node3H.get(i);
				} else {
					if (minH > node3H.get(i)) {
						minH = node3H.get(i);
					}
				}
			}
			// hasil Humidity
			x = String.format("%.4f", maxH);
			y = String.format("%.4f", minH);
			z = String.format("%.4f", temphasil);
			hasilextract += x + ";" + y + ";" + z + ";";
			// hasilextract3 += maxH + " " + minH + " " + temphasil + " ";
			temphasil = 0;

			// Rata-rata Pressure
			for (int i = 0; i < node3P.size(); i++) {
				temphasil += node3P.get(i);
			}
			temphasil = temphasil / node3P.size();

			// Maksimum dan minimum Pressure
			for (int i = 0; i < node3P.size(); i++) {
				if (maxP < node3P.get(i)) {
					maxP = node3P.get(i);
				} else {
					if (minP > node3P.get(i)) {
						minP = node3P.get(i);
					}
				}
			}
			// hasil Pressure
			x = String.format("%.4f", maxP);
			y = String.format("%.4f", minP);
			z = String.format("%.4f", temphasil);
			hasilextract += x + ";" + y + ";" + z + ";";
			// hasilextract3 += maxP + " " + minP + " " + temphasil + " ";
			writer.write(alamat[2] + ";");
			if (hitungextract1 == 1 && hitungextract2 == 0) {
				writer.write(waktu[0] + ":" + waktu[1] + ":" + "30;");
			} else {
				writer.write(waktu[0] + ":" + waktu[1] + ":" + "00;");
			}
			writer.write(hasilextract);
			writer.newLine();
			hasilextract = "";
			node3T.clear();
			node3H.clear();
			node3P.clear();
		}
		if (node4T.size() == 0) {

		} else {
			double temphasil = 0;
			String hasilextract = "";
			String x = "";
			String y = "";
			String z = "";
			double maxT = node4T.get(0);
			double minT = node4T.get(0);
			double maxH = node4H.get(0);
			double minH = node4H.get(0);
			double maxP = node4P.get(0);
			double minP = node4P.get(0);

			// Rata-rata Temperature
			for (int i = 0; i < node4T.size(); i++) {
				temphasil += node4T.get(i);
			}
			temphasil = temphasil / node4T.size();

			// Maksimum dan minimum Temperature
			for (int i = 0; i < node4T.size(); i++) {
				if (maxT < node4T.get(i)) {
					maxT = node4T.get(i);
				} else {
					if (minT > node4T.get(i)) {
						minT = node4T.get(i);
					}
				}
			}
			// hasil Temperature
			x = String.format("%.4f", maxT);
			y = String.format("%.4f", minT);
			z = String.format("%.4f", temphasil);
			hasilextract += x + ";" + y + ";" + z + ";";
			// hasilextract4 += maxT + " " + minT + " " + temphasil + " ";
			temphasil = 0;

			// Rata-rata Humidity
			for (int i = 0; i < node4H.size(); i++) {
				temphasil += node4H.get(i);
			}
			temphasil = temphasil / node4H.size();

			// Maksimum dan minimum Humidity
			for (int i = 0; i < node4H.size(); i++) {
				if (maxH < node4H.get(i)) {
					maxH = node4H.get(i);
				} else {
					if (minH > node4H.get(i)) {
						minH = node4H.get(i);
					}
				}
			}
			// hasil Humidity
			x = String.format("%.4f", maxH);
			y = String.format("%.4f", minH);
			z = String.format("%.4f", temphasil);
			hasilextract += x + ";" + y + ";" + z + ";";
			// hasilextract4 += maxH + " " + minH + " " + temphasil + " ";
			temphasil = 0;

			// Rata-rata Pressure
			for (int i = 0; i < node4P.size(); i++) {
				temphasil += node4P.get(i);
			}
			temphasil = temphasil / node4P.size();

			// Maksimum dan minimum Pressure
			for (int i = 0; i < node4P.size(); i++) {
				if (maxP < node4P.get(i)) {
					maxP = node4P.get(i);
				} else {
					if (minP > node4P.get(i)) {
						minP = node4P.get(i);
					}
				}
			}
			// hasil Pressure
			x = String.format("%.4f", maxP);
			y = String.format("%.4f", minP);
			z = String.format("%.4f", temphasil);
			hasilextract += x + ";" + y + ";" + z + ";";
			// hasilextract4 += maxP + " " + minP + " " + temphasil + " ";
			writer.write(alamat[3] + ";");
			if (hitungextract1 == 1 && hitungextract2 == 0) {
				writer.write(waktu[0] + ":" + waktu[1] + ":" + "30;");
			} else {
				writer.write(waktu[0] + ":" + waktu[1] + ":" + "00;");
			}
			writer.write(hasilextract);
			writer.newLine();
			hasilextract = "";
			node4T.clear();
			node4H.clear();
			node4P.clear();
		}
		if (node5T.size() == 0) {

		} else {
			double temphasil = 0;
			String hasilextract = "";
			String x = "";
			String y = "";
			String z = "";
			double maxT = node5T.get(0);
			double minT = node5T.get(0);
			double maxH = node5H.get(0);
			double minH = node5H.get(0);
			double maxP = node5P.get(0);
			double minP = node5P.get(0);

			// Rata-rata Temperature
			for (int i = 0; i < node5T.size(); i++) {
				temphasil += node5T.get(i);
			}
			temphasil = temphasil / node5T.size();

			// Maksimum dan minimum Temperature
			for (int i = 0; i < node5T.size(); i++) {
				if (maxT < node5T.get(i)) {
					maxT = node5T.get(i);
				} else {
					if (minT > node5T.get(i)) {
						minT = node5T.get(i);
					}
				}
			}
			// hasil Temperature
			x = String.format("%.4f", maxT);
			y = String.format("%.4f", minT);
			z = String.format("%.4f", temphasil);
			hasilextract += x + ";" + y + ";" + z + ";";
			// hasilextract5 += maxT + " " + minT + " " + temphasil + " ";
			temphasil = 0;

			// Rata-rata Humidity
			for (int i = 0; i < node5H.size(); i++) {
				temphasil += node5H.get(i);
			}
			temphasil = temphasil / node5H.size();

			// Maksimum dan minimum Humidity
			for (int i = 0; i < node5H.size(); i++) {
				if (maxH < node5H.get(i)) {
					maxH = node5H.get(i);
				} else {
					if (minH > node5H.get(i)) {
						minH = node5H.get(i);
					}
				}
			}
			// hasil Humidity
			x = String.format("%.4f", maxH);
			y = String.format("%.4f", minH);
			z = String.format("%.4f", temphasil);
			hasilextract += x + ";" + y + ";" + z + ";";
			// hasilextract5 += maxH + " " + minH + " " + temphasil + " ";
			temphasil = 0;

			// Rata-rata Pressure
			for (int i = 0; i < node5P.size(); i++) {
				temphasil += node5P.get(i);
			}
			temphasil = temphasil / node5P.size();

			// Maksimum dan minimum Pressure
			for (int i = 0; i < node5P.size(); i++) {
				if (maxP < node5P.get(i)) {
					maxP = node5P.get(i);
				} else {
					if (minP > node5P.get(i)) {
						minP = node5P.get(i);
					}
				}
			}
			// hasil Pressure
			x = String.format("%.4f", maxP);
			y = String.format("%.4f", minP);
			z = String.format("%.4f", temphasil);
			hasilextract += x + ";" + y + ";" + z + ";";
			// hasilextract5 += maxP + " " + minP + " " + temphasil + " ";
			writer.write(alamat[4] + ";");
			if (hitungextract1 == 1 && hitungextract2 == 0) {
				writer.write(waktu[0] + ":" + waktu[1] + ":" + "30;");
			} else {
				writer.write(waktu[0] + ":" + waktu[1] + ":" + "00;");
			}
			writer.write(hasilextract);
			writer.newLine();
			hasilextract = "";
			node5T.clear();
			node5H.clear();
			node5P.clear();
		}
	}
}