package com.example.librabry_spring_boot.service;

import com.example.librabry_spring_boot.dto.UserResponseDTO;
import com.example.librabry_spring_boot.dto.UserUpdateDTO;
import com.example.librabry_spring_boot.model.Role;
import com.example.librabry_spring_boot.model.User;
import com.example.librabry_spring_boot.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // đăng ký
    public User register(User user) {
        // Kiểm tra số điện thoại trước
        if (userRepository.findByPhone(user.getPhone()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Số điện thoại đã có người đăng ký");
        }

        // Thiết lập role mặc định nếu chưa có
        if (user.getRole() == null) {
            user.setRole(Role.LIBRARIAN);
        }

        // Mã hóa password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Lưu user vào database
        return userRepository.save(user);
    }

    // tạo tài khoản admin khi không có Role admin
    @PostConstruct
    public void initAdminAccount() {
        Optional<User> admin = userRepository.findByRole(Role.ADMIN);
        if (admin.isEmpty()) {
            User user = new User();
            user.setFullName("Admin Library");
            user.setPhone("0123456789");
            user.setEmail("admin@example.com");
            user.setPassword(passwordEncoder.encode("admin123")); // Mật khẩu mặc định
            user.setRole(Role.ADMIN);
            userRepository.save(user);
            System.out.println("Admin account created successfully!");
        }
    }

    //xoá user theo id
    public  void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy người dùng với id: " + id));
        if (user.getRole().toString() == "ADMIN"){
            throw new RuntimeException("Không thể xoá người dùng có vai trò là ADMIN");

        }
        userRepository.deleteById(id);
    }

    // Sửa thông tin user
    public User updateUser(Long id, UserUpdateDTO dto){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy người dùng với id: "+ id));
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        return userRepository.save(user);

    }



    // lấy danh sách user
    public List<UserResponseDTO> getAllUserDTO(){
        List<User> users = userRepository.findAll();
        return users.stream().map(
                user -> new UserResponseDTO(
                        user.getUserId(),
                        user.getFullName(),
                        user.getPhone(),
                        user.getEmail(),
                        user.getRole().toString()
                )
        ).collect(Collectors.toList());
    }

    //Login nè
    public UserResponseDTO login(String phone, String password) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("Số điện thoại không tồn tại"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }

        return new UserResponseDTO(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole().toString()
        );
    }

}