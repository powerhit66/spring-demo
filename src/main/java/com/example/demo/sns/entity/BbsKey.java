package com.example.demo.sns.entity;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
/**
 * BBS情報の主キー用 Entity
 */
@Data
public class BbsKey implements Serializable {
	
	@Column(name="id") 
	private Integer id;
	
	@Column(name="username")
	private String username;
}