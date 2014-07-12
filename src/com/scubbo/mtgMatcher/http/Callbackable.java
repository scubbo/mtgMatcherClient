package com.scubbo.mtgMatcher.http;

import java.util.concurrent.Callable;

//http://www.programmingforums.org/thread27905.html
public abstract class Callbackable<Tin,Tout> {
    protected abstract Tout operate(Tin val);

    public final Callable<Tout> pass(Tin val) {
        final Tin foo = val; //why final? Something about being called from an Inner class?

        return new Callable<Tout>() {
          public Tout call() {
              return operate(foo);
          }
        };
    }

    public static <Cin,Cout> Callbackable<Cin,Cout> getNullCallbackable(Class Cin, Class Cout) {
        return new Callbackable<Cin,Cout>() {

            @Override
            protected Cout operate(Cin val) {
                return null;
            }
        };
    }

}
