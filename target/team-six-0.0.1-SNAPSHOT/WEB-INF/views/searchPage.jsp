<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
	<title>Social Network</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<!-- Bootstrap -->
 	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" integrity="sha256-7s5uDGW3AHqw6xtJmNNtr+OBRJUlgkNJEo78P4b0yRw= sha512-nNo+yCHEyn0smMxSswnf/OnX6/KwJuZTlNZBjauKhTK0c+zT+q5JOCx0UFhXQ6rJR9jg6Es8gPuD2uZcYDLqSw==" crossorigin="anonymous">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script type="text/javascript">
	
function doAdd(one){
	
	var addData = $("#"+one).serializeArray();
	var dataObj = {};
	for(var i=0;i < addData.length;i++){
		var var2  = addData[i].value;
	    dataObj[addData[i].name] = var2;
	    }    
	var json = JSON.stringify(dataObj);
	alert("after--"+json);
	$.ajax({
		 headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
   dataType : "json",
   type: "POST",
   url: "/SE-project-team-six/searchPage/addFriend",
   data: json,
   error: function(xhr,err){
       alert("readyState: "+xhr.readyState+"\nstatus: "+xhr.status);
       alert("responseText: "+xhr.responseText+" "+err);
	    console.log(xhr.responseText);
   },
   success: function(response){
   	alert("Friend Request Sent !!");
   	 }
   });
	
   }

function doSearch(){
		
		var fromData = $('#post_form').serializeArray();
		var dataObj = {};
		for(var i=0;i < fromData.length;i++){
			var var2  = fromData[i].value;
		    dataObj[fromData[i].name] = var2;
		   }    
		var json = JSON.stringify(dataObj);
		alert(json);
		$.ajax({
			 headers: { 
			        'Accept': 'application/json',
			        'Content-Type': 'application/json' 
			    },
        dataType : "json",
        type: "POST",
        url: "/SE-project-team-six/searchPage/doSearch",
        data: json,
        error: function(xhr,err){
            alert("readyState: "+xhr.readyState+"\nstatus: "+xhr.status);
            alert("responseText: "+xhr.responseText+" "+err);
     	    console.log(xhr.responseText);
        },
        success: function(response){
        	alert("Success");
        	alert("1 : "+response.searchResult[0].usersFriendList);
        	alert("length : "+response.searchResult.length);
        	
        	$('<hr size="100" width="100%" noshade style="color:#000000padding-top:05px;margin-bottom:05px;" align="left" /><br><h3> Search Result Displayed below </h3>').appendTo("#try1");
	    	for(var i =0; i < response.searchResult.length; i++)
        		{
	    	    var var1 = response.searchResult[i].fullName;
	    	    var var2 = response.searchResult[i].emailId;
        		$('<form name="add_form" id="'+i+'" action="" method="POST"><div class="form-group-sm col-sm-6"><label for="locationSearch">'+var1+'</label><input type="text" name="addFriend" style="padding-top:05px;margin-bottom:5px;" class="form-control" id="addFriend" value="'+ var2 + '" "><button type="button" name="addFriend"  value="addFriend" style="padding-left:15px;margin-bottom:25px;"id="addFriend" class="btn btn-primary" onclick="doAdd('+i+');"> Add Friend </button></div></form>').appendTo("#try1");
        		}
        	
        	gobal=response.searchResult;
       
        $("c\\:forEach").attr("items", response.searchResult );
       alert("worked");
       document.getElementsByTagName("c\\:forEach").items(response.searchResult);
       // document.form.y.items = response.searchResult;
        }
        });
		
        }
	
	
	</script>
</head>
<body>

	<!-- Navigator for the website, Home is active. Once finalised override -->
	<nav class="navbar navbar-inverse">

		<!-- To cover the complete width -->
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand" >Social network</a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav navbar-right">
						<li class="active"><a href="profilePage">Home Page</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li class="active"><a href="index.php">Profile Page</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li class="active"><a href="searchPage">Search Friend</a></li>
					</ul>
				</div>	
			</div><!-- /.navbar-collapse -->
		</div><!-- /.container-fluid -->
	</nav>
	<div class="container" >
			<div style="float:left;line-height:35px;">
			<div style="width:70%;height:350px;position:fixed;">
			
		 	<a href="backgroundPicture"><img src="data:image/jpeg;base64,${backgroundPic}" style="padding-right:05px;" width="100%"  height="270px" alt="[]" ></a>
         	
         	</div>
         	<div style="width:15%;height:250px;position:fixed;">
    		<img src="data:image/jpeg;base64,${profilePic}" style="padding-left:15px;padding-top:20px;padding-right:05px;position:relative;z-index:-1" width="100%"  height="100%" alt="[]" />
    		</div>
         	</div>
	</div>

	
	<div class="container" style="position:relative;padding-top: 250px;" >
	
	
	<div id="left" class="row col-lg-9">
	
		<form name="post_form" id="post_form" action="" method="POST">			
	<hr size="100" width="100%" noshade style="color:#000000" align="left" />

			<br>
			<h3> Search Friend </h3>
	
			<div class="row col-lg-12">
				<div class="form-group-lg col-lg-12">
					<label for="nameSearch">Name</label>
							<input type="text" name="nameSearch" class="form-control" id="nameSearch" Placeholder="Search by name ">
				</div>
				<br>	
				<div class="form-group-lg col-lg-12">
					<label for="locationSearch">Location</label>
							<input type="text" name="locationSearch" class="form-control" id="locationSearch" Placeholder="Search by location ">
				</div>
				<br>	
				<div class="form-group-lg col-lg-12">
					<label for="schoolSearch">School</label>
							<input type="text" name="schoolSearch" class="form-control" id="schoolSearch" Placeholder="Search by school ">
				</div>
				<br>	
				<div class="form-group-lg col-lg-12">
					<label for="professionSearch">Profession</label>
							<input type="text" name="professionSearch" class="form-control" id="professionSearch" Placeholder="Search by profession ">
				</div>
				
			<div class="form-group row text-left col-lg-12" style="padding-top: 10px;">
			<div class="col-lg-12">
				<label><h3></h3></label>
				<button type="button" name="post"  value="post" id="post" class="btn btn-primary" onclick="doSearch()"> Click to Post </button>
			</div>
		</div>
	</form>	
	</div>
	
	<form name="add_form" id="add_form" action="" method="POST">
	<div class="form-group-sm col-sm-6">
		<label for="locationSearch">'+var1+'
		</label>
		<input type="text" name="addFriend" style="padding-top:05px;margin-bottom:5px;" class="form-control" id="addFriend" value="'+ var2 + '" >
		<button type="button" name="addFriend"  value="addFriend" style="padding-left:15px;margin-bottom:25px;"id="addFriend" class="btn btn-primary" onclick="doAddTry('#col1 p','#00F');"> Add Friend "'+i+'"
		</button>
	</div>
</form>		<div id="try1">
		</div>
	
	</div>


<div style="float:left;line-height:35px;">
	<div  class="pre-scrollable" style="float:right;width:300px;height:350px;position:fixed;">
				<br>	
					<div class="form-group col-lg-6">
					<label for="emailId">Email</label>
					<input type="text" name="emailId" class="form-control" id="emailId" placeholder="Enter the Email">
				</div>
					
				<div class="form-group col-lg-6" >
						
					<label for="password">Password</label>
					<input type="password" name="password" class="form-control" id="password" placeholder="Enter the password">
				</div>
	</div>	
	
	</div>
</body>
</html>