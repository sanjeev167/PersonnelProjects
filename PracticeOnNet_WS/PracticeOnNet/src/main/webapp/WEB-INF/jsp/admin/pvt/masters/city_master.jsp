

<!-- Content Header (Page header) -->

<section class="content-header">
	<h1>City Master</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
		<li class="active">City Master</li>
	</ol>
</section>

<!-- Main content -->
<section class="content container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary ">
				<div class="box-header with-border">

					<div class="box-title">
						<button type="button">
							<i class="fa fa-plus addClass" data-toggle="modal">&nbsp;</i>
						</button>
						<button type="button" id="deleteSelected">
							<i class="fa fa-trash-o" data-toggle="modal">&nbsp;</i>
						</button>
						<button type="button">
							<i class="fa fa-search searchClass" data-toggle="modal">&nbsp;</i>
						</button>
						<button type="button" id="reloadGrid">
							<i class="fa fa-refresh">&nbsp;Grid</i>
						</button>
						<button type="button" id="refreshGrid">
							<i class="fa fa-refresh">&nbsp;Page</i>
						</button>
					</div>
				</div>

				<div class="box-body ">
					<table width="100%" id="cityId"
						class="table table-striped table-bordered table-hover table-condensed dt-responsive data-table">
						<thead>
							<tr>
								<th width="6%">#</th>
								<th width="7%"><input id="chkAll" type="checkbox">&nbsp;All</th>

								<th width="6%">ID</th>
								<th>Country Name</th>
								<th>State Name</th>
								<th>City Name</th>
								<th width="6%">View</th>
								<th width="6%">Edit</th>
								<th width="6%">Delete</th>

							</tr>
						</thead>

					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
	</div>
</section>
<!-- /.content -->


<!-- Modals for this form -->

<style type="text/css">
table.cart th, #main-content table.cart th, table.cart td, #main-content table.cart td,
	table.cart tr, #main-content table.cart tr, #content-area table tr,
	#content-area table td, #content-area table th {
	max-width: 100px;
	padding: 0.857em 0.587em;
	background-color: red;
}

.field-error {
	color: red;
	font-size: small;
}

.example-modal .modal {
	position: relative;
	top: auto;
	bottom: auto;
	right: auto;
	left: auto;
	display: block;
	z-index: 1;
}

.example-modal .modal {
	background: transparent !important;
}
</style>


<style type="text/css">
.modal-dialog {
	width: 400px;
}

.modal-header {
	background-color: #337AB7;
	padding: 16px 16px;
	color: #FFF;
	border-bottom: 2px dashed #337AB7;
}
</style>

<!-- modal for add/update/view/search -->
<div class="modal fade" id="modal-common">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="commonModalTitleId">Common Modal</h4>
			</div>
			<div class="modal-body">
				<!-- form start -->
				<form class="form-horizontal" id="commonFormId" action="">
					<div class="box-body">
						<h5 id="successMsgId"></h5>
						<h5 id="searchMsgId"></h5>
						<div class="form-group">
							<label for="inputCountryName" class="col-sm-4 control-label">
								Country Name</label>
							<div class="col-sm-7">
								<select class="form-control" name="countryNameId"
									id="countryNameId">
									<option value="">-- Select --</option>

								</select> <span id="countryNameId_err" class="field-error"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputCountryName" class="col-sm-4 control-label">
								State Name</label>
							<div class="col-sm-7">
								<select class="form-control" name="stateNameId" id="stateNameId">
									<option value="">-- Select --</option>

								</select> <span id="stateNameId_err" class="field-error"></span>
							</div>
						</div>
						<div class="form-group" id="cityLabledId">
							<label for="inputSortName" class="col-sm-4 control-label">
								City Name</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" name="cityName"
									id="cityNameId" placeholder="City Name"> <span
									id="cityNameId_err" class="field-error"></span>
							</div>
						</div>

						<input hidden="true" name="id" id="cityRecordId" />
					</div>
					<!-- /.box-body -->

					<!-- /.box-footer -->
				</form>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-info" data-dismiss="modal">Close</button>
				<button type="button"
					class="btn btn-warning commonButtonActionClass"
					id="commonButtonActionId">Save</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->



<!-- modal for delete -->
<div class="modal fade" id="modal-delete">
	<div class="modal-dialog modal-sm modal-confirm">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Are you sure?</h4>

			</div>
			<div class="modal-body">
				<h5 id="deleteSuccessMsgId"></h5>
				<label>Do you really want to delete this record? This
					process cannot be undone.</label>
			</div>
			<input hidden="true" name="recordId" id="recordIdForDelete" />
			<div class="modal-footer">
				<button type="button" class="btn btn-info" data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-danger" id="deleteFormButtonId">Delete</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<!-- modal for selected Delete -->
<div class="modal fade" id="modal-sDelete">
	<!-- modal for selected delete -->
	<div class="modal-dialog modal-sm modal-confirm">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Are you sure?</h4>

			</div>
			<div class="modal-body">
				<h5 id="deleteSelectedSuccessMsgId"></h5>
				<label id="showAllertMsg"></label>
				<div id="showSelectedRow" style="margin-top: 10px;"></div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-info" data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-danger"
					id="deleteSelFormButtonId">Delete</button>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<!-- modal for success -->
<div class="modal fade" id="modal-success">
	<div class="modal-dialog modal-sm modal-success">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Success Message</h4>
			</div>
			<div class="modal-body">
				<div class="modal-body">Record has been saved successfully.</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default pull-left"
					data-dismiss="modal">Cancel</button>
				<button type="button" class="btn btn-primary">Yes</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- End of modals for form -->


<script src="${contextPath}/resources/assets/pageSpecific/js/city.js"></script>






