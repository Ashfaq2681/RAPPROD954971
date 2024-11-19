package com.example.assignmentintern


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.hbb20.CountryCodePicker


class MainActivity : AppCompatActivity() {

    private lateinit var ccp: CountryCodePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = findViewById<EditText>(R.id.t1)
        val button = findViewById<Button>(R.id.b1)
        ccp = findViewById(R.id.ccp)
        ccp.registerCarrierNumberEditText(text)

        button.setOnClickListener {
            if( text.length() != 11) {
                Toast.makeText(this, "Enter phone Number without Country Code", Toast.LENGTH_SHORT).show()
            }else{
            val intent = Intent(this, OtpVerify::class.java)
            intent.putExtra("mobile", ccp.fullNumberWithPlus.replace(" ", ""))
            startActivity(intent)}
        }
    }
}
