package av.pkg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/SubmitVote")
public class SubmitVote extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SubmitVote() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		Cookie av_cookie = null;
		if(null != cookies){
			for(int i = 0; i < cookies.length;i++){
				if("xi".equals(cookies[i].getName())){
					av_cookie = cookies[i];
					break;
				}
			}
		}
		String vote = request.getParameter("opt");
		long xi = 0;
		boolean isValid = false;
		if(null != av_cookie){
			try{
				xi = Long.parseLong(av_cookie.getValue());
				isValid = true;
			}
			catch(NumberFormatException nfe){
				nfe.printStackTrace();
			}
		}
		if(isValid && ("1".equals(vote) || "0".equals(vote))){
			Random r = new Random();
			long c;
			long rand;
			long t;
			long s;
			c = r.nextInt(10);
			if(c == 0) c = c + 1;
			rand = r.nextInt(10);
			if(rand == 0) rand = rand + 1;
			t = (long) Math.pow(2, rand);
			s =  (rand + c * binlog(xi));
			if(Math.pow(2, s) == t*(Math.pow(xi, c))){
				if("1".equals(vote)){
					try{
						addVote(av_cookie.getValue());
					}
					catch(SQLException e){
						e.printStackTrace();
					}
				}
				else{
					long temp = r.nextInt(30);
					temp = temp * 3;
					try{
						addVote(Long.toString(temp));
					}
					catch(SQLException e){
						e.printStackTrace();
					}
				}
				request.setAttribute("isValid", isValid);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/votingprocess.jsp");
			    rd.forward(request, response);
			}
			else{
				request.setAttribute("isValid", false);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/votingprocess.jsp");
			    rd.forward(request, response);
			}
		}
		else{
			request.setAttribute("isValid", false);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/votingprocess.jsp");
		    rd.forward(request, response);
		}
	}
	private void addVote(String xi) throws SQLException{
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
			con.setAutoCommit(false);
			String sql = "insert into avtable(xi) values (?);";
			stm = con.prepareStatement(sql);
			stm.setString(1, xi);
			stm.executeUpdate();
			con.commit();
		}
		catch (SQLException e){	
			e.printStackTrace();
			con.rollback();
		}
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
	}
	public static long binlog( long bits ) // returns 0 for bits=0
	{
		long log = 0;
	    if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
	    if( bits >= 256 ) { bits >>>= 8; log += 8; }
	    if( bits >= 16  ) { bits >>>= 4; log += 4; }
	    if( bits >= 4   ) { bits >>>= 2; log += 2; }
	    return log + ( bits >>> 1 );
	}
}
