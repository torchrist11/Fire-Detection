import com.virtenio.commander.io.DataConnection;
import com.virtenio.commander.toolsets.preon32.Preon32Helper;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.DefaultLogger;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Control {
	Calendar cal = Calendar.getInstance();
	private BufferedWriter writer;
	public BufferedReader readAkhir;
	private Scanner pilihan;
	private volatile boolean exit = false;
	long msecs = cal.getTimeInMillis();
	static File file;
	String path;
	public static File fileAkhir;
	static extract e = new extract();
	public static ArrayList<String> temp = new ArrayList<String>();
	stringFormatTime sfTime = new stringFormatTime();
//	private Semaphore /* used */ notused;

	public Control() {

		// used = new Semaphore(0);
//		notused = new Semaphore(1);
	}

	public synchronized void writeToFile(File file, String folder, BufferedInputStream in) throws Exception {
		new Thread() {
			byte[] buffer = new byte[2048];
			String s;
			long count = 0;
			File newFolder = new File(folder);
			String tanda = ";belum";

			public void run() {
				// disini check apakah sdh ada direktori hari ini
				// jika belum dibuat
				// maka semua file harus berada di direktori hari ini.
				if (!newFolder.exists())
					newFolder.mkdir();
				path = folder + "/" + file;
				try {
					FileWriter fw = new FileWriter(path);
					writer = new BufferedWriter(fw);
				} catch (Exception e) {
				}
				while (!exit) {
					try {
						if (in.available() > 0) {
							in.read(buffer);
							s = new String(buffer);
							String[] subStr = s.split("#");
							for (String w : subStr) {
								if (w.startsWith("(") != true) {

								} else {
									if (count == 0) {
										if (w.startsWith("(")) {
											writer.write(w, 0, w.length());
											writer.append(tanda);
											writer.newLine();
											count++;
											writer.close();
											FileWriter fw = new FileWriter(path, true);
											writer = new BufferedWriter(fw);
										}
									} else {
										if (w.startsWith("(")) {
											writer.write(w, 0, w.length());
											writer.append(tanda);
											writer.newLine();
											count++;
										}
									}
									if (count == 100) {
//										notused.waits();
										writer.close();
//										notused.signals();
										FileWriter fw = new FileWriter(path, true);
										writer = new BufferedWriter(fw);
										readAkhir = new BufferedReader(new FileReader(path));
										try {
											read();
										} catch (Exception e) {
											e.printStackTrace();
										}
										count = 0;
									}
								}
							}
						}
					} catch (IOException e) {
					}
					Arrays.fill(buffer, (byte) 0);
				}
				try {
					writer.close();
				} catch (Exception e) {
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
			;
		}
	}

	private void time_synchronize() throws Exception {
		DefaultLogger consoleLogger = getConsoleLogger();
		File buildFile = new File("C:\\Users\\torch\\eclipse-workspace\\Sandbox\\build.xml");
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
		} catch (BuildException e) {
			e.printStackTrace();
		}
	}

	public void init() throws Exception {
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
						int akhir = 1;
						fileAkhir = new File(
								"C:\\Users\\torch\\eclipse-workspace\\Prog\\Hasil Akhir\\Akhir" + akhir + ".txt");
						boolean exist = fileAkhir.exists();
						try {
							if (exist == true) {
								while (exist == true) {
									akhir++;
									fileAkhir = new File("C:\\Users\\torch\\eclipse-workspace\\Prog\\Hasil Akhir\\Akhir"
											+ akhir + ".txt");
									exist = fileAkhir.exists();
								}
							}
						} catch (Exception e) {

						}
						String date = sfTime.SFFile(msecs);
						file = new File("Sense_" + date + ".txt");
						String folder = "Hasil Sense";
						writeToFile(file, folder, in); // thread of void
						a = "false";
						break;
					} else {
						exit = true;
						break;
					}
				}
				case 0: {
					exit = true;
					break;
				}
				}
			} while (choiceentry != 0);

		} catch (Exception e) {
		}
	}

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

	public synchronized void read() throws Exception {
		String tempawal;
		String line = " ";
		List<String> lines = new ArrayList<String>();
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);

		try {
//			notused.waits();
			while ((tempawal = readAkhir.readLine()) != null) {
				temp.add(tempawal);
			}
			readAkhir.close();
//			notused.signals();
			e.inside();
			while ((line = br.readLine()) != null) {
				if (line.contains("belum"))
					line = line.replace("belum", "sudah");
				lines.add(line);
			}

			fr.close();
			br.close();

			FileWriter fw = new FileWriter(path);
			BufferedWriter out = new BufferedWriter(fw);
			for (String s : lines) {
				out.write(s);
				out.newLine();
			}
			out.flush();
			out.close();
		} catch (Exception e) {

		}
	}
}
