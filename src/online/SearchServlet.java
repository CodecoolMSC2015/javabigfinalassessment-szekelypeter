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
		
		String searchCriteria = request.getParameter("searchCriteria");
		String searchType=request.getParameter("searchType");
		CSVDataReader csvFile=new CSVDataReader("C:\\Users\\Szekely Peter\\Desktop\\bigfinalassassment\\javabigfinalassessment-szekelypeter\\Documentation\\persons.csv");

		communicateWithPersonStoreServer(searchType,searchCriteria);
	}

	public Set<Person> communicateWithPersonStoreServer(String searchType,String searchCriteria){
		Set<Person> goodPersonList=new HashSet<>();
		try{
			Socket s=new Socket("localhost",678);
			InputStream is=s.getInputStream();
			OutputStream os=s.getOutputStream();
			ObjectOutputStream oos=new ObjectOutputStream(os);
			oos.writeObject(searchType+";"+searchCriteria);
			ObjectInputStream ois=new ObjectInputStream(is);
			goodPersonList= (Set<Person>) ois.readObject();
			os.close();
			is.close();
			s.close();
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
