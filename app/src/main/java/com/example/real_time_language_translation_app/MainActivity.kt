package com.example.real_time_language_translation_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.real_time_language_translation_app.databinding.ActivityMainBinding
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var items= arrayOf("English","Hindi","Bengali","Gujarati","Tamil","Telugu")
    private var conditions = DownloadConditions.Builder()
        .requireWifi()
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemsAdapter: ArrayAdapter<String> =ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)

        binding.languageFrom.setAdapter(itemsAdapter)
        binding.languageTo.setAdapter(itemsAdapter)

        binding.translateBtn.setOnClickListener {
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(selectFrom())
                .setTargetLanguage(selectTo())
                .build()

            val englishGermanTranslator = Translation.getClient(options)

            englishGermanTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener {

                    englishGermanTranslator.translate(binding.inputTxt.text.toString())
                        .addOnSuccessListener { translatedText ->

                            binding.outputTxt.text=translatedText

                        }
                        .addOnFailureListener { exception ->

                            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()

                        }


                }
                .addOnFailureListener { exception ->

                    Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()

                }


        }
    }
    private fun selectFrom(): String {

        return when(binding.languageFrom.text.toString()){

            ""-> TranslateLanguage.ENGLISH

            "English"->TranslateLanguage.ENGLISH

            "Hindi"->TranslateLanguage.HINDI

            "Bengali"->TranslateLanguage.BENGALI

            "Gujarati"->TranslateLanguage.GUJARATI

            "Tamil"->TranslateLanguage.TAMIL

            "Telugu"->TranslateLanguage.TELUGU

            else->TranslateLanguage.ENGLISH

        }


    }

    private fun selectTo(): String {

        return when(binding.languageTo.text.toString()){

            ""-> TranslateLanguage.HINDI

            "English"->TranslateLanguage.ENGLISH

            "Hindi"->TranslateLanguage.HINDI

            "Bengali"->TranslateLanguage.BENGALI

            "Gujarati"->TranslateLanguage.GUJARATI

            "Tamil"->TranslateLanguage.TAMIL

            "Telugu"->TranslateLanguage.TELUGU

            else->TranslateLanguage.HINDI

        }


    }
}