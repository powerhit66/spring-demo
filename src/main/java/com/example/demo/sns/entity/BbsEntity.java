package com.example.demo.sns.entity;

import lombok.Data;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

//　javaxのjpaは使えなくなったため、jakartaをインポートする
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

// 掲示板投稿状況を保持するEntity
@Data // getter, setterの自動作成用アノテーション(lombok)
@Entity // エンティティクラスと明示するアノテーション
@Repository
@Table(name="bbs") // 実際のテーブル名と紐づけのアノテーション、同名なら省略OK
@IdClass(value=BbsKey.class) // 複合キーの場合はこちらを宣言する
public class BbsEntity {
	
	// 投稿ID
	// 主キーに紐づけのアノテーション
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE) // uniqueな値を自動生成する
	@Column(name="id", insertable = false, updatable = false, columnDefinition="serial") // カラム名と紐づけのアノテーション、同名なら省略OK
	private Integer id;
	
	// ユーザ名
	@Id
	@Column(name="username")
	private String username;
	
	// 投稿内容
	@Column(name="content")
	private String content;
	
	// 投稿時間
	@Column(name="created_time")
	private ZonedDateTime created_time;
	
	// 更新時間
	@Column(name="updated_time")
	private ZonedDateTime updated_time;
	
	// 返信対象
	@Column(name="reply_to")
	private String reply_to;
	
	// 表示名
	@Column(name="display_name")
	private String display_name;
	
	public String timeToString(ZonedDateTime date) {
		return DateTimeFormatter.ofPattern("yyyy年MM月dd日 - hh:mm:ss(E)", Locale.JAPANESE).format(date);
	}
	
	/*
	 * lombokによりgetter, setterが自動生成されるため、以下の宣言は不要
	 * */
}
