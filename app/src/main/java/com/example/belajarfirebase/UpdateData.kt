package com.example.belajarfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.belajarfirebase.databinding.ActivityUpdateDataBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateData : AppCompatActivity() {
    //Deklarasi Variable
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekNIM: String? = null
    private var cekNama: String? = null
    private var cekJurusan: String? = null
    private lateinit var binding: ActivityUpdateDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
       //supportActionBar!!.title = "Update Data"

//Mendapatkan Instance autentikasi dan Referensi dari Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        data //memanggil method "data"
        binding.update.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
//Mendapatkan Data Mahasiswa yang akan dicek
                cekNIM = binding.newNim.getText().toString()
                cekNama = binding.newNama.getText().toString()
                cekJurusan = binding.newJurusan.getText().toString()
//Mengecek agar tidak ada data yang kosong, saat proses update
                if (isEmpty(cekNIM!!) || isEmpty(cekNama!!) || isEmpty(cekJurusan!!))
                {
                    Toast.makeText(
                        this@UpdateData,

                        "Data tidak boleh ada yang kosong",
                        Toast.LENGTH_SHORT

                    ).show()
                } else {
                    /*Menjalankan proses update data.
                    Method Setter digunakan untuk mendapakan data baru yang diinputkan User.*/
                    val setMahasiswa = data_mahasiswa()

                    setMahasiswa.nim = binding.newNim.getText().toString()
                    setMahasiswa.nama = binding.newNama.getText().toString()
                    setMahasiswa.jurusan = binding.newJurusan.getText().toString()
                    updateMahasiswa(setMahasiswa)
                }
            }
        })
    }
    // Mengecek apakah ada data yang kosong, sebelum diupdate
    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }
    //Menampilkan data yang akan di update
    private val data: Unit
        private get() {
//Menampilkan data dari item yang dipilih sebelumnya
            val getNIM = intent.extras!!.getString("dataNIM")
            val getNama = intent.extras!!.getString("dataNama")
            val getJurusan = intent.extras!!.getString("dataJurusan")
            binding.newNim!!.setText(getNIM)
            binding.newNama!!.setText(getNama)
            binding.newJurusan!!.setText(getJurusan)
        }
    //Proses Update data yang sudah ditentukan
    private fun updateMahasiswa(mahasiswa: data_mahasiswa) {
        val userID = auth!!.uid
        val getKey = intent.extras!!.getString("getPrimaryKey")
        database!!.child("Admin")
            .child(userID!!)
            .child("Mahasiswa")
            .child(getKey!!)
            .setValue(mahasiswa)
            .addOnSuccessListener {
                binding.newNim!!.setText("")
                binding.newNama!!.setText("")
                binding.newJurusan!!.setText("")
                Toast.makeText(this@UpdateData, "Data Berhasil diubah",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}