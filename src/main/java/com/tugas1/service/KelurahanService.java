package com.tugas1.service;

import java.util.List;

import com.tugas1.model.KelurahanModel;

public interface KelurahanService {
	KelurahanModel getKelurahan(int idKelurahan);
	
	List<KelurahanModel> getAllKelurahanByIdKecamatan(int idKecamatan);
}
