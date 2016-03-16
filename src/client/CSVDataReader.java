package client;

import java.util.HashSet;
import java.util.Set;

public class CSVDataReader extends DataReader{
	private String csvFilePath;
	private Set<Person> persons;
	
	public CSVDataReader(String csvFilePath){
		this.csvFilePath=csvFilePath;
	}
	
	public Set<Person> getPersons(){
		Set<Person> listOfGoodPersons=new HashSet<Person>();
		return listOfGoodPersons;
	}
	
	@Override
	public Set<Person> getPersons(String searchCriteria, SearchType searchType) {
		return null;
	}

}
