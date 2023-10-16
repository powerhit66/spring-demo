package com.example.demo.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserEntity;

import jakarta.servlet.http.HttpSession;

@Service
// ユーザを利用するクラス
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private List<UserEntity> userList;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        userList = userRepository.findByUsername(username);

        if (userList.size() == 0) {
            throw new UsernameNotFoundException("User" + username + "was not found in the database");
        }
        
        UserEntity user = userList.get(0);
        session.setAttribute("display_name", user.getDisplay_name());

        /* 
         * userエンティティについて、もしgetter, setterが生成されない場合(maven)、
         * Java Resources/Libraries/Maven Dependencies
         * にあるlombokのjarを右クリックして、実行する -> eclipseのパスを指定してupdate -> eclipse再起動
         * -> プロジェクト右クリックして -> Maven -> Project Update 
         * */
        // 画面から渡してきたパスワードはエンコードされていないため、passwordEncoderを呼び出す必要がある
        return User.withUsername(user.getUsername()).password(
        		passwordEncoder.encode(user.getPassword())).build();
    }
    
    // ユーザ登録
    public boolean createUser(UserEntity user) {
		String username = user.getUsername();
		
		try {
		userList = getUserInfo(username);
		
			// すでに入力されたユーザ名が存在する場合、
	        if (userList.size() != 0) {
	            return false;
	        }
	        userRepository.save(user);
	        return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    
    // ユーザ更新
    public boolean modifyUser(UserEntity user) {
		
		try {
			// JPAはupdateとcreateはどちらもsaveで実施する
	        userRepository.save(user);
	        return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    
    // ユーザ削除
    public boolean deleteUser(UserEntity user) {
		try {
	        userRepository.delete(user);
	        return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    
    // ユーザ情報取得
    public List<UserEntity> getUserInfo(String username) {
    	return userRepository.findByUsername(username);
    }
}