package org.dismefront.app;

import jakarta.transaction.Transactional;
import org.dismefront.data.location.Location;
import org.dismefront.data.person.Person;
import org.dismefront.data.person.PersonRepository;
import org.dismefront.data.shared.Color;
import org.dismefront.data.shared.Country;
import org.dismefront.data.shared.Role;
import org.dismefront.data.user.User;
import org.dismefront.data.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final Environment env;
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, Environment env,
                           PersonRepository personRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode(env.getProperty("ROOT_ADMIN_PASSWORD")));
            admin.setRoles(Set.of(Role.ROLE_ADMIN));
            userRepository.save(admin);
        }
        if (personRepository.findByName("Erik").isEmpty()) {
            Person person = new Person();
            person.setEyeColor(Color.YELLOW);
            person.setName("Erik");
            person.setHeight(185L);
            person.setHairColor(Color.YELLOW);
            person.setNationality(Country.SPAIN);
            Location location = new Location();
            location.setX(13);
            location.setY(100);
            location.setZ(123F);
            person.setLocation(location);
            personRepository.save(person);
        }
    }
}
