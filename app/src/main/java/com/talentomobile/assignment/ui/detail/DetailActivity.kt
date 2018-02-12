package com.talentomobile.assignment.ui.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.talentomobile.assignment.R
import com.talentomobile.assignment.TalentoApp
import com.talentomobile.assignment.dbdata.DataBaseProvider
import com.talentomobile.assignment.ui.main.di.components.DaggerDetailComponent
import com.talentomobile.assignment.ui.main.di.components.DetailComponent
import com.talentomobile.assignment.ui.main.di.modules.DetailActivityModule
import com.talentomobile.assignment.utils.toastError
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject
import com.google.android.gms.maps.model.MarkerOptions




@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity(), OnMapReadyCallback, DetailView {



    private lateinit var detailComponent: DetailComponent
    private var latLng:LatLng? = null

    private var mMap: GoogleMap? = null

    @Inject
    lateinit var detailPresenterImpl: DetailPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        injectDependecies()
        syncMap()

    }

    /**
     *  Inject dependencies of that ui part
     */
    private fun injectDependecies(){
        val appComponent = TalentoApp.appComponent

        detailComponent = DaggerDetailComponent.builder()
                .detailActivityModule(DetailActivityModule(this))
                .appComponent(appComponent)
                .build()
        detailComponent.inject(this)
    }

    /**
     *  Need sync map to detect onMapReady method
     */
    private fun syncMap(){

        progressBarWeather.visibility = View.VISIBLE
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    /**
     *  Map detected, know we can work with it
     */
    override fun onMapReady(googleMap: GoogleMap?) {

        progressBarWeather.visibility = View.INVISIBLE

        mMap = googleMap

        val placeDB = DataBaseProvider.searchSpecific(intent.getStringExtra("city"))

        if(placeDB != null) {
            detailPresenterImpl.obtainData(placeDB.south, placeDB.north, placeDB.east, placeDB.west)

            latLng = LatLng(placeDB.lat, placeDB.lng)


            val update = CameraUpdateFactory.newLatLng(latLng)
            val zoom = CameraUpdateFactory.zoomTo(8.5f)

            googleMap!!.moveCamera(update)
            googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            googleMap.animateCamera(zoom)



            googleMap.addCircle(CircleOptions()
                    .center(latLng)
                    .radius(30.0 * 1000)
                    .strokeWidth(2f)
                    .strokeColor(resources.getColor(R.color.white))
                    .fillColor(0x30FFFFFF))



            Log.d("LAT: " + placeDB.lat, "LON: " + placeDB.lng)

        }else{
            toastError(resources.getString(R.string.cannot))
        }

    }



    override fun hideLoading() {

        progressBarWeather.visibility = View.INVISIBLE
    }

    override fun showLoading() {

        progressBarWeather.visibility = View.VISIBLE
    }

    override fun onFailure(msg: String) {

        toastError(msg)
    }

    /**
     *  Draw temperature + Stations on Map
     */
    override fun drawData(temperature: Int,  coordinates: ArrayList<LatLng>?, names_station: ArrayList<String>?) {


        var progressBarStatus = -50
        var perform:Int = -50

        val textTemp = temperature.toString() + resources.getString(R.string.grades)
        temperatureTxt.text = textTemp

        when {
            temperature < -10 -> progressBarColor.progressDrawable = resources.getDrawable(R.drawable.color_progress2, null)
            temperature in -10..10 -> progressBarColor.progressDrawable = resources.getDrawable(R.drawable.color_progress, null)
            temperature > 10 -> progressBarColor.progressDrawable = resources.getDrawable(R.drawable.color_progress3, null)

        }

        Thread(Runnable {
            while (progressBarStatus < temperature) {

                try {
                    perform += 1
                    Thread.sleep(30)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progressBarStatus = perform
                progressBarColor.progress = progressBarStatus + 50
            }

        }).start()


        if (coordinates != null) {
            for ((flag, coords: LatLng) in coordinates.withIndex()) {
                mMap!!.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_custom)).position(coords).title(names_station!![flag]))
            }
        }
    }
}
