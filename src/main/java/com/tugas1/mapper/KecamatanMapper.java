package com.tugas1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.tugas1.model.KecamatanModel;

@Mapper
public interface KecamatanMapper {
	@Select("SELECT * FROM kecamatan WHERE id = #{idKecamatan}")
	@Results(value = {
			@Result(property="idKota", column="id_kota"),
			@Result(property="namaKecamatan", column="nama_kecamatan"),
			@Result(property="kodeKecamatan", column="kode_kecamatan"),
			@Result(property="id", column="id")
	})
	KecamatanModel selectKecamatan(@Param(value="idKecamatan") int idKecamatan);
	
	@Select("SELECT id, nama_kecamatan FROM kecamatan WHERE id_kota = #{idKota} ORDER BY nama_kecamatan")
	@Results(value= {
			@Result(property="id", column="id"),
			@Result(property="namaKecamatan", column="nama_kecamatan")
	})
	List<KecamatanModel> selectAllKecamatanByIdKota(@Param(value="idKota") int idKota);
}
