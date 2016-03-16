package offline;

import java.util.HashSet;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class CSVDataReader extends DataReader{
	private String csvFilePath;
	private Set<Person> persons;
	
	public CSVDataReader(String csvFilePath){
		this.csvFilePath=csvFilePath;
	}
	
	@Override
	public Set<Person> getPersons(){
		Set<Person> listOfGoodPersons=new HashSet<Person>();
		String csvFile = "C:\\Users\\Szekely Peter\\Desktop\\bigfinalassassment\\javabigfinalassessment-szekelypeter\\Documentation\\persons.csv";
		BufferedReader br = null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] personDatas = line.split(",");
				Person testedPerson= getPerson(personDatas[0],personDatas[1],br);
				if (searchType==SearchType.Mandatory){
					if(checkSkillsMandatory(testedPerson,searchCriteria))
						listOfGoodPersons.add(testedPerson);
					}
				else{
					if(checkSkillsOptional(testedPerson,searchCriteria))
						listOfGoodPersons.add(testedPerson);
				}
			}
		}
		catch (Exception e){
			System.out.println("File not found");
		}
		finally {
			if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
				}
			}
		}		
		return listOfGoodPersons;		
	}
	
	public Person getPerson(String name,String email, BufferedReader csvfile){
		Person person=new Person(name, email);
		String line = "";
		try {
			while ((line = csvfile.readLine()) != null) {
				String[] personDatas = line.split(",");
					if(email==personDatas[1]){
						person.addSkill(new Skill(personDatas[2],personDatas[3], Double.parseDouble(personDatas[4])));
					}
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return person;
	}
	
	public boolean checkSkillsMandatory(Person testedPerson, String searchCriteria){
		for (String requiredSkill:searchCriteria.split(",")){
			boolean foundedSkill=false;
			for (Skill skill:testedPerson.getSkillset()){
				if (requiredSkill==skill.getName())
					foundedSkill=true;
			}
			if (foundedSkill!=true)
				return false;
		}
		return true;
	}
	
	public boolean checkSkillsOptional(Person testedPerson, String searchCriteria){
		for (String requiredSkill:searchCriteria.split(",")){
			boolean foundedSkill=false;
			for (Skill skill:testedPerson.getSkillset()){
				if (requiredSkill==skill.getName())
					foundedSkill=true;
			}
			if (foundedSkill==true)
				return true;
		}
		return false;
	}
}
