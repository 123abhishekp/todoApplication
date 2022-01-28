package com.abis.app.todoApplication.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	private static ArrayList<User> users = new ArrayList<User>();
	private static int userCount = 4;
	static {
		users.add(new User(1, "Abhishek", new Date()));
		users.add(new User(2, "Adam", new Date()));
		users.add(new User(3, "Ganit", new Date()));
		users.add(new User(4, "Rasam", new Date()));
	}

	public ArrayList<User> findAll() {

		return users;
	}

	public User save(User user) {

		if (user.getId() == null) {
			user.setId(++userCount);
			users.add(user);
			//userCount++;
		}else {
			users.add(user);
			}
		return user;
	}
	
	
	public User findUser(Integer id) {
		
		for(User user: users) {
			if(user.getId()==id){
				return user;
			}
		}
		return null;

	}

	public User deleteUser(int id) {

		Iterator<User> iterator = users.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (user.getId() == id) {
				iterator.remove();
				return user;
			}
		}
		return null;

	}

}
