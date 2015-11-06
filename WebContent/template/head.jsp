<%@page import="br.ages.crud.model.Usuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="pt-br">

    <head>
        <meta charset="utf-8">
        <title>AGES - Ag�ncia Experimental de Engenharia de Software</title>
        <link rel="icon" href="img/favicon.ico">
        
        <!-- JQUERY -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

		<!-- BOOTSTRAP -->
		<link rel="stylesheet" href="./css/bootstrap.min.css">
		<link rel="stylesheet" href="./css/bootstrap-theme.min.css">
		<script src="./js/bootstrap.min.js"></script>

		<!-- STYLE -->
		<link rel="stylesheet" href="./css/style.css">
		
		<!-- Include the plugin's CSS and JS: Cassio -->
		<script src="./js/jquery.bootstrap-duallistbox.min.js"></script>
		<link rel="stylesheet" type="text/css" href="./css/bootstrap-duallistbox.min.css">
		
    
    <body>
     <% Usuario usuarioSessao = (Usuario) session.getAttribute("usuarioSessao"); %>
    	<div class="container">
    	
    		
    	
   			<nav class="navbar navbar-default">
				<div class="container-fluid">
				
		    		<div class="navbar-header">
			      		<a class="navbar-brand" href="main?acao=listaProjetos">
			        		<img class="logoNavBar" src="./img/logo-ages.png" alt="AGES">
			      		</a>
			    	</div>

					<ul class="nav navbar-nav">
						
						<li class="dropdown">
			          		<a class="dropdown-toggle" data-toggle="dropdown" href="#">Usu�rios
				          		<span class="caret"></span>
				          	</a>
				          	<ul class="dropdown-menu">
				            	<li><a href="main?acao=listUser">Listar</a></li>
				            	<li><a href="main?acao=telaUser">Cadastrar</a></li> 
				          	</ul>
        				</li>

			        	<li class="dropdown">
			          		<a class="dropdown-toggle" data-toggle="dropdown" href="#">Projetos
				          		<span class="caret"></span>
			          		</a>
				          	<ul class="dropdown-menu">
				            	<li><a href="main?acao=listaProjetos">Listar</a></li>
				            	<li><a href="main?acao=telaProjeto">Cadastrar</a></li> 
				          	</ul>
        				</li>
        				
					</ul>

					<ul class="nav navbar-nav navbar-right">
        				<li class="dropdown">
        					<a class="dropdown-toggle" data-toggle="dropdown" href="#">
        						<span class="glyphicon glyphicon-user"></span>
        						Ol�, <%=usuarioSessao.getNome()%>!
        						<span class="caret"></span>
        					</a>
                           
        					<ul class="dropdown-menu dropdown-menu-right">
        						<li><a href="main?acao=logout">Deslogar</a></li>
        					</ul>
        				</li>
			      	</ul>
			      	
		    	</div>
			</nav>
    		
