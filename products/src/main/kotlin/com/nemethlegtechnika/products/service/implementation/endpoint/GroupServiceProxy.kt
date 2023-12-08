package com.nemethlegtechnika.products.service.implementation.endpoint

import com.nemethlegtechnika.products.dto.group.CreateGroupDto
import com.nemethlegtechnika.products.dto.group.GetGroupDto
import com.nemethlegtechnika.products.dto.group.UpdateGroupDto
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
    fun create(group: CreateGroupDto): GetGroupDto = groupService.create(groupMapper.map(group)).let { groupMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun update(group: UpdateGroupDto): GetGroupDto = groupService.update(groupMapper.map(group)).let { groupMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun addProduct(productId: Long, groupId: Long): GetGroupDto = groupService.addProduct(productId, groupId).let { groupMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun removeProduct(productId: Long, groupId: Long): GetGroupDto = groupService.removeProduct(productId, groupId).let { groupMapper.map(it) }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun delete(id: Long) = groupService.delete(id)
}