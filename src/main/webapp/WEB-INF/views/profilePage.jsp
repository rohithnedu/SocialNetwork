<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<title>Social Network</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<!-- Bootstrap -->
 	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" integrity="sha256-7s5uDGW3AHqw6xtJmNNtr+OBRJUlgkNJEo78P4b0yRw= sha512-nNo+yCHEyn0smMxSswnf/OnX6/KwJuZTlNZBjauKhTK0c+zT+q5JOCx0UFhXQ6rJR9jg6Es8gPuD2uZcYDLqSw==" crossorigin="anonymous">
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script type="text/javascript">
	
function doPost(){
		
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
        url: "/SE-project-team-six/homePage/doPost",
        data: json,
        error: function(xhr,err){
            alert("readyState: "+xhr.readyState+"\nstatus: "+xhr.status);
            alert("responseText: "+xhr.responseText+" "+err);
     	    console.log(xhr.responseText);
        },
        success: function(response){
        alert(response.key);
        //window.location.href = response.key;
        }
        });
        }
	
function doAccept(acceptEmail){
	$.ajax({
		 headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
    dataType : "json",
    type: "POST",
    url: "/SE-project-team-six/homePage/doPost",
    data: { email : acceptEmail },
    error: function(xhr,err){
        alert("readyState: "+xhr.readyState+"\nstatus: "+xhr.status);
        alert("responseText: "+xhr.responseText+" "+err);
 	    console.log(xhr.responseText);
    },
    success: function(response){
    alert(response.key);
    //window.location.href = response.key;
    }
    });
    }
    
function doReject(RejectEmail){
	
	data1 = { email : RejectEmail };
	alert(data1.email);
	alert("rejecting this E-mail "+RejectEmail);
	$.ajax({	
		 headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
	dataType : "json",
	type: "POST",
    url:  "/SE-project-team-six/searchPage/requestRequest",
    data: { email : RejectEmail },
    error: function(xhr,err){
        alert("readyState: "+xhr.readyState+"\nstatus: "+xhr.status);
        alert("responseText: "+xhr.responseText+" "+err);
 	    console.log(xhr.responseText);
    },
    success: function(response){
    alert(response.key);
    //window.location.href = response.key;
    }
    });
    }

	
	</script>
</head>
<body>
         <form action="/SE-project-team-six/logout" method ="Post">
        <div class="col-lg-20">
				<label><h3>Click to Logout</h3></label>
				<button type="submit" name="post"  value="post" id="post" class="btn btn-primary" > Click to Logout </button>
			</div>
			

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
						<li class="active"><a href="newsFeed">Home Page</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li class="active"><a href="profilePage">Profile Page</a></li>
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
			<br>
			<div class="row col-lg-12">
				<div class="form-group-lg col-lg-12">
					<label for="newPost"><h3>Write a new post</h3></label>
					<textarea name="newPost" class="form-control" id="newPost" style="height=300px;" placeholder="Write a new post ">
					</textarea>
				</div>
				<br>	
			<div class="form-group row text-left col-lg-12" style="padding-top: 10px;">
			<div class="col-lg-12">
				<label><h3></h3></label>
				<button type="button" name="post"  value="post" id="post" class="btn btn-primary" onclick="doPost()"> Click to Post </button>
			</div>
		</div>
	</form>	
	</div>
	
	<c:forEach items="${NewPostModel}" var="post">
		<br>
		<input name="newPost" value="${post.newPost}" />
		<br>
	</c:forEach>
	
</div>


<div style="float:left;line-height:35px;">
	<div  class="pre-scrollable" style="float:right;width:300px;height:350px;position:fixed;">
		<div class="form-group col-lg-12">		
			<h4>Pending Friend Request From </h4>
			<c:forEach items="${UserModel.pendingFriendList}" var="pendingRequest">
			
				<div class="form-group col-lg-12" >
					<input type="text" name="something" readonly="readonly" class="form-control" id="something" value="${pendingRequest.name}">
				</div>
				<div class="form-group col-lg-12">
				<button type="button" name="Accept"  value="Accept" id="Accept" class="btn btn-primary" onclick="doAccept(${pendingRequest.addFriend})"> Accept </button>
				<button type="button" name="Reject"  value="Reject" id="Reject" class="btn btn-primary" onclick="doReject(${pendingRequest.addFriend})"> Reject </button>
				</div>
			</c:forEach>
			<div class="form-group col-lg-12" >
				<hr size="10" width="100%" noshade style="color:#000000" align="left" />
			</div>
		</div>
	</div>	
	</div>
</body>
</html>