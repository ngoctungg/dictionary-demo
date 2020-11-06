package com.pm.service;

import com.pm.entity.RoleEntity;
import com.pm.entity.UserEntity;
import com.pm.exception.NotFoundPostException;
import com.pm.model.CustomUserDetails;
import com.pm.model.ResponseMessage;
import com.pm.model.UserModel;
import com.pm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByAccountAndActiveIsTrue(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new CustomUserDetails(user);
    }

    public List<UserEntity> getAllUser() {
        return userRepository.getAllAndSortByActive();
    }

    @Transactional
    public ResponseMessage registerNewUserAccount(UserModel userModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount(userModel.getAccount());
        userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(userModel.getRole());
        userEntity.setRoles(Arrays.asList(roleEntity));
        userEntity.setActive(true);

        userRepository.save(userEntity);
        return new ResponseMessage("201",
                String.format("Create Account: %s successfully", userEntity.getAccount()),
                userEntity);
    }

    @Transactional
    public ResponseMessage deleteUser(Integer id) throws NotFoundPostException {
         Optional<UserEntity> opt =  userRepository.findById(id);
        if (opt.isPresent()) {
            UserEntity userEntity = opt.get();
            userEntity.setActive(false);
            userRepository.save(userEntity);
            return new ResponseMessage("200", "Deactivate Account successfully");
        }
        throw new NotFoundPostException();
    }
    @Transactional
    public ResponseMessage updateUser(UserModel userModel) throws NotFoundPostException {
         Optional<UserEntity> opt =  userRepository.findById(userModel.getId());
        if (opt.isPresent()) {
            UserEntity userEntity = opt.get();
            if(userModel.getPassword() != null){
                userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
            }
            if(userModel.getActive() != null){
                userEntity.setActive(userModel.getActive());
            }
            if(userModel.getRole() != null){
                RoleEntity role = new RoleEntity();
                role.setId(userModel.getRole());
                userEntity.getRoles().clear();
                userEntity.getRoles().add(role);
            }
            userRepository.saveAndFlush(userEntity);
            return new ResponseMessage("200", "Update Account successfully");
        }
        throw new NotFoundPostException();
    }
}
