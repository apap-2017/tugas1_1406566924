package com.tugas1.service;

import java.util.List;

import com.tugas1.model.KelurahanModel;

public interface KelurahanService {
	KelurahanModel getKelurahan(int idKelurahan);
	KelurahanModel getKelurahanByNama(String upperCase);
	
	List<KelurahanModel> getAllKelurahanByIdKecamatan(int idKecamatan);
}
