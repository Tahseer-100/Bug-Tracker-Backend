//package Project.Base.Repository;
//
//import Project.Base.Model.Enums.Role;
//import Project.Base.Model.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
//
//import java.time.LocalDateTime;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//public class RepoTest {
//
//    @Autowired
//    private UserRepo userRepo;
//
//
//    @Test
//    public void testSaveUser() {
//        User user = new User();
//
//        user.setFullname("abcdefg");
//        user.setUsername("xyz");
//        user.setEmail("abcd@gmail.com");
//        user.setPassword("abcd123xyz");
//        user.setRole(Role.valueOf("USER"));
//        user.setCreatedAt(LocalDateTime.now());
//
//        User savedU = userRepo.save(user);
//        assertThat(savedU.getId()).isNotNull();
//        assertThat(savedU.getFullname()).isEqualTo("abcdefg");
//        assertThat(savedU.getUsername()).isEqualTo("xyz");
//        assertThat(savedU.getEmail()).isEqualTo("abcd@gmail.com");
//        assertThat(savedU.getPassword()).isEqualTo("abcd123xyz");
//        assertThat(savedU.getRole()).isEqualTo("USER");
//        assertThat(savedU.getCreatedAt()).isBefore(LocalDateTime.now());
//
//
//    }
//}
