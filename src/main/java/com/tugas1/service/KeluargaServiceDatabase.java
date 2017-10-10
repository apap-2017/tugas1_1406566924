package com.tugas1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tugas1.mapper.KeluargaMapper;
import com.tugas1.model.KeluargaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {
	@Autowired
	private KeluargaMapper keluargaMapper;

	@Override
	public KeluargaModel getKeluarga(int idKeluarga) {
		log.info("get keluarga with id {}", idKeluarga);
		return keluargaMapper.selectKeluarga(idKeluarga);
	}

	
}