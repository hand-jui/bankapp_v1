package com.tenco.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tenco.bank.repository.model.Account;

@Mapper // mybatis 연결처리
public interface AccountRepository {

	public int insert(Account account);

	public int updateById(Account account);

	public int deleteById(int id);

	public Account findById(int id);

	public List<Account> findAll();
	
	public List<Account> findByUserId(Integer userId);

}
