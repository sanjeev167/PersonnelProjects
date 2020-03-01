<div class="box box-primary direct-chat direct-chat-primary">
	<div class="box-header with-border  bg-aqua">
		<h3 class="box-title ">Admin User Channel</h3>

		<div class="box-tools pull-right">

			<button type="button" class="btn btn-box-tool" data-widget="collapse">
				<i class="fa fa-minus"></i>
			</button>
			<button type="button" class="btn btn-box-tool" data-toggle="tooltip"
				title="API Call Details" data-widget="chat-pane-toggle">
				<i class="fa fa-comments"></i>
			</button>
			<!-- <button type="button" class="btn btn-box-tool"
											data-widget="remove">
											<i class="fa fa-times"></i>
										</button> -->
		</div>
	</div>
	<!-- /.box-header -->
	<div class="box-body">
		<!-- Conversations are loaded here -->
		<div class="direct-chat-messages">
			<div>
				<b><span style="color: red;">Through LOCAL Calls:==></span> <br />Using
					this channel, a user in <span style="color: red">ADMIN </span> role
					can manage his resources once he is authenticated. Request
					containing <span style="color: red">/admin/** </span> in its url
					will go on this channel only. </b>
			</div>
			<p>
			<div>
				<i class="fa fa-fw fa-user"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
					href="/admin/pub/login">Sign In/Sign Up</a>
			</div>
			<div>
				<i class="fa fa-fw fa-dashboard"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
					href="/admin/pvt/dbPage">Dashboard</a>
			</div>
			<div>
				<i class="fa fa-fw fa-user"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
					href="/admin/pvt/profile">Profile</a>
			</div>
		</div>
		<!--/.direct-chat-messages-->

		<!-- Contacts are loaded here -->
		<div class="direct-chat-contacts"
			style="background-color: white; color: black; padding: 10px;">
			<div>
				<b><span style="color: green;">Through API Calls:==></span> <br />Using
					this channel, a user in <span style="color: red">ADMIN </span> role
					can manage his resources once he is authenticated. Request
					containing <span style="color: red">/api/admin/** </span> in its
					url will go on this channel only. </b>
			</div>
			<p>
			<div>
				<i class="fa fa-fw fa-user"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
					href="/admin/pub/loginOpen">Sign In/Sign Up</a>
			</div>
			<div>
				<i class="fa fa-fw fa-dashboard"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
					href="/admin/pvt/dbPage">Dashboard</a>
			</div>
			<div>
				<i class="fa fa-fw fa-user"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
					href="/admin/pvt/profile">Profile</a>
			</div>
			<!-- /.contatcts-list -->
		</div>
		<!-- /.direct-chat-pane -->
	</div>
	<!-- /.box-body -->
	<div class="box-footer bg-blue" style="text-align: center">Admin
		User Channel</div>
	<!-- /.box-footer-->
</div>
