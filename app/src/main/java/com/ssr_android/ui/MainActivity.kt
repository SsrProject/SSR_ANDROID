package com.ssr_android.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ssr_android.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var curPhotoPath : String
    lateinit var ImgUri : Uri

    private val disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestCameraPermission()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        observeEvent()

        binding.btnForImg.setOnClickListener(){
            openSomeActivityForResult()
        }
    }
    //파일생성 함수 불러오기 + 파일에다가 uri 넣기
    //나는 이미 uri를 선언햇어서 전역변수로 uri선언하고 거기다가 받은 uri 넣어서 fileProvider하면 될뜻
    fun testFile(){
        val photoFile : File? = try{
            createImageFile()
        }catch (ex: IOException){
            null
        }
        photoFile?.also {
            ImgUri = FileProvider.getUriForFile(
                this ,
                "com.example.myapplication.fileprovider",
                it
            )
        }
    }
    private fun createImageFile() : File {
        val timestamp :String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir : File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir)
            .apply { curPhotoPath = absolutePath }
    }//외부 파일 생성


    //rx
    private fun observeEvent() {
        viewModel.itemEventRelay
            .ofType(MainViewModel.AddSuccessEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Toast.makeText(this, it.isSuccess.toString(), Toast.LENGTH_SHORT).show()
            }
            .addTo(disposable)
    }
    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }



    //갤러리
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    ImgUri = uri
                }
                testFile()
                binding.image.setImageURI(ImgUri)
            }
        }
    private fun openSomeActivityForResult() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }
    private fun requestCameraPermission() {
        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(this, "ok $it", Toast.LENGTH_SHORT).show()
        }
        permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}