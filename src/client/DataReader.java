package client;

import java.util.Set;

public abstract class DataReader {
	private String searchCriteria;
	private SearchType searchType;
	
	public abstract Set<Person> getPersons(String searchCriteria, SearchType searchType);
	
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}
	
	
}
