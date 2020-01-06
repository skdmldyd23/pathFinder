<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%-- Insert Modal --%>
<div
  class="modal fade"
  id="insertModal"
  tabindex="-1"
  role="dialog"
  aria-labelledby="insertModalTitle"
  aria-hidden="true"
>
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header bg-olive">
        <h4 class="modal-title">
          <i class="fas fa-user-plus"></i> 사용자 추가
        </h4>
        <button type="button" class="close" data-dismiss="modal">
          &times;
        </button>
      </div>
      <form id="userCreateForm">
        <div class="modal-body">
          <div class="form-group row">
            <label for="userIdAdd" class="col-sm-3 col-form-label-sm"
              >아이디</label
            >
            <div class="col-sm-8">
              <input
                type="text"
                class="form-control"
                id="userIdAdd"
                name="userId"
                placeholder="3글자 ~ 15글자"
              />
            </div>
          </div>

          <div class="form-group row">
            <label for="userNameAdd" class="col-sm-3 col-form-label-sm"
              >이름</label
            >
            <div class="col-sm-8">
              <input
                type="text"
                class="form-control"
                id="userNameAdd"
                name="userName"
                placeholder="2글자 ~ 10글자"
              />
            </div>
          </div>

          <div class="form-group row">
            <label for="userEmailAdd" class="col-sm-3 col-form-label-sm"
              >이메일</label
            >
            <div class="col-sm-8">
              <input
                type="text"
                class="form-control"
                id="userEmailAdd"
                name="userEmail"
              />
            </div>
          </div>

          <div class="form-group row">
            <label for="userPhoneAdd" class="col-sm-3 col-form-label-sm"
              >이동전화 번호</label
            >
            <div class="col-sm-8">
              <input
                type="text"
                class="form-control"
                id="userPhoneAdd"
                name="userPhone"
                placeholder="010-1234-5678"
              />
            </div>
          </div>

          <div class="form-group row">
            <label for="areaIndexAdd" class="col-sm-3 col-form-label-sm"
              >지역</label
            >
            <div class="col-sm-8">
              <select id="areaIndexAdd" name="areaIndex">
                <option></option>
              </select>
            </div>
          </div>

          <div class="form-group row">
            <label for="branchIndexAdd" class="col-sm-3 col-form-label-sm"
              >지점</label
            >
            <div class="col-sm-8">
              <select id="branchIndexAdd" name="branchIndex">
                <option></option>
              </select>
            </div>
          </div>

          <div class="form-group row">
            <label for="userPositionAdd" class="col-sm-3 col-form-label-sm"
              >직책</label
            >
            <div class="col-sm-8">
              <select id="userPositionAdd" name="userPosition">
                <option></option>
                <option value="사원">사원</option>
                <option value="대리">대리</option>
                <option value="과장">과장</option>
                <option value="차장">차장</option>
                <option value="부장">부장</option>
                <option value="임원">임원</option>
              </select>
            </div>
          </div>

          <div class="form-group row">
            <label for="userAuth" class="col-sm-3 col-form-label-sm"
              >권한</label
            >
            <div class="col-sm-8">
              <div class="custom-control custom-control-inline custom-radio">
                <input
                  class="custom-control-input"
                  type="radio"
                  name="userAuth"
                  id="authUserAdd"
                  value="false"
                />
                <label class="custom-control-label" for="authUserAdd"
                  >일반 사용자</label
                >
              </div>
              <div class="custom-control custom-control-inline custom-radio">
                <input
                  class="custom-control-input"
                  type="radio"
                  name="userAuth"
                  id="authAdminAdd"
                  value="true"
                />
                <label class="custom-control-label" for="authAdminAdd"
                  >관리자</label
                >
              </div>
            </div>
          </div>

          <div class="formCheck" id="serverFormCheck"></div>
        </div>
        <div class="card-footer text-right">
          <button type="button" class="btn btn-success" id="InsertTest">
	   테스트
          </button>
          <button type="button" class="btn btn-secondary" data-dismiss="modal">
            취소
          </button>
          <button type="submit" class="btn btn-success" id="InsertBtn">
            등록
          </button>
        </div>
      </form>
    </div>
  </div>
</div>

<%-- Modify Modal --%>
<div
  class="modal fade"
  id="modifyModal"
  tabindex="-1"
  role="dialog"
  aria-labelledby="modifyModalTitle"
  aria-hidden="true"
