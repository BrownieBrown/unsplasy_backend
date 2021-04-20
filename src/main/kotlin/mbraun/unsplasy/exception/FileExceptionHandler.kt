package mbraun.unsplasy.exception

import mbraun.unsplasy.message.ResponseMessage
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class FileExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResultException(exc: EmptyResultDataAccessException): ResponseEntity<ResponseMessage> {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseMessage("File does not exist!"))
    }

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxSizeException(exc: MaxUploadSizeExceededException): ResponseEntity<ResponseMessage> {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseMessage("File too large!"))
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleMaxSizeException(exc: NoSuchElementException): ResponseEntity<ResponseMessage> {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseMessage("File does not exist!"))
    }
}