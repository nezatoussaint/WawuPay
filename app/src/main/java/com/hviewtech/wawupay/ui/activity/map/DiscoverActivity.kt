package com.hviewtech.wawupay.ui.activity.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.support.annotation.DrawableRes
import android.support.v4.app.ActivityCompat
import android.util.SparseArray
import android.view.View
import android.webkit.URLUtil
import android.widget.AdapterView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.hviewtech.wawupay.R
import com.hviewtech.wawupay.RC_PERMISSION_LOCATION
import com.hviewtech.wawupay.base.BaseMvpActivity
import com.hviewtech.wawupay.bean.remote.map.Category
import com.hviewtech.wawupay.bean.remote.map.MerchantPositionList
import com.hviewtech.wawupay.common.Constants
import com.hviewtech.wawupay.common.app.TransformationManagers
import com.hviewtech.wawupay.common.utils.ActivityUtils
import com.hviewtech.wawupay.common.utils.HawkExt
import com.hviewtech.wawupay.contract.map.DiscoverContract
import com.hviewtech.wawupay.presenter.map.DiscoverPresenter
import kotlinx.android.synthetic.main.act_discover.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.util.*
import javax.inject.Inject

/**
 * @author Eric
 * @version 1.0
 * @description
 */

class DiscoverActivity : BaseMvpActivity(), DiscoverContract.View, OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

  @Inject
  lateinit var mPresenter: DiscoverPresenter

  private var mMap: GoogleMap? = null
  //    private String[] caategories;
  private val markers = SparseArray<Marker>()
  private val categories = ArrayList<String>()
  private var selfMarker: Marker? = null
  private var categoryId: Int = 0


  override fun getLayoutId(): Int {
    return R.layout.act_discover
  }

  override fun initialize() {
    super.initialize()

    initLocation()

    val mapFragment = SupportMapFragment()
    ActivityUtils.addFragmentToActivity(supportFragmentManager, mapFragment, R.id.map)
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    mapFragment.getMapAsync(this)
    //GoogleMapOptions options = new GoogleMapOptions().liteMode(true);


    categories.add("All")
    //        caategories = getResources().getStringArray(R.array.maps_category);
    spinnerCategory.attachDataSource(categories)


    spinnerCategory.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
      override fun onNothingSelected(parent: AdapterView<*>?) {
      }

      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        showCategories(position)
      }

    })

    mPresenter.initCategories()


  }

  @AfterPermissionGranted(RC_PERMISSION_LOCATION)
  private fun initLocation(): Boolean {
    if (ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
      ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      EasyPermissions.requestPermissions(
        this, "Allow WawuPay to access your location in order to browse nearby shops.",
        RC_PERMISSION_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
      )
      return false
    }

    // Already have permission, do the thing
    return true
  }

  fun resizeMapIcons(@DrawableRes resId: Int, width: Int, height: Int): Bitmap {
    val imageBitmap = BitmapFactory.decodeResource(getResources(), resId)
    return Bitmap.createScaledBitmap(imageBitmap, width, height, false)
  }

  fun resizeMapIcons(bitmap: Bitmap, width: Int, height: Int): Bitmap {
    return Bitmap.createScaledBitmap(bitmap, width, height, false)
  }

  /**
   * Manipulates the map once available.
   * This callback is triggered when the map is ready to be used.
   * This is where we can add markers or lines, add listeners or move the camera. In this case,
   * we just add a marker near Sydney, Australia.
   * If Google Play services is not installed on the device, the user will be prompted to install
   * it inside the SupportMapFragment. This method will only be triggered once the user has
   * installed Google Play services and returned to the app.
   */
  @SuppressLint("MissingPermission")
  override fun onMapReady(googleMap: GoogleMap) {
    mMap = googleMap
    if (!initLocation()) {
      return
    }
    val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager ?: return
    if (!mMap!!.isMyLocationEnabled)
      mMap!!.isMyLocationEnabled = true
    var myLocation: Location? = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)

    if (myLocation == null) {
      val criteria = Criteria()
      criteria.accuracy = Criteria.ACCURACY_COARSE
      val provider = lm.getBestProvider(criteria, true)
      myLocation = lm.getLastKnownLocation(provider)
    }

    // 设置缩放级别
    if (myLocation != null) {
      val userLocation = LatLng(myLocation.latitude, myLocation.longitude)
      mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13f), 1500, null)
      mMap!!.setOnMyLocationChangeListener { location ->
        if (selfMarker != null) {
          val userLocation = LatLng(location.latitude, location.longitude)
          selfMarker!!.setPosition(userLocation)
        }
      }
      val info = HawkExt.info
      if (info != null) {

        val size = getResources().getDimensionPixelSize(R.dimen.dp_30)
        val options = RequestOptions()
          .centerCrop().placeholder(R.drawable.user_avatar).fallback(R.drawable.user_avatar)
          .transform(TransformationManagers.cropCircle())
          .override(size)
        if (!URLUtil.isValidUrl(info.profilePic)) {
          info.profilePic = Constants.Prefix.IMG + info.profilePic
        }
        Glide.with(mContext).asBitmap().load(info.profilePic).apply(options).into(object : SimpleTarget<Bitmap>() {
          override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            if (selfMarker != null) {
              selfMarker!!.remove()
            }
            selfMarker = googleMap.addMarker(
              MarkerOptions()
                .position(userLocation).title(null)
                .icon(BitmapDescriptorFactory.fromBitmap(resource))
            )
          }
        })
      }

      mPresenter.getMerchantPosition(userLocation.longitude, userLocation.latitude, 1, categoryId)
    }
    //        CameraUpdate zoom = CameraUpdateFactory.zoomTo(4);
    //        mMap.animateCamera(zoom);

    mMap!!.setOnCameraIdleListener(this)
    val uiSettings = mMap!!.uiSettings
    uiSettings.isMapToolbarEnabled = false
    uiSettings.setAllGesturesEnabled(true)
    //        uiSettings.setMyLocationButtonEnabled(false);

    // Add a marker in Sydney and move the camera
    //        LatLng sydney = new LatLng(-34, 151);
    //        MarkerOptions options = new MarkerOptions().position(sydney).title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
    //        mMap.addMarker(options);
    //        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    //        double lat = 0.0;
    //        double lng = 0.0;
    //        LatLng appointLoc = new LatLng(lat, lng);
    //
    //        // 移动地图到指定经度的位置
    //        googleMap.moveCamera(CameraUpdateFactory.newLatLng(appointLoc));

    //添加标记到指定经纬度
    //        googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Marker")
    //                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));

    /*// 点击标记点，默认点击弹出跳转google地图或导航选择，返回true则不弹出
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return true;
            }
        });

        // 单击
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });

        // 不允许手势缩放
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        //googleMap.getUiSettings().setMapToolbarEnabled(false); 禁用精简模式下右下角的工具栏

        // 不允许拖动地图
        googleMap.getUiSettings().setScrollGesturesEnabled(false);

        // 设置缩放级别
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);
        googleMap.animateCamera(zoom);*/
  }

  fun showCategories(position: Int) {
    // showError(caategories[position]);
    categoryId = position

    if (selfMarker != null) {
      val km = calculateVisibleRadius().toInt()
      val latLng = selfMarker!!.position
      mPresenter.getMerchantPosition(latLng.longitude, latLng.latitude, km, categoryId)
    }

  }

  fun showStars(view: View) {
    // showError("" + which);
  }

  fun exit(view: View) {
    onBackPressed()
  }

  override fun showMerchantList(result: MerchantPositionList) {
    val visibleRegion = mMap!!.projection.visibleRegion
    val bounds = visibleRegion.latLngBounds
    for (pos in 0..markers.size()) {
      val marker = markers[pos]
      val latLng = marker.position
      val visible = bounds.contains(latLng)
      marker.isVisible = visible
    }
    val positionList = result.merchantPositionList
    if (positionList == null || positionList!!.isEmpty()) {
      for (pos in 0..markers.size()) {
        val marker = markers[pos]
        marker.isVisible = false
      }
    }
    for (position in positionList!!) {
      val longitude = position.longitude
      val latitude = position.latitude
      val shopName = position.shopName
      val latLng = LatLng(latitude, longitude)

      //            marker.setTag(latLng.hashCode());
      val key = position.hashCode()
      if (markers.indexOfKey(key) != -1) {
        val marker = mMap!!.addMarker(
          MarkerOptions()
            .position(latLng).title(shopName)
            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
        )
        markers.put(key, marker)
      }

    }
  }

  override fun updateCategories(list: List<Category>?) {
    if (list != null) {
      for (category in list) {
        val categoryName = category.categoryName
        if (!categoryName.isNullOrBlank()) {
          categories.add(categoryName)
        }
      }
      spinnerCategory.attachDataSource(categories)
    }
  }

  fun calculateVisibleRadius(): Double {
    val distanceWidth = FloatArray(1)
    val visibleRegion = mMap!!.projection.visibleRegion
    val farRight = visibleRegion.farRight
    val farLeft = visibleRegion.farLeft
    val nearRight = visibleRegion.nearRight
    val nearLeft = visibleRegion.nearLeft
    //calculate the distance between left <-> right of map on screen
    Location.distanceBetween(
      (farLeft.latitude + nearLeft.latitude) / 2,
      farLeft.longitude,
      (farRight.latitude + nearRight.latitude) / 2,
      farRight.longitude,
      distanceWidth
    )
    // visible radius is / 2  and /1000 in Km:
    return (distanceWidth[0] / 2).toDouble()
  }

  fun calculateCenterLatLng(): LatLng {
    val point = Point()
    point.x = rlMap.getX().toInt() + rlMap.getWidth() / 2
    point.y = rlMap.getY().toInt() + rlMap.getHeight() / 2
    return mMap!!.projection.fromScreenLocation(point)
  }

  override fun onCameraIdle() {
    val km = calculateVisibleRadius().toInt()
    val latLng = calculateCenterLatLng()
    mPresenter.getMerchantPosition(latLng.longitude, latLng.latitude, km, categoryId)
  }


}
