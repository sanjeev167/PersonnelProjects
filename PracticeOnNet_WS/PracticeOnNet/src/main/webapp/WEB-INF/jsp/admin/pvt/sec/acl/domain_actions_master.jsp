<!-- Content Header (Page header) -->
<section class="content-header">
	<h1>Record actions defined on domain for ACL </h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
		<li class="active">Domain Action</li>
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
					<table width="100%" id="domainActionId"
					
						class="table table-striped table-bordered table-hover table-condensed dt-responsive data-table">
						<thead>
							<tr>
								<th width="2%">#</th>
								<th width="7%"><input id="chkAll" type="checkbox">&nbsp;All</th>
								<th>ID</th>
								<th>App. Context</th>
								<th>Module</th>
								<th>Domain</th>
								<th>Package</th>
								<th>Action</th>
								<th>Sort</th>
								<th>Number</th>
								
								<th>V</th>
								<th>E</th>
								<th>D</th>
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
	<div class="modal-dialog" >
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
							<label for="inputDepartmentName" class="col-sm-4 control-label">
								App. Context</label>
							<div class="col-sm-7">
								<select class="form-control" name="departmentNameId" id="departmentNameId" >
									<option value="">-- Select --</option>	
																	
								</select>
								 <span id="departmentNameId_err" class="field-error"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputModuleName" class="col-sm-4 control-label">
								Module</label>
							<div class="col-sm-7">
								<select class="form-control" name="moduleNameId" id="moduleNameId" >
									<option value="">-- Select --</option>																	
								</select>
								 <span id="moduleNameId_err" class="field-error"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPageName" class="col-sm-4 control-label">
								Domain</label>
							<div class="col-sm-7">
								<select class="form-control" name="domainNameId" id="domainNameId" >
									<option value="">-- Select --</option>	
																	
								</select>
								 <span id="domainNameId_err" class="field-error"></span>
							</div>
						</div>						
						
						<div class="form-group" id="packageNameLabledId">
							<label for="inputBaseurlName" class="col-sm-4 control-label">
								Domain Pkg</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" name="packageName"
									id="packageNameId" placeholder="Package Name">
								<span id="packageName_err" class="field-error"></span>
							</div>
						</div>						
						<div class="form-group" id="actionNameLabledId">
							<label for="inputOpName" class="col-sm-4 control-label">
								Action</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" name="actionName"
									id="actionNameId" placeholder="Action name">
								<span id="actionName_err" class="field-error"></span>
							</div>
						</div>						
						<div class="form-group" id="sortNameLabledId">
							<label for="inputSortName" class="col-sm-4 control-label">
							 Sort Name</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" name="sortName"
									id="sortNameId" placeholder="Sort name">
								<span id="sortName_err" class="field-error"></span>
							</div>
						</div>
						<div class="form-group" id="actionNumberLabledId">
							<label for="inputSortName" class="col-sm-4 control-label">
								Action [No]</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" name="actionNumber"
									id="actionNumberId" placeholder="Action Number">
								<span id="actionNumber_err" class="field-error"></span>
							</div>
						</div>						
											
						<input hidden="true" name="id" id="actionRecordId" />
					</div>
					<!-- /.box-body -->
					<!-- /.box-footer -->
				</form>

			</div>
			<div class="modal-footer" id="buttonLabledId">
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
<div class="modal fade" id="modal-restricted">
	<div class="modal-dialog modal-sm modal-confirm">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Are you sure?</h4>

			</div>
			<div class="modal-body">
				<h5 id="deleteSuccessMsgId"></h5>
				<label style="color:red; font:strong">
				This is a pre-defined action on a domain. It can't be deleted or updated.</label>
			</div>
			<input hidden="true" name="recordId" id="recordIdForDelete" />
			<div class="modal-footer">
				<button type="button" class="btn btn-info" data-dismiss="modal">Cancel</button>
				
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
				<label id="showAllertMsgR"></label>
				
				<div id="showSelectedRowR" style="margin-top: 10px;"></div>
				<label id="showAllertMsgU"></label>
				<div id="showSelectedRowU" style="margin-top: 10px;"></div>

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


<script src="${contextPath}/resources/assets/pageSpecific/js/domain_action.js"></script>
<!-- End of modals for form -->


