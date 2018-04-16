import static java.nio.file.StandardOpenOption.CREATE;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

public class CsvFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		
		String[] choiceArray = {"Create new CSV file", "Read CSV file", "Display CSV files"};
		
		Object menuChoice = JOptionPane.showInputDialog(null, "What do you want to do?:", "CSV File Program", JOptionPane.QUESTION_MESSAGE, null, choiceArray, choiceArray[0]);
		
		if (menuChoice == null)
		{
			System.exit(0);
		}
		else if (menuChoice == "Create new CSV file")
		{
			Path createCSVFilePath = GetFilePath("CSV file that will be created:  ");
			//JOptionPane.showMessageDialog(null, createCSVFilePath);
			int columnCount = GetColumnQuan();
			//JOptionPane.showMessageDialog(null, columnCount);
			String[]csvHeader = GetHeaderParts(columnCount);
			
			String csvString = CreateCSVString(columnCount, csvHeader);
			JOptionPane.showMessageDialog(null, csvString);
			
			
			CreateCSVFile(createCSVFilePath, csvString);
		}
		else if (menuChoice == "Read CSV file")
		{
			String fileName = JOptionPane.showInputDialog(null, "Enter the name of the file you would like to read");
			
			
			
			String cvsRead = "";
			
			try
			{
				Path usersPath = Paths.get(System.getProperty("user.home") + "\\" + fileName);
				InputStream usersInput = new BufferedInputStream(Files.newInputStream(usersPath));
				BufferedReader readerProperty = new BufferedReader(new InputStreamReader(usersInput));
				System.out.println();
				
				//Display entries in csv file
				cvsRead = readerProperty.readLine(); //reading first line
				String fileString = "";
				while(cvsRead != null)
				{				
					fileString = fileString + cvsRead + System.getProperty("line.separator");
					
					//System.out.println(cvsRead);		// display previously read then read next	
					cvsRead = readerProperty.readLine();			
				}		
				readerProperty.close();		
				JOptionPane.showMessageDialog(null, fileString);
			} //try
			catch (Exception e)
			{
				System.out.println("Message:  " + e);
			}
		}
		else if (menuChoice == "Display CSV files")
		{
			String directoryProfile = System.getProperty("user.home");
			String dirPath = directoryProfile;
			File dir = new File(dirPath);
			String[] files = dir.list();
			String csvFiles = "";
			if (files.length == 0)
			{
				System.out.println(directoryProfile + " is empty");
			}
			else  //list available csv files, enter the one to delete, then delete it
			{				
				System.out.println("------ CSV files in profile ------");
				for (String aFile : files)	//collection of files named files
											// each object named aFile when looked at
				{
					if (aFile.endsWith(".csv"))
					{
						csvFiles = csvFiles + aFile+ System.getProperty("line.separator");
						
					}					
				}  //terminates for loop
				JOptionPane.showMessageDialog(null, csvFiles);
				System.out.println();								
			} 
		}
		
	}
	
	private static Path GetFilePath(String ending)
	{
		String fileName;
		fileName = JOptionPane.showInputDialog(null, "Name of " + ending);
		
		Path returnPath = Paths.get(System.getProperty("user.home") + "\\" + fileName);
		return returnPath;
	}
	
	private static int GetColumnQuan()
	{
		int count;
		String[] choiceValues = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
				"12", "13", "14", "15", "16", "17", "18", "19", "20"};
		
		Object numberChoice = JOptionPane.showInputDialog(null, "How many fields?:  ", "CSV File Program", JOptionPane.QUESTION_MESSAGE, null, choiceValues, choiceValues[0]);
		
		if (numberChoice == null)
		{
			System.exit(0);
		}
		count = Integer.parseInt(numberChoice.toString());
		return count;
	}
	
	private static String[] GetHeaderParts(int colQuan)
	{
		String propertyName;
		int count = 1;
		
		String[] propertyArray = new String[colQuan + 1];
		
		propertyName = JOptionPane.showInputDialog(null, "Propery #" + (count) + ": ");
		if (propertyName == null)
		{
			System.exit(0);
		}
		propertyArray[count] = propertyName;
		
		count = 2;
		
		while(count <= colQuan)
		{
			propertyName = JOptionPane.showInputDialog(null, "Propery #" + (count) + ": ");
			if (propertyName == null)
			{
				System.exit(0);
			}
			propertyArray[count] = propertyName;
			
			count = count + 1;
		}
		return propertyArray;
	}
	
	private static String CreateCSVString(int colCount, String[] header)
	{
		String tempString;
		String fileString = header[1];
		int count = 2;
		
		while(count <= colCount)
		{
			fileString = fileString + "," + header[count];
			count = count + 1;
		}
		fileString = fileString + System.getProperty("line.separator");
		
		//String more = "Y";
		int more = 0;
		
		//while(more.equals("Y"))
		while (more == 0)
		{
			count = 1;
			
			tempString = JOptionPane.showInputDialog(null, header[count] + ": ");
			if (tempString == null)
			{
				System.exit(0);
			}
			fileString = fileString + tempString;
			
			count = 2;
			while(count <= colCount)
			{
				tempString = JOptionPane.showInputDialog(null, header[count] + ": ");
				if (tempString == null)
				{
					System.exit(0);
				}
				fileString = fileString + "," + tempString;
				
				count = count + 1;
			}
			fileString = fileString + System.getProperty("line.separator");
			
			more = JOptionPane.showConfirmDialog(null, "Enter more fields?");
			//more = JOptionPane.showInputDialog(null, "More? Y/N  ");
			if (tempString == null)
			{
				System.exit(0);
			}
			//more = more.toUpperCase();
		}
		return fileString;
	}
	
	private static void CreateCSVFile(Path filePath, String fileString)
	{
		try
		{
			OutputStream outNew = new BufferedOutputStream(Files.newOutputStream(filePath, CREATE));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outNew));
			writer.write(fileString, 0, fileString.length());
			
			writer.close();
		}
		catch (Exception e)
		{
			System.out.println("Error message: " + e);
		}
	}

}
