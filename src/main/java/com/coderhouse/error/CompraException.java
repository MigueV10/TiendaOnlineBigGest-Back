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
	public class CompraException extends Exception implements Serializable {

	    public CompraException(String message) {
	        super(message);
	    }
}
