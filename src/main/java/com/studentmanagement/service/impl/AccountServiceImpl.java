package com.studentmanagement.service.impl;

import com.studentmanagement.converter.UserConverter;
import com.studentmanagement.dto.security.UserDTO;
import com.studentmanagement.email.EmailSender;
import com.studentmanagement.email.token.ConfirmationToken;
import com.studentmanagement.email.token.ConfirmationTokenService;
import com.studentmanagement.entity.security.RoleEntity;
import com.studentmanagement.entity.security.UserEntity;
import com.studentmanagement.exception.Exception404;
import com.studentmanagement.exception.Exception409;
import com.studentmanagement.repository.security.RoleRepository;
import com.studentmanagement.repository.security.UserRepository;
import com.studentmanagement.service.AccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public AccountServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder, UserConverter userConverter,
                              ConfirmationTokenService confirmationTokenService, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userConverter = userConverter;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSender = emailSender;
    }

    @Override
    public String addNewRole(String roleName) {
        RoleEntity entity = roleRepository.findByName(roleName);
        if (entity != null) {
            return "This role already exist";
        }
        RoleEntity NewEntity = new RoleEntity(roleName);
        roleRepository.save(NewEntity);
        return "role name " + roleName + " was added";
    }

    @Override
    public UserEntity loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String updateRole(String role_name, Long id) {
        UserEntity entity = userRepository.findOneById(id);
        entity.setRoles(roleRepository.findOneByName(role_name));
        userRepository.save(entity);
        return "successfully updated role";
    }

    @Override
    public String deleteUser(Long[] ids) {
        for (Long id : ids) {
            userRepository.deleteById(id);
        }
        return "Successfully deleted user";
    }

    @Override
    public UserDTO showUser(Long id) {
        UserEntity entity = userRepository.findOneById(id);
        if (entity != null) {
            return userConverter.toDTO(entity);
        }
        throw new Exception404("NOT FOUND USER");
    }

    @Override
    public String addNewUser(UserDTO dto) {
        UserEntity entity = userRepository.findByUsername(dto.getUsername());
        if (entity != null) {
            throw new Exception409("This user already exits");
        }
        if (dto.getId() != null) {
            UserEntity oldEntity = userRepository.findOneById(dto.getId());
            UserEntity newEntity = userConverter.toEntity(dto, oldEntity);
            newEntity.setPassword(passwordEncoder.encode(newEntity.getPassword()));
            userRepository.save(newEntity);
            return "Successfully updated user with id: " + newEntity.getId();
        }
        UserEntity newEntity = userConverter.toEntity(dto);
        newEntity.setPassword(passwordEncoder.encode(newEntity.getPassword()));
        newEntity.setRoles(roleRepository.findOneByName("USER"));
        userRepository.save(newEntity);
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                newEntity
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        String link = "http://localhost:8080/signup/confirm?token=" + token;
        emailSender.send(
                dto.getEmail(),
                buildEmail(dto.getUsername(), link));
        return "Successfully created an user with id :" + newEntity.getId() + "\nplease check email to verify your account";
    }

    @Override
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userRepository.enableAppUser(
                confirmationToken.getUser().getUsername());
        return "confirmed";
    }

    public String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
