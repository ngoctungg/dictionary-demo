package com.pm.controller;

import com.pm.model.ResponseMessage;
import com.pm.model.UserModel;
import com.pm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserRestController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity createUser(@RequestBody UserModel userModel) throws Exception{
        validateUserModel(userModel);
        ResponseMessage message = userService.registerNewUserAccount(userModel);
        return ResponseEntity.ok().body(message);
    }

    @PutMapping("/user")
    public ResponseEntity updateUser(@RequestBody UserModel userModel) throws Exception{
        if(userModel.getId() == null){
            throw new Exception();
        }
        ResponseMessage message = userService.updateUser(userModel);
        return ResponseEntity.ok().body(message);
    }

    private void validateUserModel(@RequestBody UserModel userModel) throws Exception {
        if (userModel.getAccount() == null || userModel.getAccount().trim().length() < 4) {
            throw new Exception();
        }
        if (userModel.getPassword() == null || userModel.getPassword().trim().length() < 4) {
            throw new Exception();
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable(name = "id") Integer id) throws Exception{
        if (id == null){
            throw  new Exception();
        }
        ResponseMessage message = userService.deleteUser(id);
        return ResponseEntity.ok().body(message);
    }
}
