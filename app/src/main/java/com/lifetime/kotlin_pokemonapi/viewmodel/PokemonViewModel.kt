@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.lifetime.kotlin_pokemonapi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.common.base.CaseFormat
import com.lifetime.kotlin_pokemonapi.activity.PokemonInformationActivity
import com.lifetime.kotlin_pokemonapi.constant.Constant.Companion.FAILURE
import com.lifetime.kotlin_pokemonapi.fragment.MoveFragment
import com.lifetime.kotlin_pokemonapi.fragment.StatFragment
import com.lifetime.kotlin_pokemonapi.model.*
import com.lifetime.kotlin_pokemonapi.retrofit.PokemonApi
import com.lifetime.kotlin_pokemonapi.retrofit.RetrofitClientInstance
import com.lifetime.kotlin_pokemonapi.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonViewModel : ViewModel() {
    private var pokemonApi: PokemonApi? = null

    var statsMutableLiveData: MutableLiveData<List<Stat>> = MutableLiveData()
    var descriptionMutableLiveData: MutableLiveData<String> = MutableLiveData()
    var urlImageMutableLiveData: MutableLiveData<String> = MutableLiveData()
    var movesMutableLiveData: MutableLiveData<List<String>> = MutableLiveData()
    var nameMutableLiveData: MutableLiveData<String> = MutableLiveData()

    var validResult: MutableLiveData<Boolean> = MutableLiveData()

    var countState = 0

    init {
        pokemonApi = RetrofitClientInstance.createService()
    }

    fun getStats(name: String) {
        pokemonApi?.getPokemon(name)?.enqueue(object : Callback<ResultForAll> {
            override fun onFailure(call: Call<ResultForAll>, t: Throwable) {
                Log.d(FAILURE, t.message)
            }

            override fun onResponse(call: Call<ResultForAll>, response: Response<ResultForAll>) {
                val resultForAllResponse: ResultForAll? = response.body()
                if (resultForAllResponse != null) {
                    statsMutableLiveData.value = resultForAllResponse.stats
                    validResult.value = true
                } else {
                    validResult.value = false
                }
            }

        })
    }

    fun getStatsInFragment(context: StatFragment, name: String) {
        countState++
        pokemonApi?.getPokemon(name)?.enqueue(object : Callback<ResultForAll> {
            override fun onFailure(call: Call<ResultForAll>, t: Throwable) {
                countState--
                context.checkFinishApi(countState)
                Log.d(FAILURE, t.message)
            }

            override fun onResponse(call: Call<ResultForAll>, response: Response<ResultForAll>) {
                val resultForAllResponse: ResultForAll? = response.body()
                if (resultForAllResponse != null) {
                    statsMutableLiveData.value = resultForAllResponse.stats
                    validResult.value = true
                } else {
                    validResult.value = false
                }
                countState--
                context.checkFinishApi(countState)
            }

        })
    }

    fun getDescription(context: PokemonInformationActivity,name: String) {
        countState++
        pokemonApi?.getPokemonDescription(name)?.enqueue(object : Callback<DescriptionApiResponse> {
            override fun onFailure(call: Call<DescriptionApiResponse>, t: Throwable) {
                countState--
                context.checkFinishApi(countState)
                Log.d(FAILURE, t.message)
            }

            override fun onResponse(call: Call<DescriptionApiResponse>, response: Response<DescriptionApiResponse>) {
                val descriptionApiResponse: DescriptionApiResponse? = response.body()
                val descriptions: List<Description>? = descriptionApiResponse?.descriptions
                descriptionMutableLiveData.value = descriptions?.get(1)?.description
                countState--



                context.checkFinishApi(countState)
            }
        })
    }

    fun getUrlImage(context: PokemonInformationActivity,name: String) {
        countState++
        pokemonApi?.getPokemonImage(name)?.enqueue(object : Callback<ImageResponse> {
            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                countState--
                context.checkFinishApi(countState)
                Log.d(FAILURE,t.message)
        }

            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                val imageResponse: ImageResponse? = response.body()
                var sprite: Sprite? = imageResponse?.sprite
                urlImageMutableLiveData.value = sprite?.linkUrl?:""
                countState--
                context.checkFinishApi(countState)
            }
        })
    }

    fun getMoves(context: MoveFragment,name: String){
        countState++
        pokemonApi?.getPokemon(name)?.enqueue(object : Callback<ResultForAll> {
            override fun onFailure(call: Call<ResultForAll>, t: Throwable) {
                Log.d(FAILURE,t.message)
                countState--
                context.checkFinishApi(countState)
            }

            override fun onResponse(call: Call<ResultForAll>, response: Response<ResultForAll>) {
                val resultForAllResponse: ResultForAll? = response.body()
                val moves: List<Move>? = resultForAllResponse?.moves
                val listMoveName : MutableList<String> = List(moves?.size!!){ String()}.toMutableList()
                for (i in 0 until moves.size){
                    val currentMoveName:String = moves[i].moveInfo.name
                    listMoveName[i] = Utils.splitCamelCase(CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL,currentMoveName))
                }
                movesMutableLiveData.value = listMoveName
                countState--
                context.checkFinishApi(countState)
            }
        })
    }

    fun getName(context: PokemonInformationActivity,name: String){
        countState++
        pokemonApi?.getPokemon(name)?.enqueue(object: Callback<ResultForAll> {
            override fun onFailure(call: Call<ResultForAll>, t: Throwable) {
                countState--
                context.checkFinishApi(countState)
                Log.d(FAILURE,t.message)
            }

            override fun onResponse(call: Call<ResultForAll>, response: Response<ResultForAll>) {
                val resultForAllResponse: ResultForAll? = response.body()
                val forms: List<Form>? = resultForAllResponse?.forms
                nameMutableLiveData.value = forms?.get(0)?.name
                countState--
                context.checkFinishApi(countState)
            }
        })
    }
}