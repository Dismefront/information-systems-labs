package org.dismefront.api.requests;

import lombok.RequiredArgsConstructor;
import org.dismefront.data.requests.AdminRequest;
import org.dismefront.data.requests.RequestRepository;
import org.dismefront.data.shared.RequestStatus;
import org.dismefront.data.shared.Role;
import org.dismefront.data.user.User;
import org.dismefront.data.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/requests")
public class RequestsController {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    @GetMapping("/list")
    public ResponseEntity findAll() {
        List<User> userList = requestRepository.findAllByStatus(RequestStatus.PENDING)
                .stream().map(x -> userRepository.getReferenceById(x.getUserId())).toList();
        return ResponseEntity.ok(userList);
    }

    @PutMapping("/accept/{id}")
    public ResponseEntity acceptRequest(@PathVariable long id) {
        User user = userRepository.getReferenceById(id);
        user.setRoles(Set.of(Role.ROLE_ADMIN));
        userRepository.save(user);
        requestRepository.findAllByUserIdAndStatus(user.getId(), RequestStatus.PENDING).forEach(x -> {
            x.setStatus(RequestStatus.ACCEPTED);
            requestRepository.save(x);
        });
        return ResponseEntity.ok(user.getUsername() + " accepted");
    }

    @PutMapping("/decline/{id}")
    public ResponseEntity declineRequest(@PathVariable long id) {
        User user = userRepository.getReferenceById(id);
        requestRepository.findAllByUserIdAndStatus(user.getId(), RequestStatus.PENDING).forEach(x -> {
            x.setStatus(RequestStatus.REJECTED);
            requestRepository.save(x);
        });
        return ResponseEntity.ok(user.getUsername() + " declined");
    }

    @PostMapping("/post")
    public ResponseEntity postRequest(@RequestBody AdminHttpRequest request) {
        List<AdminRequest> adminRequests = requestRepository.findAllByUserIdAndStatus(request.getUserId(), RequestStatus.PENDING);
        if (!adminRequests.isEmpty()) {
            return ResponseEntity.status(202).body("Вы уже подали заявку и она обрабатывается");
        }
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setStatus(RequestStatus.PENDING);
        adminRequest.setUserId(request.getUserId());
        requestRepository.save(adminRequest);
        return ResponseEntity.status(202).body("Вы подали заявку на администратора");
    }

}
