
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>User | Log in</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.7 -->
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/assets/bower_components/bootstrap/dist/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/assets/bower_components/font-awesome/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/assets/bower_components/Ionicons/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/assets/dist/css/AdminLTE.min.css">
<!-- iCheck -->
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/assets/plugins/iCheck/square/red.css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

<!-- Google Font -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>

<body class="hold-transition login-page">
	<div class="login-box" style="margin-top: 5px;" >
		<div class="login-logo">
			<a href="#"> <b>PracticeOn</b>Net
			</a>
		</div>
		<!-- /.login-logo -->
		<div class="login-box-body">
			<a href="/">Home</a>&nbsp;&nbsp;		
			
			<p class="login-box-msg">Sign in to start your session.[ <b style="font-size: small;">ADMIN User</b> ]</p>
             <span style="color: red">${expiredThroughAjax}</span>
			<span style="color: red">${errorMessge}</span>
			<span style="color: red">${expired}</span>
			<span style="color: red">${invalid}</span>
			
			
			
		

			<!-- This is a default login processing url for spring security -->
			<form action="/admin/doLogin" method='POST'>
				<div class="form-group has-feedback">
					<input type="Email" name="username" class="form-control"
						placeholder="Email"> <span
						class="glyphicon glyphicon-envelope form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input type="password" name="password" class="form-control"
						placeholder="Password"> <span
						class="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>
				<div class="row">
					<div class="col-xs-8">
						<div class="checkbox icheck">
							<label> <input type="checkbox"> Remember Me
							</label>
						</div>
					</div>
					<!-- /.col -->
					<div class="col-xs-4">
						<button type="submit" class="btn btn-primary btn-block btn-flat">Sign
							In</button>
					</div>
					<!-- /.col -->
				</div>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>

			<div class="social-auth-links text-center">
				<p>- OR -</p>
				<a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i
					class="fa fa-facebook"></i> Sign in using Facebook</a> <a href="#"
					class="btn btn-block btn-social btn-google btn-flat"><i
					class="fa fa-google-plus"></i> Sign in using Google+</a>
			</div>

			<!-- /.social-auth-links -->
			<a href="/admin/pub/fgotpwd">I forgot my password</a> 

		</div>
		<!-- /.login-box-body -->
	</div>
	<!-- /.login-box -->

	<!-- jQuery 3 -->
	<script
		src="${contextPath}/resources/assets/bower_components/jquery/dist/jquery.min.js"
		type="text/javascript"></script>
	<!-- Bootstrap 3.3.7 -->
	<script
		src="${contextPath}/resources/assets/bower_components/bootstrap/dist/js/bootstrap.min.js"
		type="text/javascript"></script>
	<!-- iCheck -->
	<script
		src="${contextPath}/resources/assets/plugins/iCheck/icheck.min.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			$('input').iCheck({
				checkboxClass : 'icheckbox_square-red',
				radioClass : 'iradio_square-red',
				increaseArea : '20%' /* optional */
			});
		});
	</script>
</body>
</html>
