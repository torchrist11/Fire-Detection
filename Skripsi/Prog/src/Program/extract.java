package Program;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class extract {
	private BufferedWriter writer;
	private BufferedWriter writerX;
	public static String[] alamat;
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
	String idx = "";
	int counter1 = 2;
	int counter2 = 2;
	int counter3 = 2;
	int counter4 = 2;
	int counter5 = 2;
	int counter6 = 2;
	int counter7 = 2;
	int counter8 = 2;
	int counter9 = 2;
	int counter10 = 2;

	public static void main(String[] args) {
	}

	public void Run() {
		try {
			writer = new BufferedWriter(new FileWriter(Control.fileExtract, true));
			writerX = new BufferedWriter(new FileWriter(Control.filePredict, false));
//			if(awal==0) {
//				writer.write("Node Sensor;max Temperature; min Temperature; rata-rata Temperature; max Humidity; min Humidity; rata-rata Humidity; max Pressure; min Pressure; rata-rata Pressure;tanda");
//			awal++;
//			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		alamat = new String[] { "(AAAF)", "(AADC)", "(CACF)", "(BCAF)", "(DACE)" };
		try {
			for (int d = 0; d < Control.temp.size(); d++) {
				temp2 = Control.temp.get(d).split(";");
				if (temp2[6].equals("[0]")) {
					idx = temp2[0];
					if (idx.equalsIgnoreCase(alamat[0])) {
						waktu = temp2[1].split(":");
						String jam = waktu[0];
						String menit = waktu[1];
						int counterwaktu = Integer.parseInt(waktu[2]);
						if (counterwaktu <= 30) {
							
							inputtoarray(1);
							counter1 = 1;
						} else if (counterwaktu > 30 && counterwaktu < 60) {
							if (counter1 == 1) {
								writeextract(1, jam, menit, "30");
								counter1 = 0;
							}
							inputtoarray(1);
							counter2 = 1;
						}
						if (counterwaktu <= 30 && counter2 == 1) {
							writeextract(1, jam, menit, "00");
							counter2 = 0;
						}
					} else if (idx.equalsIgnoreCase(alamat[1])) {
						waktu = temp2[1].split(":");
						String jam = waktu[0];
						String menit = waktu[1];
						int counterwaktu = Integer.parseInt(waktu[2]);
						if (counterwaktu <= 30) {
							// Masukin tiap nilai sensing ke array
							inputtoarray(2);
							counter3 = 0;
						} else {
							if (counter3 == 0) {
								writeextract(2, jam, menit, "30");
								counter3 = 1;
							}
							inputtoarray(2);
							counter4 = 1;
						}
						if (counterwaktu <= 30 && counter4 == 1) {
							writeextract(2, jam, menit, "00");
							counter4 = 0;
						}
					} else if (idx.equalsIgnoreCase(alamat[2])) {
						waktu = temp2[1].split(":");
						String jam = waktu[0];
						String menit = waktu[1];
						int counterwaktu = Integer.parseInt(waktu[2]);
						if (counterwaktu <= 30) {
							// Masukin tiap nilai sensing ke array
							inputtoarray(3);
							counter5 = 0;
						} else {
							if (counter5 == 0) {
								writeextract(3, jam, menit, "30");
								counter5 = 1;
							}
							inputtoarray(3);
							counter6 = 1;
						}
						if (counterwaktu <= 30 && counter6 == 1) {
							writeextract(3, jam, menit, "00");
							counter6 = 0;
						}
					} else if (idx.equalsIgnoreCase(alamat[3])) {
						waktu = temp2[1].split(":");
						String jam = waktu[0];
						String menit = waktu[1];
						int counterwaktu = Integer.parseInt(waktu[2]);
						if (counterwaktu <= 30) {
							// Masukin tiap nilai sensing ke array
							inputtoarray(4);
							counter7 = 0;
						} else {

							if (counter7 == 0) {
								writeextract(4, jam, menit, "30");
								counter7 = 1;
							}
							inputtoarray(4);
							counter8 = 1;
						}
						if (counterwaktu <= 30 && counter8 == 1) {
							writeextract(4, jam, menit, "00");
							counter8 = 0;
						}
					} else if (idx.equalsIgnoreCase(alamat[4])) {
						waktu = temp2[1].split(":");
						String jam = waktu[0];
						String menit = waktu[1];
						int counterwaktu = Integer.parseInt(waktu[2]);
						if (counterwaktu <= 30) {
							// Masukin tiap nilai sensing ke array
							inputtoarray(5);
							counter9 = 0;
						} else {

							if (counter9 == 0) {
								writeextract(5, jam, menit, "30");
								counter9 = 1;
							}
							inputtoarray(5);
							counter10 = 1;
						}
						if (counterwaktu <= 30 && counter10 == 1) {
							writeextract(5, jam, menit, "00");
							counter10 = 0;
						}
					}
				} else {

				}
			}
			writer.close();
			writerX.close();
		} catch (Exception e) {

		}
	}

	private void inputtoarray(int code) {
		// Masukin tiap nilai sensing ke array
		if (code == 1) {
			node1T.add(Double.parseDouble(temp2[3]));
			node1H.add(Double.parseDouble(temp2[4]));
			node1P.add(Double.parseDouble(temp2[5]));
		} else if (code == 2) {
			node2T.add(Double.parseDouble(temp2[3]));
			node2H.add(Double.parseDouble(temp2[4]));
			node2P.add(Double.parseDouble(temp2[5]));
		} else if (code == 3) {
			node3T.add(Double.parseDouble(temp2[3]));
			node3H.add(Double.parseDouble(temp2[4]));
			node3P.add(Double.parseDouble(temp2[5]));
		} else if (code == 4) {
			node4T.add(Double.parseDouble(temp2[3]));
			node4H.add(Double.parseDouble(temp2[4]));
			node4P.add(Double.parseDouble(temp2[5]));
		} else if (code == 5) {
			node5T.add(Double.parseDouble(temp2[3]));
			node5H.add(Double.parseDouble(temp2[4]));
			node5P.add(Double.parseDouble(temp2[5]));
		}
	}

	private void writeextract(int choice, String jam, String menit, String detik) throws IOException {
		if (choice == 1) {
			double tempRata2 = 0;
			String hasilextract = "";
			String hasilextracttest = "";
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
				tempRata2 += node1T.get(i);
			}
			tempRata2 = tempRata2 / node1T.size();

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
			z = String.format("%.4f", tempRata2);
			hasilextract += x + ";" + y + ";" + z + ";";
			hasilextracttest += "1:" + x + " 2:" + y + " 3:" + z;
			tempRata2 = 0;

			// Rata-rata Humidity
			for (int i = 0; i < node1H.size(); i++) {
				tempRata2 += node1H.get(i);
			}
			tempRata2 = tempRata2 / node1H.size();

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
			z = String.format("%.4f", tempRata2);
			hasilextract += x + ";" + y + ";" + z + ";";
			hasilextracttest += " 4:" + x + " 5:" + y + " 6:" + z;
			tempRata2 = 0;

			// Rata-rata Pressure
			for (int i = 0; i < node1P.size(); i++) {
				tempRata2 += node1P.get(i);
			}
			tempRata2 = tempRata2 / node1P.size();

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
			z = String.format("%.4f", tempRata2);
			hasilextract += x + ";" + y + ";" + z + ";";
			hasilextracttest += " 7:" + x + " 8:" + y + " 9:" + z;
			writer.write(alamat[0] + ";");
			writer.write(jam + ":" + menit + ":" + detik + ";");
			writer.write(hasilextract + "[0];");
			writerX.write(hasilextracttest);
			writer.newLine();
			writerX.newLine();
			hasilextract = "";
			hasilextracttest = "";
			node1T.clear();
			node1H.clear();
			node1P.clear();
		} else if (choice == 2) {
			double tempRata2 = 0;
			String hasilextract = "";
			String hasilextracttest = "";
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
				tempRata2 += node2T.get(i);
			}
			tempRata2 = tempRata2 / node2T.size();

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
			z = String.format("%.4f", tempRata2);
			hasilextract += x + ";" + y + ";" + z + ";";
			hasilextracttest += "1:" + x + " 2:" + y + " 3:" + z;
			tempRata2 = 0;

			// Rata-rata Humidity
			for (int i = 0; i < node2H.size(); i++) {
				tempRata2 += node2H.get(i);
			}
			tempRata2 = tempRata2 / node2H.size();

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
			z = String.format("%.4f", tempRata2);
			hasilextract += x + ";" + y + ";" + z + ";";
			hasilextracttest += " 4:" + x + " 5:" + y + " 6:" + z;
			tempRata2 = 0;

			// Rata-rata Pressure
			for (int i = 0; i < node2P.size(); i++) {
				tempRata2 += node2P.get(i);
			}
			tempRata2 = tempRata2 / node2P.size();

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
			z = String.format("%.4f", tempRata2);
			hasilextract += x + ";" + y + ";" + z + ";";
			hasilextracttest += " 7:" + x + " 8:" + y + " 9:" + z;
			writer.write(alamat[1] + ";");
			writer.write(jam + ":" + menit + ":" + detik + ";");
			writer.write(hasilextract + "[0];");
			writerX.write(hasilextracttest);
			writer.newLine();
			writerX.newLine();
			hasilextract = "";
			hasilextracttest = "";
			node2T.clear();
			node2H.clear();
			node2P.clear();
		} else if (choice == 3) {
			double tempRata2 = 0;
			String hasilextract = "";
			String hasilextracttest = "";
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
				tempRata2 += node3T.get(i);
			}
			tempRata2 = tempRata2 / node3T.size();

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
			z = String.format("%.4f", tempRata2);
			hasilextract += x + ";" + y + ";" + z + ";";
			hasilextracttest += "1:" + x + " 2:" + y + " 3:" + z;
			tempRata2 = 0;

			// Rata-rata Humidity
			for (int i = 0; i < node3H.size(); i++) {
				tempRata2 += node3H.get(i);
			}
			tempRata2 = tempRata2 / node3H.size();

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
			z = String.format("%.4f", tempRata2);
			hasilextract += x + ";" + y + ";" + z + ";";
			hasilextracttest += " 4:" + x + " 5:" + y + " 6:" + z;
			tempRata2 = 0;

			// Rata-rata Pressure
			for (int i = 0; i < node3P.size(); i++) {
				tempRata2 += node3P.get(i);
			}
			tempRata2 = tempRata2 / node3P.size();

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
			z = String.format("%.4f", tempRata2);
			hasilextract += x + ";" + y + ";" + z + ";";
			hasilextracttest += " 7:" + x + " 8:" + y + " 9:" + z;
			writer.write(alamat[2] + ";");
			writer.write(jam + ":" + menit + ":" + detik + ";");
			writer.write(hasilextract + "[0];");
			writerX.write(hasilextracttest);
			writer.newLine();
			writerX.newLine();
			writer.close();
			hasilextract = "";
			hasilextracttest = "";
			node3T.clear();
			node3H.clear();
			node3P.clear();
		} else if (choice == 4) {
			double tempRata2 = 0;
			String hasilextract = "";
			String hasilextracttest = "";
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
				tempRata2 += node4T.get(i);
			}
			tempRata2 = tempRata2 / node4T.size();

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
			z = String.format("%.4f", tempRata2);
			hasilextract += x + ";" + y + ";" + z + ";";
			hasilextracttest += "1:" + x + " 2:" + y + " 3:" + z;
			tempRata2 = 0;

			// Rata-rata Humidity
			for (int i = 0; i < node4H.size(); i++) {
				tempRata2 += node4H.get(i);
			}
			tempRata2 = tempRata2 / node4H.size();

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
			z = String.format("%.4f", tempRata2);
			hasilextract += x + ";" + y + ";" + z + ";";
			hasilextracttest += " 4:" + x + " 5:" + y + " 6:" + z;
			tempRata2 = 0;

			// Rata-rata Pressure
			for (int i = 0; i < node4P.size(); i++) {
				tempRata2 += node4P.get(i);
			}
			tempRata2 = tempRata2 / node4P.size();

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
			z = String.format("%.4f", tempRata2);
			hasilextract += x + ";" + y + ";" + z + ";";
			hasilextracttest += " 7:" + x + " 8:" + y + " 9:" + z;
			writer.write(alamat[3] + ";");
			writer.write(jam + ":" + menit + ":" + detik + ";");
			writer.write(hasilextract + "[0];");
			writerX.write(hasilextracttest);
			writer.newLine();
			writerX.newLine();
			hasilextract = "";
			hasilextracttest = "";
			node4T.clear();
			node4H.clear();
			node4P.clear();
		} else if (choice == 5) {
			double tempRata2 = 0;
			String hasilextract = "";
			String hasilextracttest = "";
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
				tempRata2 += node5T.get(i);
			}
			tempRata2 = tempRata2 / node5T.size();

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
			z = String.format("%.4f", tempRata2);
			hasilextract += x + ";" + y + ";" + z + ";";
			hasilextracttest += "1:" + x + " 2:" + y + " 3:" + z;
			tempRata2 = 0;

			// Rata-rata Humidity
			for (int i = 0; i < node5H.size(); i++) {
				tempRata2 += node5H.get(i);
			}
			tempRata2 = tempRata2 / node5H.size();

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
			z = String.format("%.4f", tempRata2);
			hasilextract += x + ";" + y + ";" + z + ";";
			hasilextracttest += " 4:" + x + " 5:" + y + " 6:" + z;
			tempRata2 = 0;

			// Rata-rata Pressure
			for (int i = 0; i < node5P.size(); i++) {
				tempRata2 += node5P.get(i);
			}
			tempRata2 = tempRata2 / node5P.size();

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
			z = String.format("%.4f", tempRata2);
			hasilextract += x + ";" + y + ";" + z + ";";

			hasilextracttest += " 7:" + x + " 8:" + y + " 9:" + z;
			writer.write(alamat[4]);
			writer.write(jam + ":" + menit + ":" + detik + ";");
			writer.write(hasilextract + "[0];");
			writerX.write(hasilextracttest);
			writer.newLine();
			writerX.newLine();
			hasilextract = "";
			hasilextracttest = "";
			node5T.clear();
			node5H.clear();
			node5P.clear();
		}
	}
}