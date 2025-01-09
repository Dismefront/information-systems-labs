import http from 'k6/http';
import { check } from 'k6';

const BASE_URL = 'http://localhost:8080/api/register';

const NUM_USERS = 1000;
const USERS = Array.from({ length: NUM_USERS }, (_, i) => {
    const userId = i + 1;
    return {
        username: `test_user_${userId}`,
        password: `test_user_${userId}`,
        passwordRepeat: `test_user_${userId}`,
    };
});

export const options = {
    vus: 1, // Virtual users to simulate concurrent requests
    iterations: NUM_USERS,
};

export default function () {
    const user = USERS[__ITER];

    const response = http.post(BASE_URL, JSON.stringify(user), {
        headers: { 'Content-Type': 'application/json' },
    });

    check(response, {
        'status is 200 or 201': (r) => r.status === 200 || r.status === 201,
    });

    if (response.status !== 200 && response.status !== 201) {
        console.error(`Failed to register ${user.username}: ${response.body}`);
    }
}


// /\      Grafana   /‾‾/
// /\  /  \     |\  __   /  /
// /  \/    \    | |/ /  /   ‾‾\
//   /          \   |   (  |  (‾)  |
// / __________ \  |_|\_\  \_____/
//
// execution: local
// script: tests/register.js
// output: -
//
//     scenarios: (100.00%) 1 scenario, 1 max VUs, 10m30s max duration (incl. graceful stop):
// * default: 1000 iterations shared among 1 VUs (maxDuration: 10m0s, gracefulStop: 30s)
//
// ERRO[0000] Failed to register test_user_1: The user with these credentials already exists  source=console
//
//      ✗ status is 200 or 201
//       ↳  99% — ✓ 999 / ✗ 1
//
// checks.........................: 99.90% 999 out of 1000
// data_received..................: 561 kB 14 kB/s
// data_sent......................: 233 kB 5.7 kB/s
// http_req_blocked...............: avg=373.94µs min=2µs     med=6µs     max=359.6ms  p(90)=14µs    p(95)=16µs
// http_req_connecting............: avg=8.3µs    min=0s      med=0s      max=1.39ms   p(90)=0s      p(95)=0s
// http_req_duration..............: avg=39.92ms  min=13.61ms med=30.49ms max=150.59ms p(90)=77.14ms p(95)=85.55ms
// { expected_response:true }...: avg=39.92ms  min=13.61ms med=30.47ms max=150.59ms p(90)=77.15ms p(95)=85.55ms
// http_req_failed................: 0.10%  1 out of 1000
// http_req_receiving.............: avg=264.66µs min=47µs    med=209µs   max=5.81ms   p(90)=499.1µs p(95)=661.05µs
// http_req_sending...............: avg=33.68µs  min=5µs     med=25µs    max=1.06ms   p(90)=61µs    p(95)=66µs
// http_req_tls_handshaking.......: avg=0s       min=0s      med=0s      max=0s       p(90)=0s      p(95)=0s
// http_req_waiting...............: avg=39.62ms  min=13.51ms med=30.16ms max=149.87ms p(90)=76.66ms p(95)=84.84ms
// http_reqs......................: 1000   24.633216/s
// iteration_duration.............: avg=40.57ms  min=13.72ms med=30.69ms max=401.03ms p(90)=77.68ms p(95)=86.12ms
// iterations.....................: 1000   24.633216/s
// vus............................: 1      min=1           max=1
// vus_max........................: 1      min=1           max=1
//
//
// running (00m40.6s), 0/1 VUs, 1000 complete and 0 interrupted iterations
// default ✓ [======================================] 1 VUs  00m40.6s/10m0s  1000/1000 shared iters

//Реализованный сценарий должен проверять корректность соблюдения системой ограничений уникальности предметной области при одновременной попытке нескольких пользователей создать объект с одним и тем же уникальным значением.
