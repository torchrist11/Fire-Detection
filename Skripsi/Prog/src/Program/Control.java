package Program;

import com.virtenio.commander.io.DataConnection;
import com.virtenio.commander.toolsets.preon32.Preon32Helper;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import libsvm.svm;
import libsvm.svm_model;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.DefaultLogger;
import svm.svm_predict;

public class Control {
	Calendar cal = Calendar.getInstance();
	private BufferedWriter writer;
	public BufferedReader readAkhir;
	private volatile boolean exit = false;
	long msecs = cal.getTimeInMillis();
	static File file;
	String path;
	public static File fileExtract;
	public static File filePredict;
	static extract e = new extract();
	public static ArrayList<String> temp = new ArrayList<String>();
	stringFormatTime sfTime = new stringFormatTime();
	svm_model model;

	public void init() throws Exception {
		try {
			Preon32Helper nodeHelper = new Preon32Helper("COM4", 115200);
			DataConnection conn = nodeHelper.runModule("BaseStation");
			BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
			conn.flush();

			int nomorFile = 1;
			fileExtract = new File(
					"C:\\Users\\torch\\eclipse-workspace\\Prog\\Hasil Extract\\Extract_" + nomorFile + ".txt");
			boolean exist = fileExtract.exists();
			try {
				if (exist == true) {
					while (exist == true) {
						nomorFile++;
						fileExtract = new File("C:\\Users\\torch\\eclipse-workspace\\Prog\\Hasil Extract\\Extract_"
								+ nomorFile + ".txt");
						exist = fileExtract.exists();
					}
				}
			} catch (Exception e) {

			}
			String pathmodel = "C:\\Users\\torch\\eclipse-workspace\\Prog\\windows\\dummy.train.model";
			model = svm.svm_load_model(pathmodel);
			if (model == null) {
				System.err.print("can't open model file " + pathmodel + "\n");
				System.exit(1);
			}
			if (svm.svm_check_probability_model(model) != 0) {
				svm_predict.info("Model supports probability estimates, but disabled in prediction.\n");
			}
			String date = sfTime.SFFile(msecs);
			file = new File("Sense_" + date + ".txt");
			String folder = "Hasil Sense";
			writeToFile(file, folder, in); // thread of void
		} catch (Exception e) {

		}
	}

	public synchronized void writeToFile(File file, String folder, BufferedInputStream in) throws Exception {
		new Thread() {
			byte[] buffer = new byte[2048];
			String s;
			long count = 0;
			File newFolder = new File(folder);
			String tanda = ";[0]";

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
//									if (count == 0) {
//										if (w.startsWith("(")) {
//											writer.write(w, 0, w.length());
//											writer.append(tanda);
//											writer.newLine();
//											count++;
//											writer.close();
//											FileWriter fw = new FileWriter(path, true);
//											writer = new BufferedWriter(fw);
//										}
//									} else {
										if (w.startsWith("(")) {
											writer.write(w, 0, w.length());
											writer.append(tanda);
											writer.newLine();
											count++;
										}
//									}
									if (count == 100) {
										writer.close();
										FileWriter fw = new FileWriter(path, true);
										writer = new BufferedWriter(fw);
										readAkhir = new BufferedReader(new FileReader(path));
										try {
											filePredict = new File(
													"C:\\Users\\torch\\eclipse-workspace\\Prog\\windows\\dummy.test.txt");
											read();
											BufferedReader breader = new BufferedReader(new FileReader(fileExtract));
											String line = "";
											List<String> lines = new ArrayList<String>();
											while ((line = breader.readLine()) != null) {
												lines.add(line);
											}
											breader.close();
											if (lines.isEmpty()) {

											} else {
												svm_predict.predict(filePredict, model);
												PrintWriter writerHapus = new PrintWriter(filePredict);
												writerHapus.print("");
												writerHapus.close();
											}

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
			String target = "cmd.time.synchronize";
			antProject.executeTarget(target);
			antProject.fireBuildFinished(null);
		} catch (BuildException e) {
			e.printStackTrace();
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
		ctrl.init();
	}

	public synchronized void read() throws Exception {
		String tempawal;
		String line = " ";
		List<String> lines = new ArrayList<String>();
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);

		try {
			while ((tempawal = readAkhir.readLine()) != null) {
				String[] splitter = tempawal.split(";");
				if (splitter[6].equals("[0]")) {
					temp.add(tempawal);
				} else {

				}
			}
			readAkhir.close();
			e.Run();
			temp.clear();
			while ((line = br.readLine()) != null) {
				if (line.contains("[0]"))
					line = line.replace("[0]", "[1]");
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
