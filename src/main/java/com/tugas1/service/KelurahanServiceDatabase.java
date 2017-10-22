package com.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tugas1.mapper.KelurahanMapper;
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

	@Override
	public List<KelurahanModel> getAllKelurahanByIdKecamatan(int idKecamatan) {
		log.info("get kelurahan with id kecamatan {}", idKecamatan);
		return kelurahanMapper.selectAllKelurahanByIdKecamatan(idKecamatan);
	}

	@Override
	public KelurahanModel getKelurahanByNama(String namaKelurahan) {
		log.info("get kelurahan with nama {}", namaKelurahan);
		return kelurahanMapper.selectKelurahanByNama(namaKelurahan);
	}

	
}