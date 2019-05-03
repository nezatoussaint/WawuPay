package com.hviewtech.wawupay.bean.remote.common

/**
 * @author su
 * @date 2018/3/18
 * @description
 */

data class ProvinceCitys(

    var list: List<Address>? = null
) {
    data class Address(
        var name: String? = null,
        var provinceId: Int = 0,
        var addressDistrictVOList: List<AddressDistrictVOList>? = null
    ) {

        data class AddressDistrictVOList(
            var districtId: Int = 0,
            var districtName: String? = null,
            var provinceId: Int = 0,
            var addressSectorVOList: List<AddressSectorVOList>? = null
        ) {
            data class AddressSectorVOList(
                var districtId: Int = 0,
                var sectorId: Int = 0,
                var sectorName: String? = null
            )
        }
    }
}
