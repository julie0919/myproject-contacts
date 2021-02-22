package com.julie.test.util;

public interface Iterator {
  boolean hasNext();
  Object next();
}

/*
Creator: 객체의 생성은 생성되는 객체의 컨텍스트를 알고 있는 다른 객체가 있다면, 컨텍스트를 알고 있는 객체에 부여하자. 
A 객체와 B 객체의 관계의 관계가 다음 중 하나라면 A의 생성을 B의 역할로 부여하라.
- B 객체가 A 객체를 포함하고 있다.
- B 객체가 A 객체의 정보를 기록하고 있다.
- A 객체가 B 객체의 일부이다.
- B 객체가 A 객체를 긴밀하게 사용하고 있다.
- B 객체가 A 객체의 생성에 필요한 정보를 가지고 있다.
 */