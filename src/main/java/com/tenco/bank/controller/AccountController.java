package com.tenco.bank.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tenco.bank.dto.DepositFormDto;
import com.tenco.bank.dto.SaveFormDto;
import com.tenco.bank.dto.TransferFormDto;
import com.tenco.bank.dto.WithdrawFormDto;
import com.tenco.bank.dto.response.HistoryDto;
import com.tenco.bank.handler.exception.CustomRestfullException;
import com.tenco.bank.handler.exception.UnAuthorizedException;
import com.tenco.bank.repository.model.Account;
import com.tenco.bank.repository.model.User;
import com.tenco.bank.service.AccountService;
import com.tenco.bank.utils.Define;

@Controller
@RequestMapping("/account")
public class AccountController {

//	todo
//	계좌 목록 페이지
//	입금
//	출금
//	이체
//	계좌 상세보기
//	계좌 생성페이지 등

	@Autowired
	private HttpSession session;

	@Autowired
	private AccountService accountService;

	/**
	 * 계좌 목록 페이지
	 * 
	 * @return 목록 페이지 이동
	 */
	@GetMapping({ "/list", "/" })
	public String list(Model model) {

		User principal = (User) session.getAttribute(Define.PRINCIPAL);

		List<Account> accountList = accountService.readAccountList(principal.getId());
		if (accountList.isEmpty()) {
			model.addAttribute("accountList", null);
		} else {
			model.addAttribute("accountList", accountList);
		}
//		view 화면으로 데이터를 내려주는 기술
//		Model 과 ModelAndView

		return "/account/list";
	}

//	출금 페이지
	@GetMapping("/withdraw")
	public String withdraw() {

		return "/account/withdrawForm";
	}

	/**
	 * 출금 처리 기능
	 * 
	 * @param withdrawFormDto
	 * @return
	 */
	@PostMapping("/withdraw-proc")
	public String withdrawProc(WithdrawFormDto withdrawFormDto) {

		User principal = (User) session.getAttribute(Define.PRINCIPAL);

		if (withdrawFormDto.getAmount() == null) {
			throw new CustomRestfullException("금액을 입력하세요.", HttpStatus.BAD_REQUEST);
		}

		if (withdrawFormDto.getAmount().longValue() <= 0) {
			throw new CustomRestfullException("출금액이 0원 이하일 수는 없습니다.", HttpStatus.BAD_REQUEST);
		}

		if (withdrawFormDto.getWAccountNumber() == null || withdrawFormDto.getWAccountNumber().isEmpty()) {
			throw new CustomRestfullException("계좌 번호를 입력해주세요", HttpStatus.BAD_REQUEST);
		}

		if (withdrawFormDto.getWAccountPassword() == null || withdrawFormDto.getWAccountPassword().isEmpty()) {
			throw new CustomRestfullException("계좌 비밀번호를 입력해주세요", HttpStatus.BAD_REQUEST);
		}

//		서비스 호출 예정
		accountService.updateAccountWithdraw(withdrawFormDto, principal.getId());

		return "redirect:/account/list";
	}

//	입금 페이지
	@GetMapping("/deposit")
	public String deposit() {

		return "/account/depositForm";
	}

