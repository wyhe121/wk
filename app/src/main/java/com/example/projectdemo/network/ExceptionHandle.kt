package com.example.projectdemo.network

import android.util.MalformedJsonException
import com.google.gson.JsonParseException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLException

object ExceptionHandle {
    fun handleException(e:Throwable):AppException{
        val ex:AppException
        e?.let {
            when(it)
            {
                  is HttpException ->{
                      ex= AppException(Error.NETWORK_ERROR,e)
                         return ex
                  }
                is JsonParseException,is JSONException,is ParseException,is MalformedJsonException->{
                    ex= AppException(Error.PARSE_ERROR,e)
                    return ex
                }
                is ConnectException->{
                    ex= AppException(Error.NETWORK_ERROR,e)
                    return ex
                }
                is javax.net.ssl.SSLException->{
                    ex= AppException(Error.SSL_ERROR,e)
                    return ex
                }
                is ConnectTimeoutException,is UnknownHostException,is SocketTimeoutException->{
                    ex= AppException(Error.TIMEOUT_ERROR,e)
                    return ex
                }
                is AppException->return it
                else->{
                    ex= AppException(Error.UNKNOWN,e)
                    return ex
                }
            }
        }
        ex= AppException(Error.UNKNOWN,e)
        return ex
    }
}