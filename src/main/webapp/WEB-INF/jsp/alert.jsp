<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- Success -->
<div id="successModal" class="modal fade">
	<div class="modal-dialog modal-confirm modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<div class="icon-box">
					<i class="material-icons">&#xE876;</i>
				</div>				
				<p class="text-center"><h4 class="modal-title">등록완료</h4></p>
			</div>
			<div class="modal-body">
				<p class="text-center">정보가 성공적으로 등록되었습니다.</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-success btn-block" data-dismiss="modal"
				onClick="location.reload()">확인</button>
			</div>
		</div>
	</div>
</div>     

<!-- Fail -->
<div id="failModal" class="modal fade">
	<div class="modal-dialog modal-error modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<div class="icon-box">
					<i class="material-icons">&#xE5CD;</i>
				</div>				
				<p class="text-center"><h4 class="modal-title">등록실패</h4></p>
			</div>
			<div class="modal-body">
				<p class="text-center" id="errorMessage"></p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger btn-block" data-dismiss="modal">확인</button>
			</div>
		</div>
	</div>
</div> 

<!-- Update -->
<div id="updateAlertModal" class="modal fade">
	<div class="modal-dialog modal-confirm modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<div class="icon-box">
					<i class="material-icons">&#xE876;</i>
				</div>				
				<p class="text-center"><h4 class="modal-title" id="updateTitle"></h4></p>
			</div>
			<div class="modal-body">
				<p class="text-center" id="updateMessage"></p>
			</div>
			<div class="modal-footer">
				<button type="button" id="updateAlertModalOk" class="btn btn-danger btn-block" data-dismiss="modal">확인</button>
			</div>
		</div>
	</div>
</div>

<!-- Check -->
<div id="checkModal" class="modal fade">
	<div class="modal-dialog modal-error modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<div class="icon-box">
					<i class="material-icons">report_problem</i>
				</div>				
				<p class="text-center"><h4 class="modal-title" id="checkTitle"></h4></p>
			</div>
			<div class="modal-body">
				<p class="text-center" id="checkMessage"></p>
			</div>
			<div class="modal-footer">
				<div class="col">
					<button type="button" class="btn btn-danger btn-block" id="checkOk" data-dismiss="modal">확인</button>
				</div>
				<div class="col">
					<button type="button" class="btn btn-info btn-block" data-dismiss="modal">취소</button>
				</div>
			</div>
		</div>
	</div>
</div> 