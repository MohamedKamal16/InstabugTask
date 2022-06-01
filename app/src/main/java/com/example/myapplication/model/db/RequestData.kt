package com.example.myapplication.model.db

import android.location.LocationRequest

//class to pass data to dbAdabter
class RequestData{
    var id:Int=0
    var requestType: String?
    var url: String?
    var body: String?

    constructor(requestType: String?, url: String?, body: String?) {
        this.requestType = requestType
        this.url = url
        this.body = body
    }

    constructor(id: Int, requestType: String, url: String, body: String) {
        this.id = id
        this.requestType = requestType
        this.url = url
        this.body = body
    }
}


