<%@ page language="java" session="true" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AnonymousVetoResults</title>
<script src="JS/jquery-2.1.3.js" type="text/javascript"></script>
<script src="JS/jquery-ui.js"  type="text/javascript"></script>
<script src="JS/bootstrap.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="CSS/jquery-ui.theme.css"/>
<link rel="stylesheet" type="text/css" href="CSS/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="CSS/jquery-ui.structure.css"/>
<link rel="stylesheet" type="text/css" href="CSS/bootstraps1.css"/>
</head>
<%response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate"); 
%>

<script type="text/javascript">
$(function(){
	
	$('#reset').hide();
	$(document).on('click', '#resultBtn', function(){
		$.ajax(
		{
			url: 'ShowResults',
			type: 'post',
			success: function(data)
			{
				$('#main').html( data );
			}
		});
		return false;	
	});
	$(document).on('click', '#resetBtn', function(){
		$.ajax(
		{
			url: 'ResetVote',
			type: 'post',
			success: function(data)
			{
				if(data.isValid){
					$('#reset').show();
				}
			}
		});
		return false;	
	});
});
</script>

<body>

<div class="container-fluid">

<div id="main" class="panel panel-primary">

<div class="panel-heading">
    <h3 class="panel-title">Anonymous Veto Example Result</h3>
</div>

<div class="panel-body">
<div id="reset" class="form-group">
	<div class="alert alert-dismissible alert-success">
  		Vote Reseted!
	</div>
</div>
<div class="form-group">
      <div class="col-md-5 col-md-offset-2">
        <button id="resultBtn" class="btn btn-success">Results</button>
      </div>
      <div class="col-md-5">
        <button id="resetBtn" class="btn btn-danger">Reset</button>
      </div>
</div>

</div>

</div>

<div class="navbar navbar-inverse navbar-static-bottom">
	<div class="navbar-header col-md-offset-5">
      <a class="navbar-brand" href="about.jsp">AnonymousVeto</a>
    </div>
</div>

</div>

</body>

</html>