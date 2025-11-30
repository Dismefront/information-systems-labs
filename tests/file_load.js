import http from 'k6/http';
import { check } from 'k6';

const BASE_URL = 'http://localhost:8080';
const USERS = 100;

export const options = {
    vus: USERS,
    iterations: USERS,
};

let fileContent = open('./data1.yaml', 'r');

export default function () {
    const loginPayload = JSON.stringify({
        username: `test_user_${__VU}`,
        password: `test_user_${__VU}`,
    });

    const loginHeaders = { 'Content-Type': 'application/json' };
    const loginResponse = http.post(`${BASE_URL}/api/login`, loginPayload, {
        headers: loginHeaders,
    });

    check(loginResponse, {
        'login succeeded': (res) => res.status === 200,
    });

    const cookies = loginResponse.cookies;
    const jsessionId = cookies?.JSESSIONID?.[0]?.value;

    if (!jsessionId) {
        throw new Error('Failed to retrieve JSESSIONID for ' + 'test_user_' + __VU);
    }

    const createHeaders = {
        'Content-Type': 'application/json',
        Cookie: `JSESSIONID=${jsessionId}`,
    };

    const createResponse = http.post(
        `${BASE_URL}/api/product/upload-data`,
        fileContent,
        { headers: createHeaders }
    );

    check(createResponse, {
        'upload file succeeded': (res) => res.status === 200,
    });
}

// ✗ login succeeded
//       ↳  10% — ✓ 109 / ✗ 891
//      ✓ create coordinates succeeded
//
// checks.........................: 19.65% 218 out of 1109
// data_received..................: 94 kB  75 kB/s
// data_sent......................: 226 kB 180 kB/s
// http_req_blocked...............: avg=74.38ms  min=1µs     med=84.75ms  max=133.93ms p(90)=89.35ms  p(95)=89.75ms
// http_req_connecting............: avg=54.18ms  min=0s      med=61.05ms  max=115.64ms p(90)=65.14ms  p(95)=65.59ms
// http_req_duration..............: avg=112.78ms min=24.65ms med=32.92ms  max=1.01s    p(90)=446.39ms p(95)=572.92ms
// { expected_response:true }...: avg=436.54ms min=24.65ms med=452.51ms max=1.01s    p(90)=787.92ms p(95)=897.63ms
// http_req_failed................: 80.34% 891 out of 1109
// http_req_receiving.............: avg=14.42µs  min=0s      med=0s       max=1.19ms   p(90)=50.4µs   p(95)=96µs
// http_req_sending...............: avg=50.16µs  min=4µs     med=17µs     max=845µs    p(90)=132µs    p(95)=203.59µs
// http_req_tls_handshaking.......: avg=0s       min=0s      med=0s       max=0s       p(90)=0s       p(95)=0s
// http_req_waiting...............: avg=112.71ms min=24.54ms med=32.87ms  max=1.01s    p(90)=446.3ms  p(95)=572.82ms
// http_reqs......................: 1109   880.458505/s
// iteration_duration.............: avg=339.76ms min=77.04ms med=277.01ms max=1.24s    p(90)=722.55ms p(95)=985.63ms
// iterations.....................: 1000   793.921105/s
// vus............................: 57     min=57          max=57
// vus_max........................: 1000   min=1000        max=1000
//
//
// running (00m01.3s), 0000/1000 VUs, 1000 complete and 0 interrupted iterations
