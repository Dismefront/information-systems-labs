import { API_ENDPOINT } from "@/App";

export const SpecialCases: React.FC = () => {
    return <><button onClick={() => {
        fetch(`${API_ENDPOINT}/product/by-manufacturer`, {
            method: 'get',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }}>count-by-manufacturer</button>
    <button onClick={() => {
        fetch(`${API_ENDPOINT}/product/by-rating?rating=32`, {
            method: 'get',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }}>count-by-rating</button>
    <button onClick={() => {
        fetch(`${API_ENDPOINT}/product/by-partNumber?partNumber=8800`, {
            method: 'get',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }}>count-by-partNumber</button>
    <button onClick={() => {
        fetch(`${API_ENDPOINT}/product/products-by-manufacturer?manufacturerId=2`, {
            method: 'get',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }}>get-by-manufacturer</button>
    <button onClick={() => {
        fetch(`${API_ENDPOINT}/product/reduce-price?percent=10`, {
            method: 'get',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }}>reduce-by-percent</button></>
}