package operations;

import java.util.Iterator;
import java.util.Map.Entry;



public class Implement {

	public static void main(String[] args) {
			try{
				
			ParseData pd = new ParseData(args[0]);
			int[] lines_read = pd.readData();
			if(lines_read[0]!=-1){
			GetInfo g = new GetInfo(pd);
			System.out.println("------------------ Class Sections Taught: ---------------------");
			Iterator<String> i1 = g.classSections();
			while(i1.hasNext())
			{
				System.out.println(i1.next());
			}
			
			System.out.println("\n\n--------------- Classes Taken By Each Student: ---------------------");
			for(Entry<String, String> entry : g.classesTakenByStudents().entrySet())
			{
				System.out.println(entry.getKey() + ":  "+entry.getValue());
			}
			
			System.out.println("\n\n--------------- How many Students registered for each class? ---------------------");
			for(Entry<String, Integer> entry : g.registeredStudents().entrySet())
			{
				System.out.println(entry.getKey() + ":  "+entry.getValue());
			}
			
			System.out.println("\n\n--------------- How many Students take more than one class ---------------------");
			if(g.professorsTeachingMany().entrySet().isEmpty())
			{
				System.out.println("No such Professors");
			}
			else
			{
				System.out.println(g.studentsTakingMany().entrySet().size());
				for(Entry<String, Integer> entry : g.studentsTakingMany().entrySet())
				{
					System.out.println(entry.getKey() + ":  "+entry.getValue());
				}
			}
			System.out.println("\n\n--------------- How many Profs teach more than one class ---------------------");
			if(g.professorsTeachingMany().entrySet().isEmpty())
			{
				System.out.println("No such Professors");
			}
			else
			{
				System.out.println(g.professorsTeachingMany().entrySet().size());
				for(Entry<String, String> entry : g.professorsTeachingMany().entrySet())
				{
					System.out.println(entry.getKey() + ":  "+entry.getValue());
				}
			}
			System.out.println("\n\n--------------- Professors with at least 2 classes with 2 or more of the same students ---------------------");
			if(g.profStudentsMany().entrySet().isEmpty())
			{
				System.out.println("No such Professors");
			}
			for(Entry<String, String> entry : g.profStudentsMany().entrySet())
			{
					System.out.println(entry.getKey() + ":  "+entry.getValue());
			}}
			System.out.println("\n\n----------------------SUMMARY-------------------------");
			System.out.println("TOTAL ROWS IN FILE: "+(lines_read[0]+lines_read[1]));
			System.out.println("LINES READ SUCCESSFULLY: "+lines_read[1]);
			System.out.println("ERRORS DETECTED WHEN ROWS WERE READ: "+lines_read[0]+ "\nERRORS HAVE BEEN LISTED AT THE BEGINNING OF THIS RUN");
			
			}
			
			catch(ArrayIndexOutOfBoundsException e)
			{
				System.out.println("No arguments have been provided");
			}
			
	}

}
