package com.shopping.admin.domain.system.service

import com.shopping.admin.domain.system.dto.ScreenDto
import com.shopping.admin.domain.system.entity.Screen
import com.shopping.admin.domain.system.repository.ScreenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ScreenService(
    val screenRepository: ScreenRepository
) {

    fun createScreen(dto: ScreenDto) {

        val screen: Screen = Screen(
            name = dto.name,
            path = dto.path,
            useYn = dto.useYn
        )

        screenRepository.save(screen)
    }

    @Transactional
    fun updateScreen(id: Long, dto: ScreenDto) {
        val findScreen: Screen = screenRepository.findScreenById(id)
        findScreen.name = dto.name
        findScreen.path = dto.path
        findScreen.useYn = dto.useYn
    }

    fun getScreen(dto: ScreenDto): List<ScreenDto> {

        val screenList: List<ScreenDto> = screenRepository.findScreens(dto)

//        val dtoList = screenList.map {
//            ScreenDto(
//                id = it.id,
//                name = it.name,
//                path = it.path,
//                useYn = it.useYn
//            )
//        }
        return screenList
    }

    fun getScreenDtoById(id: Long): ScreenDto {
        return screenRepository.findScreenDtoById(id)
    }

    fun deleteScreen(id: Long) {

        // TODO: 현재 사용중인 화면이면 삭제 불가
        val findEntity = screenRepository.findScreenById(id)
        screenRepository.delete(findEntity)
    }
}