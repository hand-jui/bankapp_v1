<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/layout/header.jsp"%>
<!-- Todo!! : main영역으로 지정 예정 -->
<div class="col-sm-8">
	<h2>계좌 생성 페이지)인증)</h2>
	<h5>어서오세요, 환영합니다</h5>
	
	<div class="bg-light p-md-5 h-75">
		<form action="#" method="post">
		
			<div class="form-group">
				<label for="number">계좌 번호:</label>
				<input type="text" class="form-control" placeholder="생성할 계좌번호를 입력하시오" id="number" name="number">
			</div>
			
			<div class="form-group">
				<label for="password">계좌 비밀번호:</label>
				<input type="password" class="form-control" placeholder="계좌 비밀번호" id="password" name="password">
			</div>
			
			<div class="form-group">
				<label for="balance">입금 금액:</label>
				<input type="text" class="form-control" placeholder="입금 금액" id="balance" name="balance">
			</div>
			
			<div class="form-group">
				<label for="password">계좌 비밀번호:</label>
				<input type="password" class="form-control" placeholder="계좌 비밀번호" id="password" name="password">
			</div>
			
			<button type="submit" class="btn btn-primary">입금</button>
			
		</form>
	</div>
	<br>
</div>

<%@ include file="/WEB-INF/view/layout/footer.jsp"%>
