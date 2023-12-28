package cn.soldat.jbutils.codec

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.*

object JwtUtil {

    private const val EXPIRATION = 60 * 60 // 604800 默认一小时后过期
    private const val SECRET: String = "bdaf82f092bd4104940590422dc8f0cc"

    const val TOKEN_HEAD: String = "Bearer"
    const val TOKEN_HEADER: String = "Authorization"

    // 荷载的用户名的Key
    private const val CLAIM_KEY_USERID = "uid"
    // JWT的创建事件的 Key
    private const val CLAIN_KEY_CREATED = "created"

    /**
     * 根据 UserId 生成 token
     */
    fun create(uid: String): String = createToken(hashMapOf(
        CLAIM_KEY_USERID to uid,
        CLAIN_KEY_CREATED to Date()
    ))

    /**
     * 从Token中获取 uid
     */
    fun getUserIdFromToken(token: String): String{
        val claims = getClaimsFromToken(token)
        claims[CLAIM_KEY_USERID]?.let { return it.toString() }
        return ""
    }

    /**
     * 验证Token是否有效
     */
    fun validate(token:String, uid: String): Boolean{
        val userId = getUserIdFromToken(token)
        return userId == uid && !isTokenExpired(token)
    }

    /**
     * 判断Token是否可以被刷新
     * 如果过期了就可以刷新，没过期不可以刷新
     */
    fun canRefresh(token: String): Boolean{
        return !isTokenExpired(token)
    }

    /**
     * 刷新Token
     */
    fun refreshToken(token: String): String{
        val claims = getClaimsFromToken(token)
        claims[CLAIN_KEY_CREATED] = Date()
        return createToken(claims)
    }

    /**
     * 判断Token是否失效
     */
    private fun isTokenExpired(token: String): Boolean {
        val expiredDate: Date = getExpiredDateFromToken(token)
        return expiredDate.before(Date())
    }

    /**
     * 获取过期时间
     */
    private fun getExpiredDateFromToken(token: String): Date {
        val claims = getClaimsFromToken(token)
        return claims.expiration
    }


    /**
     * 从Token获取荷载
     */
    private fun getClaimsFromToken(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(SECRET.toByteArray()))
            .build()
            .parseClaimsJws(token)
            .body
    }

    /**
     * 根据荷载生成Token
     */
    private fun createToken(claims: Map<String, Any>): String{
        return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
            .signWith(Keys.hmacShaKeyFor(SECRET.toByteArray()), SignatureAlgorithm.HS256)
            .compact()
    }

    /**
     * 生成Token失效时间
     */
    private fun generateExpirationDate(): Date {
        return Date(System.currentTimeMillis() + EXPIRATION * 1000)
    }

}