package org.jiulou.dao;

import com.xiamen.domain.Person;

public interface PersonDAO{

	public Person getPersonById(int id);
	
	public int insert(Person person);
}
