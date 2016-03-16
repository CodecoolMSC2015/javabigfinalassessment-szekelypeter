package online;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.internal.compiler.batch.Main;

import offline.CSVDataReader;
import offline.Person;
import offline.SearchType;

public class PersonStoreServerSocket {
	public PersonStoreServerSocket() {
		try{
			ServerSocket socketServer=new ServerSocket(10012);
			CSVDataReader csvFile=new CSVDataReader("C:\\Users\\Szekely Peter\\Desktop\\persons.csv");
			while(true){
			Socket socket=socketServer.accept();
			
			InputStream inputStream=socket.getInputStream();
			ObjectInputStream objectInputStream=new ObjectInputStream(inputStream);
			String searchType= (String) objectInputStream.readObject();
			if (searchType=="Mandatory")
			csvFile.setSearchType(SearchType.Mandatory);
			else
			csvFile.setSearchType(SearchType.Optional);
			String searchCriteria=(String) objectInputStream.readObject();
			csvFile.setSearchCriteria(searchCriteria);
			Set<Person> goodPersonList=csvFile.getPersons();
			OutputStream outputStream=socket.getOutputStream();	
			ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);			
			inputStream.close();
			outputStream.close();
			socket.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

}
