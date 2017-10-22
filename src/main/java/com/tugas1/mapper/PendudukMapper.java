package com.tugas1.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tugas1.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	@Select("SELECT * FROM penduduk WHERE nik = #{nik} LIMIT 1")
	@Results(value = { @Result(property = "id", column = "id"), @Result(property = "nik", column = "nik"),
			@Result(property = "nama", column = "nama"), @Result(property = "tempatLahir", column = "tempat_lahir"),
			@Result(property = "tanggalLahir", column = "tanggal_lahir"),
			@Result(property = "jenisKelamin", column = "jenis_kelamin"),
			@Result(property = "isWni", column = "is_wni"), @Result(property = "idKeluarga", column = "id_keluarga"),
			@Result(property = "agama", column = "agama"), @Result(property = "pekerjaan", column = "pekerjaan"),
			@Result(property = "statusPerkawinan", column = "status_perkawinan"),
			@Result(property = "statusDalamKeluarga", column = "status_dalam_keluarga"),
			@Result(property = "golonganDarah", column = "golongan_darah"),
			@Result(property = "isWafat", column = "is_wafat") })
	PendudukModel selectPenduduk(@Param(value = "nik") String nik);

	@Insert("INSERT INTO penduduk (id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, " +
	"pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) VALUES (#{id}, #{nik}, #{nama}, #{tempatLahir}, " +
	"#{tanggalLahir}, #{jenisKelamin}, #{isWni}, #{idKeluarga}, #{agama}, #{pekerjaan}, #{statusPerkawinan}, " +
	"#{statusDalamKeluarga}, #{golonganDarah}, #{isWafat})")
	void insertPenduduk(PendudukModel penduduk);
	
	@Select("SELECT nik FROM penduduk WHERE nik LIKE '${nik}%' ORDER BY id DESC LIMIT 1")
	String selectNikSamaPenduduk(@Param(value="nik") String nik);
	
	@Select("SELECT id FROM penduduk ORDER BY id DESC LIMIT 1")
	int selectIdPendudukTerakhir();
	
	@Update("UPDATE penduduk SET nik=#{nik}, nama=#{nama}, tempat_lahir=#{tempatLahir}, tanggal_lahir=#{tanggalLahir}, jenis_kelamin=#{jenisKelamin}, " +
	"is_wni=#{isWni}, id_keluarga=#{idKeluarga}, agama=#{agama}, pekerjaan=#{pekerjaan}, status_perkawinan=#{statusPerkawinan}," +
	" status_dalam_keluarga=#{statusDalamKeluarga}, golongan_darah=#{golonganDarah}, is_wafat=#{isWafat} WHERE id=#{id}")
	void updatePenduduk(PendudukModel penduduk);
}
