package com.example.belajarfirebase

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter
    ( private val listMahasiswa: ArrayList<data_mahasiswa>, context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() { private val context: Context

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val NIM: TextView
        val Nama: TextView
        val Jurusan: TextView
        val ListItem: LinearLayout
        init {//Menginisialisasi View yang terpasang pada layout RecyclerView kita
            NIM = itemView.findViewById(R.id.nimx)
            Nama = itemView.findViewById(R.id.namax)
            Jurusan = itemView.findViewById(R.id.jurusanx)
            ListItem = itemView.findViewById(R.id.list_item)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//Membuat View untuk Menyiapkan & Memasang Layout yang digunakan pada RecyclerView
        val V: View = LayoutInflater.from(parent.context).inflate(
            R.layout.view_design, parent, false)
        return ViewHolder(V)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
//Mengambil Nilai/Value pada RecyclerView berdasarkan Posisi Tertentu
        val NIM: String? = listMahasiswa[position].nim
        val Nama: String? = listMahasiswa[position].nama
        val Jurusan: String? = listMahasiswa[position].jurusan
//Memasukan Nilai/Value kedalam View (TextView: NIM, Nama, Jurusan)
        holder.NIM.text = "NIM: $NIM"
        holder.Nama.text = "Nama: $Nama"
        holder.Jurusan.text = "Jurusan: $Jurusan"

        holder.ListItem.setOnLongClickListener {
            holder.ListItem.setOnLongClickListener { view ->
                val action = arrayOf("Update", "Delete")
                val alert: AlertDialog.Builder = AlertDialog.Builder(view.context)
                alert.setItems(action) { _, i ->
                    when (i) {
                        0 -> {
                            /* Berpindah Activity pada halaman layout updateData dan mengambil data pada
                                    listMahasiswa, berdasarkan posisinya untuk dikirim pada activity selanjutnya */
                            val bundle = Bundle()
                            bundle.putString("dataNIM", listMahasiswa[position].nim)
                            bundle.putString("dataNama", listMahasiswa[position].nama)
                            bundle.putString("dataJurusan", listMahasiswa[position].jurusan)
                            bundle.putString("getPrimaryKey", listMahasiswa[position].key)
                            val intent = Intent(view.context, UpdateData::class.java)
                            intent.putExtras(bundle)
                            context.startActivity(intent)
                        }

                        1 -> {
                            //Menggunakan interface untuk mengirim data mahasiswa, yang akan dihapus
                            listener?.onDeleteData(listMahasiswa.get(position), position)
                        }
                    }
                }
                alert.create()
                var show: Any = alert.show()
                true

            }
            true
        }
    }
    //Membuat Interfece
    interface dataListener {
        fun onDeleteData(data: data_mahasiswa?, position: Int)
    }
    override fun getItemCount(): Int {
//Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
        return listMahasiswa.size
    }
    //Deklarasi objek dari Interfece
    var listener: dataListener? = null
    //Membuat Konstruktor, untuk menerima input dari Database
    init {
        this.context = context
        this.listener = context as MyListData //menambahkan baris ini saja
    }
}