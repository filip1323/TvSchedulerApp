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
public class DebugError extends Error {

    public DebugError(String s) {
	super(s);
    }
}