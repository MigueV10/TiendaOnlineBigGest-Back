package com.coderhouse.error;


import java.io.Serializable;

import org.springframework.aot.hint.annotation.RegisterReflection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@ToString
@RegisterReflection
public class AgregarCategoriaException extends Exception implements Serializable {

    public AgregarCategoriaException(String message) {
        super(message);
    }
}