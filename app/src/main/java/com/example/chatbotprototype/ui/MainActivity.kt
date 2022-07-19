package com.example.chatbotprototype.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbotprototype.Data.Message
import com.example.chatbotprototype.R
import com.example.chatbotprototype.Utils.BotResponse
import com.example.chatbotprototype.Utils.Constants.OPEN_GOOGLE
import com.example.chatbotprototype.Utils.Constants.OPEN_SEARCH
import com.example.chatbotprototype.Utils.Constants.OPEN_YOUTUBE
import com.example.chatbotprototype.Utils.Constants.RECEIVED_ID
import com.example.chatbotprototype.Utils.Constants.SEND_ID
import com.example.chatbotprototype.Utils.Time
import com.example.chatbotprototype.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.message_item.*
import kotlinx.coroutines.*
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    var messagesList = mutableListOf<Message>()
    private lateinit var adapter: MessagingAdapter
    lateinit var textToSpeech: TextToSpeech
    lateinit var activityResultLauncher:ActivityResultLauncher<Intent>
    private val botList =" P.H.U.P.H.S. "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        textToSpeech= TextToSpeech(this@MainActivity,this@MainActivity)



            microPhone.setOnClickListener{


                    microPhone.animate().apply {
                        duration=1000
                        rotationYBy(360f)
                    }.start()

                    val i =Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                    i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                    i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Please Speak!")
                    activityResultLauncher.launch(i)



            }

        recylcerView()
        clickEvents()
        et_message.setOnKeyListener(View.OnKeyListener{v,keycode,event->
            if(keycode==KeyEvent.KEYCODE_ENTER&&event.action==KeyEvent.ACTION_UP){
                val message = et_message.text.toString()
                val timeStamp = Time.timeStamp()
                if (message.isNotEmpty()) {
                    et_message.setText("")
                    adapter.insertMessage(Message(message, SEND_ID, timeStamp))
                    rv_messages.scrollToPosition(adapter.itemCount - 1)

                    botResponse(message)
                }
                return@OnKeyListener true

            }
            false
        })
        val random = (0..3).random()
        customMessage("Hello! Today You Are Speaking With $botList\n How May I Help You? ")
        activityResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it!!.resultCode== RESULT_OK&&it!!.data!=null){
                val speechText=it!!.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<Editable>
                et_message.text=speechText[0]
                val message = et_message.text.toString()
                val timeStamp = Time.timeStamp()
                if (message.isNotEmpty()) {
                    et_message.setText("")
                    adapter.insertMessage(Message(message, SEND_ID, timeStamp))
                    rv_messages.scrollToPosition(adapter.itemCount - 1)

                    botResponse(message)
                }

            }
        }
    }

    fun rereadMessage(){
        val message = et_message.text.toString()
        textToSpeech.speak(message,TextToSpeech.QUEUE_FLUSH,null,null)
    }




    private fun recylcerView() {
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun sendMessage() {

        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()
        if (message.isNotEmpty()) {
            et_message.setText("")
            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val response = BotResponse.basicResponses(message)
                textToSpeech.speak(response,TextToSpeech.QUEUE_FLUSH,null,null)
                adapter.insertMessage(Message(response, RECEIVED_ID, timeStamp))
                rv_messages.scrollToPosition(adapter.itemCount - 1)

                when (response) {
                    OPEN_GOOGLE -> {
                        val web="http://www.google.com"
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data=Uri.parse(web)

                        textToSpeech.speak("Opening Google",TextToSpeech.QUEUE_FLUSH,null,null)
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfter("search")
                        site.data = Uri.parse("https://www.google.com/search?q=$searchTerm")

                        textToSpeech.speak("Searching",TextToSpeech.QUEUE_FLUSH,null,null)
                        startActivity(site)
                    }
                    OPEN_YOUTUBE->{
                        val site=Intent(Intent.ACTION_VIEW)
                        val searchTerm:String?=message.substringAfter("play")
                        site.data=Uri.parse("https://www.youtube.com/results?search_query=$searchTerm")
                        textToSpeech.speak("Opening Youtube",TextToSpeech.QUEUE_FLUSH,null,null)
                        startActivity(site)

                    }                    }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {

                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
    private fun clickEvents(){
        btn_send.setOnClickListener{
            btn_send.animate().apply {
                duration=1000
                rotationYBy(360f)
            }.start()
            sendMessage()
        }
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main) {

                    rv_messages.scrollToPosition(adapter.itemCount - 1)


                }}
        }
    }
        private fun customMessage(message: String) {
            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main) {
                    val timeStamp = Time.timeStamp()
                    textToSpeech.speak("Hello! Today You Are Speaking With phuupss\n" +
                            " How May I Help You",TextToSpeech.QUEUE_FLUSH,null,null)
                    adapter.insertMessage(Message(message, RECEIVED_ID, timeStamp))
                    rv_messages.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
        val res= textToSpeech.setLanguage(Locale.getDefault())
            if(res==TextToSpeech.LANG_MISSING_DATA||res==TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this,"The Language is not supported",Toast.LENGTH_SHORT).show()
            }
            else{
                et_message.isEnabled=true
            }
        }
        else{
            Toast.makeText(this,"The Bot is unable to Speak",Toast.LENGTH_SHORT).show()
        }
    }

}
