package com.tugas1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tugas1.mapper.KotaMapper;
import com.tugas1.model.KotaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KotaServiceDatabase implements KotaService {
	@Autowired
	private KotaMapper kotaMapper;

	@Override
	public KotaModel getKota(int idKota) {
		log.info("get kota with id {}", idKota);
		return kotaMapper.selectKota(idKota);
	}

	
}