package operations;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ParseData {
	
	String file;
	BufferedReader br;
	HashMap<String,String> section_student;
	HashMap<String,String> class_prof;
	
	
	public ParseData() {
		super();
		section_student = new HashMap<String,String>();
		class_prof = new HashMap<String,String>();
	}


	public ParseData(String file) {
		super();
		 try 
		 {
			section_student = new HashMap<String,String>();
			class_prof = new HashMap<String,String>();
			br = new BufferedReader(new FileReader(file));
			this.file = file;
		 } 
		 catch (FileNotFoundException | NullPointerException e) {
			System.out.println("No file with the specified name/path can be found");
		}
	}

	
	public HashMap<String, String> getSection_student() {
		return section_student;
	}


	public HashMap<String, String> getClass_prof() {
		return class_prof;
	}

	/****************************************
	 * Method : readData()
	 * This method will fetch the contents of CSV in two HashMaps.
	 * HashMap section_student: Key: course,prof Value:comma separated string of student IDs
	 * Example: Key: chemistry,joe Value: 1234,45,67
	 * 
	 * HashMap class_prof: Key: prof Value:comma separated string of courses
	 * Example: Key: joe Value: chemistry,history
	 * 
	 * The above 2 hashmaps are sufficient for all data retrieval operations.
	 *****************************************/

	public int[] readData()
	{
		String line="";
		int errorcount=0;
		int countread = 0;
		
		try {
			
			while((line=br.readLine())!=null)
			{
				String[] temp = line.trim().split(",");
				String course = temp[0].trim().toLowerCase();
				String prof = temp[1].trim().toLowerCase();
				String student = temp[2].trim();
				Integer number;
				boolean csvValid=true;
				try
				{
					number = Integer.parseInt(student);
					if(number<0)
						throw new NumberFormatException(); 
				}
				catch (NumberFormatException e)
				{
					csvValid=false;
					errorcount++;
					System.out.println("Looks like the student ID for this row is too long.\n");
				}
				if(!course.matches("[a-zA-Z ]+")|| !prof.matches("[a-zA-Z ]+"))
				{
					System.out.println("This row will be skipped as course/prof name does not exist or is not alphabetic: ");
					System.out.println("course: "+course);
					System.out.println("Professor: "+prof);
					csvValid=false;
					errorcount++;
				}
				else if(!(course.equals("chemistry")||course.equals("physics")||
						course.equals("history")||course.equals("mathematics")))
				{
					System.out.println("You have an invalid course in your CSV: " + course);
					csvValid=false;
					errorcount++;
				}
				
				if(csvValid==true){
				countread++;
				String sectionKey = course+","+prof;
				StringBuffer sectionValue = new StringBuffer(student);
				StringBuffer classValue = new StringBuffer(course);
				
				
					if(class_prof.containsKey(prof) )
					{
						if(!class_prof.get(prof).contains(course))
						{
							classValue.append(","+class_prof.get(prof));
						}
						else
						{
							classValue = new StringBuffer(class_prof.get(prof));
						}
								
					}
					class_prof.put(prof, classValue.toString());
					
				
					if(section_student.containsKey(sectionKey))
					{
						if(!section_student.get(sectionKey).contains(sectionValue))
						{
							sectionValue.append(","+section_student.get(sectionKey));
						}
						else
						{
							sectionValue = new StringBuffer(section_student.get(sectionKey));
						}
						
					}
					section_student.put(sectionKey, sectionValue.toString());	
				
				}	
			}
			br.close();
			
		} 
		catch(IOException e)
		{
			errorcount=-1;
			System.out.println("Some error occurred in reading the CSV file: IOException.CANNOT READ CSV FURTHER");
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			errorcount=-1;
			System.out.println("Looks like your csv file is empty or malformed.\n Check for spaces beneath the CSV if all's fine!! \n CANNOT READ CSV FURTHER");
		}
		catch(NullPointerException e)
		{
			errorcount=-1;
			System.out.println("File does not exist.");
		}
		
		int[] numbersToReturn = {errorcount,countread};
	return numbersToReturn;
	}

}
