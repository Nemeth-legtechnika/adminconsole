package com.nemethlegtechnika.products.service.implementation.endpoint

import com.nemethlegtechnika.products.db.model.ProductGroup
import com.nemethlegtechnika.products.dto.group.GetGroupDto
import com.nemethlegtechnika.products.mapper.GroupMapper
import com.nemethlegtechnika.products.service.interfaces.GroupService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class GroupServiceProxy(
    private val groupService: GroupService,
    private val groupMapper: GroupMapper
) {
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun getAll(): List<GetGroupDto> = groupService.getAll().map { groupMapper.map(it) }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    fun get(id: Long): GetGroupDto = groupService.get(id).let { groupMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun create(group: ProductGroup): GetGroupDto = groupService.create(group).let { groupMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun update(group: ProductGroup): GetGroupDto = groupService.update(group).let { groupMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun addProduct(productId: Long, groupId: Long): GetGroupDto = groupService.addProduct(productId, groupId).let { groupMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun removeProduct(productId: Long, groupId: Long): GetGroupDto = groupService.removeProduct(productId, groupId).let { groupMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun delete(id: Long) = groupService.delete(id)
}