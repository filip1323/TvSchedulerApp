/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_exceptions;

/**
 *
 * @author Filip
 */
public class WrongUrlException extends UserException {

    public WrongUrlException(String message) {
	super(message);
    }
}
