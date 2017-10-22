package com.tugas1.service;

import java.util.List;

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
	public KeluargaModel getKeluargaById(int idKeluarga) {
		log.info("get keluarga with id {}", idKeluarga);
		return keluargaMapper.selectKeluargaById(idKeluarga);
	}

	@Override
	public KeluargaModel getKeluargaByNKK(String nomorKK) {
		log.info("get keluarga with nomor kk {}", nomorKK);
		return keluargaMapper.selectKeluargaByNKK(nomorKK);
	}

	@Override
	public List<KeluargaModel> getAllKeluarga() {
		log.info("get all keluarga");
		return keluargaMapper.selectAllKeluarga();
	}

	@Override
	public String getNkkSamaKeluarga(String nomorKK) {
		log.info("get the lastest nik that same with {} in the first 12 characters", nomorKK);
		return keluargaMapper.selectNkkSamaKeluarga(nomorKK);
	}

	@Override
	public int getIdKeluargaTerakhir() {
		log.info("get id keluarga terakhir");
		return keluargaMapper.selectIdKeluargaTerakhir();
	}

	
}