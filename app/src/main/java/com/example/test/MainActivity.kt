package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.example.test.databinding.ActivityMainBinding
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var translator: Translator
    private var bool = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.RUSSIAN)
            .setTargetLanguage(TranslateLanguage.ENGLISH)
            .build()
        translator = Translation.getClient(options)

        var conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        binding.btn.setOnClickListener{

            translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener {
                    bool = true
                    translator.translate(binding.orig.text.toString())
                        .addOnSuccessListener { translatedText ->
                            binding.translate.text = translatedText
                        }
                        .addOnFailureListener { exception ->
                            binding.translate.text = exception.message
                        }
                    Log.d("tagg", "тру")
                }
                .addOnFailureListener { exception ->
                    bool = false
                    exception.message?.let { Log.d("tagg", it) }
                }
        }
    }
}