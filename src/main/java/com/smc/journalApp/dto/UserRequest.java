package com.smc.journalApp.dto;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

@NoArgsConstructor
@Data
public class UserRequest {

    @Indexed(unique = true)
    @NonNull
    private String username;

    @NonNull
    private String password;

    private String email;

    private boolean sentimentAnalysis;

}
