package com.example.ecanteen.util

import android.util.Patterns
import java.util.regex.Pattern

fun validateEmail(email: String): RegisterValidation{
    if(email.isEmpty())
        return RegisterValidation.Failed("Email tidak boleh kosong")

    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("Email Format Salah")

    return RegisterValidation.Success

}
fun validatePassword(password: String): RegisterValidation{
    if(password.isEmpty())
        return RegisterValidation.Failed("Password tidak boleh kosong")
    if(password.length <6)
        return RegisterValidation.Failed("Password harus memiliki 6 huruf/angka")
    return RegisterValidation.Success
}