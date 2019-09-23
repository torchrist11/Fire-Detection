import com.virtenio.commander.io.DataConnection;
import com.virtenio.commander.toolsets.preon32.Preon32Helper;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.DefaultLogger;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Control {
	Calendar cal = Calendar.getInstance();

	private BufferedWriter writer;
	private Scanner pilihan;
	private volatile boolean exit = false;
	long msecs = cal.getTimeInMillis();
	String file;
	public static String file2;
//	private int[] alamat = new int[] { 0xDACE, 0xAAAF, 0xAADC, 0xCACF, 0xBCAF, 0xDACE };
//	private int[] node1T, node1H, node1P = new int[100];
//	private int[] node2T, node2H, node2P = new int[100];
//	private int[] node3T, node3H, node3P = new int[100];
//	private int[] node4T, node4H, node4P = new int[100];
//	private int[] node5T, node5H, node5P = new int[100];
//	private int[] node6T, node6H, node6P = new int[100];

	public void writeToFile(String file, String folder, BufferedInputStream in) throws Exception {
		new Thread() {
			byte[] buffer = new byte[2048];
			String s;
			long count = 0;
			File newFolder = new File(folder);

			public void run() {
				// disini check apakah sdh ada direktori hari ini
				// jika belum dibuat
				// maka semua file harus berada di direktori hari ini.
				if (!newFolder.exists())
					newFolder.mkdir();
				String path = folder + "/" + file;
				try {
					FileWriter fw = new FileWriter(path);
					writer = new BufferedWriter(fw);
				} catch (Exception e) {
//					System.out.print("z");
				}
				while (!exit) {
					try {
						if (in.available() > 0) {
							in.read(buffer);
							s = new String(buffer);
							String[] subStr = s.split("#");
							for (String w : subStr) {
								if (w.startsWith("(")) {
//								System.out.println(w);
									writer.write(w, 0, w.length());
									writer.newLine();
								}
							}
//							System.out.println(s);
							count++;
							if (count == 5) {
								writer.close();
								FileWriter fw = new FileWriter(path, true);
								writer = new BufferedWriter(fw);
								count = 0;
							}
						}
					} catch (IOException e) {
//						System.out.print("y");
					}

					Arrays.fill(buffer, (byte) 0);
				}
				try {
					writer.close();
				} catch (Exception e) {
//					System.out.print("w");
					;
				}
			}
		}.start();
	}

	private void context(String target) throws Exception {
		DefaultLogger consoleLogger = getConsoleLogger();
		// Prepare ant project
		File buildFile = new File("C:\\Users\\torch\\eclipse-workspace\\Sandbox\\buildUser.xml");
		Project antProject = new Project();
		antProject.setUserProperty("ant.file", buildFile.getAbsolutePath());
		antProject.addBuildListener(consoleLogger);

		try {
			antProject.fireBuildStarted();
			antProject.init();
			ProjectHelper helper = ProjectHelper.getProjectHelper();
			antProject.addReference("ant.ProjectHelper", helper);
			helper.parse(antProject, buildFile);
			//
			antProject.executeTarget(target);
			antProject.fireBuildFinished(null);
		} catch (BuildException e) {
//			System.out.print("n");
			;
		}
	}

	private void time_synchronize() throws Exception
	{
		DefaultLogger consoleLogger = getConsoleLogger();
		File buildFile = new File ("C:\\Users\\torch\\eclipse-workspace\\Sandbox\\build.xml");
		Project antProject = new Project();
		antProject.setUserProperty("ant.file", buildFile.getAbsolutePath());
		antProject.addBuildListener(consoleLogger);
		
		try {
			antProject.fireBuildStarted();
			antProject.init();
			ProjectHelper helper = ProjectHelper.getProjectHelper();
			antProject.addReference("ant.ProjectHelper", helper);
			helper.parse(antProject, buildFile);
			//
			String target = "cmd.time.synchronize";
			antProject.executeTarget(target);
			antProject.fireBuildFinished(null);
		} catch (BuildException e) { e.printStackTrace();}
	}
	
	public void init() throws Exception {
		stringFormatTime sfTime = new stringFormatTime();
		String a = "true";
		try {
			Preon32Helper nodeHelper = new Preon32Helper("COM4", 115200);
			DataConnection conn = nodeHelper.runModule("BaseStation");

			BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
			// BufferedOutputStream out = new BufferedOutputStream(conn.getOutputStream());
			int choiceentry;
			// byte[] buffer = new byte[2048];
			pilihan = new Scanner(System.in);
			/// START MENU
			conn.flush();
			do {
				System.out.println("MENU");
				System.out.println("1. Sense");
				System.out.println("0. Exit");
				System.out.println("Choice: ");

				choiceentry = pilihan.nextInt();
				conn.write(choiceentry);
				Thread.sleep(200);
				switch (choiceentry) {
				case 1: {
					// System.out.println("Sense: Temperature [°C], Humidity [Rh], Pressure [kPa]");
					if (a.equalsIgnoreCase("true")) {
						file = sfTime.SFFile(msecs);
						file = "Sense " + file + ".txt";
						file2 = file;
						String folder = "Hasil Sense";
						writeToFile(file, folder, in); // thread of void
						a = "false";
						break;
					} else {
						a = "false";
						System.out.print("Sudah ada");
						break;
					}
				}
				case 0:{
					exit=true;
					break;
				}
				}

			} while (choiceentry != 0);

		} catch (Exception e) {
//			System.out.print("Menu tidak tersedia");
		}
	}

//	private void extract() throws Exception {
//		String temp;
//		int idx = 0;
//		int j = 0;
//		int k = 0;
//		int l = 0;
//		int m = 0;
//		int n = 0;
//		int o = 0;
//		String hasilextract1 = "";
//		String hasilextract2 = "";
//		String hasilextract3 = "";
//		String hasilextract4 = "";
//		String hasilextract5 = "";
//		String hasilextract6 = "";
//		int akhir = 1;
//		File fileAkhir = new File("C:\\Users\\torch\\eclipse-workspace\\Prog\\Hasil Akhir\\Akhir" + akhir + ".txt");
//		boolean exist = fileAkhir.exists();
//		
//		try {
//			if (exist = true) {
//				while (exist=true) {
//					akhir++;
//					fileAkhir = new File("C:\\Users\\torch\\eclipse-workspace\\Prog\\Hasil Akhir\\Akhir" + akhir + ".txt");
//					exist = fileAkhir.exists();
//				}
//			}
//			BufferedReader read = null;
//			read = new BufferedReader(new FileReader(file));
//			BufferedWriter writer = new BufferedWriter(new FileWriter(fileAkhir));
//			while ((temp = read.readLine()) != null) {
//				String[] temp2 = temp.split(";");
//				for (int i = 0; i < 1; i++) {
//					idx = Integer.parseInt(temp2[i]);
//				}
//				if (idx == alamat[0]) {
//					// Masukin tiap nilai sensing ke array
//					if (j < 100) {
//						node1T[j] = Integer.parseInt(temp2[1]);
//						node1H[j] = Integer.parseInt(temp2[2]);
//						node1P[j] = Integer.parseInt(temp2[3]);
//						j++;
//					} else {
//						j = 0;
//						int temphasil = 0;
//						// String hasilextract = "";
//						int maxT = node1T[0];
//						int minT = node1T[0];
//						int maxH = node1H[0];
//						int minH = node1H[0];
//						int maxP = node1P[0];
//						int minP = node1P[0];
//
//						// Rata-rata Temperature
//						for (int i = 0; i < node1T.length; i++) {
//							temphasil += node1T[i];
//						}
//						temphasil = temphasil / node1T.length;
//
//						// Maksimum dan minimum Temperature
//						for (int i = 0; i < node1T.length; i++) {
//							if (maxT < node1T[i]) {
//								maxT = node1T[i];
//							} else {
//								if (minT > node1T[i]) {
//									minT = node1T[i];
//								}
//							}
//
//							// hasil Temperature
////								String x = String.format("%.4f", maxT);
////								String y = String.format("%.4f", minT);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract1 += maxT + " " + minT + " " + temphasil + " ";
//						}
//						temphasil = 0;
//
//						// Rata-rata Humidity
//						for (int i = 0; i < node1H.length; i++) {
//							temphasil += node1H[i];
//						}
//						temphasil = temphasil / node1H.length;
//
//						// Maksimum dan minimum Humidity
//						for (int i = 0; i < node1H.length; i++) {
//							if (maxH < node1H[i]) {
//								maxH = node1H[i];
//							} else {
//								if (minH > node1H[i]) {
//									minH = node1H[i];
//								}
//							}
//
//							// hasil Humidity
////								String x = String.format("%.4f", maxH);
////								String y = String.format("%.4f", minH);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract1 += maxH + " " + minH + " " + temphasil + " ";
//						}
//						temphasil = 0;
//
//						// Rata-rata Pressure
//						for (int i = 0; i < node1P.length; i++) {
//							temphasil += node1P[i];
//						}
//						temphasil = temphasil / node1P.length;
//
//						// Maksimum dan minimum Pressure
//						for (int i = 0; i < node1P.length; i++) {
//							if (maxP < node1P[i]) {
//								maxP = node1P[i];
//							} else {
//								if (minP > node1P[i]) {
//									minP = node1P[i];
//								}
//							}
//							// hasil Pressure
////								String x = String.format("%.4f", maxP);
////								String y = String.format("%.4f", minP);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract1 += maxP + " " + minP + " " + temphasil + " ";
//						}
//						writer.write(hasilextract1);
//					}
//				} else if (idx == alamat[1]) {
//					// Masukin tiap nilai sensing ke array
//					if (k < 100) {
//						node2T[k] = Integer.parseInt(temp2[1]);
//						node2H[k] = Integer.parseInt(temp2[2]);
//						node2P[k] = Integer.parseInt(temp2[3]);
//						k++;
//					} else {
//						k = 0;
//						int temphasil = 0;
//						// String hasilextract = "";
//						int maxT = node2T[0];
//						int minT = node2T[0];
//						int maxH = node2H[0];
//						int minH = node2H[0];
//						int maxP = node2P[0];
//						int minP = node2P[0];
//
//						// Rata-rata Temperature
//						for (int i = 0; i < node2T.length; i++) {
//							temphasil += node2T[i];
//						}
//						temphasil = temphasil / node2T.length;
//
//						// Maksimum dan minimum Temperature
//						for (int i = 0; i < node2T.length; i++) {
//							if (maxT < node2T[i]) {
//								maxT = node2T[i];
//							} else {
//								if (minT > node2T[i]) {
//									minT = node2T[i];
//								}
//							}
//
//							// hasil Temperature
////								String x = String.format("%.4f", maxT);
////								String y = String.format("%.4f", minT);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract2 += maxT + " " + minT + " " + temphasil + " ";
//						}
//						temphasil = 0;
//
//						// Rata-rata Humidity
//						for (int i = 0; i < node2H.length; i++) {
//							temphasil += node2H[i];
//						}
//						temphasil = temphasil / node2H.length;
//
//						// Maksimum dan minimum Humidity
//						for (int i = 0; i < node2H.length; i++) {
//							if (maxH < node2H[i]) {
//								maxH = node2H[i];
//							} else {
//								if (minH > node2H[i]) {
//									minH = node2H[i];
//								}
//							}
//
//							// hasil Humidity
////								String x = String.format("%.4f", maxH);
////								String y = String.format("%.4f", minH);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract2 += maxH + " " + minH + " " + temphasil + " ";
//						}
//						temphasil = 0;
//
//						// Rata-rata Pressure
//						for (int i = 0; i < node2P.length; i++) {
//							temphasil += node2P[i];
//						}
//						temphasil = temphasil / node2P.length;
//
//						// Maksimum dan minimum Pressure
//						for (int i = 0; i < node2P.length; i++) {
//							if (maxP < node2P[i]) {
//								maxP = node2P[i];
//							} else {
//								if (minP > node2P[i]) {
//									minP = node2P[i];
//								}
//							}
//
//							// hasil Pressure
////								String x = String.format("%.4f", maxP);
////								String y = String.format("%.4f", minP);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract2 += maxP + " " + minP + " " + temphasil + " ";
//						}
//						writer.write(hasilextract2);
//					}
//				} else if (idx == alamat[2]) {
//					// Masukin tiap nilai sensing ke array
//					if (l < 100) {
//						node3T[l] = Integer.parseInt(temp2[1]);
//						node3H[l] = Integer.parseInt(temp2[2]);
//						node3P[l] = Integer.parseInt(temp2[3]);
//						l++;
//					} else {
//						l = 0;
//						int temphasil = 0;
//						// String hasilextract = "";
//						int maxT = node3T[0];
//						int minT = node3T[0];
//						int maxH = node3H[0];
//						int minH = node3H[0];
//						int maxP = node3P[0];
//						int minP = node3P[0];
//
//						// Rata-rata Temperature
//						for (int i = 0; i < node3T.length; i++) {
//							temphasil += node3T[i];
//						}
//						temphasil = temphasil / node3T.length;
//
//						// Maksimum dan minimum Temperature
//						for (int i = 0; i < node3T.length; i++) {
//							if (maxT < node3T[i]) {
//								maxT = node3T[i];
//							} else {
//								if (minT > node3T[i]) {
//									minT = node3T[i];
//								}
//							}
//
//							// hasil Temperature
////								String x = String.format("%.4f", maxT);
////								String y = String.format("%.4f", minT);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract3 += maxT + " " + minT + " " + temphasil + " ";
//						}
//						temphasil = 0;
//
//						// Rata-rata Humidity
//						for (int i = 0; i < node3H.length; i++) {
//							temphasil += node3H[i];
//						}
//						temphasil = temphasil / node3H.length;
//
//						// Maksimum dan minimum Humidity
//						for (int i = 0; i < node3H.length; i++) {
//							if (maxH < node3H[i]) {
//								maxH = node3H[i];
//							} else {
//								if (minH > node3H[i]) {
//									minH = node3H[i];
//								}
//							}
//
//							// hasil Humidity
////								String x = String.format("%.4f", maxH);
////								String y = String.format("%.4f", minH);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract3 += maxH + " " + minH + " " + temphasil + " ";
//						}
//						temphasil = 0;
//
//						// Rata-rata Pressure
//						for (int i = 0; i < node3P.length; i++) {
//							temphasil += node3P[i];
//						}
//						temphasil = temphasil / node3P.length;
//
//						// Maksimum dan minimum Pressure
//						for (int i = 0; i < node3P.length; i++) {
//							if (maxP < node3P[i]) {
//								maxP = node3P[i];
//							} else {
//								if (minP > node3P[i]) {
//									minP = node3P[i];
//								}
//							}
//
//							// hasil Pressure
////								String x = String.format("%.4f", maxP);
////								String y = String.format("%.4f", minP);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract3 += maxP + " " + minP + " " + temphasil + " ";
//						}
//						writer.write(hasilextract3);
//					}
//				} else if (idx == alamat[3]) {
//					// Masukin tiap nilai sensing ke array
//					if (m < 100) {
//						node4T[l] = Integer.parseInt(temp2[1]);
//						node4H[l] = Integer.parseInt(temp2[2]);
//						node4P[l] = Integer.parseInt(temp2[3]);
//						m++;
//					} else {
//						m = 0;
//						int temphasil = 0;
//						// String hasilextract = "";
//						int maxT = node4T[0];
//						int minT = node4T[0];
//						int maxH = node4H[0];
//						int minH = node4H[0];
//						int maxP = node4P[0];
//						int minP = node4P[0];
//
//						// Rata-rata Temperature
//						for (int i = 0; i < node4T.length; i++) {
//							temphasil += node4T[i];
//						}
//						temphasil = temphasil / node4T.length;
//
//						// Maksimum dan minimum Temperature
//						for (int i = 0; i < node4T.length; i++) {
//							if (maxT < node4T[i]) {
//								maxT = node4T[i];
//							} else {
//								if (minT > node4T[i]) {
//									minT = node4T[i];
//								}
//							}
//
//							// hasil Temperature
////								String x = String.format("%.4f", maxT);
////								String y = String.format("%.4f", minT);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract4 += maxT + " " + minT + " " + temphasil + " ";
//						}
//						temphasil = 0;
//
//						// Rata-rata Humidity
//						for (int i = 0; i < node4H.length; i++) {
//							temphasil += node4H[i];
//						}
//						temphasil = temphasil / node4H.length;
//
//						// Maksimum dan minimum Humidity
//						for (int i = 0; i < node4H.length; i++) {
//							if (maxH < node4H[i]) {
//								maxH = node4H[i];
//							} else {
//								if (minH > node4H[i]) {
//									minH = node4H[i];
//								}
//							}
//
//							// hasil Humidity
////								String x = String.format("%.4f", maxH);
////								String y = String.format("%.4f", minH);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract4 += maxH + " " + minH + " " + temphasil + " ";
//						}
//						temphasil = 0;
//
//						// Rata-rata Pressure
//						for (int i = 0; i < node4P.length; i++) {
//							temphasil += node4P[i];
//						}
//						temphasil = temphasil / node4P.length;
//
//						// Maksimum dan minimum Pressure
//						for (int i = 0; i < node4P.length; i++) {
//							if (maxP < node4P[i]) {
//								maxP = node4P[i];
//							} else {
//								if (minP > node4P[i]) {
//									minP = node4P[i];
//								}
//							}
//
//							// hasil Pressure
////								String x = String.format("%.4f", maxP);
////								String y = String.format("%.4f", minP);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract4 += maxP + " " + minP + " " + temphasil + " ";
//						}
//						writer.write(hasilextract4);
//					}
//				} else if (idx == alamat[4]) {
//					// Masukin tiap nilai sensing ke array
//					if (n < 100) {
//						node5T[l] = Integer.parseInt(temp2[1]);
//						node5H[l] = Integer.parseInt(temp2[2]);
//						node5P[l] = Integer.parseInt(temp2[3]);
//						n++;
//					} else {
//						n = 0;
//						int temphasil = 0;
//						// String hasilextract = "";
//						int maxT = node5T[0];
//						int minT = node5T[0];
//						int maxH = node5H[0];
//						int minH = node5H[0];
//						int maxP = node5P[0];
//						int minP = node5P[0];
//
//						// Rata-rata Temperature
//						for (int i = 0; i < node5T.length; i++) {
//							temphasil += node5T[i];
//						}
//						temphasil = temphasil / node5T.length;
//
//						// Maksimum dan minimum Temperature
//						for (int i = 0; i < node5T.length; i++) {
//							if (maxT < node5T[i]) {
//								maxT = node5T[i];
//							} else {
//								if (minT > node5T[i]) {
//									minT = node5T[i];
//								}
//							}
//
//							// hasil Temperature
////								String x = String.format("%.4f", maxT);
////								String y = String.format("%.4f", minT);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract5 += maxT + " " + minT + " " + temphasil + " ";
//						}
//						temphasil = 0;
//
//						// Rata-rata Humidity
//						for (int i = 0; i < node5H.length; i++) {
//							temphasil += node5H[i];
//						}
//						temphasil = temphasil / node5H.length;
//
//						// Maksimum dan minimum Humidity
//						for (int i = 0; i < node5H.length; i++) {
//							if (maxH < node5H[i]) {
//								maxH = node5H[i];
//							} else {
//								if (minH > node5H[i]) {
//									minH = node5H[i];
//								}
//							}
//
//							// hasil Humidity
////								String x = String.format("%.4f", maxH);
////								String y = String.format("%.4f", minH);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract5 += maxH + " " + minH + " " + temphasil + " ";
//						}
//						temphasil = 0;
//
//						// Rata-rata Pressure
//						for (int i = 0; i < node5P.length; i++) {
//							temphasil += node5P[i];
//						}
//						temphasil = temphasil / node5P.length;
//
//						// Maksimum dan minimum Pressure
//						for (int i = 0; i < node5P.length; i++) {
//							if (maxP < node5P[i]) {
//								maxP = node5P[i];
//							} else {
//								if (minP > node5P[i]) {
//									minP = node5P[i];
//								}
//							}
//
//							// hasil Pressure
////								String x = String.format("%.4f", maxP);
////								String y = String.format("%.4f", minP);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract5 += maxP + " " + minP + " " + temphasil + " ";
//						}
//						writer.write(hasilextract5);
//					}
//				} else if (idx == alamat[5]) {
//					// Masukin tiap nilai sensing ke array
//					if (o < 100) {
//						node6T[l] = Integer.parseInt(temp2[1]);
//						node6H[l] = Integer.parseInt(temp2[2]);
//						node6P[l] = Integer.parseInt(temp2[3]);
//						o++;
//					} else {
//						o = 0;
//						int temphasil = 0;
//						// String hasilextract = "";
//						int maxT = node6T[0];
//						int minT = node6T[0];
//						int maxH = node6H[0];
//						int minH = node6H[0];
//						int maxP = node6P[0];
//						int minP = node6P[0];
//
//						// Rata-rata Temperature
//						for (int i = 0; i < node6T.length; i++) {
//							temphasil += node6T[i];
//						}
//						temphasil = temphasil / node6T.length;
//
//						// Maksimum dan minimum Temperature
//						for (int i = 0; i < node6T.length; i++) {
//							if (maxT < node6T[i]) {
//								maxT = node6T[i];
//							} else {
//								if (minT > node6T[i]) {
//									minT = node6T[i];
//								}
//							}
//
//							// hasil Temperature
////								String x = String.format("%.4f", maxT);
////								String y = String.format("%.4f", minT);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract6 += maxT + " " + minT + " " + temphasil + " ";
//						}
//						temphasil = 0;
//
//						// Rata-rata Humidity
//						for (int i = 0; i < node6H.length; i++) {
//							temphasil += node6H[i];
//						}
//						temphasil = temphasil / node6H.length;
//
//						// Maksimum dan minimum Humidity
//						for (int i = 0; i < node6H.length; i++) {
//							if (maxH < node6H[i]) {
//								maxH = node6H[i];
//							} else {
//								if (minH > node6H[i]) {
//									minH = node6H[i];
//								}
//							}
//
//							// hasil Humidity
////								String x = String.format("%.4f", maxH);
////								String y = String.format("%.4f", minH);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract6 += maxH + " " + minH + " " + temphasil + " ";
//						}
//						temphasil = 0;
//
//						// Rata-rata Pressure
//						for (int i = 0; i < node6P.length; i++) {
//							temphasil += node6P[i];
//						}
//						temphasil = temphasil / node6P.length;
//
//						// Maksimum dan minimum Pressure
//						for (int i = 0; i < node6P.length; i++) {
//							if (maxP < node6P[i]) {
//								maxP = node6P[i];
//							} else {
//								if (minP > node6P[i]) {
//									minP = node6P[i];
//								}
//							}
//
//							// hasil Pressure
////								String x = String.format("%.4f", maxP);
////								String y = String.format("%.4f", minP);
////								String z = String.format("%.4f", temphasil);
////								hasilextract += x + " " + y + " " + z + " ";
//							hasilextract6 += maxP + " " + minP + " " + temphasil + " ";
//						}
//						writer.write(hasilextract6);
//					}
//				}
//			}
//		} catch (Exception e) {
//
//		}
//	}

	private static DefaultLogger getConsoleLogger() {
		DefaultLogger consoleLogger = new DefaultLogger();
		consoleLogger.setErrorPrintStream(System.err);
		consoleLogger.setOutputPrintStream(System.out);
		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
		return consoleLogger;
	}

	public static void main(String[] args) throws Exception {
		Control ctrl = new Control();

		ctrl.context("context.set.1");
		ctrl.time_synchronize();
//		aGet.context_set("context.set.2");
		ctrl.init();
	}
}

