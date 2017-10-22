package com.tugas1.service;

import java.util.List;

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
	public String getNikSamaPenduduk(String nik) {
		log.info("get the lastest nik that same with {} in the first 12 characters", nik);
		return pendudukMapper.selectNikSamaPenduduk(nik);
	}

	@Override
	public int getIdPendudukTerakhir() {
		log.info("get id penduduk terakhir");
		return pendudukMapper.selectIdPendudukTerakhir();
	}

	@Override
	public void editPenduduk(PendudukModel penduduk) {
		log.info("edit penduduk with id {} and nik {}", penduduk.getId(), penduduk.getNik());
		pendudukMapper.updatePenduduk(penduduk);
	}

	@Override
	public List<PendudukModel> getAllPendudukByIdKelurahan(int idKelurahan) {
		log.info("get all penduduk in id kelurahan {}", idKelurahan);
		return pendudukMapper.selectAllPendudukByIdKelurahan(idKelurahan);
	}

	@Override
	public PendudukModel getPendudukTermudaByIdKelurahan(int idKelurahan) {
		log.info("get penduduk termuda in id kelurahan {}", idKelurahan);
		return pendudukMapper.selectPendudukTermudaByIdKelurahan(idKelurahan);
	}

	@Override
	public PendudukModel getPendudukTerturaByIdKelurahan(int idKelurahan) {
		log.info("get penduduk tertua in id kelurahan {}", idKelurahan);
		return pendudukMapper.selectPendudukTertuaByIdKelurahan(idKelurahan);
	}

	
}