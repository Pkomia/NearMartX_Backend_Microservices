package com._Rbrothers.user_service.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    
    private Long id;
    private String name;
    private String email;
    private Set<String> roles;
}
