package cn.soldat.jutils.network

import com.sun.org.slf4j.internal.LoggerFactory
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object OkhttpUtil {

    private val logger = LoggerFactory.getLogger(OkhttpUtil::class.java)
    private val client: OkHttpClient by lazy { getOkhttpClient() }
    private const val HTTP_METHOD_GET = "GET"
    private const val HTTP_METHOD_POST = "POST"
    private const val HTTP_METHOD_PUT = "PUT"
    private const val HTTP_METHOD_DELETE = "DELETE"

    fun getInstance() = client

    /**
     * HTTP GET 同步回调
     */
    fun get(url: String, headers: Map<String, Any>? = null, onSuccess: (String) -> Unit, onFailed: (() -> Unit)? = null) {
        val request: Request = Request.Builder().url(url).apply {
            if (!headers.isNullOrEmpty())
                headers.entries.forEach { addHeader(it.key, it.value.toString()) }
        }.build()
        request(request, onSuccess, onFailed = { onFailed?.invoke() })
    }

    /**
     * HTTP GET 同步 / 异步
     */
    fun get(url: String, headers: Map<String, Any>? = null, sync: Boolean = true): String? {
        val request: Request = Request.Builder().url(url).apply {
            if (!headers.isNullOrEmpty())
                headers.entries.forEach { addHeader(it.key, it.value.toString()) }
        }.build()
        if (sync) return request(request)
        asyncRequest(request)
        return null
    }

    /**
     * HTTP POST 同步回调
     */
    fun post(url: String, headers: Map<String, Any>? = null, params: Map<String, Any>? = null,
             onSuccess: (String) -> Unit, onFailed: (() -> Unit)? = null) {
        val request: Request = Request.Builder().url(url).apply {
            if (!headers.isNullOrEmpty())
                headers.entries.forEach { addHeader(it.key, it.value.toString()) }
        }.method(HTTP_METHOD_POST, body(params)).build()
        request(request, onSuccess, onFailed = { onFailed?.invoke() })
    }

    /**
     * HTTP POST 同步 / 异步
     */
    fun post(url: String, headers: Map<String, Any>? = null, params: Map<String, Any>? = null, sync: Boolean = true): String? {
        val body = body(params)
        val request: Request = Request.Builder().url(url).apply {
            if (!headers.isNullOrEmpty())
                headers.entries.forEach { addHeader(it.key, it.value.toString()) }
        }.method(HTTP_METHOD_POST, body).build()
        if (sync) return request(request)
        asyncRequest(request)
        return null
    }

    // curl -v -u username:password -H "Content-Type: application/json"
    fun post(url: String, headers: Map<String, Any>? = null, params: Map<String, Any>? = null,
             username: String, password: String): String {
        val credential = Credentials.basic(username, password)
        val request: Request = Request.Builder().url(url).apply {
            addHeader("Authorization", credential)
            // addHeader("Content-Type", "application/x-www-form-urlencoded")
            if (!headers.isNullOrEmpty())
                headers.entries.forEach { addHeader(it.key, it.value.toString()) }
        }.apply {
            if (!params.isNullOrEmpty()) method(HTTP_METHOD_POST, body(params))
        }.method(HTTP_METHOD_POST, body(params)).build()
        return request(request)!!
    }

    fun uploadFile(url: String, file: File, onSuccess: (String) -> Unit, onFailed: (() -> Unit)? = null) {
        val requestBody = file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
        val request = Request.Builder().url(url)
            .header("filename", file.nameWithoutExtension)
            .post(requestBody).build()
        request(request, onSuccess, onFailed = { onFailed?.invoke() })
    }

    fun uploadFile(url: String, headers: Map<String, Any>? = null,
                   file: File, onSuccess: (String) -> Unit, onFailed: ((Response?) -> Unit)? = null) {
        val requestBody = file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
        val request = Request.Builder().url(url)
            .apply {
                if (!headers.isNullOrEmpty()) headers.entries.forEach { addHeader(it.key, it.value.toString()) }
            }
            .post(requestBody).build()
        request(request, onSuccess, onFailed = { onFailed?.invoke(it) })
    }

    private fun body(params: Map<String, Any>?): RequestBody {
        if (params.isNullOrEmpty()) return FormBody.Builder().build()
        val body = FormBody.Builder()
        params.entries.forEach { body.add(it.key, it.value.toString()) }
        return body.build()
    }

    /**
     * 获取 OkhttpClient 不验证SSL证书
     */
    private fun getOkhttpClient(): OkHttpClient {
        val trustAllCerts: Array<TrustManager> = arrayOf(object: X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return emptyArray()
            }
        })
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        val sslSocketFactory = sslContext.socketFactory
        val client = OkHttpClient.Builder()
        client.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        client.hostnameVerifier { _, _ -> true }
        client.connectTimeout(30, TimeUnit.SECONDS)
        return client.build()
    }

    /**
     * 发起HTTP请求
     */
    private fun request(request: Request, onSuccess: ((String) -> Unit)? = null, onFailed: ((Response?) -> Unit)? = null): String? {
        logger.debug("[HTTP-${request.method}|SYNC]: ${request.url}")
        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val result = response.body?.string()
                    ?: throw RuntimeException("${response.code}: ${response.message}")
                logger.debug("[HTTP|RESPONSE]: $result")
                if (onSuccess == null) return result
                else onSuccess(result)
            } else {
                logger.error("[HTTP|FAILED]: $response")
                onFailed?.invoke(response)
            }
            return null
        } catch (e: IOException) {
            logger.error(e.localizedMessage)
            onFailed?.invoke(null)
        }
        return null
    }

    /**
     * HTTP 异步
     */
    private fun asyncRequest(request: Request) {
        logger.debug("[HTTP-${request.method}|ASYNC]: ${request.url}")
        try {
            client.newCall(request).enqueue(object: Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val result = response.body?.string()
                            ?: throw RuntimeException("${response.code}: ${response.message}")
                        logger.debug("[HTTP|RESPONSE]: $result")
                    } else logger.error("$response")
                }

                override fun onFailure(call: Call, e: IOException) {
                    logger.error("[HTTP|FAILED]: ${e.localizedMessage}")
                }
            })
        } catch (e: IOException) {
            logger.error(e.localizedMessage)
        }
    }
}