//import com.virtenio.commander.io.DataConnection;
//import com.virtenio.commander.toolsets.preon32.Preon32Helper;
//import org.apache.tools.ant.BuildException;
//import org.apache.tools.ant.Project;
//import org.apache.tools.ant.ProjectHelper;
//import org.apache.tools.ant.DefaultLogger;
//import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Scanner;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//
//public class Control {
//	Calendar cal = Calendar.getInstance();
//
//	private BufferedWriter writer;
//	private Scanner pilihan;
//	private volatile boolean exit = false;
//	long msecs = cal.getTimeInMillis();
//	public  String file;
//	public static String file2;
//	File pathFile;
//	private int[] alamat = new int[] { 0xDACE, 0xAAAF, 0xAADC, 0xCACF, 0xBCAF, 0xDACE };
//	private int[] node1T, node1H, node1P = new int[100];
//	private int[] node2T, node2H, node2P = new int[100];
//	private int[] node3T, node3H, node3P = new int[100];
//	private int[] node4T, node4H, node4P = new int[100];
//	private int[] node5T, node5H, node5P = new int[100];
//	private int[] node6T, node6H, node6P = new int[100];
//
//	String temp;
//	int idx = 0;
//	int j = 0;
//	int k = 0;
//	int l = 0;
//	int m = 0;
//	int n = 0;
//	int o = 0;
//	String hasilextract1 = "";
//	String hasilextract2 = "";
//	String hasilextract3 = "";
//	String hasilextract4 = "";
//	String hasilextract5 = "";
//	String hasilextract6 = "";
//	int akhir = 1;
//	String pathFileAkhir = "C:\\Users\\torch\\eclipse-workspace\\Prog\\Hasil Akhir\\";
//
//	public void writeToFile(String file, String folder, BufferedInputStream in) throws Exception {
//		new Thread() {
//			byte[] buffer = new byte[2048];
//			String s;
//			long count = 0;
//			File newFolder = new File(folder);
//
//			public void run() {
//				// disini check apakah sdh ada direktori hari ini
//				// jika belum dibuat
//				// maka semua file harus berada di direktori hari ini.
//				if (!newFolder.exists())
//					newFolder.mkdir();
//				String path = folder + "/" + file;
//				try {
//					FileWriter fw = new FileWriter(path);
//					writer = new BufferedWriter(fw);
//				} catch (Exception e) {
////					System.out.print("z");
//				}
//				while (!exit) {
//					try {
//						if (in.available() > 0) {
//							in.read(buffer);
//							s = new String(buffer);
//							String[] subStr = s.split("#");
//							for (String w : subStr) {
//								if (w.startsWith("(")) {
////								System.out.println(w);
//									writer.write(w, 0, w.length());
//									writer.newLine();
//								}
//							}
////							System.out.println(s);
//							count++;
//							if (count == 5) {
//								writer.close();
//								FileWriter fw = new FileWriter(path, true);
//								writer = new BufferedWriter(fw);
//								count = 0;
//							}
//						}
//					} catch (IOException e) {
////						System.out.print("y");
//					}
//
//					Arrays.fill(buffer, (byte) 0);
//				}
//				try {
//					writer.close();
//				} catch (Exception e) {
////					System.out.print("w");
//					;
//				}
//			}
//		}.start();
//	}
//
//	private void context(String target) throws Exception {
//		DefaultLogger consoleLogger = getConsoleLogger();
//		// Prepare ant project
//		File buildFile = new File("C:\\Users\\torch\\eclipse-workspace\\Sandbox\\buildUser.xml");
//		Project antProject = new Project();
//		antProject.setUserProperty("ant.file", buildFile.getAbsolutePath());
//		antProject.addBuildListener(consoleLogger);
//
//		try {
//			antProject.fireBuildStarted();
//			antProject.init();
//			ProjectHelper helper = ProjectHelper.getProjectHelper();
//			antProject.addReference("ant.ProjectHelper", helper);
//			helper.parse(antProject, buildFile);
//			//
//			antProject.executeTarget(target);
//			antProject.fireBuildFinished(null);
//		} catch (BuildException e) {
////			System.out.print("n");
//			;
//		}
//	}
//
//	private void time_synchronize() throws Exception {
//		DefaultLogger consoleLogger = getConsoleLogger();
//		File buildFile = new File("C:\\Users\\torch\\eclipse-workspace\\Sandbox\\build.xml");
//		Project antProject = new Project();
//		antProject.setUserProperty("ant.file", buildFile.getAbsolutePath());
//		antProject.addBuildListener(consoleLogger);
//
//		try {
//			antProject.fireBuildStarted();
//			antProject.init();
//			ProjectHelper helper = ProjectHelper.getProjectHelper();
//			antProject.addReference("ant.ProjectHelper", helper);
//			helper.parse(antProject, buildFile);
//			//
//			String target = "cmd.time.synchronize";
//			antProject.executeTarget(target);
//			antProject.fireBuildFinished(null);
//		} catch (BuildException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void init() throws Exception {
//		stringFormatTime sfTime = new stringFormatTime();
//		String a = "true";
//		try {
//			Preon32Helper nodeHelper = new Preon32Helper("COM4", 115200);
//			DataConnection conn = nodeHelper.runModule("BaseStation");
//
//			BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
//			// BufferedOutputStream out = new BufferedOutputStream(conn.getOutputStream());
//			int choiceentry;
//			// byte[] buffer = new byte[2048];
//			pilihan = new Scanner(System.in);
//			/// START MENU
//			conn.flush();
//			do {
//				System.out.println("MENU");
//				System.out.println("1. Sense");
//				System.out.println("0. Exit");
//				System.out.println("Choice: ");
//
//				choiceentry = pilihan.nextInt();
//				conn.write(choiceentry);
//				Thread.sleep(200);
//				switch (choiceentry) {
//				case 1: {
//					// System.out.println("Sense: Temperature [°C], Humidity [Rh], Pressure [kPa]");
//					if (a.equalsIgnoreCase("true")) {
//						file = sfTime.SFFile(msecs);
//						pathFileAkhir += "Akhir " + file + ".txt";
//						file = "Sense " + file + ".txt";
//						String folder = "Hasil Sense";
//						writeToFile(file, folder, in); // thread of void
//						a = "false";
//
//						FileWriter writer = new FileWriter(pathFileAkhir);
//						try {
//							BufferedReader read = new BufferedReader(
//									new FileReader("C:\\Users\\torch\\eclipse-workspace\\Prog\\Hasil Sense" + file));
//							while ((temp = read.readLine()) != null) {
//								String[] temp2 = temp.split(";");
//								for (int i = 0; i < 1; i++) {
//									idx = Integer.parseInt(temp2[i]);
//								}
//								if (idx == alamat[0]) {
//									// Masukin tiap nilai sensing ke array
//									if (j < 100) {
//										node1T[j] = Integer.parseInt(temp2[2]);
//										node1H[j] = Integer.parseInt(temp2[3]);
//										node1P[j] = Integer.parseInt(temp2[4]);
//										j++;
//									} else {
//										j = 0;
//										int temphasil = 0;
//										// String hasilextract = "";
//										int maxT = node1T[0];
//										int minT = node1T[0];
//										int maxH = node1H[0];
//										int minH = node1H[0];
//										int maxP = node1P[0];
//										int minP = node1P[0];
//
//										// Rata-rata Temperature
//										for (int i = 0; i < node1T.length; i++) {
//											temphasil += node1T[i];
//										}
//										temphasil = temphasil / node1T.length;
//
//										// Maksimum dan minimum Temperature
//										for (int i = 0; i < node1T.length; i++) {
//											if (maxT < node1T[i]) {
//												maxT = node1T[i];
//											} else {
//												if (minT > node1T[i]) {
//													minT = node1T[i];
//												}
//											}
//
//											// hasil Temperature
////													String x = String.format("%.4f", maxT);
////													String y = String.format("%.4f", minT);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract1 += maxT + " " + minT + " " + temphasil + " ";
//										}
//										temphasil = 0;
//
//										// Rata-rata Humidity
//										for (int i = 0; i < node1H.length; i++) {
//											temphasil += node1H[i];
//										}
//										temphasil = temphasil / node1H.length;
//
//										// Maksimum dan minimum Humidity
//										for (int i = 0; i < node1H.length; i++) {
//											if (maxH < node1H[i]) {
//												maxH = node1H[i];
//											} else {
//												if (minH > node1H[i]) {
//													minH = node1H[i];
//												}
//											}
//
//											// hasil Humidity
////													String x = String.format("%.4f", maxH);
////													String y = String.format("%.4f", minH);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract1 += maxH + " " + minH + " " + temphasil + " ";
//										}
//										temphasil = 0;
//
//										// Rata-rata Pressure
//										for (int i = 0; i < node1P.length; i++) {
//											temphasil += node1P[i];
//										}
//										temphasil = temphasil / node1P.length;
//
//										// Maksimum dan minimum Pressure
//										for (int i = 0; i < node1P.length; i++) {
//											if (maxP < node1P[i]) {
//												maxP = node1P[i];
//											} else {
//												if (minP > node1P[i]) {
//													minP = node1P[i];
//												}
//											}
//											// hasil Pressure
////													String x = String.format("%.4f", maxP);
////													String y = String.format("%.4f", minP);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract1 += maxP + " " + minP + " " + temphasil + " ";
//										}
//										writer.write(hasilextract1);
//									}
//								} else if (idx == alamat[1]) {
//									// Masukin tiap nilai sensing ke array
//									if (k < 100) {
//										node2T[k] = Integer.parseInt(temp2[1]);
//										node2H[k] = Integer.parseInt(temp2[2]);
//										node2P[k] = Integer.parseInt(temp2[3]);
//										k++;
//									} else {
//										k = 0;
//										int temphasil = 0;
//										// String hasilextract = "";
//										int maxT = node2T[0];
//										int minT = node2T[0];
//										int maxH = node2H[0];
//										int minH = node2H[0];
//										int maxP = node2P[0];
//										int minP = node2P[0];
//
//										// Rata-rata Temperature
//										for (int i = 0; i < node2T.length; i++) {
//											temphasil += node2T[i];
//										}
//										temphasil = temphasil / node2T.length;
//
//										// Maksimum dan minimum Temperature
//										for (int i = 0; i < node2T.length; i++) {
//											if (maxT < node2T[i]) {
//												maxT = node2T[i];
//											} else {
//												if (minT > node2T[i]) {
//													minT = node2T[i];
//												}
//											}
//
//											// hasil Temperature
////													String x = String.format("%.4f", maxT);
////													String y = String.format("%.4f", minT);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract2 += maxT + " " + minT + " " + temphasil + " ";
//										}
//										temphasil = 0;
//
//										// Rata-rata Humidity
//										for (int i = 0; i < node2H.length; i++) {
//											temphasil += node2H[i];
//										}
//										temphasil = temphasil / node2H.length;
//
//										// Maksimum dan minimum Humidity
//										for (int i = 0; i < node2H.length; i++) {
//											if (maxH < node2H[i]) {
//												maxH = node2H[i];
//											} else {
//												if (minH > node2H[i]) {
//													minH = node2H[i];
//												}
//											}
//
//											// hasil Humidity
////													String x = String.format("%.4f", maxH);
////													String y = String.format("%.4f", minH);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract2 += maxH + " " + minH + " " + temphasil + " ";
//										}
//										temphasil = 0;
//
//										// Rata-rata Pressure
//										for (int i = 0; i < node2P.length; i++) {
//											temphasil += node2P[i];
//										}
//										temphasil = temphasil / node2P.length;
//
//										// Maksimum dan minimum Pressure
//										for (int i = 0; i < node2P.length; i++) {
//											if (maxP < node2P[i]) {
//												maxP = node2P[i];
//											} else {
//												if (minP > node2P[i]) {
//													minP = node2P[i];
//												}
//											}
//
//											// hasil Pressure
////													String x = String.format("%.4f", maxP);
////													String y = String.format("%.4f", minP);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract2 += maxP + " " + minP + " " + temphasil + " ";
//										}
//										writer.write(hasilextract2);
//									}
//								} else if (idx == alamat[2]) {
//									// Masukin tiap nilai sensing ke array
//									if (l < 100) {
//										node3T[l] = Integer.parseInt(temp2[2]);
//										node3H[l] = Integer.parseInt(temp2[3]);
//										node3P[l] = Integer.parseInt(temp2[4]);
//										l++;
//									} else {
//										l = 0;
//										int temphasil = 0;
//										// String hasilextract = "";
//										int maxT = node3T[0];
//										int minT = node3T[0];
//										int maxH = node3H[0];
//										int minH = node3H[0];
//										int maxP = node3P[0];
//										int minP = node3P[0];
//
//										// Rata-rata Temperature
//										for (int i = 0; i < node3T.length; i++) {
//											temphasil += node3T[i];
//										}
//										temphasil = temphasil / node3T.length;
//
//										// Maksimum dan minimum Temperature
//										for (int i = 0; i < node3T.length; i++) {
//											if (maxT < node3T[i]) {
//												maxT = node3T[i];
//											} else {
//												if (minT > node3T[i]) {
//													minT = node3T[i];
//												}
//											}
//
//											// hasil Temperature
////													String x = String.format("%.4f", maxT);
////													String y = String.format("%.4f", minT);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract3 += maxT + " " + minT + " " + temphasil + " ";
//										}
//										temphasil = 0;
//
//										// Rata-rata Humidity
//										for (int i = 0; i < node3H.length; i++) {
//											temphasil += node3H[i];
//										}
//										temphasil = temphasil / node3H.length;
//
//										// Maksimum dan minimum Humidity
//										for (int i = 0; i < node3H.length; i++) {
//											if (maxH < node3H[i]) {
//												maxH = node3H[i];
//											} else {
//												if (minH > node3H[i]) {
//													minH = node3H[i];
//												}
//											}
//
//											// hasil Humidity
////													String x = String.format("%.4f", maxH);
////													String y = String.format("%.4f", minH);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract3 += maxH + " " + minH + " " + temphasil + " ";
//										}
//										temphasil = 0;
//
//										// Rata-rata Pressure
//										for (int i = 0; i < node3P.length; i++) {
//											temphasil += node3P[i];
//										}
//										temphasil = temphasil / node3P.length;
//
//										// Maksimum dan minimum Pressure
//										for (int i = 0; i < node3P.length; i++) {
//											if (maxP < node3P[i]) {
//												maxP = node3P[i];
//											} else {
//												if (minP > node3P[i]) {
//													minP = node3P[i];
//												}
//											}
//
//											// hasil Pressure
////													String x = String.format("%.4f", maxP);
////													String y = String.format("%.4f", minP);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract3 += maxP + " " + minP + " " + temphasil + " ";
//										}
//										writer.write(hasilextract3);
//									}
//								} else if (idx == alamat[3]) {
//									// Masukin tiap nilai sensing ke array
//									if (m < 100) {
//										node4T[l] = Integer.parseInt(temp2[2]);
//										node4H[l] = Integer.parseInt(temp2[3]);
//										node4P[l] = Integer.parseInt(temp2[4]);
//										m++;
//									} else {
//										m = 0;
//										int temphasil = 0;
//										// String hasilextract = "";
//										int maxT = node4T[0];
//										int minT = node4T[0];
//										int maxH = node4H[0];
//										int minH = node4H[0];
//										int maxP = node4P[0];
//										int minP = node4P[0];
//
//										// Rata-rata Temperature
//										for (int i = 0; i < node4T.length; i++) {
//											temphasil += node4T[i];
//										}
//										temphasil = temphasil / node4T.length;
//
//										// Maksimum dan minimum Temperature
//										for (int i = 0; i < node4T.length; i++) {
//											if (maxT < node4T[i]) {
//												maxT = node4T[i];
//											} else {
//												if (minT > node4T[i]) {
//													minT = node4T[i];
//												}
//											}
//
//											// hasil Temperature
////													String x = String.format("%.4f", maxT);
////													String y = String.format("%.4f", minT);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract4 += maxT + " " + minT + " " + temphasil + " ";
//										}
//										temphasil = 0;
//
//										// Rata-rata Humidity
//										for (int i = 0; i < node4H.length; i++) {
//											temphasil += node4H[i];
//										}
//										temphasil = temphasil / node4H.length;
//
//										// Maksimum dan minimum Humidity
//										for (int i = 0; i < node4H.length; i++) {
//											if (maxH < node4H[i]) {
//												maxH = node4H[i];
//											} else {
//												if (minH > node4H[i]) {
//													minH = node4H[i];
//												}
//											}
//
//											// hasil Humidity
////													String x = String.format("%.4f", maxH);
////													String y = String.format("%.4f", minH);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract4 += maxH + " " + minH + " " + temphasil + " ";
//										}
//										temphasil = 0;
//
//										// Rata-rata Pressure
//										for (int i = 0; i < node4P.length; i++) {
//											temphasil += node4P[i];
//										}
//										temphasil = temphasil / node4P.length;
//
//										// Maksimum dan minimum Pressure
//										for (int i = 0; i < node4P.length; i++) {
//											if (maxP < node4P[i]) {
//												maxP = node4P[i];
//											} else {
//												if (minP > node4P[i]) {
//													minP = node4P[i];
//												}
//											}
//
//											// hasil Pressure
////													String x = String.format("%.4f", maxP);
////													String y = String.format("%.4f", minP);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract4 += maxP + " " + minP + " " + temphasil + " ";
//										}
//										writer.write(hasilextract4);
//									}
//								} else if (idx == alamat[4]) {
//									// Masukin tiap nilai sensing ke array
//									if (n < 100) {
//										node5T[l] = Integer.parseInt(temp2[2]);
//										node5H[l] = Integer.parseInt(temp2[3]);
//										node5P[l] = Integer.parseInt(temp2[4]);
//										n++;
//									} else {
//										n = 0;
//										int temphasil = 0;
//										// String hasilextract = "";
//										int maxT = node5T[0];
//										int minT = node5T[0];
//										int maxH = node5H[0];
//										int minH = node5H[0];
//										int maxP = node5P[0];
//										int minP = node5P[0];
//
//										// Rata-rata Temperature
//										for (int i = 0; i < node5T.length; i++) {
//											temphasil += node5T[i];
//										}
//										temphasil = temphasil / node5T.length;
//
//										// Maksimum dan minimum Temperature
//										for (int i = 0; i < node5T.length; i++) {
//											if (maxT < node5T[i]) {
//												maxT = node5T[i];
//											} else {
//												if (minT > node5T[i]) {
//													minT = node5T[i];
//												}
//											}
//
//											// hasil Temperature
////													String x = String.format("%.4f", maxT);
////													String y = String.format("%.4f", minT);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract5 += maxT + " " + minT + " " + temphasil + " ";
//										}
//										temphasil = 0;
//
//										// Rata-rata Humidity
//										for (int i = 0; i < node5H.length; i++) {
//											temphasil += node5H[i];
//										}
//										temphasil = temphasil / node5H.length;
//
//										// Maksimum dan minimum Humidity
//										for (int i = 0; i < node5H.length; i++) {
//											if (maxH < node5H[i]) {
//												maxH = node5H[i];
//											} else {
//												if (minH > node5H[i]) {
//													minH = node5H[i];
//												}
//											}
//
//											// hasil Humidity
////													String x = String.format("%.4f", maxH);
////													String y = String.format("%.4f", minH);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract5 += maxH + " " + minH + " " + temphasil + " ";
//										}
//										temphasil = 0;
//
//										// Rata-rata Pressure
//										for (int i = 0; i < node5P.length; i++) {
//											temphasil += node5P[i];
//										}
//										temphasil = temphasil / node5P.length;
//
//										// Maksimum dan minimum Pressure
//										for (int i = 0; i < node5P.length; i++) {
//											if (maxP < node5P[i]) {
//												maxP = node5P[i];
//											} else {
//												if (minP > node5P[i]) {
//													minP = node5P[i];
//												}
//											}
//
//											// hasil Pressure
////													String x = String.format("%.4f", maxP);
////													String y = String.format("%.4f", minP);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract5 += maxP + " " + minP + " " + temphasil + " ";
//										}
//										writer.write(hasilextract5);
//									}
//								} else if (idx == alamat[5]) {
//									// Masukin tiap nilai sensing ke array
//									if (o < 100) {
//										node6T[l] = Integer.parseInt(temp2[2]);
//										node6H[l] = Integer.parseInt(temp2[3]);
//										node6P[l] = Integer.parseInt(temp2[4]);
//										o++;
//									} else {
//										o = 0;
//										int temphasil = 0;
//										// String hasilextract = "";
//										int maxT = node6T[0];
//										int minT = node6T[0];
//										int maxH = node6H[0];
//										int minH = node6H[0];
//										int maxP = node6P[0];
//										int minP = node6P[0];
//
//										// Rata-rata Temperature
//										for (int i = 0; i < node6T.length; i++) {
//											temphasil += node6T[i];
//										}
//										temphasil = temphasil / node6T.length;
//
//										// Maksimum dan minimum Temperature
//										for (int i = 0; i < node6T.length; i++) {
//											if (maxT < node6T[i]) {
//												maxT = node6T[i];
//											} else {
//												if (minT > node6T[i]) {
//													minT = node6T[i];
//												}
//											}
//
//											// hasil Temperature
////													String x = String.format("%.4f", maxT);
////													String y = String.format("%.4f", minT);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract6 += maxT + " " + minT + " " + temphasil + " ";
//										}
//										temphasil = 0;
//
//										// Rata-rata Humidity
//										for (int i = 0; i < node6H.length; i++) {
//											temphasil += node6H[i];
//										}
//										temphasil = temphasil / node6H.length;
//
//										// Maksimum dan minimum Humidity
//										for (int i = 0; i < node6H.length; i++) {
//											if (maxH < node6H[i]) {
//												maxH = node6H[i];
//											} else {
//												if (minH > node6H[i]) {
//													minH = node6H[i];
//												}
//											}
//
//											// hasil Humidity
////													String x = String.format("%.4f", maxH);
////													String y = String.format("%.4f", minH);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract6 += maxH + " " + minH + " " + temphasil + " ";
//										}
//										temphasil = 0;
//
//										// Rata-rata Pressure
//										for (int i = 0; i < node6P.length; i++) {
//											temphasil += node6P[i];
//										}
//										temphasil = temphasil / node6P.length;
//
//										// Maksimum dan minimum Pressure
//										for (int i = 0; i < node6P.length; i++) {
//											if (maxP < node6P[i]) {
//												maxP = node6P[i];
//											} else {
//												if (minP > node6P[i]) {
//													minP = node6P[i];
//												}
//											}
//
//											// hasil Pressure
////													String x = String.format("%.4f", maxP);
////													String y = String.format("%.4f", minP);
////													String z = String.format("%.4f", temphasil);
////													hasilextract += x + " " + y + " " + z + " ";
//											hasilextract6 += maxP + " " + minP + " " + temphasil + " ";
//										}
//										writer.write(hasilextract6);
//									}
//								}
//							}
//							read.close();
//							writer.close();
//						} catch (Exception e) {
//
//						}
//
//						break;
//					} else {
//						a = "false";
//						System.out.print("Sudah ada");
//						break;
//					}
//				}
//				case 0: {
//					exit = true;
//					break;
//				}
//				}
//
//			} while (choiceentry != 0);
//
//		} catch (Exception e) {
////			System.out.print("Menu tidak tersedia");
//		}
//	}
//
//	private static DefaultLogger getConsoleLogger() {
//		DefaultLogger consoleLogger = new DefaultLogger();
//		consoleLogger.setErrorPrintStream(System.err);
//		consoleLogger.setOutputPrintStream(System.out);
//		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
//		return consoleLogger;
//	}
//
//	public static void main(String[] args) throws Exception {
//		Control ctrl = new Control();
//
//		ctrl.context("context.set.1");
//		ctrl.time_synchronize();
////		aGet.context_set("context.set.2");
//		ctrl.init();
//	}
//}