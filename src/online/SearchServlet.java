package online;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import offline.CSVDataReader;
import offline.Person;
import offline.SearchType;


@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("index.html").include(request, response);
		new PersonStoreServerSocket();
		
		String searchCriteria = request.getParameter("searchCriteria");
		String searchType=request.getParameter("searchType");	
		System.out.println(searchType);
		System.out.println(searchCriteria);
		Set<Person> goodPersonList=communicateWithPersonStoreServer(searchType,searchCriteria);
		for (Person person:goodPersonList){
			out.print(person.getName());
		}
		
	}

	public Set<Person> communicateWithPersonStoreServer(String searchType,String searchCriteria){
		Set<Person> goodPersonList=new HashSet<Person>();
		try{
			Socket socket=new Socket("localhost",10012);
			OutputStream outputStream=socket.getOutputStream();
			ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(searchType);
			objectOutputStream.writeObject(searchCriteria);
			InputStream inputStream=socket.getInputStream();
			ObjectInputStream objectInputStream=new ObjectInputStream(inputStream);
			goodPersonList= (Set<Person>) objectInputStream.readObject();
			outputStream.close();
			inputStream.close();
			socket.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return goodPersonList;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
