/**
 * 
 */
package com.abis.app.todoApplication.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abis.app.todoApplication.user.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

}
