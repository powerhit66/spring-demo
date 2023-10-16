package com.example.demo.user;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserEntity;

// DBにアクセスするユーザrepository interface
@Repository // DAOに使用するアノテーション
public interface UserRepository extends JpaRepository<UserEntity, Integer>  {
	// 必要なメソッドの追加定義、こうすることでJPAはusernameで探してくる
	List<UserEntity> findByUsername(String username);
}

