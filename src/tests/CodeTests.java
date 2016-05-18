package tests;

import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameter;

import operations.GetInfo;
import operations.ParseData;

public class CodeTests{
	
	
@Parameter
public ParseData pdTest = new ParseData("courseInfoTest1.csv");
public ParseData pdTestInvalid;
public ParseData emptyPd;
public ParseData noFilePd;
public ParseData invalidFileName = new ParseData(" ");

public GetInfo i;
public int[] result;

FileWriter createFile()
{
	
		FileWriter fw = null;
		try {
			fw = new FileWriter("testCSV.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	return fw;
}
	
	void generateParamertizedCSV(String courseVal, String profVal, String studVal,FileWriter fw,boolean isLast)
	{
				try {
					fw.append(courseVal);
					fw.append(","+profVal);
					fw.append(","+studVal);
					fw.append("\n");
					
					if(isLast)
					{
						fw.flush();
						fw.close();
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	}

	/******* TESTS FOR ParseData Class starts.......
	 * ParseData_ReadData_Success()
	 * ParseData_ReadData_EmptyFileString()
	 * ParseData_ReadData_emptyFile()
	 * ParseData_ReadData_InvalidStudentId()
	 * ParseData_ReadData_NegativeStudentId()
	 * ParseData_ReadData_InvalidCourseValue()
	 * ParseData_ReadData_InvalidProf()
	 * ParseData_ReadData_InvalidCourse()
	 * ParseData_ReadData_MalformedCSV()
	 * 
	 * ********/
	@Test
	public void ParseData_ReadData_Success()
	{
		result = pdTest.readData();
		int[]expected = {0,11};
		assertNotNull(result);
		Assert.assertArrayEquals(expected, result);
	}
	
	@Test
	public void ParseData_ReadData_EmptyFileString()
	{
		invalidFileName = new ParseData(" ");
		result = invalidFileName.readData();
		int[]expected = {-1,0};
		assertNotNull(result);
		Assert.assertArrayEquals(expected, result);
		//assertEquals(expected,result);
	}
	
	@Test
	public void ParseData_ReadData_emptyFile()
	{	
		generateParamertizedCSV(" "," "," ",createFile(),true);
		emptyPd=new ParseData("testCSV.csv");
		result = emptyPd.readData();
		int[] expected = {-1,0};
		assertNotNull(result);
		Assert.assertArrayEquals(expected, result);
	}
	
	@Test
	public void ParseData_ReadData_InvalidStudentId()
	{
		generateParamertizedCSV("Chemistry","John","Student",createFile(),true);
		pdTestInvalid=new ParseData("testCSV.csv");
		result=pdTestInvalid.readData();
		int[] expected = {1,0};
		assertNotNull(result);
		Assert.assertArrayEquals(expected, result);		
	}
	@Test
	public void ParseData_ReadData_NegativeStudentId()
	{
		generateParamertizedCSV("Chemistry","John","-123",createFile(),true);
		pdTestInvalid=new ParseData("testCSV.csv");
		result=pdTestInvalid.readData();
		int[] expected = {1,0};
		assertNotNull(result);
		Assert.assertArrayEquals(expected, result);		
	}
	
	@Test
	public void ParseData_ReadData_InvalidCourseValue()
	{
		generateParamertizedCSV("1234","John","1234",createFile(),true);
		pdTestInvalid=new ParseData("testCSV.csv");
		result=pdTestInvalid.readData();
		int[] expected = {1,0};
		assertNotNull(result);
		Assert.assertArrayEquals(expected, result);		
	}
	
	@Test
	public void ParseData_ReadData_InvalidProf()
	{
		generateParamertizedCSV("Chemistry","1234","1234",createFile(),true);
		pdTestInvalid=new ParseData("testCSV.csv");
		result=pdTestInvalid.readData();
		int[] expected = {1,0};
		assertNotNull(result);
		Assert.assertArrayEquals(expected, result);		
	}
	
	@Test
	public void ParseData_ReadData_InvalidCourse()
	{
		generateParamertizedCSV("Geography","John","1234",createFile(),true);
		pdTestInvalid=new ParseData("testCSV.csv");
		result=pdTestInvalid.readData();
		int[] expected = {1,0};
		assertNotNull(result);
		Assert.assertArrayEquals(expected, result);		
	}
	
	@Test
	public void ParseData_ReadData_MalformedCSV()
	{
		generateParamertizedCSV(",",",",",",createFile(),true);
		pdTestInvalid=new ParseData("testCSV.csv");
		result=pdTestInvalid.readData();
		int[] expected = {-1,0};
		assertNotNull(result);
		Assert.assertArrayEquals(expected, result);		
	}
	
	/******* TESTS FOR ParseData Class ends.......*********/

	/******* TESTS FOR GetInfo Class starts.......
	 * GetInfo_classSections_iteratorNotNull()
	 * GetInfo_classSections_iteratorNull()
	 * GetInfo_classesTakenByStudents_SUCCESS()
	 * GetInfo_registeredStudents_SUCCESS()
	 * GetInfo_studentsTakingMany_SUCCESS()
	 * GetInfo_professorsTeachingMany_SUCCESS()
	 * GetInfo_profStudentsMany_SUCCESS()
	 * GetInfo_profStudentsMany_DUPLICATES()
	 * 
	 * *********/
	@Test
	public void GetInfo_classSections_iteratorNotNull() {
		pdTest.readData();
		GetInfo i = new GetInfo(pdTest);
		assertNotNull(i.classSections());
	}
	
	@Test
	public void GetInfo_classSections_iteratorNull() {
		GetInfo i = new GetInfo(pdTest);
		assertFalse(i.classSections().hasNext());
	}
	
	@Test
	public void GetInfo_classesTakenByStudents_SUCCESS() {
		pdTest.readData();

		GetInfo i = new GetInfo(pdTest);
		HashMap<String,String> expected = new HashMap<String,String>();
		expected.put("1234","chemistry");
		expected.put("3455","history,mathematics,chemistry");
		expected.put("56767","mathematics");
		expected.put("999","physics,chemistry,history");
		expected.put("2834","physics");
		expected.put("323","physics,history");
		
		HashMap<String,String> actual = i.classesTakenByStudents();

		assertEquals(expected.size(),actual.size());
		assertEquals(expected.entrySet(),actual.entrySet());
	}

	@Test
	public void GetInfo_registeredStudents_SUCCESS() {
		pdTest.readData();
		GetInfo i = new GetInfo(pdTest);
		HashMap<String,Integer> expected = new HashMap<String,Integer>();
		expected.put("chemistry",3);
		expected.put("history",3);
		expected.put("mathematics",2);
		expected.put("physics",3);
		
		HashMap<String,Integer> actual = i.registeredStudents();

		assertEquals(expected.size(),actual.size());
		assertEquals(expected.entrySet(),actual.entrySet());
	}
	
	@Test
	public void GetInfo_studentsTakingMany_SUCCESS() {
		pdTest.readData();
		GetInfo i = new GetInfo(pdTest);
		HashMap<String,Integer> expected = new HashMap<String,Integer>();
		expected.put("3455",3);
		expected.put("999",3);
		expected.put("323",2);
		
		HashMap<String,Integer> actual = i.studentsTakingMany();

		assertEquals(expected.size(),actual.size());
		assertEquals(expected.entrySet(),actual.entrySet());
	}
	
	@Test
	public void GetInfo_professorsTeachingMany_SUCCESS() {
		pdTest.readData();
		GetInfo i = new GetInfo(pdTest);
		HashMap<String,String> expected = new HashMap<String,String>();
		expected.put("smith","history,physics");
		expected.put("einstein","mathematics,physics");
		expected.put("jane","history,chemistry");
		
		HashMap<String,String> actual = i.professorsTeachingMany();

		assertEquals(expected.size(),actual.size());
		assertEquals(expected.entrySet(),actual.entrySet());
	}
	
	@Test
	public void GetInfo_profStudentsMany_SUCCESS() {
		pdTest.readData();
		GetInfo i = new GetInfo(pdTest);
		HashMap<String,String> expected = new HashMap<String,String>();
		expected.put("smith","history,physics :  999 323");
		
		HashMap<String,String> actual = i.profStudentsMany();

		assertEquals(expected.size(),actual.size());
		assertEquals(expected.entrySet(),actual.entrySet());
	}
	
	@Test
	public void GetInfo_classesTakenByStudents_FAILURE() {
		pdTest.readData();

		GetInfo i = new GetInfo(pdTest);
		HashMap<String,String> expected = new HashMap<String,String>();
		expected.put("1234","chemistry");
		expected.put("3455","history,mathematics,chemistry");
		expected.put("56767","mathematics");
		expected.put("999","physics,chemistry,history");
		expected.put("2834","physics");
		expected.put("323","physics,history");
		
		HashMap<String,String> actual = i.classesTakenByStudents();

		assertEquals(expected.size(),actual.size());
		assertEquals(expected.entrySet(),actual.entrySet());
	}
	
	@Test
	public void GetInfo_profStudentsMany_DUPLICATES() {
		FileWriter file;
		try {
			file = new FileWriter("courseInfoTest2.csv",true);
			generateParamertizedCSV("Physics","Smith","323",file,false);
			generateParamertizedCSV("Physics","Smith","323",file,true);
			pdTest=new ParseData("courseInfoTest2.csv");
			pdTest.readData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GetInfo i = new GetInfo(pdTest);
		HashMap<String,String> expected = new HashMap<String,String>();
		expected.put("smith","history,physics :  999 323");
		
		HashMap<String,String> actual = i.profStudentsMany();

		assertEquals(expected.size(),actual.size());
		assertEquals(expected.entrySet(),actual.entrySet());
		
	}
	
	/******* TESTS FOR GetInfo Class ends.......*********/
}
