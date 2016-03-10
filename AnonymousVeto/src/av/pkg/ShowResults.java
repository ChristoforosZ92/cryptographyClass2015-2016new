package av.pkg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/ShowResults")
public class ShowResults extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ShowResults() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int n = getVoteCount();
		if(n<2){
			request.setAttribute("isVetoed", false);
			request.setAttribute("isValid", false);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/result.jsp");
		    rd.forward(request, response);
		}
		else{
			long[] xi = new long[n];
			String[][] yi = new String[n][n];
			List<String> Σxiyi = new ArrayList<String>();
			DataSource ds=null;
			Connection con = null;
			PreparedStatement stm = null;
			try
			{
				Context ctx = new InitialContext();
				ds = (DataSource)ctx.lookup("java:comp/env/jdbc/votedb");
			}
			catch (NamingException e)
			{
				e.printStackTrace();
			}
			try
			{
				con = ds.getConnection();
				String sql = "select * from avtable;";
				stm = con.prepareStatement(sql);
				ResultSet votes = stm.executeQuery();
				int i = 0;
				while(votes.next()){
					xi[i] = votes.getLong("xi");
					i++;
				}
			}
			catch (SQLException e) 
			{	e.printStackTrace();}
			finally
			{
			    try 
			    {    	if(null!=stm)stm.close();	} 
			    catch (SQLException e) 
			    {    	e.printStackTrace();    }
			    try 
			    {    	if(null!=con)con.close();   } 
			    catch (SQLException e) 
			    {    	e.printStackTrace();    }
			}
			for(int i=0; i < n; i++){
				int count = n-i;
				count = n - count;
				for(int j=0; j < n; j++){
					if(i == j){
						yi[i][j] = "";
					}
					else{
						if(count > 0){
							yi[i][j] = "+x"+(j+1);
							count--;
						}
						else yi[i][j] = "-x"+(j+1);
					}
				}
			}
			long c;
			long rand;
			long t;
			long s;
			Random r = new Random();
			for(int i=0; i < n; i++){
				c = r.nextInt(10);
				if(c == 0) c = c + 1;
				rand = r.nextInt(10);
				if(rand == 0) rand = rand + 1;
				t = (long) Math.pow(2, rand);
				s =  (rand + c * binlog(xi[i]));
				if(Math.pow(2, s) == t*(Math.pow(xi[i], c))){
					for(int j=0; j < n; j++){
						if(!"".equals(yi[i][j])){
							String txt = yi[i][j];
							String sign = txt.substring(0,1);
							String newtxt = txt.substring(2);
							int x = Integer.parseInt(newtxt);
							if((i+1) < x){
								txt = sign + "x"+(i+1) + "x" + x;
								System.out.println( txt + " list item" );
							}
							else{
								txt = yi[i][j] + "x"+(i+1);
								System.out.println(txt + " list item" );
							}
							Σxiyi.add(txt);
						}
					}
				}
				else{
					for(int j=0; j < n; j++){
						if(!"".equals(yi[i][j])){
							String txt = yi[i][j];
							String sign = txt.substring(0,1);
							String newtxt = txt.substring(2);
							int x = Integer.parseInt(newtxt);
							if((i+1) < x){
								txt = sign + "r"+(i+1) + "x" + x;
								System.out.println( txt + " list item" );
							}
							else{
								txt = yi[i][j] + "r"+(i+1);
								System.out.println( txt + " list item" );
							}
							Σxiyi.add(txt);
						}
					}
				}
			}
			for(int i=0; i < Σxiyi.size(); i++){
				if(!"".equals(Σxiyi.get(i))){
					String txt = Σxiyi.get(i);
					if("-".equals(txt.substring(0,1)))txt = "+" + txt.substring(1);
					else txt = "-" + txt.substring(1,txt.length());
					for(int j=0; j < Σxiyi.size(); j++){
						if(txt.equals(Σxiyi.get(j))){
							Σxiyi.set(i, "");
							Σxiyi.set(j, "");
							break;
						}
					}
				}
			}
			int count = 0;
			for(int i=0; i < Σxiyi.size(); i++){
				if(!"".equals(Σxiyi.get(i))) count++;
			}
			if(count > 0){
				request.setAttribute("isVetoed", true);
				request.setAttribute("isValid", true);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/result.jsp");
			    rd.forward(request, response);
			}
			else{
				request.setAttribute("isVetoed", false);
				request.setAttribute("isValid", true);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/result.jsp");
			    rd.forward(request, response);
			}
		}
	}
	private int getVoteCount(){
		DataSource ds=null;
		Connection con = null;
		PreparedStatement stm = null;
		int count = 0;
		try
		{
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/votedb");
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
		try
		{
			con = ds.getConnection();
			String sql = "select count(*) as count from avtable;";
			stm = con.prepareStatement(sql);
			ResultSet votes = stm.executeQuery();
			if(votes.next()){
				count = votes.getInt("count");
			}
		}
		catch (SQLException e) 
		{	e.printStackTrace();}
		finally
		{
		    try 
		    {    	if(null!=stm)stm.close();	} 
		    catch (SQLException e) 
		    {    	e.printStackTrace();    }
		    try 
		    {    	if(null!=con)con.close();   } 
		    catch (SQLException e) 
		    {    	e.printStackTrace();    }
		}
		return count;
	}
	private static long binlog( long bits ) // returns 0 for bits=0
	{
		long log = 0;
	    if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
	    if( bits >= 256 ) { bits >>>= 8; log += 8; }
	    if( bits >= 16  ) { bits >>>= 4; log += 4; }
	    if( bits >= 4   ) { bits >>>= 2; log += 2; }
	    return log + ( bits >>> 1 );
	}
}