>
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header bg-olive">
        <h4 class="modal-title">
          <i class="fas fa-user-edit"></i> 사용자 수정
        </h4>
        <button type="button" class="close" data-dismiss="modal">
          &times;
        </button>
      </div>
      <form id="userModifyForm">
        <div class="modal-body">
          <div class="form-group row" style="display:none;">
            <label for="userIndexModify" class="col-sm-3 col-form-label-sm"
              >번호</label
            >
            <div class="col-sm-8">
              <input
                type="text"
                class="form-control"
                id="userIndexModify"
                name="userIndex"
                readonly
              />
            </div>
          </div>

          <div class="form-group row">
            <label for="userIdModify" class="col-sm-3 col-form-label-sm"
              >아이디</label
            >
            <div class="col-sm-8">
              <input
                type="text"
                class="form-control"
                id="userIdModify"
                name="userId"
                placeholder="3글자 ~ 15글자"
                readonly
              />
            </div>
          </div>

          <div class="form-group row">
            <label for="userPwModify" class="col-sm-3 col-form-label-sm"
              >비밀번호</label
            >
            <div class="col-sm-8">
              <input
                type="button"
                class="btn btn-danger"
                id="userPwModify"
                name="userPw"
                value="초기화"
              />
            </div>
          </div>

          <div class="form-group row">
            <label for="userNameModify" class="col-sm-3 col-form-label-sm"
              >이름</label
            >
            <div class="col-sm-8">
              <input
                type="text"
                class="form-control"
                id="userNameModify"
                name="userName"
                placeholder="2글자 ~ 10글자"
              />
            </div>
          </div>

          <div class="form-group row">
            <label for="userEmailModify" class="col-sm-3 col-form-label-sm"
              >이메일</label
            >
            <div class="col-sm-8">
              <input
                type="text"
                class="form-control"
                id="userEmailModify"
                name="userEmail"
              />
            </div>
          </div>

          <div class="form-group row">
            <label for="userPhoneModify" class="col-sm-3 col-form-label-sm"
              >이동전화 번호</label
            >
            <div class="col-sm-8">
              <input
                type="text"
                class="form-control"
                id="userPhoneModify"
                name="userPhone"
                placeholder="010-1234-5678"
              />
            </div>
          </div>

          <div class="form-group row">
            <label for="areaIndexModify" class="col-sm-3 col-form-label-sm"
              >지역</label
            >
            <div class="col-sm-8">
              <select id="areaIndexModify" name="areaIndex"> </select>
            </div>
          </div>

          <div class="form-group row">
            <label for="branchIndexModify" class="col-sm-3 col-form-label-sm"
              >지점</label
            >
            <div class="col-sm-8">
              <select id="branchIndexModify" name="branchIndex"> </select>
            </div>
          </div>

          <div class="form-group row">
            <label for="userPositionModify" class="col-sm-3 col-form-label-sm"
              >직책</label
            >
            <div class="col-sm-8">
              <select id="userPositionModify" name="userPosition">
                <option value="" disabled selected>선택</option>
                <option>사원</option>
                <option>대리</option>
                <option>과장</option>
                <option>차장</option>
                <option>부장</option>
                <option>임원</option>
              </select>
            </div>
          </div>

          <div class="form-group row">
            <label for="userAuth" class="col-sm-3 col-form-label-sm"
              >권한</label
            >
            <div class="col-sm-8">
              <div class="custom-control custom-control-inline custom-radio">
                <input
                  class="custom-control-input"
                  type="radio"
                  name="userAuth"
                  id="authUserModify"
                  value="false"
                />
                <label class="custom-control-label" for="authUserModify"
                  >일반 사용자</label
                >
              </div>
              <div class="custom-control custom-control-inline custom-radio">
                <input
                  class="custom-control-input"
                  type="radio"
                  name="userAuth"
                  id="authAdminModify"
                  value="true"
                />
                <label class="custom-control-label" for="authAdminModify"
                  >관리자</label
                >
              </div>
            </div>
          </div>
        </div>
        <div class="formCheck" id="serverFormCheck"></div>
        <div class="card-footer text-right">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">
            취소
          </button>
          <button type="submit" class="btn btn-success" id="ModifyBtn">
            수정
          </button>
        </div>
      </form>
    </div>
  </div>
</div>