
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
			<a href="index2.html"> <b>PracticeOn</b>Net
			</a>
		</div>
		<!-- /.login-logo -->
		<div class="login-box-body">
			<a href="/">Home</a>&nbsp;&nbsp;
			<p class="login-box-msg">
				[ <b style=" font-size: small;">Web User</b> ]
			</p>

			<p class="login-box-msg">We will send you password at your mail Id</p>

			<span style="color: red">${message}</span>

			<!-- This is a default login processing url for spring security -->
			<form action="/user/pub/fgotpwd" method='POST'>
				<div class="form-group has-feedback">
					<input type="email" name="username" class="form-control"
						placeholder="Email"> <span
						class="glyphicon glyphicon-envelope form-control-feedback"></span>
				</div>


				<span style="color: red">${errorMessge}</span>

				<div class="row">
					<div class="col-xs-12">
						<button type="submit" class="btn bg-blue btn-block btn-flat">Submit</button>
					</div>

				</div>
				
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
			<br>
			<div class="row">
				<!-- /.col -->
				<div class="col-xs-12">
					<button onclick="window.history.go(-1); " class="btn bg-red btn-block btn-flat">Go
						Back</button>
				</div>
				<!-- /.col -->
			</div>
			<br><br>

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
