package Program;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class change {

	private static BufferedWriter writer;
	public static BufferedReader reader;
	public static ArrayList<String> temp = new ArrayList<String>();

//	public static void main(String[] args) throws IOException {
//
//	}

	public void tandaPredict() throws IOException {
		String tempawal;
		reader = new BufferedReader(new FileReader(Control.fileExtract));
		writer = new BufferedWriter(new FileWriter(Control.fileExtract));

		try {

			while ((tempawal = reader.readLine()) != null) {
				String[] splitter = tempawal.split(";");
				if (splitter[11].equals("[0]")) {
					splitter[11] = "x";
				}
				for (int i = 0; i < splitter.length; i++) {
					writer.write(splitter[i] + ";");
				}
			}
			reader.close();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
