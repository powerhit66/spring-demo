package com.example.demo.sns.entity;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.UserEntity;

// DBにアクセスするユーザrepository interface
@Repository // DAOに使用するアノテーション
public interface BbsRepository extends JpaRepository<BbsEntity, BbsKey>  {
	// 複合キーを使用するときにSQL書かないと 
	@Query(value="select * from bbs where id = :id and username = :username", nativeQuery = true)
	// @Query(value="select b from bbs b where b.id = :id and b.username = :username") JPQLだとこの書き方が必要
	BbsEntity findByCompositePrimaryKey(@Param("id")Integer id,@Param("username")String username);
	List<BbsEntity> findByUsername(@Param("username")String username);
	
	@Query(value="select MAX(id) from bbs", nativeQuery = true)
	Integer findMaxId();
	
	@Query(value="select * from bbs order by id", nativeQuery = true)
	List<BbsEntity> findAll();
	
	// Paging用
	@Query(value="select * from bbs order by id", nativeQuery = true)
	Page<BbsEntity> findAll(Pageable pageable);
}
