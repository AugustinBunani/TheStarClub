package com.augustin.thestarclub.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.augustin.thestarclub.model.UserData
import com.augustin.thestarclub.utilities.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class UserDataRepository @Inject constructor() {

    fun getUserData(context: Context): MutableLiveData<Resource<UserData>>
    {
        val _setUserData: MutableLiveData<Resource<UserData>> = MutableLiveData<Resource<UserData>>()
        val urlData = "https://run.mocky.io/v3/c1819867-9260-4d1e-b9e1-3a77372c83df"
        val gson = Gson()

        try{
            val queue = Volley.newRequestQueue(context)
            Log.d("Tag", "getUserDataResponse: $queue")

            val stringRequest = StringRequest(
                Request.Method.GET, urlData,
                { response ->
                    val responseValues: UserData = gson.fromJson(
                        response,
                        object : TypeToken<UserData>() {}.type
                    )
                    _setUserData.postValue(Resource.Success(responseValues))


                }
            ){error ->
                _setUserData.postValue(Resource.Error("An unkown error has occured: ${error.localizedMessage}"))
            }
            queue.add(stringRequest)
        } catch (e: Exception){
            _setUserData.postValue(Resource.Error("An unkown error has occured."))

        }
        return _setUserData

    }

}