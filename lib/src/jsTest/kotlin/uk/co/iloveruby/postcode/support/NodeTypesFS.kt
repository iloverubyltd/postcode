package uk.co.iloveruby.postcode.support

import org.w3c.dom.url.URLSearchParams

@JsNonModule
external class Buffer

@JsNonModule
external class NodeURL {
    constructor(input: String, base: String?)
    constructor(input: String, base: NodeURL)

    var hash: String
    var host: String
    var hostname: String
    var href: String
    val origin: String
    var password: String
    var pathname: String
    var port: String
    var protocol: String
    var search: String
    val searchParams: URLSearchParams
    var username: String
    override fun toString(): String
    fun toJSON(): String
}

@JsNonModule
external interface FSOptions {
    val encoding: String?
    var flag: String
}

@JsNonModule
external interface BufferEncodingOption {
    var encoding: String? // 'buffer'
}

@JsNonModule
external interface FSOptionsBufferEncodingOption {
    var encoding: BufferEncodingOption?
    var flag: String
}

// @JsNonModule
// open external class Error : Throwable

@JsNonModule
external class ErrnoException : Throwable {
    var errno: Number?
    var code: String?
    var path: String?
    var syscall: String?
    var stack: String?
}

@JsNonModule
external interface FS {
    fun readFile(path: String, options: FSOptions?, callback: (err: ErrnoException?, data: Buffer) -> Unit): Unit
    fun readFile(path: Buffer, options: FSOptions?, callback: (err: ErrnoException?, data: Buffer) -> Unit): Unit
    fun readFile(path: NodeURL, options: FSOptions?, callback: (err: ErrnoException?, data: Buffer) -> Unit): Unit
    fun readFile(path: Number, options: FSOptions?, callback: (err: ErrnoException?, data: Buffer) -> Unit): Unit

    fun readFile(path: String, options: FSOptions?, callback: (err: ErrnoException?, data: String) -> Unit): Unit
    fun readFile(path: Buffer, options: FSOptions?, callback: (err: ErrnoException?, data: String) -> Unit): Unit
    fun readFile(path: NodeURL, options: FSOptions?, callback: (err: ErrnoException?, data: String) -> Unit): Unit
    fun readFile(path: Number, options: FSOptions?, callback: (err: ErrnoException?, data: String) -> Unit): Unit

    fun readFile(path: String, options: String, callback: (err: ErrnoException?, data: String) -> Unit): Unit
    fun readFile(path: Buffer, options: String, callback: (err: ErrnoException?, data: String) -> Unit): Unit
    fun readFile(path: NodeURL, options: String, callback: (err: ErrnoException?, data: String) -> Unit): Unit
    fun readFile(path: Number, options: String, callback: (err: ErrnoException?, data: String) -> Unit): Unit

    fun readFile(
        path: String,
        options: FSOptionsBufferEncodingOption?,
        callback: (err: ErrnoException?, data: String) -> Unit
    ): Unit
    fun readFile(
        path: Buffer,
        options: FSOptionsBufferEncodingOption?,
        callback: (err: ErrnoException?, data: String) -> Unit
    ): Unit
    fun readFile(
        path: NodeURL,
        options: FSOptionsBufferEncodingOption?,
        callback: (err: ErrnoException?, data: String) -> Unit
    ): Unit
    fun readFile(
        path: Number,
        options: FSOptionsBufferEncodingOption?,
        callback: (err: ErrnoException?, data: String) -> Unit
    ): Unit

    fun readFile(
        path: String,
        options: FSOptionsBufferEncodingOption?,
        callback: (err: ErrnoException?, data: Buffer) -> Unit
    ): Unit
    fun readFile(
        path: Buffer,
        options: FSOptionsBufferEncodingOption?,
        callback: (err: ErrnoException?, data: Buffer) -> Unit
    ): Unit
    fun readFile(
        path: NodeURL,
        options: FSOptionsBufferEncodingOption?,
        callback: (err: ErrnoException?, data: Buffer) -> Unit
    ): Unit
    fun readFile(
        path: Number,
        options: FSOptionsBufferEncodingOption?,
        callback: (err: ErrnoException?, data: Buffer) -> Unit
    ): Unit

    fun readFile(path: String, callback: (err: ErrnoException?, data: Buffer) -> Unit): Unit
    fun readFile(path: Buffer, callback: (err: ErrnoException?, data: Buffer) -> Unit): Unit
    fun readFile(path: NodeURL, callback: (err: ErrnoException?, data: Buffer) -> Unit): Unit
    fun readFile(path: Number, callback: (err: ErrnoException?, data: Buffer) -> Unit): Unit
}
