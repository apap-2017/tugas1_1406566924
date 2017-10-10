package com.tugas1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tugas1.mapper.PendudukMapper;
import com.tugas1.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {
	@Autowired
	private PendudukMapper pendudukMapper;

	@Override
	public PendudukModel getPenduduk(String nik) {
		log.info("get penduduk with nik {}", nik);
		log.info("{}", pendudukMapper);
		PendudukModel pm = pendudukMapper.selectPenduduk(nik);
		log.info("berhasil query penduduk");
		return pm;
	}

	
}