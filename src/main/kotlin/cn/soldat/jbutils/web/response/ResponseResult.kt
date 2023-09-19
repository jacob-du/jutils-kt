package cn.soldat.jbutils.web.response

//import io.swagger.v3.oas.annotations.media.Schema

/**
 * 统一响应数据类
 */
//@Schema(title = "统一响应类")
data class ResponseResult<T> (
//    @Schema(description = "响应码")
    val code: Int,
//    @Schema(description = "响应信息")
    val msg: String,
//    @Schema(description = "响应数据")
    val data: T? = null
){

    companion object{
        /**
         * 成功响应
         */
        fun success() = ResponseResult<Nothing>(ResponseCode.SUCCESS.code, ResponseCode.SUCCESS.msg)
        fun success(code: Int) = ResponseResult<Nothing>(code, ResponseCode.SUCCESS.msg)
        fun success(msg: String) = ResponseResult<Nothing>(ResponseCode.SUCCESS.code, msg)
        fun success(code: Int, msg: String) = ResponseResult<Nothing>(code, msg)
        fun success(respCode: ResponseCode) = ResponseResult<Nothing>(respCode.code, respCode.msg)
        inline fun <reified T> success(data: T) = ResponseResult(ResponseCode.SUCCESS.code, ResponseCode.SUCCESS.msg, data)
        inline fun <reified T> success(code: Int, data: T) = ResponseResult(code, ResponseCode.SUCCESS.msg, data)
        inline fun <reified T> success(msg: String, data: T) = ResponseResult(ResponseCode.SUCCESS.code, msg, data)
        inline fun <reified T> success(code: Int, msg: String, data: T) = ResponseResult(code, msg, data)
        inline fun <reified T> success(respCode: ResponseCode, data: T) = ResponseResult(respCode.code, respCode.msg, data)

        /**
         * 失败响应
         */
        fun error() = ResponseResult<Nothing>(ResponseCode.ERROR.code, ResponseCode.ERROR.msg)
        fun error(code: Int) = ResponseResult<Nothing>(code, ResponseCode.ERROR.msg)
        fun error(msg: String) = ResponseResult<Nothing>(ResponseCode.ERROR.code, msg)
        fun error(code: Int, msg: String) = ResponseResult<Nothing>(code, msg)
        fun error(respCode: ResponseCode) = ResponseResult<Nothing>(respCode.code, respCode.msg)
        inline fun <reified T> error(data: T) = ResponseResult(ResponseCode.ERROR.code, ResponseCode.ERROR.msg, data)
        inline fun <reified T> error(code: Int, data: T) = ResponseResult(code, ResponseCode.ERROR.msg, data)
        inline fun <reified T> error(msg: String, data: T) = ResponseResult(ResponseCode.ERROR.code, msg, data)
        inline fun <reified T> error(respCode: ResponseCode, data: T) = ResponseResult(respCode.code, respCode.msg, data)
    }
}