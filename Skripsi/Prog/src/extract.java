import java.io.BufferedWriter;
import java.io.FileWriter;

public class extract {
//	static BufferedReader read;
//	static BufferedWriter writer;
	private BufferedWriter writer;
	private static String[] alamat;
	private static double[] node1T;
	private static double[] node1H;
	private static double[] node1P;
	private static double[] node2T;
	private static double[] node2H;
	private static double[] node2P;
	private static double[] node3T;
	private static double[] node3H;
	private static double[] node3P;
	private static double[] node4T;
	private static double[] node4H;
	private static double[] node4P;
	private static double[] node5T;
	private static double[] node5H;
	private static double[] node5P;
	private static double[] node6T;
	private static double[] node6H;
	private static double[] node6P;
//	public static void main(String[] args) {
//		
//		try {
//			fiturextract();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
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
		Control ctrl = new Control();
		try {
			writer = new BufferedWriter(new FileWriter(Control.fileAkhir));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		alamat = new String[] { "(BAFE)", "(AAAF)", "(AADC)", "(CACF)", "(BCAF)", "(DACE)" };
		node1T = new double[100];
		node1H = new double[100];
		node1P = new double[100];
		node2T = new double[100];
		node2H = new double[100];
		node2P = new double[100];
		node3T = new double[100];
		node3H = new double[100];
		node3P = new double[100];
		node4T = new double[100];
		node4H = new double[100];
		node4P = new double[100];
		node5T = new double[100];
		node5H = new double[100];
		node5P = new double[100];
		node6T = new double[100];
		node6H = new double[100];
		node6P = new double[100];
		String idx = "";
		int j = 0;
		int k = 0;
		int l = 0;
		int m = 0;
		int n = 0;
		int o = 0;
		try {
			for (int d = 0; d < ctrl.temp.size(); d++) {
				String[] temp2 = ctrl.temp.get(d).split(";");
				if (temp2[5].equals("belum")) {
					for (int i = 0; i < 1; i++) {
						// idx = Integer.parseInt(temp2[i]);
						idx = temp2[i];
					}
					if (idx.equalsIgnoreCase(alamat[0])) {
						// Masukin tiap nilai sensing ke array
						if (j < 100) {
							node1T[j] = Double.parseDouble(temp2[2]);
							node1H[j] = Double.parseDouble(temp2[3]);
							node1P[j] = Double.parseDouble(temp2[4]);
							j++;
						} else {
							j = 0;
							double temphasil = 0;
							String hasilextract = "";
							String x = "";
							String y = "";
							String z = "";
							double maxT = node1T[0];
							double minT = node1T[0];
							double maxH = node1H[0];
							double minH = node1H[0];
							double maxP = node1P[0];
							double minP = node1P[0];

							// Rata-rata Temperature
							for (int i = 0; i < node1T.length; i++) {
								temphasil += node1T[i];
							}
							temphasil = temphasil / node1T.length;

							// Maksimum dan minimum Temperature
							for (int i = 0; i < node1T.length; i++) {
								if (maxT < node1T[i]) {
									maxT = node1T[i];
								} else {
									if (minT > node1T[i]) {
										minT = node1T[i];
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
							for (int i = 0; i < node1H.length; i++) {
								temphasil += node1H[i];
							}
							temphasil = temphasil / node1H.length;

							// Maksimum dan minimum Humidity
							for (int i = 0; i < node1H.length; i++) {
								if (maxH < node1H[i]) {
									maxH = node1H[i];
								} else {
									if (minH > node1H[i]) {
										minH = node1H[i];
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
							for (int i = 0; i < node1P.length; i++) {
								temphasil += node1P[i];
							}
							temphasil = temphasil / node1P.length;

							// Maksimum dan minimum Pressure
							for (int i = 0; i < node1P.length; i++) {
								if (maxP < node1P[i]) {
									maxP = node1P[i];
								} else {
									if (minP > node1P[i]) {
										minP = node1P[i];
									}
								}
							}
							// hasil Pressure
							x = String.format("%.4f", maxP);
							y = String.format("%.4f", minP);
							z = String.format("%.4f", temphasil);
							hasilextract += x + " " + y + ";" + z + ";";
							// hasilextract1 += maxP + " " + minP + " " + temphasil + " ";
							writer.write(alamat[0]);
							writer.write(hasilextract);
							writer.newLine();
							hasilextract = "";
						}
					} else if (idx.equalsIgnoreCase(alamat[1])) {
						// Masukin tiap nilai sensing ke array
						if (k < 100) {
							node2T[k] = Double.parseDouble(temp2[2]);
							node2H[k] = Double.parseDouble(temp2[3]);
							node2P[k] = Double.parseDouble(temp2[4]);
							k++;
						} else {
							k = 0;
							double temphasil = 0;
							String hasilextract = "";
							String x = "";
							String y = "";
							String z = "";
							double maxT = node2T[0];
							double minT = node2T[0];
							double maxH = node2H[0];
							double minH = node2H[0];
							double maxP = node2P[0];
							double minP = node2P[0];

							// Rata-rata Temperature
							for (int i = 0; i < node2T.length; i++) {
								temphasil += node2T[i];
							}
							temphasil = temphasil / node2T.length;

							// Maksimum dan minimum Temperature
							for (int i = 0; i < node2T.length; i++) {
								if (maxT < node2T[i]) {
									maxT = node2T[i];
								} else {
									if (minT > node2T[i]) {
										minT = node2T[i];
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
							for (int i = 0; i < node2H.length; i++) {
								temphasil += node2H[i];
							}
							temphasil = temphasil / node2H.length;

							// Maksimum dan minimum Humidity
							for (int i = 0; i < node2H.length; i++) {
								if (maxH < node2H[i]) {
									maxH = node2H[i];
								} else {
									if (minH > node2H[i]) {
										minH = node2H[i];
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
							for (int i = 0; i < node2P.length; i++) {
								temphasil += node2P[i];
							}
							temphasil = temphasil / node2P.length;

							// Maksimum dan minimum Pressure
							for (int i = 0; i < node2P.length; i++) {
								if (maxP < node2P[i]) {
									maxP = node2P[i];
								} else {
									if (minP > node2P[i]) {
										minP = node2P[i];
									}
								}
							}
							// hasil Pressure
							x = String.format("%.4f", maxP);
							y = String.format("%.4f", minP);
							z = String.format("%.4f", temphasil);
							hasilextract += x + ";" + y + ";" + z + ";";
							// hasilextract2 += maxP + " " + minP + " " + temphasil + " ";
							writer.write(alamat[1]);
							writer.write(hasilextract);
							writer.newLine();
							hasilextract = "";
						}
					} else if (idx.equalsIgnoreCase(alamat[2])) {
						// Masukin tiap nilai sensing ke array
						if (l < 100) {
							node3T[l] = Double.parseDouble(temp2[2]);
							node3H[l] = Double.parseDouble(temp2[3]);
							node3P[l] = Double.parseDouble(temp2[4]);
							l++;
						} else {
							l = 0;
							double temphasil = 0;
							String hasilextract = "";
							String x = "";
							String y = "";
							String z = "";
							double maxT = node3T[0];
							double minT = node3T[0];
							double maxH = node3H[0];
							double minH = node3H[0];
							double maxP = node3P[0];
							double minP = node3P[0];

							// Rata-rata Temperature
							for (int i = 0; i < node3T.length; i++) {
								temphasil += node3T[i];
							}
							temphasil = temphasil / node3T.length;

							// Maksimum dan minimum Temperature
							for (int i = 0; i < node3T.length; i++) {
								if (maxT < node3T[i]) {
									maxT = node3T[i];
								} else {
									if (minT > node3T[i]) {
										minT = node3T[i];
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
							for (int i = 0; i < node3H.length; i++) {
								temphasil += node3H[i];
							}
							temphasil = temphasil / node3H.length;

							// Maksimum dan minimum Humidity
							for (int i = 0; i < node3H.length; i++) {
								if (maxH < node3H[i]) {
									maxH = node3H[i];
								} else {
									if (minH > node3H[i]) {
										minH = node3H[i];
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
							for (int i = 0; i < node3P.length; i++) {
								temphasil += node3P[i];
							}
							temphasil = temphasil / node3P.length;

							// Maksimum dan minimum Pressure
							for (int i = 0; i < node3P.length; i++) {
								if (maxP < node3P[i]) {
									maxP = node3P[i];
								} else {
									if (minP > node3P[i]) {
										minP = node3P[i];
									}
								}
							}
							// hasil Pressure
							x = String.format("%.4f", maxP);
							y = String.format("%.4f", minP);
							z = String.format("%.4f", temphasil);
							hasilextract += x + ";" + y + ";" + z + ";";
							// hasilextract3 += maxP + " " + minP + " " + temphasil + " ";
							writer.write(alamat[2]);
							writer.write(hasilextract);
							writer.newLine();
							hasilextract = "";
						}
					} else if (idx.equalsIgnoreCase(alamat[3])) {
						// Masukin tiap nilai sensing ke array
						if (m < 100) {
							node4T[m] = Double.parseDouble(temp2[2]);
							node4H[m] = Double.parseDouble(temp2[3]);
							node4P[m] = Double.parseDouble(temp2[4]);
							m++;
						} else {
							m = 0;
							double temphasil = 0;
							String hasilextract = "";
							String x = "";
							String y = "";
							String z = "";
							double maxT = node4T[0];
							double minT = node4T[0];
							double maxH = node4H[0];
							double minH = node4H[0];
							double maxP = node4P[0];
							double minP = node4P[0];

							// Rata-rata Temperature
							for (int i = 0; i < node4T.length; i++) {
								temphasil += node4T[i];
							}
							temphasil = temphasil / node4T.length;

							// Maksimum dan minimum Temperature
							for (int i = 0; i < node4T.length; i++) {
								if (maxT < node4T[i]) {
									maxT = node4T[i];
								} else {
									if (minT > node4T[i]) {
										minT = node4T[i];
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
							for (int i = 0; i < node4H.length; i++) {
								temphasil += node4H[i];
							}
							temphasil = temphasil / node4H.length;

							// Maksimum dan minimum Humidity
							for (int i = 0; i < node4H.length; i++) {
								if (maxH < node4H[i]) {
									maxH = node4H[i];
								} else {
									if (minH > node4H[i]) {
										minH = node4H[i];
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
							for (int i = 0; i < node4P.length; i++) {
								temphasil += node4P[i];
							}
							temphasil = temphasil / node4P.length;

							// Maksimum dan minimum Pressure
							for (int i = 0; i < node4P.length; i++) {
								if (maxP < node4P[i]) {
									maxP = node4P[i];
								} else {
									if (minP > node4P[i]) {
										minP = node4P[i];
									}
								}
							}
							// hasil Pressure
							x = String.format("%.4f", maxP);
							y = String.format("%.4f", minP);
							z = String.format("%.4f", temphasil);
							hasilextract += x + ";" + y + ";" + z + ";";
							// hasilextract4 += maxP + " " + minP + " " + temphasil + " ";
							writer.write(alamat[3]);
							writer.write(hasilextract);
							writer.newLine();
							hasilextract = "";
						}
					} else if (idx.equalsIgnoreCase(alamat[4])) {
						// Masukin tiap nilai sensing ke array
						if (n < 100) {
							node5T[n] = Double.parseDouble(temp2[2]);
							node5H[n] = Double.parseDouble(temp2[3]);
							node5P[n] = Double.parseDouble(temp2[4]);
							n++;
						} else {
							n = 0;
							double temphasil = 0;
							String hasilextract = "";
							String x = "";
							String y = "";
							String z = "";
							double maxT = node5T[0];
							double minT = node5T[0];
							double maxH = node5H[0];
							double minH = node5H[0];
							double maxP = node5P[0];
							double minP = node5P[0];

							// Rata-rata Temperature
							for (int i = 0; i < node5T.length; i++) {
								temphasil += node5T[i];
							}
							temphasil = temphasil / node5T.length;

							// Maksimum dan minimum Temperature
							for (int i = 0; i < node5T.length; i++) {
								if (maxT < node5T[i]) {
									maxT = node5T[i];
								} else {
									if (minT > node5T[i]) {
										minT = node5T[i];
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
							for (int i = 0; i < node5H.length; i++) {
								temphasil += node5H[i];
							}
							temphasil = temphasil / node5H.length;

							// Maksimum dan minimum Humidity
							for (int i = 0; i < node5H.length; i++) {
								if (maxH < node5H[i]) {
									maxH = node5H[i];
								} else {
									if (minH > node5H[i]) {
										minH = node5H[i];
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
							for (int i = 0; i < node5P.length; i++) {
								temphasil += node5P[i];
							}
							temphasil = temphasil / node5P.length;

							// Maksimum dan minimum Pressure
							for (int i = 0; i < node5P.length; i++) {
								if (maxP < node5P[i]) {
									maxP = node5P[i];
								} else {
									if (minP > node5P[i]) {
										minP = node5P[i];
									}
								}
							}
							// hasil Pressure
							x = String.format("%.4f", maxP);
							y = String.format("%.4f", minP);
							z = String.format("%.4f", temphasil);
							hasilextract += x + ";" + y + ";" + z + ";";
							// hasilextract5 += maxP + " " + minP + " " + temphasil + " ";
							writer.write(alamat[4]);
							writer.write(hasilextract);
							writer.newLine();
							hasilextract = "";
						}
					} else if (idx.equalsIgnoreCase(alamat[5])) {
						// Masukin tiap nilai sensing ke array
						if (o < 100) {
							node6T[o] = Double.parseDouble(temp2[2]);
							node6H[o] = Double.parseDouble(temp2[3]);
							node6P[o] = Double.parseDouble(temp2[4]);
							o++;
						} else {
							o = 0;
							double temphasil = 0;
							String hasilextract = "";
							String x = "";
							String y = "";
							String z = "";
							double maxT = node6T[0];
							double minT = node6T[0];
							double maxH = node6H[0];
							double minH = node6H[0];
							double maxP = node6P[0];
							double minP = node6P[0];

							// Rata-rata Temperature
							for (int i = 0; i < node6T.length; i++) {
								temphasil += node6T[i];
							}
							temphasil = temphasil / node6T.length;

							// Maksimum dan minimum Temperature
							for (int i = 0; i < node6T.length; i++) {
								if (maxT < node6T[i]) {
									maxT = node6T[i];
								} else {
									if (minT > node6T[i]) {
										minT = node6T[i];
									}
								}
							}
							// hasil Temperature
							x = String.format("%.4f", maxT);
							y = String.format("%.4f", minT);
							z = String.format("%.4f", temphasil);
							hasilextract += x + ";" + y + ";" + z + ";";
							// hasilextract6 += maxT + " " + minT + " " + temphasil + " ";
							temphasil = 0;

							// Rata-rata Humidity
							for (int i = 0; i < node6H.length; i++) {
								temphasil += node6H[i];
							}
							temphasil = temphasil / node6H.length;

							// Maksimum dan minimum Humidity
							for (int i = 0; i < node6H.length; i++) {
								if (maxH < node6H[i]) {
									maxH = node6H[i];
								} else {
									if (minH > node6H[i]) {
										minH = node6H[i];
									}
								}
							}
							// hasil Humidity
							x = String.format("%.4f", maxH);
							y = String.format("%.4f", minH);
							z = String.format("%.4f", temphasil);
							hasilextract += x + ";" + y + ";" + z + ";";
							// hasilextract6 += maxH + " " + minH + " " + temphasil + " ";
							temphasil = 0;

							// Rata-rata Pressure
							for (int i = 0; i < node6P.length; i++) {
								temphasil += node6P[i];
							}
							temphasil = temphasil / node6P.length;

							// Maksimum dan minimum Pressure
							for (int i = 0; i < node6P.length; i++) {
								if (maxP < node6P[i]) {
									maxP = node6P[i];
								} else {
									if (minP > node6P[i]) {
										minP = node6P[i];
									}
								}
							}
							// hasil Pressure
							x = String.format("%.4f", maxP);
							y = String.format("%.4f", minP);
							z = String.format("%.4f", temphasil);
							hasilextract += x + ";" + y + ";" + z + ";";
							// hasilextract6 += maxP + " " + minP + " " + temphasil + " ";
							writer.write(alamat[5]);
							writer.write(hasilextract);
							writer.newLine();
							hasilextract = "";
						}
					}
				}
			}
			writer.close();
		} catch (Exception e) {

		}
	}
}