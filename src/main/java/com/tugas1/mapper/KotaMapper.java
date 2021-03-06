package com.tugas1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.tugas1.model.KeluargaModel;
import com.tugas1.model.KotaModel;

@Mapper
public interface KotaMapper {
	@Select("SELECT * FROM kota WHERE id = #{idKota}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="kodeKota", column="kode_kota"),
			@Result(property="namaKota", column="nama_kota")
	})
	KotaModel selectKota(@Param(value="idKota") int idKota);
	
	@Select("SELECT id, nama_kota FROM kota ORDER BY nama_kota")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="namaKota", column="nama_kota")
	})
	List<KotaModel> selectAllKota();
}
