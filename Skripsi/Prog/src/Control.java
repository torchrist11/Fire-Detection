import com.virtenio.commander.io.DataConnection;
import com.virtenio.commander.toolsets.preon32.Preon32Helper;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.DefaultLogger;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Control {
	Calendar cal = Calendar.getInstance();

	private BufferedWriter writer;
	private Scanner pilihan;
	private volatile boolean exit = false;
	long msecs = cal.getTimeInMillis();
	String file;
	private int[] alamat = new int[] {
			0xDACE, 0xAAAF, 0xAADC, 0xCACF, 0xBCAF, 0xDACE
	};
	private String [] [] [] node1 = new String [100] [100] [100];
	private String [] [] [] node2 = new String [100] [100] [100];
	private String [] [] [] node3 = new String [100] [100] [100];
	private String [] [] [] node4 = new String [100] [100] [100];
	private String [] [] [] node5 = new String [100] [100] [100];
	private String [] [] [] node6 = new String [100] [100] [100];
	
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

	public void init() throws Exception {
		stringFormatTime sfTime = new stringFormatTime();
		String a = "true";
		try {
			Preon32Helper nodeHelper = new Preon32Helper("COM7", 115200);
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
				case 0: {
					exit = true;
					break;
				}
				case 1: {
					
					if (a.equalsIgnoreCase("true")) {
						file = sfTime.SFFile(msecs);
						file = "Sense " + file + ".txt";
						String folder = "Hasil Sense";
						writeToFile(file, folder, in); // thread of void
						a = "false";
						break;
					} else {
						a ="false";
						System.out.print("Sudah ada");
						break;
					}
				}
				}

			} while (choiceentry != 0);

		} catch (Exception e) {
//			System.out.print("Menu tidak tersedia");
		}
	}
	
	private void extract() {
		
		BufferedReader br = null;
		while(true) {
			try {
				br = new BufferedReader(new FileReader(file));
				String temp;
				int idx;
				while((temp = br.readLine()) != null) {
					idx = Integer.parseInt(temp.substring(0, 5));
					if(idx == alamat[0]) {
						
					} else if(idx == alamat[0]) {
						
					} else if(idx == alamat[1]) {
						
					} else if(idx == alamat[2]) {
						
					} else if(idx == alamat[3]) {
						
					} else if(idx == alamat[4]) {
						
					} else if(idx == alamat[5]) {
						
					}
				}
			}
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
//		aGet.time_synchronize();
//		aGet.context_set("context.set.2");
		ctrl.init();
	}
}