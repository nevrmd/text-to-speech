package com.nevrmd.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.nevrmd.texttospeech.databinding.ActivityMainBinding
import org.w3c.dom.Text
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var textToSpeech: TextToSpeech? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        textToSpeech = TextToSpeech(this, this)

        binding.btnSpell.setOnClickListener {
            if (binding.etTextToSpell.text.isEmpty()) {
                Toast.makeText(this, "Enter something!", Toast.LENGTH_SHORT).show()
            } else {
                speakOut(binding.etTextToSpell.text.toString())
            }
        }
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                Log.e("TTS", "The Language is not supported!")

        } else {
            Log.e("TTS", "Init Failed")
        }
    }

    public override fun onDestroy() {
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }

        super.onDestroy()
    }

    private fun speakOut(text: String) {
        textToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}