package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider

import com.example.test.databinding.ActivityMainBinding
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

class MainActivity : AppCompatActivity() {

    private val key = "dict.1.1.20230331T191958Z.4928d8dc763a4001.141ac10c39fef65bc7b8d3509022e2e7eaa148e0"
    private lateinit var binding: ActivityMainBinding
    private lateinit var translator: Translator
    private var bool = false
    val options = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.RUSSIAN)
        .setTargetLanguage(TranslateLanguage.ENGLISH)
        .build()
    var conditions = DownloadConditions.Builder()
        .requireWifi()
        .build()
    private lateinit var viewModel: DictApiViewModel
    val repository = DictApiRepository()
    val factory = DictApiViewModelFactory(repository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory).get(DictApiViewModel::class.java)
        translator = Translation.getClient(options)

        binding.btn.setOnClickListener{
            translating()
        }
    }

    private fun translating(){

        translator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                bool = true
                translator.translate(binding.orig.text.toString())
                    .addOnSuccessListener { translatedText ->
                        binding.translate.text = translatedText
                        ydict(translatedText)
                    }
                    .addOnFailureListener { exception ->
                        binding.translate.text = exception.message
                    }
            }
            .addOnFailureListener { exception ->
                bool = false
            }
    }

    private fun ydict(translate: String){
        viewModel.getWord(key, "en-en", translate)
        viewModel.response.observe(this) {
            if(it.isSuccessful) {
                Log.d("dictt", "ок")
                Log.d("dictt", "слово ${it.body()!!.def[0].text}")
                Log.d("dictt", "транскрипция ${it.body()!!.def[0].ts}")
                Log.d("dictt", "другой перевод ${it.body()!!.def[0].tr[0].text}")
                Log.d("dictt", "синоним ${it.body()!!.def[0].tr[0].syn[0].text}")
            }
            else{
                Log.d("dictt", "ошибка")
            }
        }
    }
}