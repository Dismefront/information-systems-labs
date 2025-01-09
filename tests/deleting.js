import http from 'k6/http';
import { check, sleep } from 'k6';

const BASE_URL = 'http://localhost:8080';
const USERS = 1000;

export const options = {
    vus: USERS,
    iterations: 1000,
};

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
        throw new Error('Failed to retrieve JSESSIONID');
    }

    const getAllHeaders = {
        'Content-Type': 'application/json',
        Cookie: `JSESSIONID=${jsessionId}`,
    };

    const getAllResponse = http.get(`${BASE_URL}/api/coordinates/get-all`, {
        headers: getAllHeaders,
    });

    check(getAllResponse, {
        'fetch coordinates succeeded': (res) => res.status === 200,
    });

    const coordinatesList = getAllResponse.json();

    if (!coordinatesList || coordinatesList.length === 0) {
        throw new Error('No coordinates available to delete');
    }

    const coordinateToDelete = coordinatesList[Math.floor(Math.random() * coordinatesList.length)];

    if (!coordinateToDelete || !coordinateToDelete.id) {
        throw new Error('Invalid coordinate data');
    }

    const deleteResponse = http.post(
        `${BASE_URL}/api/coordinates/delete/${coordinateToDelete.id}`,
        null,
        { headers: getAllHeaders }
    );

    check(deleteResponse, {
        'delete coordinate succeeded': (res) => res.status === 200 || res.status === 204,
    });
}

//  ✗ login succeeded
//       ↳  10% — ✓ 106 / ✗ 894
//      ✓ fetch coordinates succeeded
//      ✗ delete coordinate succeeded
//       ↳  0% — ✓ 0 / ✗ 106
//
// checks.........................: 17.49% 212 out of 1212
// data_received..................: 1.7 MB 321 kB/s
// data_sent......................: 247 kB 45 kB/s
// http_req_blocked...............: avg=68.09ms  min=1µs     med=84.95ms  max=134.06ms p(90)=89.96ms  p(95)=90.72ms
// http_req_connecting............: avg=50.51ms  min=0s      med=61.77ms  max=110.27ms p(90)=66.76ms  p(95)=67.19ms
// http_req_duration..............: avg=125.53ms min=521µs   med=31.39ms  max=5.33s    p(90)=272ms    p(95)=507.45ms
// { expected_response:true }...: avg=576.94ms min=16.13ms med=312.82ms max=5.33s    p(90)=733.61ms p(95)=860.58ms
// http_req_failed................: 82.50% 1000 out of 1212
// http_req_receiving.............: avg=20.48µs  min=0s      med=0s       max=883µs    p(90)=75.9µs   p(95)=112.89µs
// http_req_sending...............: avg=53.91µs  min=5µs     med=15µs     max=1.17ms   p(90)=122µs    p(95)=247.34µs
// http_req_tls_handshaking.......: avg=0s       min=0s      med=0s       max=0s       p(90)=0s       p(95)=0s
// http_req_waiting...............: avg=125.45ms min=484µs   med=31.32ms  max=5.33s    p(90)=271.92ms p(95)=507.33ms
// http_reqs......................: 1212   222.463734/s
// iteration_duration.............: avg=361.59ms min=76.01ms med=281.21ms max=5.44s    p(90)=531.94ms p(95)=883.84ms
// iterations.....................: 1000   183.550936/s
// vus............................: 10     min=10           max=34
// vus_max........................: 1000   min=1000         max=1000
