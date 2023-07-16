package com.ceyentra.springboot.visitersmanager.util.convert;

import java.util.List;

public interface CustomConvertor<T,R>{

    List<R> convert(List<T> t);
}
