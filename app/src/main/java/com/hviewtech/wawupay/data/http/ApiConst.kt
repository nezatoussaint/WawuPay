package com.hviewtech.wawupay.data.http

object ApiConst {

  /**
   * Base
   */
  // 用户注册
  const val postUserRegister = "/base/userRegister"
  // 发送短信
  const val postSendVerCode = "/base/sendVerCode/{phone}"
  // 验证手机号验证码
  const val postVerificationPhone = "/base/verificationPhone/{phone}/{code}"
  // 获取省份列表
  const val postGetProvinceCitys = "/base/getProvinceCitys"
  // 用户上传身份信息
  const val postUserCertificateUpload = "/base/userCertificateUpload"
  // 修改密码
  const val postModifyPassword = "/base/modifyPassword"
  // 用户密码登录
  const val postUserLogin = "/base/userLogin"
  // 用户token登录
  const val postUserLoginByToken = "/base/userLoginByToken/{token}"
  // 判断用户是否已进行实名认证
  const val postUserIsRealRegister = "/base/userIsRealRegister/{num}"
  // 获取店铺类型分类
  const val postGetShopCategories = "/base/getShopCategories"
  // 新增银行卡
  const val postAddBankCard = "/base/addBankCard"
  // 删除银行卡
  const val postDelBankCard = "/base/delBankCard/{bankCardId}"
  // 银行卡列表查询
  const val postBankCardList = "/base/bankCardList"
  // 判断商家或者用户是否已设置支付密码
  const val postPayPasswordIsExist = "/base/payPasswordIsExist"
  /**
   * Img
   */
  // 上传图片
  const val putUploadImage = "/fileManage/uploadImg"
  // 图片获取
  const val getShowImage = "/fileManage/showImg/"


  /**
   * Pay
   */
  // 获取资金账户信息
  const val postGetAccountInfo = "/account/getAccountInfo/{accountId}"
  // 获取扣率
  const val postGetPlatformFee = "/account/getPlatformFee/{type}"
  // 银行卡提现
  const val postAccountWithdrawForBankCard = "/account/accountWithdrawForBankCard"
  // 银行卡充值
  const val postAccountRechargeForBankCard = "/account/accountRechargeForBankCard"
  // 用户端获取预支付ID
  const val postGetUserPrepayId = "/pay/getUserPrepayId"
  // 用户端扫码获取待支付信息
  const val postQrGainPay = "/pay/qrGainPay/{paySign}"
  // 扫码支付
  const val postQrPayment = "/pay/qrPayment"
  // 用户端获取预支付ID
  const val postGetAPIMerPrepayId = "/pay/getAPIMerPrepayId"

  /**
   * User
   */
  //    // 新增联系人(新增)
  //    const val postAddContacts = "/contact/addBContacts"
  //    // 获取联系人列表
  //    const val postGetContacts = "/contact/getBContacts"
  //    // 查询最近几天的联系人处理记录
  //    const val postGetRecentlyContacts = "/contact/getBContactsByRecently/{userNum}/{page}"
  //  // 搜索平台用户
  //  const val postGetPlatformUser = "/contact/getPlatformUser"
  //  const val getQRPlatformUser = "/contact/qrPlatformUser/{userNum}"
  //    // 处理联系人操作(关系变更处理)
  //    const val postOperationContactRelation = "/contact/operationContactRelation"
  // 获取商家坐标
  const val postGetMerchantPosition = "/user/getMerchantPosition"
  // 单独验证用户支付密码
  const val postValidatePayPassword = "/user/validatePayPassword"
  // 通过短信验证修改用户支付密码
  const val postModifyPayPassword = "/user/modifyPayPassword"
  //    // 发红包 一对一
  //    const val postSendGiftOneToOne = "/user/sendGiftOneToOne"
  //    // 收红包或者转账
  //    const val postReceiveGiftOneToOne = "/user/receiveGiftOneToOne"
  //    // 收群红包
  //    const val postReceiveGiftOneToMany = "/user/receiveGiftOneToMany"
  //    // 驳回红包或者转账
  //    const val postRejectOneToOne = "/user/rejectOneToOne"
  //    // 转账 一对一
  //    const val postSendTransferOneToOne = "/user/sendTransferOneToOne"
  //    // 获取红包和转账详情(一对一)
  //    const val postGetDetailOneToOne = "/user/getGiftDetailOneToOne/{msgRecordId}"
  //    // 用户创建群
  //    const val postCreateUserGroup = "/userGroup/createUserGroup"
  //    // 获取群详情(扫描群二维码、点击群详情)
  //    const val postGainUserGroupInfo = "/userGroup/gainUserGroupInfo/{groupNum}"
  //    // 二维码申请加入群
  //    const val postApplyJoinUserGroup = "/userGroup/applyJoinUserGroup"
  //    // 邀请用户加入群
  //    const val postInvitationJoinUserGroup = "/userGroup/invitationJoinUserGroup"
  //    // 单独修改群内昵称
  //    const val postModifyUserGroupUserNick = "/userGroup/modifyUserGroupUserNick"
  //    // 群主修改群资料
  //    const val postModifyUserGroupInfo = "/userGroup/modifyUserGroupInfo"
  //    // 退出群
  //    const val postQuitUserGroup = "/userGroup/quitUserGroup"
  //    // 同意或拒绝申请群
  //    const val postOperateUserGroupApplicationRecord = "/userGroup/operateUserGroupApplicationRecord"
  //    // 获取群申请列表
  //    const val postGainUserGroupApplicationRecords = "/userGroup/gainUserGroupApplicationRecords"
  //    // 发红包(一对多)
  //    const val postSendGiftOneToMany = "/user/sendGiftOneToMany"
  //    // 发送AA收款
  //    const val postSendAAReceipts = "/user/sendAAReceipts"
  //    // 获取红包详情
  //    const val postGainGiftDetail = "/user/gainGiftDetail"
  //    // 获取AA收款详情
  //    const val postGainAAReceiptsDetail = "/user/gainAAReceiptsDetail"
  //    // AA账单支付(一对多)
  //    const val postPaymentAABill = "/user/paymentAABill"
  // 获取用户交易记录列表(零钱功能也调这个接口)
  const val postGetUserBillInfoList = "/user/getUserBillInfoList"
  //    // 获取群列表
  //    const val postGainUserGroups = "/userGroup/gainUserGroups/{userNum}"
  //    // 删除好友
  //    const val postDelUserContact = "/contact/delUserContact"
  // 用户投诉订单
  const val postUserComplaintInfo = "/user/userComplaintInfo"
  // 用户修改基本信息
  const val postModifyUserInfo = "/user/modifyUserInfo"
  //    // AA账单拒绝支付
  //    const val postRefuseAABill = "/user/refuseAABill"
  //    // 取用户的最近聊天列表
  //    const val postChatHistory = "/chat/chatHistory"
  //    // 取聊天消息记录
  //    const val postUserMessageHistory = "/chat/userMessageHistory"
  //    // 通过手机号码搜索用户信息
  //    const val postCheckUserByPhone = "/contact/checkUserStatusByPhone"
  //    // 邀请朋友注册账号
  //    const val postInviteUserOnPlatform = "/contact/inviteUserOnPlatform/{phone}/{num}"

}