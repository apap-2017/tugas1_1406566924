package com.tugas1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.tugas1.model.KelurahanModel;

@Mapper
public interface KelurahanMapper {
	@Select("SELECT * FROM kelurahan WHERE id = #{idKelurahan}")
	@Results(value = {
			@Result(property="idKecamatan", column="id_kecamatan"),
			@Result(property="kodeKelurahan", column="kode_kelurahan"),
			@Result(property="namaKelurahan", column="nama_kelurahan"),
			@Result(property="kodePos", column="kode_pos"),
			@Result(property="id", column="id")
	})
	KelurahanModel selectKelurahan(@Param(value="idKelurahan") int idKelurahan);
	
	@Select("SELECT id, nama_kelurahan FROM kelurahan WHERE id_kecamatan = #{idKecamatan}")
	@Results(value= {
			@Result(property="id", column="id"),
			@Result(property="namaKelurahan", column="nama_kelurahan")
	})
	List<KelurahanModel> selectAllKelurahanByIdKecamatan(@Param(value="idKecamatan") int idKecamatan);
}
