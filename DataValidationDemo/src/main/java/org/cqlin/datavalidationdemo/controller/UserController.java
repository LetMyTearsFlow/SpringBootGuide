package org.cqlin.datavalidationdemo.controller;

import jakarta.validation.Valid;
import org.cqlin.datavalidationdemo.common.ApiResponse;
import org.cqlin.datavalidationdemo.common.ValidationError;
import org.cqlin.datavalidationdemo.dto.UserCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    /**
     * Demonstrates controller-level validation error handling with BindingResult.
     */
    @PostMapping("/binding-result")
    public ResponseEntity<ApiResponse<?>> createWithBindingResult(
            @Valid @RequestBody UserCreateRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ValidationError> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                    .toList();
            return ResponseEntity.badRequest()
                    .body(ApiResponse.failure(400, "validation failed", errors));
        }
        return ResponseEntity.ok(ApiResponse.success("user created: " + request.username()));
    }

    /**
     * Demonstrates validation errors delegated to the global exception handler.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(
            @Valid @RequestBody UserCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("user created: " + request.username()));
    }
}
