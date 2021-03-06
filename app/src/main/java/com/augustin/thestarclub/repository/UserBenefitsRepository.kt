package com.augustin.thestarclub.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.augustin.thestarclub.model.Benefit
import com.augustin.thestarclub.model.BenefitX
import com.augustin.thestarclub.utilities.Resource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.scopes.ActivityScoped
import org.json.JSONArray
import javax.inject.Inject


@ActivityScoped
class UserBenefitsRepository @Inject constructor() {

    fun getBenefitsData(context: Context): MutableLiveData<Resource<Benefit>>
    {
        val _setUserBenefits: MutableLiveData<Resource<Benefit>> = MutableLiveData<Resource<Benefit>>()
        val urlBenefits = "https://run.mocky.io/v3/6bd03c3d-8b70-40fe-b26c-36bfc03296ff"
        val gson = Gson()

        try{
            val queue = Volley.newRequestQueue(context)
            Log.d("Tag", "getUserBenefitsResponse: $queue")

            val stringRequest = StringRequest(
                Request.Method.GET, urlBenefits,
                { response ->

                    val responsObjectRequest: Benefit = gson.fromJson(
                        response,
                        object : TypeToken<Benefit>() {}.type
                    )
                    _setUserBenefits.postValue(Resource.Success(responsObjectRequest))
                }
            ){error ->
                _setUserBenefits.postValue(Resource.Error("An unkown error has occured: ${error.localizedMessage}"))
            }
            queue.add(stringRequest)
        } catch (e: Exception){
            _setUserBenefits.postValue(Resource.Error("An unkown error has occured."))

        }
        return _setUserBenefits

    }

}