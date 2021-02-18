package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class InfrastructureDepartment {

	private List<Billboard> billboards;
	public final static String SEPARATOR = "\\|";
	public final static String SAVE_PATH_FILE = "data/billboards.kam";
	public final static String YES = "true";
	public final static String NO = "false";
	public final static Double DANGEROUS = 160000.0;
	public final static String BILLBOARD = "Billboard ";
	public final static String TEXT = " with area ";

	public InfrastructureDepartment() {

		billboards = new ArrayList<>();
	}

	public void addBillboard(Double width, Double height, boolean inUse, String brand) throws IOException {
		billboards.add(new Billboard(width,height,inUse,brand));
		saveData();
	}

	public List<Billboard> getBillboards() {
		return billboards;
	}

	public void setBillboards(List<Billboard> billboards) {
		this.billboards = billboards;
	}

	public void importData(String fileName) throws IOException{

		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();

		while(line!=null){

			String[] parts = line.split(SEPARATOR);

			Double width = Double.parseDouble(parts[0]);
			Double height = Double.parseDouble(parts[1]);

			boolean inUse = false;

			if(parts[2].equalsIgnoreCase(YES)) {
				inUse = true;
			}
			else if(parts[2].equalsIgnoreCase(NO)) {
				inUse = false;
			}

			addBillboard(width,height,inUse,parts[3]);
			line = br.readLine();
		}
		br.close();
	}

	public void exportData(String fileName) throws FileNotFoundException{

		PrintWriter pw = new PrintWriter(fileName);
		
		String message =  "===========================\r\n"
						+ "DANGEROUS BILLBOARD REPORT\r\n"
						+ "===========================\r\n";
		
		pw.print(message);
		
		for(int i=0;i<billboards.size();i++){
			
			if(billboards.get(i).calculateArea() >= DANGEROUS) {
				
				Billboard billboard = billboards.get(i);
				pw.println(BILLBOARD+billboard.getBrand()+TEXT+billboard.calculateArea());
			}
		}
		pw.close();
	}

	public void saveData() throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE));
		oos.writeObject(billboards);
		oos.close();
	}

	@SuppressWarnings("unchecked")
	public boolean loadData() throws IOException, ClassNotFoundException{
		File f = new File(SAVE_PATH_FILE);
		boolean loaded = false;
		if(f.exists()){
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			billboards = (List<Billboard>)ois.readObject();
			ois.close();
			loaded = true;
		}
		return loaded;
	}
}