	/**
	 * 입금 처리 기능
	 * 
	 * @param depositFormDto
	 * @return
	 */
	@PostMapping("/deposit-proc")
	public String depositProc(DepositFormDto depositFormDto) {

		if (depositFormDto.getAmount() == null) {
			throw new CustomRestfullException("금액을 입력해주세요.", HttpStatus.BAD_REQUEST);
		}

		if (depositFormDto.getAmount().longValue() <= 0) {

			throw new CustomRestfullException("입금 금액이 0원 이하 일 수 없습니다.", HttpStatus.BAD_REQUEST);

		}
		if (depositFormDto.getDAccountNumber() == null || depositFormDto.getDAccountNumber().isEmpty()) {
			throw new CustomRestfullException("입금할 계좌번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}

		accountService.updateAccountDeposit(depositFormDto);

		return "redirect:/account/list";
	}

//	이체 페이지
	@GetMapping("/transfer")
	public String transfer() {

		return "/account/transferForm";
	}

	/**
	 * 이체 기능
	 * 
	 * @param transferFormDto
	 * @return
	 */
	@PostMapping("/transfer-proc")
	public String transferProc(TransferFormDto transferFormDto) {
		User principal = (User) session.getAttribute(Define.PRINCIPAL);

//		1. 출금 계좌 번호 입력 여부
		if (transferFormDto.getWAccountNumber() == null || transferFormDto.getWAccountNumber().isEmpty()) {
			throw new CustomRestfullException("출금 계좌 번호를 입력 하세요.", HttpStatus.BAD_REQUEST);
		}

//		2. 입금 계좌 번호 입력 여부
		if (transferFormDto.getDAccountNumber() == null || transferFormDto.getDAccountNumber().isEmpty()) {
			throw new CustomRestfullException("입금 계좌 번호를 입력 하세요.", HttpStatus.BAD_REQUEST);
		}

//		3. 출금 계좌 비밀번호 입력 여부
		if (transferFormDto.getWAccountPassword() == null || transferFormDto.getWAccountPassword().isEmpty()) {
			throw new CustomRestfullException("출금 계좌 비밀번호를 입력 하세요.", HttpStatus.BAD_REQUEST);
		}

//		4. 이체 금액 0원 이상 확인
		if (transferFormDto.getAmount() <= 0) {
			throw new CustomRestfullException("이체 금액이 0원 이하 입니다.", HttpStatus.BAD_REQUEST);
		}

//		5. 출금 계좌 != 입금 계좌 확인
		if (transferFormDto.getWAccountNumber().equals(transferFormDto.getDAccountNumber())) {
			throw new CustomRestfullException("출금 계좌와 입금 계좌가 동일합니다.", HttpStatus.BAD_REQUEST);
		}

//		서비스 호출
		accountService.updateAccountTransfer(transferFormDto, principal.getId());

		return "redirect:/account/list";
	}

//	계좌 생성 페이지
	@GetMapping("/save")
	public String save() {

//		인증 검사 처리
		return "/account/saveForm";
	}

	/**
	 * 계좌 생성
	 * 인증 검사,
	 * 유효성 검사 처리 - 0원 입력 가능, 마이너스 입력 불가
	 * 
	 * @param saveFormDto
	 * @return 계좌 목록 페이지
	 */
	@PostMapping("/save-proc")
	public String saveProc(SaveFormDto saveFormDto) {

		User principal = (User) session.getAttribute(Define.PRINCIPAL);

//		유효성 검사하기
		if (saveFormDto.getNumber() == null || saveFormDto.getNumber().isEmpty()) {
			throw new CustomRestfullException("계좌번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}

		if (saveFormDto.getPassword() == null || saveFormDto.getPassword().isEmpty()) {
			throw new CustomRestfullException("비밀번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}

		if (saveFormDto.getBalance() == null || saveFormDto.getBalance() < 0) {
			throw new CustomRestfullException("잘못된 금액입니다.", HttpStatus.BAD_REQUEST);
		}

//		서비스 호출
		accountService.createAccount(saveFormDto, principal.getId());

		return "redirect:/account/list";
	}

//	계좌 상세 보기 페이지
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable Integer id,
			@RequestParam(name = "type", defaultValue = "all", required = false) String type, Model model) {

		User principal = (User) session.getAttribute(Define.PRINCIPAL);

		Account account = accountService.readAccount(id);
//		거래내역 결과 집합 = 서비스.메서드();
		List<HistoryDto> historyList = accountService.readHistoryListByAccount(type, id);

//		화면을 구성하기 위해 필요한 데이터
//		소유자 이름
//		계좌 번호 (1개), 계좌 잔액
//		거래 내역

		model.addAttribute(Define.PRINCIPAL, principal);
		model.addAttribute("account", account);
		model.addAttribute("historyList", historyList);

		System.out.println("accountId : " + id);

		return "/account/detail";
	}
}
