<% 
boolean vetoed = false;
if(null != request.getAttribute("isVetoed"))vetoed = Boolean.valueOf(request.getAttribute("isVetoed").toString());
boolean valid = false;
if(null != request.getAttribute("isValid"))valid = Boolean.valueOf(request.getAttribute("isValid").toString());
%>
<div class="panel-heading">
    <h3 class="panel-title">Anonymous Veto Example Result</h3>
</div>
<div class="panel-body">
<%if(valid){ %>
	<%if(!vetoed){ %>
		<h4>The vote passed!</h4>
	<%}else{ %>
		<h4>The vote was vetoed!</h4>
	<%} %>
<%}else{ %>
	<h4>We need at least 2 people to vote before you can request the results!</h4>
<%} %>
</div>