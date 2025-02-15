package com.pucpr.br.AuthServer.items

import com.pucpr.br.AuthServer.auxfunctions.SortDir
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ItemService(
    val itemRepository: ItemRepository
) {
    fun insert(item: Item) = itemRepository.save(item)

    fun findAll(dir: SortDir) =
        when(dir) {
            SortDir.ASC -> itemRepository.findAll(Sort.by("code"))
            SortDir.DESC -> itemRepository.findAll(Sort.by("code").descending())
        }
    fun findByIdOrNull(id: Long) = itemRepository.findByIdOrNull(id)
    fun findByCode(code: Int) = itemRepository.findByCodeOrNull(code)

    fun delete(id: Long) = itemRepository.deleteById(id)
}