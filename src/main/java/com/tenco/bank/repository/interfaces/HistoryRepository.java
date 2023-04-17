package com.tenco.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tenco.bank.repository.model.History;

@Mapper
public interface HistoryRepository {

	public int insert(History history);

	public int update(History history);

	public int delete(int id);

	public History findById(int id);

	public List<History> findAll();

}
