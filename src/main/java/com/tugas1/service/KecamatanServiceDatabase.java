package com.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tugas1.mapper.KecamatanMapper;
import com.tugas1.model.KecamatanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KecamatanServiceDatabase implements KecamatanService {
	@Autowired
	private KecamatanMapper kecamatanMapper;

	@Override
	public KecamatanModel getKecamatan(int idKecamatan) {
		log.info("get kecamatan with id {}", idKecamatan);
		return kecamatanMapper.selectKecamatan(idKecamatan);
	}

	@Override
	public List<KecamatanModel> getAllKecamatanByIdKota(int idKota) {
		log.info("get kecamatan with id kota {}", idKota);
		return kecamatanMapper.selectAllKecamatanByIdKota(idKota);
	}

	
}