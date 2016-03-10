package av.pkg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

@WebServlet("/ResetVote")
public class ResetVote extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ResetVote() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds=null;
		Connection con = null;
		PreparedStatement stm = null;
		boolean isValid = false;
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
			String sql = "delete from avtable;";
			stm = con.prepareStatement(sql);
			stm.executeUpdate();
			con.commit();
			isValid = true;
		}
		catch (SQLException e){	
			e.printStackTrace();
			try {
				con.rollback();
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
			}
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
		Map <String,Object> map = new HashMap<String,Object>();
		map.put("isValid",isValid);
		write(response,map);
		map.clear();
	}
	private void write(HttpServletResponse response, Map<String, Object> map) throws IOException 
	{
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}
}
