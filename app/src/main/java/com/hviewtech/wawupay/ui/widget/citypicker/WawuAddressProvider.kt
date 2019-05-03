package com.hviewtech.wawupay.ui.widget.citypicker

import com.hviewtech.wawupay.bean.remote.common.ProvinceCitys
import xyz.icoder.citypicker.AddressProvider
import xyz.icoder.citypicker.model.City
import xyz.icoder.citypicker.model.County
import xyz.icoder.citypicker.model.Province
import xyz.icoder.citypicker.model.Street
import java.util.*


class WawuAddressProvider : AddressProvider {

  private val addresses = ArrayList<ProvinceCitys.Address>()

  constructor(addresses: ProvinceCitys?) {
    this.addresses.clear()
    val list = addresses?.list
    list ?: return
    this.addresses.addAll(list)
  }

  override fun provideProvinces(addressReceiver: AddressProvider.AddressReceiver<Province>) {
    var province: Province
    val provinces = ArrayList<Province>()
    for ((name) in addresses) {
      province = Province()
      province.name = name
      provinces.add(province)
    }
    addressReceiver.send(provinces)
  }

  override fun provideCitiesWith(provinceId: Int, addressReceiver: AddressProvider.AddressReceiver<City>) {

    var city: City
    val cities = ArrayList<City>()
    for ((_, districtName) in addresses[provinceId].addressDistrictVOList!!) {
      city = City()
      city.name = districtName
      cities.add(city)
    }

    addressReceiver.send(cities)
  }

  override fun provideCountiesWith(
    provinceIndex: Int,
    cityIndex: Int,
    addressReceiver: AddressProvider.AddressReceiver<County>
  ) {
    var county: County
    val counties = ArrayList<County>()
    for (addressDistrictVOList in addresses[provinceIndex].addressDistrictVOList!![cityIndex].addressSectorVOList!!) {
      county = County()
      county.name = addressDistrictVOList.sectorName
      counties.add(county)
    }
    addressReceiver.send(counties)
  }

  override fun provideStreetsWith(
    provinceIndex: Int,
    cityIndex: Int,
    countyIndex: Int,
    addressReceiver: AddressProvider.AddressReceiver<Street>
  ) {
    addressReceiver.send(ArrayList())
  }
}
