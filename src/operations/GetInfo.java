package operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


public class GetInfo {
	ParseData pd;
	HashMap<String,String> prof_course;
	HashMap<String,String> sections;
	
	
	public GetInfo(ParseData pd) {
		super();
		this.pd = pd;
		prof_course = pd.getClass_prof();
		sections = pd.getSection_student();
	}
	
	
	public HashMap<String, String> getProf_course() {
		return prof_course;
	}


	public HashMap<String, String> getSections() {
		return sections;
	}

/******
 * Method:classSections :: returns Iterator
 * Description: Class sections taught  
 *****/
	public Iterator<String> classSections()
	{
		Iterator<String> i = sections.keySet().iterator();	
		return i;
		
	}
/******
	 * Method:classesTakenByStudents :: returns HashMap
	 * Description: Which classes have been taken by each of the studdent.List them.  
*****/
	public HashMap<String,String> classesTakenByStudents()
	{
		
		HashMap<String,String> classesTaken = new HashMap<String,String>();
		for(Entry<String, String> entry : sections.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    
		    
		    String[] studs = {value};
		    if(value.contains(","))
		    {
		    	studs = value.split(",");
		    }
		    
		    for(int i=0;i<studs.length;i++)
		    {
		    	StringBuffer subject = new StringBuffer(key.split(",")[0]);
		    	if(classesTaken.containsKey(studs[i]))
		    	{
		    		if(!classesTaken.get(studs[i]).contains(subject))
		    		{
		    			subject.append(","+classesTaken.get(studs[i]));
		    		}
		    		else
		    		{
		    			subject = new StringBuffer(classesTaken.get(studs[i]));
		    		}
		    	}	
		    	classesTaken.put(studs[i], subject.toString());
		    }
		    
		    
		}
		return classesTaken;
	}
/******
	 * Method:registeredStudents :: returns HashMap
	 * Description: How many students have registered for each class?  
*****/
	public HashMap<String,Integer> registeredStudents()
	{
		
		
		HashMap<String,Integer> countStud = new HashMap<String,Integer>();
		
		for(Entry<String, String> entry : sections.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    int count= value.split(",").length;
		    
		    if(countStud.containsKey(key.split(",")[0]))
		    {
		    	count = count+countStud.get(key.split(",")[0]);
		    }
		    countStud.put(key.split(",")[0], count);
		    
		}
			return countStud;
	}
	
/******
	 * Method:studentsTakingMany :: returns HashMap
	 * Description: How many students have taken more than one class? List them.  
*****/
	public HashMap<String,Integer> studentsTakingMany()
	{
		
		HashMap<String,Integer> studTakingMany = new HashMap<String,Integer>();
		HashMap<String,Integer> sol = new HashMap<String,Integer>();
		for(Entry<String, String> entry : sections.entrySet()) {
		   
		    String value = entry.getValue();
		    
		    
		    String[] studs = {value};
		    if(value.contains(","))
		    {
		    	studs = value.split(",");
		    }
		    
		    for(int i=0;i<studs.length;i++)
		    {
		    	int count = 1;
		    	if(studTakingMany.containsKey(studs[i]))
		    	{
		    		
		    		count = studTakingMany.get(studs[i])+count;
		    	}	
		    	studTakingMany.put(studs[i], count);
		    }
		}
		
		for(Entry<String,Integer> en : studTakingMany.entrySet())
		{
			String key = en.getKey();
			Integer val = en.getValue();
			
			if(val>1)
			{
				sol.put(key, val);
			}
		}
		
		return sol;
	}
	
/******
	 * Method:professorsTeachingMany :: returns HashMap
	 * Description: How many professors teach more than one class?List them.  
*****/
	public HashMap<String,String> professorsTeachingMany()
	{
		HashMap<String,String> profsTeachMany = new HashMap<String,String>();
		for(Entry<String, String> entry : prof_course.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    
		    if (value.split(",").length>1)
		    {
		    	profsTeachMany.put(key,value);
		    }
		    
		}
		
		return profsTeachMany;
	}

/******
	 * Method:profStudentsMany :: returns HashMap
	 * Description:Professors with at least 2 classes with 2 or more of the same students  
*****/
	public HashMap<String,String> profStudentsMany()
	{
		HashMap<String,String> sol = new HashMap<String,String>();
		ArrayList<String> st = new ArrayList<String>();
		
		for(Entry<String, String> entry : prof_course.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    
		    if (value.split(",").length>1)
		    {
		    	st.add(key);
		    	sol.put(key,value);
		    }
		}
	
		for (String temp : st)
	    {
			StringBuffer value1 = new StringBuffer();
			for(Entry<String, String> entry : sections.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
		    	if (temp.equals(key.split(",")[1]))
		    	{
		    		value1 = value1.append(value+",");
		    	}
		    }
			String[] dups = value1.toString().split(",");
			int multipledups=0;
			String val="";
			for(int i=0;i<dups.length;i++)
				for(int j=i+1;j<dups.length;j++)
				{
					if(dups[i].equals(dups[j]))
					{
						multipledups++;
						val = val.contains(dups[i])?val:val +" "+dups[i];
					}
						
				}
			
			if(multipledups>1)
			{
				String finalValue = sol.get(temp)+" : "+val;
				sol.put(temp,finalValue);
			}
			else
			{
				sol.remove(temp);
			}
		}
	
		return sol;
	}
	

}
