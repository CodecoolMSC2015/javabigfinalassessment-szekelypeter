package offline;

import java.util.Set;

public abstract class DataReader {
	public String searchCriteria;
	public SearchType searchType;
	
	public abstract Set<Person> getPersons();
	
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}
	
	
}
