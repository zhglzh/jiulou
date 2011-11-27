package org.jiulou.service.impl;

import javax.annotation.Resource;

import org.jiulou.dao.PersonDAO;
import org.jiulou.service.PersonBiz;
import org.springframework.stereotype.Service;

import com.xiamen.domain.Person;

@Service("personBiz")
public class PersonBizImpl implements PersonBiz {

	@Resource
	private PersonDAO personDao;

	public boolean insertPerson(Person person) {
		int flag=this.personDao.insert(person);
		if(flag>0){
			return true;
		}else{
		return false;
		}
		}

	public Person checkById(int id) {
		Person person=this.personDao.getPersonById(id);
		return person;
	}

}
