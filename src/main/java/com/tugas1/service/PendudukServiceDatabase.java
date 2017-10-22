package com.tugas1.service;

import java.util.Date;

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
		return pendudukMapper.selectPenduduk(nik);
	}

	@Override
	public void addPenduduk(PendudukModel penduduk) {
		log.info("add penduduk with nik {}", penduduk.getNik());
		pendudukMapper.insertPenduduk(penduduk);
	}

	@Override
	public String getNikSamaPenduduk(int idKelurahan, Date tanggalLahir) {
		log.info("get nik that same in idKelurahan {} and tanggalLahir {}", idKelurahan, tanggalLahir);
		return pendudukMapper.selectNikSamaPenduduk(idKelurahan, tanggalLahir);
	}

	
}