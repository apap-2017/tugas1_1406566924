package com.tugas1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tugas1.mapper.KelurahanMapper;
import com.tugas1.model.KeluargaModel;
import com.tugas1.model.KelurahanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KelurahanServiceDatabase implements KelurahanService {
	@Autowired
	private KelurahanMapper kelurahanMapper;

	@Override
	public KelurahanModel getKelurahan(int idKelurahan) {
		log.info("get kelurahan with id {}", idKelurahan);
		return kelurahanMapper.selectKelurahan(idKelurahan);
	}

	
}