package cn.soldat.jutils.system

import cn.soldat.jutils.web.response.ResponseCode

/**
 * 全局异常处理 (msg)
 */
data class ResultException(val msg: String): RuntimeException(msg)

/**
 * 全局异常处理 (ResponseCode)
 */
data class ResultCodeException(val code: ResponseCode): RuntimeException(code.msg)