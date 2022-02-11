package com.example.projectdemo.network

class AppException : Exception {
    var errorMsg: String
    var errCode: Int = 0
    var errorLog: String?
    var throwable: Throwable? = null
    constructor(errCode:Int,error:String?,errorLog:String?="",throwable:Throwable?=null):super(error)
    {
        this.errorMsg=error ?:"请求失败,请稍后再试"
        this.errCode=errCode
        this.errorLog=errorLog?:this.errorMsg
        this.throwable=throwable
    }
    constructor(error:Error,e:Throwable?)
    {
        errCode=error.getKey()
        errorMsg=error.getValue()
        errorLog=e?.message
        throwable=e
    }

}