package com.talentomobile.assignment.ui.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import com.talentomobile.assignment.R
import com.talentomobile.assignment.TalentoApp
import com.talentomobile.assignment.adapter.PlaceAdapter
import com.talentomobile.assignment.dbdata.db.PlaceDB
import com.talentomobile.assignment.service.DataBaseService
import com.talentomobile.assignment.ui.detail.DetailActivity
import com.talentomobile.assignment.ui.main.di.components.DaggerMainComponent
import com.talentomobile.assignment.ui.main.di.components.MainComponent
import com.talentomobile.assignment.ui.main.di.modules.MainModule
import com.talentomobile.assignment.utils.goToActivity
import com.talentomobile.assignment.utils.hideKeyboard
import com.talentomobile.assignment.utils.toastError
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() , MainView{


    private lateinit var mainComponent: MainComponent

    @Inject
    lateinit var mainPresenter: MainPresenterImpl

    private var placeAdapter: PlaceAdapter? = null
    private var places: List<PlaceDB>? = null
    var dataBaseService: DataBaseService? = null
    var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependecies()
        setupRecyclerView(rvPlaces)
        listenSearch()

    }

    /**
     *  Inject dependecies
     */
    private fun injectDependecies(){

        val appComponent = TalentoApp.appComponent
        mainComponent = DaggerMainComponent.builder()
                .mainModule(MainModule(this))
                .appComponent(appComponent)
                .build()
        mainComponent.inject(this)
    }

    override fun showLoading() { progressBar.visibility = View.VISIBLE }

    override fun hideLoading() { progressBar.visibility = View.INVISIBLE }

    override fun onFailure(msg: String) {
        toastError(msg)
    }

    /**
     *  Move forward
     */
    override fun goNext(city:String) {

        hideKeyboard()
        goToActivity<DetailActivity> { putExtra("city", city) }
    }

    /**
     *  Fill recyclerview with items
     */
    override fun showStuff(places : List<PlaceDB>) {

        placeAdapter = PlaceAdapter( places, this, this)
        rvPlaces.adapter = placeAdapter
    }

    /**
     *  Layout manager for RecyclerView
     */
    private fun setupRecyclerView(recyclerView: RecyclerView) {

        rvPlaces.isNestedScrollingEnabled

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

    }

    private fun configVIew(){
        if(places != null ){
            if(places!!.isEmpty()){
                arrow.visibility = View.VISIBLE
                searchTxt.visibility = View.VISIBLE
                arrow.setColorFilter(Color.WHITE)
            }else{
                arrow.visibility = View.INVISIBLE
                searchTxt.visibility = View.INVISIBLE
                arrow.setColorFilter(Color.WHITE)
            }

        }

    }

    /**
     *  Search city
     */
    private fun listenSearch(){

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(city: String): Boolean {
                mainPresenter.getCity(city)
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {

                return true
            }
        })
    }
    
    


    /**
     *  Binding the service
     */

    private val myConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName,
                                        service: IBinder) {
            val binder = service as DataBaseService.LocalService
            dataBaseService = binder.getService()
            isBound = true

            places = dataBaseService?.getTeams()
            configVIew()
            showStuff(places!!)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, DataBaseService::class.java)
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(myConnection)
        }
    }

    override fun onResume() {
        super.onResume()
        configVIew()
    }
}
