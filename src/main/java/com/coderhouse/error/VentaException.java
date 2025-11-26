package com.coderhouse.error;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
	@Setter
	public class VentaException extends Exception implements Serializable {
	    public VentaException(String message) {
	        super(message);
	    }
}
