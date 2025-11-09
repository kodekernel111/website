package com.kodekernel.website.model;

import lombok.*;

@Builder @NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class EmailDTO {
    private String name;
    private String email;
    private String service;
    private String message;

}
