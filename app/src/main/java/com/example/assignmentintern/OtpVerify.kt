package com.example.assignmentintern

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

    class OtpVerify : AppCompatActivity() {

        private lateinit var phoneNumber: String
        private var verificationId: String? = null
        private lateinit var mAuth: FirebaseAuth

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_otp_verify)

            val otpField1 = findViewById<EditText>(R.id.otpField1)
            val otpField2 = findViewById<EditText>(R.id.otpField2)
            val otpField3 = findViewById<EditText>(R.id.otpField3)
            val otpField4 = findViewById<EditText>(R.id.otpField4)
            val otpField5 = findViewById<EditText>(R.id.otpField5)
            val otpField6 = findViewById<EditText>(R.id.otpField6)
            val verifyButton = findViewById<Button>(R.id.verifyButton)

            mAuth = FirebaseAuth.getInstance()

            // Retrieve the phone number from the Intent
            phoneNumber = intent.getStringExtra("mobile").toString()

            // Initiate OTP verification
            sendOtpToPhoneNumber()

            // Set up OTP focus movement
            setupOtpFocusMovement(otpField1, otpField2, otpField3, otpField4, otpField5, otpField6)

            verifyButton.setOnClickListener {
                val otp = otpField1.text.toString() + otpField2.text.toString() +
                        otpField3.text.toString() + otpField4.text.toString() +
                        otpField5.text.toString() + otpField6.text.toString()

                if (otp.length == 6) {
                    val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
                    signInWithPhoneAuthCredential(credential)
                } else {
                    Toast.makeText(this, "Please enter a valid 6-digit OTP", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun setupOtpFocusMovement(vararg otpFields: EditText) {
            for (i in otpFields.indices) {
                otpFields[i].addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        // If the current field is not empty and it’s not the last field, move to the next one
                        if (!s.isNullOrEmpty() && i < otpFields.size - 1) {
                            otpFields[i + 1].requestFocus()
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })
            }
        }

        private fun sendOtpToPhoneNumber() {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,         // Phone number to verify
                60,                  // Timeout duration
                TimeUnit.SECONDS,    // Unit of timeout
                this,                // Activity (for callback binding)
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onCodeSent(
                        verificationId: String,
                        forceResendingToken: PhoneAuthProvider.ForceResendingToken
                    ) {
                        this@OtpVerify.verificationId = verificationId
                    }

                    override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential)
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                    }
                })
        }

        private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
            mAuth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "OTP is matched", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, "OTP verification failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }







