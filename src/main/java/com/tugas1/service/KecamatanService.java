package com.tugas1.service;

import java.util.List;

import com.tugas1.model.KecamatanModel;

public interface KecamatanService {
	KecamatanModel getKecamatan(int idKecamatan);
	
	List<KecamatanModel> getAllKecamatanByIdKota(int idKota);
}
