<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- Modal -->
<!-- 추가모달 -->
<div class="modal fade" id="insertModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header bg-olive">
				<h4 class="modal-title">
      				<i class="fas fa-building"></i> 지점 추가
      			</h4>
      			<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			<form name="branchInsertform" id="branchInsertform">
			<div class="modal-body">
					<div class="form-group row">
						<label for="b1" class="col-sm-3 col-form-label-sm">지점명 </label>
						<div class="col-sm-8 ">	
							<input type="text" class="form-control" name="branchName"
								id="branchName" placeholder="3글자 ~ 15글자" >
						</div>
					</div>
					<div class="form-group row">
						<label for="b2" class="col-sm-3 col-form-label-sm">지점장 </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" name="branchOwner"
								id="branchOwner" placeholder="2글자 ~ 10글자">
						</div>
					</div>
					<div class="form-group row">
						<label for="b3" class="col-sm-3 col-form-label-sm">운반비</label>
						<div class="col-sm-8">
							<input type="number" class="form-control" name="branchValue"
								id="branchValue" placeholder="5천원 ~ 10만원" step=100>
						</div>
					</div>
					<div class="form-group row">
						<label for="b4" class="col-sm-3 col-form-label-sm">주소</label>
						<div class="col-sm-8">
							<input class ="btn btn-secondary btn-sm" type="button" onclick="addressFind()" value="주소 검색" />
							 <input
								type="text" class="form-control" id="branch_Addr"
								name="branchAddr" placeholder="주소" readonly  />
						</div>
					</div>
					<div class="form-group row">
						<label for="b5" class="col-sm-3 col-form-label-sm">상세주소</label>
						<div class="col-sm-8">
							<input type="text" id="branchDaddr" class="form-control"
								name="branchDaddr" placeholder="상세주소" >
						</div>
					</div>
					<div class="form-group row">
						<label for="b7" class="col-sm-3 col-form-label-sm"> 지역</label>
						<div class="col-sm-8">
							<input readonly type="text" id="branch_Area" class="form-control"
								name="areaIndex" >
						</div>
					</div>
					<div class="form-group row">
						<label for="b6" class="col-sm-3 col-form-label-sm"> 전화 번호</label>
						<div class="col-sm-8">
						
							<input type="text" class="form-control" name="branchPhone"
								id="branchPhone" placeholder="02-123-5676" >
						
						</div>
					</div>
					<div id="branchPhoneVaild"></div>
					<input hidden type="text" name="branchLat"> <input hidden
						type="text" name="branchLng">
			</div>
			<div class="card-footer text-right">
				<input type="button" name="branchInsertBtn"
					id="branchInsertTest" class="btn btn-success" value="테스트">
				<input type="button" class="btn btn btn-secondary" data-dismiss="modal"
					value="취소">
				<input type="submit" name="branchInsertBtn"
					id="branchInsertBtn" class="btn btn-success" value="등록">
		    </div>
			</form>
		</div>
	</div>
</div>

<%-- data-dismiss="modal" --%>

<!-- 수정 모달 -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
	aria-labelledby="modifyModalTitle" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header bg-olive">
				<h4 class="modal-title">
      				<i class="fas fa-building"></i> 지점 수정
      			</h4>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			<form name="branchUpdateForm" id="branchUpdateForm">
				<div class="modal-body">
					<input hidden type="text" id="updateAreaIndex" name="branchIndex">
					<input hidden type="text" id="areaIndex" name="areaIndex">

					<div class="form-group row">
						<label for="branchArea" class="col-sm-3 col-form-label-sm">지역
						</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="branchArea1"
								name="branchArea" readonly>
						</div>
					</div>

					<div class="form-group row">
						<label for="branchName" class="col-sm-3 col-form-label-sm">
							지점명 </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="branchName1"
								name="branchName" placeholder="3글자 ~ 15글자" readonly>
						</div>
					</div>

					<div class="form-group row">
						<label for="branchOwner" class="col-sm-3 col-form-label-sm">
							지점장</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="branchOwner1"
								name="branchOwner" placeholder="2글자 ~ 10글자">
						</div>
					</div>

					<div class="form-group row">
						<label for="branchValue" class="col-sm-3 col-form-label-sm">운반비</label>
						<div class="col-sm-8">
							<input type="number" class="form-control"
								id="branchValue1" name="branchValue" placeholder="5천원 ~ 10만원">
						</div>
					</div>

					<div class="form-group row">
						<label for="branchPhone" class="col-sm-3 col-form-label-sm">전화 번호</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="branchPhone1"
								name="branchPhone" placeholder="02-123-5676">
						</div>
					</div>
				</div>
				<div class="card-footer text-right">
					<input type="button" class="btn btn-secondary"
						data-dismiss="modal" value="취소">
					<input type="submit" name="branchUpdateSaveBtn"
						class="btn btn-success" value="수정" id="branchUpdateBtn">
		    	</div>
			</form>
		</div>
	</div>
</div>

