package uz.chelkatrao.testapp.config.application;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uz.chelkatrao.testapp.domain.auth.Authority;
import uz.chelkatrao.testapp.domain.auth.User;
import uz.chelkatrao.testapp.repository.auth.AuthorityRepository;
import uz.chelkatrao.testapp.repository.auth.UserRepository;
import uz.chelkatrao.testapp.security.AuthoritiesConstants;

import java.util.List;

@Configuration
public class AdminConfig {

    @Bean
    CommandLineRunner commandLineRunnerAuthoritiesAndUser(AuthorityRepository authorityRepository,
                                                          UserRepository userRepository) {
        return args -> {
            String adminStr = "admin";
            if (!userRepository.findByLogin(adminStr).isPresent()) {

                Authority authorityAdmin = new Authority();
                authorityAdmin.setName(AuthoritiesConstants.ADMIN);

                Authority authorityUser = new Authority();
                authorityUser.setName(AuthoritiesConstants.USER);

                Authority authorityAnonymous = new Authority();
                authorityAnonymous.setName(AuthoritiesConstants.ANONYMOUS);

                User admin = new User();
                admin.setActivated(true);
                admin.setFirstName("Ashraf");
                admin.setLastName("Qayumov");
                admin.setLogin(adminStr);
                admin.setPassword(new BCryptPasswordEncoder(10).encode(adminStr));
                admin.setEmail("chelkatrao@gmail.com");
                admin.setAuthorities(Sets.newHashSet(authorityAdmin, authorityUser, authorityAnonymous));
                admin.setCreatedBy("system");

                authorityRepository.saveAll(Lists.newArrayList(authorityAdmin, authorityAnonymous, authorityUser));
                userRepository.save(admin);
            }
        };
    }

}
