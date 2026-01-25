package org.dismefront.data.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CachedUserService {

  private final UserRepository userRepository;

  @Cacheable(value = "users", key = "#username")
  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @CacheEvict(value = "users", key = "#user.username")
  public User save(User user) {
    return userRepository.save(user);
  }

  @CacheEvict(value = "users", key = "#username")
  public void deleteByUsername(String username) {
    userRepository.findByUsername(username).ifPresent(userRepository::delete);
  }
}
