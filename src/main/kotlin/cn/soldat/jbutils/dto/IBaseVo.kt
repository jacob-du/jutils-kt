package cn.soldat.jbutils.dto

/**
 * VO: View Object / Value Object 一般为响应到前端的数据
 */
interface IBaseVo<E, VO>{
    /**
     * 将实体类转换为对应的VO
     */
    fun copy(e: E): VO

    /**
     * 将实体类列表转换为对应的VO列表
     */
    fun copy(list: List<E>): List<VO> = list.map { copy(it) }
}

/**
 * DTO : Data Transfer Object 前端传到后端的而对象，转换成json的对象， Controller层定义的东西
 */
interface IBaseDto<DTO, E>{
    /**
     * 将 DTO 转换为实体类
     */
    fun copy(dto: DTO): E

    /**
     * 将 DTO 列表转换为实体类列表
     */
    fun copy(list: List<DTO>): List<E> = list.map { copy(it) }
}

/**
 * BO： Business Object 一个业务对象，就是PO的组合
 */
interface IBaseBo

/**
 * PO：Persistent Object 持久化对象，等同于Entity，一条数据库中的记录
 */
interface IBasePo

/**
 * DO: Domain Object 在 DDD（Domain-Driven Design）邻域驱动中的DO， 等同于 BO
 */
interface IBaseDo

/**
 * DO: Data Object 等同于 PO
 */
//interface IBaseDo

interface DeepCopyable<out R> {
    fun deepCopy(): R
}
