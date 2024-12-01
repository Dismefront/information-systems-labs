import { API_ENDPOINT } from '@/App';
import { useState } from 'react';

export const SpecialCases: React.FC = () => {
    const [productCount, updProductCount] = useState<any[] | null>(null);
    const [rating, updRating] = useState<number | null>(null);
    const [partNumberCnt, updpartNumberCnt] = useState<number | null>(null);
    const [byManufacturer, updByManufacturer] = useState<any[] | null>(null);
    return (
        <div style={{ display: 'flex', flexDirection: 'column' }}>
            <div>
                <button
                    onClick={() => {
                        fetch(`${API_ENDPOINT}/product/by-manufacturer`, {
                            method: 'get',
                            credentials: 'include',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                        })
                            .then((res) => res.json())
                            .then((data) => updProductCount(data));
                    }}
                >
                    count-by-manufacturer
                </button>
                <div>
                    <div>подсчёт количества объектов по полю manufacturer</div>
                    <div
                        style={{
                            minHeight: '50px',
                            maxWidth: '50%',
                            backgroundColor: '#eee',
                            overflow: 'scroll',
                        }}
                    >
                        {productCount
                            ?.map((x) => {
                                return `${x.organization.name}(${x.count})`;
                            })
                            .join(', ')}
                    </div>
                </div>
            </div>

            <form
                onSubmit={(e: React.FormEvent<HTMLFormElement>) => {
                    e.preventDefault();
                    const target = e.target as any;
                    if (!target.rating.value.trim()) {
                        return;
                    }
                    fetch(
                        `${API_ENDPOINT}/product/by-rating?rating=${target.rating.value.trim()}`,
                        {
                            method: 'get',
                            credentials: 'include',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                        }
                    )
                        .then((res) => res.json())
                        .then((data) => updRating(data));
                }}
            >
                <input name="rating" placeholder="rating"></input>
                <button>count-by-rating</button>
                <div>{rating}</div>
            </form>

            <form
                onSubmit={(e: React.FormEvent<HTMLFormElement>) => {
                    e.preventDefault();
                    const target = e.target as any;
                    if (!target.partNumber.value.trim()) {
                        return;
                    }
                    fetch(
                        `${API_ENDPOINT}/product/by-partNumber?partNumber=${target.partNumber.value.trim()}`,
                        {
                            method: 'get',
                            credentials: 'include',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                        }
                    )
                        .then((res) => res.json())
                        .then((data) => updpartNumberCnt(data));
                }}
            >
                <input name="partNumber" placeholder="partNumber"></input>
                <button>count-by-partNumber</button>
                <div>{partNumberCnt}</div>
            </form>

            <form
                onSubmit={(e: React.FormEvent<HTMLFormElement>) => {
                    e.preventDefault();
                    const target = e.target as any;
                    if (!target.manufacturerId.value.trim()) {
                        return;
                    }
                    fetch(
                        `${API_ENDPOINT}/product/products-by-manufacturer?manufacturerId=${target.manufacturerId.value.trim()}`,
                        {
                            method: 'get',
                            credentials: 'include',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                        }
                    )
                        .then((res) => res.json())
                        .then((data) => updByManufacturer(data));
                }}
            >
                <input name="manufacturerId" placeholder="manufacturerId"></input>
                <button>get-by-manufacturer</button>
                <div>
                    <div>получить продукты по полю manufacturer</div>
                    <div
                        style={{
                            minHeight: '50px',
                            maxWidth: '50%',
                            backgroundColor: '#eee',
                            overflow: 'scroll',
                        }}
                    >
                        {byManufacturer
                            ?.map((x) => {
                                return `${x.name}(${x.coordinates.x}, ${x.coordinates.y})`;
                            })
                            .join(', ')}
                    </div>
                </div>
            </form>

            <form
                onSubmit={(e: React.FormEvent<HTMLFormElement>) => {
                    e.preventDefault();
                    const target = e.target as any;
                    if (!target.percent.value.trim()) {
                        return;
                    }
                    fetch(
                        `${API_ENDPOINT}/product/reduce-price?percent=${target.percent.value.trim()}`,
                        {
                            method: 'get',
                            credentials: 'include',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                        }
                    ).then((res) => res.json());
                }}
            >
                <input name="percent" placeholder="percent"></input>
                <button>reduce-by-percent</button>
            </form>
        </div>
    );
};
