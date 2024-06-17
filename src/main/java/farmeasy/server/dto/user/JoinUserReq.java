package farmeasy.server.dto.user;

import farmeasy.server.entity.user.Address;
import farmeasy.server.entity.user.Gender;
import farmeasy.server.entity.user.Role;
import farmeasy.server.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinUserReq {
    @Schema(description = "유저 아이디")
    private String username;
    @Schema(description = "유저 비밀번호")
    private String password;
    @Schema(description = "비밀번호 확인")
    private String checkPassword;
    @Schema(description = "유저 이름")
    private String name;
    @Schema(description = "생년월일")
    private String birthDay;
    @Schema(description = "성별")
    private Gender gender;
    @Email
    @Schema(description = "유저 이메일", example = "alswns11346@farmpal.com")
    private String email;
    @Schema(description = "우편번호")
    private Long zipcode;
    @Schema(description = "주소", example = "충청북도 청주시 상당구 월평로 189")
    private String address;
    @Schema(description = "도", example = "충청북도")
    private String sido;
    @Schema(description = "시구", example = "청주시 상당구")
    private String sigungu;
    @Schema(description = "휴대폰", example = "010-1234-5678")
    private String phoneNumber;

    public static User toEntity(JoinUserReq form){
        return new User(
                form.getUsername(),
                form.getPassword(),
                form.getName(),
                form.getGender(),
                form.getPhoneNumber(),
                form.getEmail(),
                form.getBirthDay(),
                new Address(form.getZipcode(), form.getAddress(), form.getSido(), form.getSigungu()),
                Role.USER
        );
    }
}
