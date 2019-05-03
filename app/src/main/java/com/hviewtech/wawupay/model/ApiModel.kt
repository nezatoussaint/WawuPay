package com.hviewtech.wawupay.model

import com.hviewtech.wawupay.bean.remote.BaseResult
import com.hviewtech.wawupay.bean.remote.account.Login
import com.hviewtech.wawupay.bean.remote.account.Register
import com.hviewtech.wawupay.bean.remote.account.VerCode
import com.hviewtech.wawupay.bean.remote.common.ProvinceCitys
import com.hviewtech.wawupay.bean.remote.map.CategoryList
import com.hviewtech.wawupay.bean.remote.wallet.BankCardList
import io.reactivex.Flowable

/**
 * @author Eric
 * @version V1.0
 * @Description:
 * @date 2018/3/5
 */

interface ApiModel {

  fun postUserRegister(
    nickname: String?,
    firstName: String?,
    lastName: String?,
    email: String?,
    phone: String?,
    loginPassword: String?
  ): Flowable<BaseResult<Register?>>

  fun postGetProviceCitys(): Flowable<BaseResult<ProvinceCitys?>>

  fun postSendVerCode(
    phone: String?
  ): Flowable<BaseResult<VerCode?>>


  fun postVerificationPhone(
    phone: String?,
    code: String?
  ): Flowable<BaseResult<Any?>>

  /**
   * @param phone        手机号
   * @param verCode      验证码
   * @param password     密码
   * @param passwordType 密码类型(1-登陆密码 2-支付密码)
   * @param customerType 对象类型(1-用户 2-商户)
   * @return
   */
  fun postModifyPassword(
    phone: String?,
    verCode: String?,
    password: String?,
    passwordType: Int,
    customerType: Int
  ): Flowable<BaseResult<Any?>>

  /**
   * @param accountNum 账号
   * @param validate   密码
   * @return
   */
  fun postUserLogin(
    accountNum: String?,
    validate: String?
  ): Flowable<BaseResult<Login?>>

  fun postUserLoginByToken(
    token: String
  ): Flowable<BaseResult<Login?>>

  /**
   * 判断用户是否已进行实名认证
   *
   * @param num 用户编号
   * @return
   */
  fun postUserIsRealRegister(
    num: String?
  ): Flowable<BaseResult<Any?>>

  /**
   * @param num        用户编号
   * @param idType     证件类型(1,身份证 , 2,护照)
   * @param idNo       证件号码
   * @param realName   真实姓名
   * @param idFrontPic 证件正面
   * @param idBackPic  证件背面
   * @return
   */
  fun postUserCertificateUpload(
    num: String?,
    idType: Int,
    idNo: String?,
    realName: String?,
    idFrontPic: String?,
    idBackPic: String?
  ): Flowable<BaseResult<Any?>>


  /**
   * @param userType 用户类型(1-用户; 2-商户;3-API商户)
   * @param num      关联用户编号 (根据用户类型传用户或者商户的编号)
   * @param realName 真实姓名
   * @param cardNo   卡号
   * @param bankName 银行名
   * @return
   */
  fun postAddBankCard(
    userType: Int,
    num: String?,
    realName: String?,
    bankName: String?,
    cardNo: String?
  ): Flowable<BaseResult<Any?>>

  /**
   * 银行卡ID
   *
   * @param bankCardId
   * @return
   */
  fun postDelBankCard(
    bankCardId: Int
  ): Flowable<BaseResult<Any?>>

  fun postBankCardList(
    userType: Int,
    num: String?
  ): Flowable<BaseResult<BankCardList?>>

  fun postPayPasswordIsExist(
    userType: Int,
    num: String?
  ): Flowable<BaseResult<Any?>>


  fun postGetShopCategories(): Flowable<BaseResult<CategoryList?>>
}
