package org.jiulou.service;

import com.xiamen.domain.Person;

public interface PersonBiz {

	public boolean insertPerson(Person person);
	
	public Person checkById(int id);
}
