package com.nemethlegtechnika.products.controller

import com.nemethlegtechnika.products.db.model.BaseEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

abstract class BaseController {
    protected fun <T: BaseEntity, S> T?.response(mapping: (T) -> S): ResponseEntity<S> {
        return this?.let {
            ResponseEntity.ok(mapping(this))
        } ?: ResponseEntity.notFound().build()
    }

    protected fun created(id: Long, path: String = "/{id}"): ResponseEntity<Unit> {
        val uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path(path)
            .buildAndExpand(id)
            .toUriString()

        return ResponseEntity.created(URI.create(uri)).build()
    }

    protected fun <T> created(id: Long, path: String = "/{id}", body: T? = null): ResponseEntity<T> {
        val uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path(path)
            .buildAndExpand(id)
            .toUriString()

        return ResponseEntity.created(URI.create(uri)).body(body)
    }
}