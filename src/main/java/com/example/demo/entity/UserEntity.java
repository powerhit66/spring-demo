package com.example.demo.entity;

import lombok.Data;

import org.springframework.stereotype.Service;

//　javaxのjpaは使えなくなったため、jakartaをインポートする
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// ユーザ情報を保持するEntity
@Data // getter, setterの自動作成用アノテーション(lombok)
@Entity // エンティティクラスと明示するアノテーション
@Table(name="users") // 実際のテーブル名と紐づけのアノテーション、同名なら省略OK
public class UserEntity {
	
	// ユーザ名
	@Id // 主キーに紐づけのアノテーション
	@Column(name="username") // カラム名と紐づけのアノテーション、同名なら省略OK
	private String username;
	
	// ユーザパスワード
	@Column(name="password")
	private String password;
	
	// ユーザ表示名
	@Column(name="display_name")
	private String display_name;
	
	// ユーザメールアドレス
	@Column(name="email")
	private String email;
	/*
	 * lombokによりgetter, setterが自動生成されるため、以下の宣言は不要
	 * */
}
